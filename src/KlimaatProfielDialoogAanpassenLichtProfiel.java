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

public class KlimaatProfielDialoogAanpassenLichtProfiel extends JDialog implements ActionListener, ChangeListener {
    private JTextField jtProfielNaam;// haal waarde uit database
    private JButton jbBevestigenKnop;
    private JTextField jtVan;
    private JTextField jtTot;
    private String waardeVan;
    private String waardeTot;
    private JLabel jlWaardeSlider;
    private int waardeSlider;
    private JSlider jsSlider;
    private String profielnaam;
    private LocalTime vanWaarde;
    private LocalTime totWaarde;
    private Boolean errorCheck = false;
    private JLabel jlErrorMessage;

    public KlimaatProfielDialoogAanpassenLichtProfiel(JFrame frame, boolean modal) {
        super(frame, modal);
        setSize(800, 500);
        setTitle("Lichtsterkte profiel aanpassen pop-up");


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement titlestmt = connection.prepareStatement("select light_strength_profile_name, light_start_time, light_end_time, profile_light_strength from light_strength_profile where light_strength_profile_name = ? ");
            titlestmt.setString(1, KlimaatProfiel.getProfilename());
            ResultSet getLightrs = titlestmt.executeQuery();
            getLightrs.next();

            LocalTime WaardeVanTijd = LocalTime.parse(String.valueOf(getLightrs.getTime(2)));
            LocalTime WaardeTotTijd = LocalTime.parse(String.valueOf(getLightrs.getTime(3)));
            profielnaam = getLightrs.getString(1);
            waardeVan = String.valueOf(WaardeVanTijd.format(DateTimeFormatter.ofPattern("HH:mm")));
            waardeTot = String.valueOf(WaardeTotTijd.format(DateTimeFormatter.ofPattern("HH:mm")));
            waardeSlider = getLightrs.getInt(4);



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
        sliderPnl.add(jtVan = new JTextField(waardeVan,5));
        sliderPnl.add(jtTot = new JTextField(waardeTot,5));
        sliderPnl.add(jsSlider = new JSlider(0,500));
        jsSlider.setValue(waardeSlider);
        jsSlider.addChangeListener(this);
        sliderPnl.add(jlWaardeSlider = new JLabel(String.valueOf(waardeSlider = jsSlider.getValue()) + " LM"));

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
            if (!jtProfielNaam.getText().equals("")) {
                profielnaam = jtProfielNaam.getText();
            } else {
                errorCheck = true;
            }
            if (jtProfielNaam.getText().length() > 12) {
                errorCheck = true;

            }
            try {
                vanWaarde = LocalTime.parse(jtVan.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                totWaarde = LocalTime.parse(jtTot.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException dtEx) {
                errorCheck = true;
            }
            if (!errorCheck) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";

                    Connection connection = DriverManager.getConnection(url, username, password);

                    PreparedStatement pstmt = connection.prepareStatement("Update light_strength_profile set light_strength_profile_name = ?, light_start_time = ?, light_end_time = ?, profile_light_strength = ? where light_strength_profile_name = ?");

                    pstmt.setString(1, profielnaam);
                    pstmt.setTime(2, Time.valueOf(vanWaarde));
                    pstmt.setTime(3, Time.valueOf(totWaarde));
                    pstmt.setInt(4, waardeSlider);
                    pstmt.setString(5, KlimaatProfiel.getProfilename());

                    int i = pstmt.executeUpdate();
                    System.out.println(i + " records inserted");


                    pstmt.close();
                    connection.close();
                } catch (SQLException sqlEx) {
                    System.out.println(sqlEx.getMessage());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println(profielnaam);
                System.out.println(vanWaarde);
                System.out.println(totWaarde);
                System.out.println(waardeSlider);
                dispose();
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == jsSlider) {
            jlWaardeSlider.setText(String.valueOf(waardeSlider = jsSlider.getValue()) + " LM");
        }
    }
}
