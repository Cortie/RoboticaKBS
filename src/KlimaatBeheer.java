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

    // fetch lightsensor value
    private GetLights lampje = new GetLights();
    private int lightvalue;

    private JLabel jlTitel;
    private JButton jbProfielKnop;
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
            Double tempPi = Double.parseDouble(piTemp);
            Double humidPi = Double.parseDouble(piHumid);
            Double pressPi = Double.parseDouble(piPress);

            statement.setDouble(1, tempPi);
            statement.setDouble(2, humidPi);
            statement.setDouble(3, pressPi);
            statement.setInt(4, lightvalue);

            int i = statement.executeUpdate();
            System.out.println(i + " records inserted");

            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("geen verbinding met de database!");
        }

        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backbuttenPnl = new JPanel(new FlowLayout());
        backbuttenPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        backButton.addActionListener(this);

        JPanel titelTekstPnl = new JPanel(new BorderLayout());
        titelTekstPnl.add(jlTitel = new JLabel("Klimaatbeheer"),BorderLayout.NORTH);
        jlTitel.setFont(font1);

//        JPanel titelPnl = new JPanel(new BorderLayout());
//        titelPnl.add(backbuttenPnl,BorderLayout.WEST);
//        titelPnl.add(titelTekstPnl,BorderLayout.CENTER);
        //titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST),BorderLayout.WEST);
        //titelPnl.add(jlTitel = new JLabel("Klimaatbeheer"),BorderLayout.NORTH);

        JPanel profielKnopPnl = new JPanel(new FlowLayout());
        profielKnopPnl.add(jbProfielKnop = new JButton("Profielen aanpassen"));
        jbProfielKnop.addActionListener(this);

//        JPanel slidersGedeeltePnl = new JPanel(new GridLayout(4, 2));
//        slidersGedeeltePnl.add(jlTempAanpassen = new JLabel("Temperatuur aanpassen"));
//        slidersGedeeltePnl.add(jlEmpty = new JLabel(""));
//        slidersGedeeltePnl.add(jsTempWaarde = new JSlider());
//        tempWaarde = jsTempWaarde.getValue();
//        jsTempWaarde.addChangeListener(this);
//        slidersGedeeltePnl.add(jlTempWaarde = new JLabel(String.valueOf(tempWaarde) + " °C"));
//        slidersGedeeltePnl.add(jlLichtsterkteAanpassen = new JLabel("Lichtsterkte aanpassen"));
//        slidersGedeeltePnl.add(jlEmpty = new JLabel(""));
//        slidersGedeeltePnl.add(jsLichtsterkte = new JSlider(0, 500));
//        jsLichtsterkte.addChangeListener(this);
//        lichtsterkteWaarde = jsLichtsterkte.getValue();
//        slidersGedeeltePnl.add(jlLichtsterkteWaarde = new JLabel(String.valueOf(lichtsterkteWaarde) + " LM"));

//        JPanel profielenPnl = new JPanel(new GridLayout(2, 2));
//        profielenPnl.add(jlTempprofiel = new JLabel("Temperatuurprofiel: "));
//        profielenPnl.add(new JLabel(tempProfiel));
//        profielenPnl.add(jlLichtsterkteprofiel = new JLabel("Lichtsterkteprofiel: "));
//        profielenPnl.add(new JLabel(lichtsterkteProfiel));

//        JPanel ondersteGedeelteLinksPnl = new JPanel(new BorderLayout());
        //ondersteGedeelteLinksPnl.add(slidersGedeeltePnl, BorderLayout.CENTER);
        //ondersteGedeelteLinksPnl.add(profielenPnl, BorderLayout.NORTH);

        if (lightvalue >= 150 ) {
            lampStatus = "uit";
        }
        if (lightvalue < 150) {
            lampStatus = "aan";
        }

        JPanel sensorgegevenPnl = new JPanel(new GridLayout(5, 2));
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

    }

    public static void main(String[] args) {
        KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == jsTempWaarde) {
            jlTempWaarde.setText(String.valueOf(tempWaarde = jsTempWaarde.getValue()) + " °C");
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
