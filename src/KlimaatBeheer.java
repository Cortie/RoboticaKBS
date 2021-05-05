import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class KlimaatBeheer extends JFrame implements ActionListener, ChangeListener
{
    private JLabel jlTitel;
    private JButton jbProfielKnop;
    private JLabel jlTempAanpassen;
    private JLabel jlLichtsterkteAanpassen;
    private JSlider jsTempWaarde;
    private JSlider jsLichtsterkte;
    private JLabel jlEmpty;
    private JLabel jlTempWaarde;
    private JLabel jlLichtsterkteWaarde;
    private int tempWaarde;
    private int lichtsterkteWaarde;
    private BasicArrowButton backButton;
    private JLabel jlTempprofiel;
    private String tempProfiel = "zomer";
    private JLabel jlLichtsterkteprofiel;
    private String lichtsterkteProfiel="zomer";
    private JLabel jltempSensor;
    private int tempSensor = 19;
    private JLabel jlLuchtdrukSensor;
    private int luchtdrukSensor= 93;
    private JLabel jlLuchtvochtigheidSensor;
    private int luchtvochtigheidSensor= 90;
    private JLabel jlLichtsterkteSensor;
    private JLabel jlLichtsterkteSensorWaarde;
    public SerialPort port;
    public Scanner data;
    private int lichtwaarde;
    //private int lichtsterkteSensor=70;






    public KlimaatBeheer()
    {
        SerialPort[] availablePorts = SerialPort.getCommPorts();
        for (SerialPort comPort : availablePorts) {
            if(comPort.getDescriptivePortName().length() > 10) {
                  String naam = comPort.getDescriptivePortName().substring(0, 11);
                  if (naam.equalsIgnoreCase("Arduino Uno")) {
                   this.port = comPort;
                }
            }

        }

        if (port.openPort()) {
            System.out.println("Successfully opened the port.");
        } else {
            System.out.println("Unable to open the port.");
            return;
        }
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        data = new Scanner(port.getInputStream());
        if(data.hasNextLine()) {

            lichtwaarde = 0;

            try {

                lichtwaarde = Integer.parseInt(data.nextLine());

            } catch (Exception e) {
                System.out.println("lichtwaarde kon niet worden uitgelezen!");
                lichtwaarde = 0;
            }
            System.out.println(lichtwaarde);
        }

        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        backButton.addActionListener(this);
        titelPnl.add(jlTitel = new JLabel("Klimaatbeheer"));

        JPanel profielKnopPnl = new JPanel(new FlowLayout());
        profielKnopPnl.add(jbProfielKnop = new JButton("Profielen aanpassen"));
        jbProfielKnop.addActionListener(this);

        JPanel slidersGedeeltePnl = new JPanel(new GridLayout(4,2));
        slidersGedeeltePnl.add(jlTempAanpassen = new JLabel("Temperatuur aanpassen"));
        slidersGedeeltePnl.add(jlEmpty= new JLabel(""));
        slidersGedeeltePnl.add(jsTempWaarde= new JSlider());
        tempWaarde = jsTempWaarde.getValue();
        jsTempWaarde.addChangeListener(this);
        slidersGedeeltePnl.add(jlTempWaarde= new JLabel(String.valueOf(tempWaarde)+" °C"));
        slidersGedeeltePnl.add(jlLichtsterkteAanpassen = new JLabel("Lichtsterkte aanpassen"));
        slidersGedeeltePnl.add(jlEmpty= new JLabel(""));
        slidersGedeeltePnl.add(jsLichtsterkte = new JSlider());
        jsLichtsterkte.addChangeListener(this);
        lichtsterkteWaarde= jsLichtsterkte.getValue();
        slidersGedeeltePnl.add(jlLichtsterkteWaarde= new JLabel(50+" LM"));

        JPanel profielenPnl = new JPanel(new GridLayout(2,2));
        profielenPnl.add(jlTempprofiel=new JLabel("Temperatuurprofiel: "));
        profielenPnl.add(new JLabel(tempProfiel));
        profielenPnl.add(jlLichtsterkteprofiel=new JLabel("Lichtsterkteprofiel: "));
        profielenPnl.add(new JLabel(lichtsterkteProfiel));

        JPanel ondersteGedeelteLinksPnl = new JPanel(new BorderLayout());
        ondersteGedeelteLinksPnl.add(slidersGedeeltePnl,BorderLayout.CENTER);
        ondersteGedeelteLinksPnl.add(profielenPnl,BorderLayout.NORTH);

        JPanel sensorgegevenPnl = new JPanel(new GridLayout(4,2));
        sensorgegevenPnl.add(jltempSensor = new JLabel("Temperatuur: "));
        sensorgegevenPnl.add(new JLabel(tempSensor+" °C"));
        sensorgegevenPnl.add(jlLuchtdrukSensor = new JLabel("Luchtdruk: "));
        sensorgegevenPnl.add(new JLabel(luchtdrukSensor+" hPa"));
        sensorgegevenPnl.add(jlLuchtvochtigheidSensor = new JLabel("Luchtvochtigheid: "));
        sensorgegevenPnl.add(new JLabel(luchtvochtigheidSensor+"%"));
        sensorgegevenPnl.add(jlLichtsterkteSensor = new JLabel("Lichtsterkte: "));
        sensorgegevenPnl.add(jlLichtsterkteSensorWaarde = new JLabel(lichtwaarde+" LM"));

        JPanel ondersteGedeelteRechtsPnl = new JPanel(new BorderLayout());
        ondersteGedeelteRechtsPnl.add(profielKnopPnl,BorderLayout.CENTER);
        ondersteGedeelteRechtsPnl.add(sensorgegevenPnl,BorderLayout.NORTH);

        JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
        ondersteGedeeltePnl.add(ondersteGedeelteLinksPnl,BorderLayout.WEST);
        ondersteGedeeltePnl.add(ondersteGedeelteRechtsPnl,BorderLayout.EAST);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelPnl, BorderLayout.NORTH);
        borderPnl.add(ondersteGedeeltePnl,BorderLayout.CENTER);

        add(borderPnl);
        setVisible(true);




    }





    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbProfielKnop)
        {
            System.out.println("link naar profielen aanpassen");
            KlimaatProfiel klimaatProfiel = new KlimaatProfiel();
            port.closePort();
            this.dispose();
        }
        if (e.getSource()==backButton)
        {
            Dashboard dash = new Dashboard();
            port.closePort();
            this.dispose();
        }

    }

    public static void main(String[] args)
    {
        KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();



    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource()==jsTempWaarde)
        {
            jlTempWaarde.setText(String.valueOf(tempWaarde=jsTempWaarde.getValue())+" °C");
        }
        if (e.getSource()==jsLichtsterkte)
        {
            jlLichtsterkteWaarde.setText(String.valueOf(lichtsterkteWaarde=jsLichtsterkte.getValue())+" LM");
        }

    }


}
