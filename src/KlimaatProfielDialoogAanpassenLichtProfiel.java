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
    private JLabel jlWaardeSlider;
    private int waardeSlider;
    private JSlider jsSlider;
    private String profielnaam;
    private Boolean errorCheck = false;
    private JLabel jlErrorMessage;

    public KlimaatProfielDialoogAanpassenLichtProfiel(JFrame frame, boolean modal) {
        super(frame, modal);
        setSize(800, 500);
        setTitle("Lichtsterkte profiel aanpassen");
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
            this.setIconImage(icon);


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement titlestmt = connection.prepareStatement("select light_strength_profile_name, profile_light_strength from light_strength_profile where light_strength_profile_name = ? ");
            titlestmt.setString(1, KlimaatProfiel.getProfilename());
            ResultSet getLightrs = titlestmt.executeQuery();
            getLightrs.next();

            profielnaam = getLightrs.getString(1);
            waardeSlider = getLightrs.getInt(2);



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
            if (!jtProfielNaam.getText().equals("") && jtProfielNaam.getText().length() <= 15) {
                profielnaam = jtProfielNaam.getText();
            } else {
                errorCheck = true;
            }

            if (!errorCheck) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";

                    Connection connection = DriverManager.getConnection(url, username, password);

                    PreparedStatement pstmt = connection.prepareStatement("Update light_strength_profile set light_strength_profile_name = ?, profile_light_strength = ? where light_strength_profile_name = ?");

                    pstmt.setString(1, profielnaam);
                    pstmt.setInt(2, waardeSlider);
                    pstmt.setString(3, KlimaatProfiel.getProfilename());

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
            jlWaardeSlider.setText(String.valueOf(waardeSlider = jsSlider.getValue()) + " LM");
        }
    }
}
