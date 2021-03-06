import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.sql.*;

public class KlimaatBeheer extends JFrame implements ActionListener, ChangeListener {
    // definitions for getting the current temperature
    static PiListener sensor = new PiListener();
    static String piTemp = sensor.Temp.substring(13);
    static String piPress = sensor.Press;
    static String piHumid = sensor.Humid;

    private Double temp = Double.parseDouble(piTemp);
    private Double defaultTemp = 18.0;

    // fetch lightsensor value
    private GetLights lampje = new GetLights();
    private int defaultlight = 150;
    private int lightvalue;

    private JLabel jlTitel;
    private JButton jbProfielKnop;
    private JButton jbRefresh;
    private JLabel jlTempAanpassen;
    private JLabel jlLichtsterkteAanpassen;
    private JSlider jsTempWaarde;
    private JSlider jsLichtsterkte;
    private JLabel jlEmpty;
    private JLabel jlTempWaarde;
    private JLabel jlLuchtdrukWaarde;
    private JLabel jlLuchtvochtigheidWaarde;
    private JLabel jlLichtsterkteWaarde;
    private int tempWaarde;
    private int lichtsterkteWaarde;
    private BasicArrowButton backButton;
    private JLabel jlTempprofiel;
    private String tempProfiel = "zomer";
    private JLabel jlLichtsterkteprofiel;
    private String lichtsterkteProfiel = "zomer";
    private JLabel jltempSensor;
    private int tempSensor = 19;
    private JLabel jlLuchtdrukSensor;
    private int luchtdrukSensor = 93;
    private JLabel jlLuchtvochtigheidSensor;
    private int luchtvochtigheidSensor = 90;
    private JLabel jlLichtsterkteSensor;
    private JLabel jlLichtsterkteSensorWaarde;
    private String lampStatus;
    private JLabel jlLampStatus;
    private JLabel jlLampStatusWaarde;
    private JLabel jVerwarmStatus;
    private JLabel jVerwarmStatusWaarde;
    private String verwarmStatus;
    public Scanner data;

    public KlimaatBeheer() {
        lightvalue = lampje.lichtwaarde;
        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("Insert into climate_log Values (?,?,?,?);");
            try {
                Double tempPi = Double.parseDouble(piTemp);
                Double humidPi = Double.parseDouble(piHumid);
                Double pressPi = Double.parseDouble(piPress);

                statement.setDouble(1, tempPi);
                statement.setDouble(2, humidPi);
                statement.setDouble(3, pressPi);
                statement.setInt(4, lightvalue);

                int i = statement.executeUpdate();
                System.out.println(i + " records inserted");
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
            this.setIconImage(icon);

        JPanel backbuttenPnl = new JPanel(new FlowLayout());
        backbuttenPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        backButton.addActionListener(this);

        JPanel titelTekstPnl = new JPanel(new BorderLayout());
        titelTekstPnl.add(jlTitel = new JLabel("Klimaatbeheer"),BorderLayout.NORTH);
        jlTitel.setFont(font1);

        JPanel profielKnopPnl = new JPanel(new FlowLayout());
        profielKnopPnl.add(jbProfielKnop = new JButton("Profielen aanpassen"));
        profielKnopPnl.add(jbRefresh = new JButton("Refresh"));
        jbProfielKnop.addActionListener(this);
        jbRefresh.addActionListener(this);

        if (lightvalue >= PersoonlijkeInstellingen.lightSetting ) {
            lampStatus = "uit";
        }
        if (lightvalue < PersoonlijkeInstellingen.lightSetting) {
            lampStatus = "aan";
        }

        if(temp >= PersoonlijkeInstellingen.tempSetting){
            verwarmStatus = "uit";
        }
        if(temp < PersoonlijkeInstellingen.tempSetting){
            verwarmStatus = "aan";
        } 

        JPanel sensorgegevenPnl = new JPanel(new GridLayout(6, 2));
        sensorgegevenPnl.add(jltempSensor = new JLabel("Temperatuur: "));
        jltempSensor.setFont(font1);
        sensorgegevenPnl.add(jlTempWaarde=new JLabel(piTemp + " °C"));
        jlTempWaarde.setFont(font1);
        sensorgegevenPnl.add(jlLuchtdrukSensor = new JLabel("Luchtdruk: "));
        jlLuchtdrukSensor.setFont(font1);
        sensorgegevenPnl.add(jlLuchtdrukWaarde = new JLabel(piPress + " hPa"));
        jlLuchtdrukWaarde.setFont(font1);
        sensorgegevenPnl.add(jlLuchtvochtigheidSensor = new JLabel("Luchtvochtigheid: "));
        jlLuchtvochtigheidSensor.setFont(font1);
        sensorgegevenPnl.add(jlLuchtvochtigheidWaarde=new JLabel(piHumid + "%"));
        jlLuchtvochtigheidWaarde.setFont(font1);
        sensorgegevenPnl.add(jlLichtsterkteSensor = new JLabel("Lichtsterkte: "));
        jlLichtsterkteSensor.setFont(font1);
        sensorgegevenPnl.add(jlLichtsterkteSensorWaarde = new JLabel(lightvalue + " LM"));
        jlLichtsterkteSensorWaarde.setFont(font1);
        sensorgegevenPnl.add(jlLampStatus = new JLabel("Lampstatus: "));
        jlLampStatus.setFont(font1);
        sensorgegevenPnl.add(jlLampStatusWaarde = new JLabel(lampStatus));
        jlLampStatusWaarde.setFont(font1);
        sensorgegevenPnl.add(jVerwarmStatus = new JLabel("Verwarming status: "));
        jVerwarmStatus.setFont(font1);
        sensorgegevenPnl.add(jVerwarmStatusWaarde = new JLabel(verwarmStatus));
        jVerwarmStatusWaarde.setFont(font1);

        JPanel ondersteGedeelteRechtsPnl = new JPanel(new BorderLayout());
        ondersteGedeelteRechtsPnl.add(profielKnopPnl, BorderLayout.NORTH);
        ondersteGedeelteRechtsPnl.add(sensorgegevenPnl, BorderLayout.CENTER);

        JPanel onderstegedeeltePnl = new JPanel(new BorderLayout());
       // ondersteGedeeltePnl.add(ondersteGedeelteLinksPnl, BorderLayout.WEST);
        onderstegedeeltePnl.add(ondersteGedeelteRechtsPnl, BorderLayout.CENTER);

        JPanel rechterkantPnl = new JPanel(new BorderLayout());
        rechterkantPnl.add(titelTekstPnl,BorderLayout.NORTH);
        rechterkantPnl.add(onderstegedeeltePnl,BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(backbuttenPnl, BorderLayout.WEST);
        borderPnl.add(rechterkantPnl, BorderLayout.CENTER);

        add(borderPnl);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbProfielKnop) {
            //System.out.println("link naar profielen aanpassen");
            KlimaatProfiel klimaatProfiel = new KlimaatProfiel();
            this.dispose();
        }
        if (e.getSource() == backButton) {
            Dashboard dash = new Dashboard();
            this.dispose();
        }
        if(e.getSource() == jbRefresh) {
            KlimaatBeheer beheer = new KlimaatBeheer();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == jsTempWaarde) {
            jlTempWaarde.setText(String.valueOf(tempWaarde = jsTempWaarde.getValue()) + " °C");
            
            if(temp >= jsTempWaarde.getValue()){
                verwarmStatus = "uit";
                jVerwarmStatusWaarde.setText(verwarmStatus);
            }
            if(temp< jsTempWaarde.getValue()){
                verwarmStatus = "aan";
                jVerwarmStatusWaarde.setText(verwarmStatus);
            }
        }
        if (e.getSource() == jsLichtsterkte) {
            jlLichtsterkteWaarde.setText(String.valueOf(lichtsterkteWaarde = jsLichtsterkte.getValue()) + " LM");
            lightvalue = Integer.parseInt(data.nextLine());
            jlLichtsterkteSensorWaarde.setText(lightvalue + " LM");
            if (lightvalue >= jsLichtsterkte.getValue()) {
                lampStatus = "aan";
                jlLampStatusWaarde.setText(lampStatus);
            }
            if (lightvalue < jsLichtsterkte.getValue()) {
                lampStatus = "uit";
                jlLampStatusWaarde.setText(lampStatus);
            }

        }

    }


}
