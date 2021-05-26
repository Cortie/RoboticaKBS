import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.format.DateTimeFormatter.ofPattern;

public class KlimaatProfielDialoogAanpassenTempProfiel extends JDialog implements ActionListener, ChangeListener {
    private JTextField jtProfielNaam;
    private JButton jbBevestigenKnop;
    private JLabel jlWaardeSlider;
    private int waardeSlider;
    private JSlider jsSlider;
    private String profielnaam;
    private Boolean errorCheck = false;
    private JLabel jlErrorMessage;



    public KlimaatProfielDialoogAanpassenTempProfiel(JFrame frame, boolean modal) {
        super(frame, modal);
        setSize(800, 500);
        setTitle("Temperatuur profiel aanpassen");
        setLocationRelativeTo(null);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement titlestmt = connection.prepareStatement("select temp_profile_name,  profile_temperature from temperature_profile where temp_profile_name = ? ");
            titlestmt.setString(1, KlimaatProfiel.getProfilename());
            ResultSet getTemprs = titlestmt.executeQuery();
            getTemprs.next();

            profielnaam = getTemprs.getString(1);
            waardeSlider = getTemprs.getInt(2);

        }catch(SQLException sqlEx){
            System.out.println(sqlEx.getMessage());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        JPanel profielNaamPnl = new JPanel(new FlowLayout());
        profielNaamPnl.add(new JLabel("Profiel naam"));
        profielNaamPnl.add(jtProfielNaam = new JTextField(profielnaam,5));

        JPanel bevestigenKnopPnl = new JPanel(new FlowLayout());
        bevestigenKnopPnl.add(jbBevestigenKnop = new JButton("Bevestigen"));
        jbBevestigenKnop.addActionListener(this);

        JPanel sliderPnl = new JPanel(new FlowLayout());
        sliderPnl.add(jsSlider = new JSlider());
        jsSlider.setValue(waardeSlider);
        jsSlider.addChangeListener(this);
        sliderPnl.add(jlWaardeSlider = new JLabel(String.valueOf(waardeSlider = jsSlider.getValue()) + " °C"));

        JPanel errorPanel = new JPanel(new FlowLayout());
        jlErrorMessage = new JLabel();
        errorPanel.add(jlErrorMessage);

        JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
        ondersteGedeeltePnl.add(sliderPnl, BorderLayout.NORTH);
        ondersteGedeeltePnl.add(bevestigenKnopPnl, BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(profielNaamPnl, BorderLayout.NORTH);
        borderPnl.add(ondersteGedeeltePnl, BorderLayout.CENTER);
        borderPnl.add(errorPanel,BorderLayout.SOUTH);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbBevestigenKnop) {

            if(!jtProfielNaam.getText().equals("")&& jtProfielNaam.getText().length() <= 15) {
                profielnaam = jtProfielNaam.getText();
            }   else{
                errorCheck = true;
            }


            if(!errorCheck){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");

                String url = "jdbc:mysql://localhost/domotica_database";
                String username = "root", password = "";

                Connection connection = DriverManager.getConnection(url, username, password);

                PreparedStatement pstmt = connection.prepareStatement("Update temperature_profile set temp_profile_name = ?, profile_temperature = ? where temp_profile_name = ?");

                pstmt.setString(1, profielnaam);
                pstmt.setInt(2, waardeSlider);
                pstmt.setString(3,KlimaatProfiel.getProfilename());

                int i = pstmt.executeUpdate();
                System.out.println(i + " records inserted");


                pstmt.close();
                connection.close();
            }catch(SQLException sqlEx){
                System.out.println(sqlEx.getMessage());
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }


            System.out.println(profielnaam);
            System.out.println(waardeSlider);

            dispose();
        }
        }
        if(errorCheck){
            jlErrorMessage.setText("je hebt ongeldige data ingevuld");
            if(jtProfielNaam.getText().length() >15){
                jlErrorMessage.setText("je profielnaam mag niet langer zijn dan 15 tekens!");
            }
            SwingUtilities.updateComponentTreeUI(this);
            errorCheck = false;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == jsSlider) {
            jlWaardeSlider.setText(String.valueOf(waardeSlider = jsSlider.getValue()) + " °C");
        }
    }
}
