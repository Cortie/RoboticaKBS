import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.Clock;

import static java.lang.Math.abs;
public class Music implements Runnable
{
    public Music(MuziekAfspeler afspeler)
    {
        this.afspeler = afspeler;
    }
    private final MuziekAfspeler afspeler;
    public int currentSong;
    private int divider = 0;
    private int noteDuration = 500;
    private int tone;
    public SerialPort port;
    public int songLength;
    @Override
    public void run()
    {
        SerialPort[] availablePorts = SerialPort.getCommPorts();
        for (SerialPort comPort : availablePorts) {
            if (comPort.getDescriptivePortName().length() > 10) {
                String naam = comPort.getDescriptivePortName().substring(0, 11);
                if (naam.equalsIgnoreCase("Arduino Uno")) {
                    this.port = comPort;
                }
            
                if (this.port == null && naam.equalsIgnoreCase("USB Serial ")) {
                    this.port = comPort;
                }
            }
        }
        if(port.openPort())
        {
            System.out.println("port open");
        }
        else
        {
            System.out.println("Something wrong with Arduino connection");
        }
        port.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }
        
            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }
                byte[] newData = new byte[port.bytesAvailable()];
                int numRead = port.readBytes(newData, newData.length);
                String test = new String(newData);
                //System.out.println(test);
                //System.out.println("Read " + numRead + " bytes. " + newData[0]);
            }
        });
        String url = "jdbc:mysql://localhost/domotica_database";
        String username = "root", password = "";
    
        Connection connection;
        try
        {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement userstmt = connection.prepareStatement("select MAX(position) from song_note where song_id =" + currentSong );
            ResultSet length = userstmt.executeQuery();
            length.next();
            songLength = length.getInt(1);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        while (afspeler.isPlay())
        {
            try
            {
                Thread.sleep(noteDuration);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            getMusic();
            int wholeNote = (60000 * 4) / 150;
            if (divider > 0) {
                // regular note, just proceed
                noteDuration = (wholeNote) / divider;
            }
            else if (divider < 0)
            {
                // dotted notes are represented with negative durations!!
                noteDuration = (wholeNote) / abs(divider);
                noteDuration *= 1.5; // increases the duration in half for dotted notes
            }
            System.out.println(tone + " | " + noteDuration);
            sendMusic(tone, noteDuration);
            afspeler.setThisNote(afspeler.getThisNote() + 1);
            afspeler.currentNote.setText(songLength - afspeler.getThisNote() + " resterende noten");
            if(afspeler.getThisNote() == songLength)
            {
                if(currentSong == afspeler.finalSong)
                {
                    afspeler.setSong(1);
                }
                else{
                    afspeler.nextSong();
                }
            }
            afspeler.jsTijd.setValue(afspeler.getThisNote());
            afspeler.jsTijd.setMaximum(songLength);
        }
    }
    public void getMusic(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
            
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement userstmt = connection.prepareStatement("select duration, note from song_note where song_id =" + currentSong +" AND position =" + afspeler.getThisNote());
            ResultSet duration = userstmt.executeQuery();
            duration.next();
            divider = duration.getInt(1);
            String note = duration.getString(2);
            duration.close();
            PreparedStatement userstmt2 = connection.prepareStatement("select tone from notes where note ='" + note + "'");
            ResultSet noteTone = userstmt2.executeQuery();
            noteTone.next();
            tone = noteTone.getInt(1);
            noteTone.close();
            connection.close();
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void sendMusic(int tone, int noteDuration)
    {
        String info ="<"  + tone + ", " + noteDuration + ">";
        PrintWriter out = new PrintWriter(port.getOutputStream(), true);
        out.println(info);
    }
}
