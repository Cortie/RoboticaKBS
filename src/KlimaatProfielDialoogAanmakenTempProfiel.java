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
import java.util.regex.Pattern;

public class KlimaatProfielDialoogAanmakenTempProfiel extends JDialog implements ActionListener,ChangeListener
{
    private JTextField jtProfielNaam;
    private JButton jbBevestigenKnop;
    private JTextField jtVan;
    private JTextField jtTot;
    private String waardeVan = "00:00";
    private String waardeTot = "23:59";
    private JLabel jlWaardeSlider;
    private JSlider jsSlider;

    private int waardeSlider;
    private String profielnaam;
    private LocalTime vanWaarde;
    private LocalTime totWaarde;
    private Boolean errorCheck = false;
    private JLabel jlErrorMessage;
    public KlimaatProfielDialoogAanmakenTempProfiel(JFrame frame, boolean modal){
                super(frame, modal);
                setSize(800, 500);
                setTitle("Temperatuur profiel aanmaken pop-up");

                JPanel profielNaamPnl = new JPanel(new FlowLayout());
                profielNaamPnl.add(new JLabel("Profiel naam"));
                profielNaamPnl.add(jtProfielNaam = new JTextField(5));

                JPanel bevestigenKnopPnl = new JPanel(new FlowLayout());
                bevestigenKnopPnl.add(jbBevestigenKnop = new JButton("Bevestigen"));
                jbBevestigenKnop.addActionListener(this);

                JPanel errorPanel = new JPanel(new FlowLayout());
                jlErrorMessage = new JLabel();
                errorPanel.add(jlErrorMessage);


                JPanel sliderPnl = new JPanel(new FlowLayout());
                sliderPnl.add(jtVan=new JTextField(waardeVan,5));
                sliderPnl.add(jtTot=new JTextField(waardeTot,5));
                sliderPnl.add(jsSlider=new JSlider());
                jsSlider.addChangeListener(this);
                sliderPnl.add(jlWaardeSlider = new JLabel(String.valueOf(waardeSlider=jsSlider.getValue())+" °C"));

                JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
                ondersteGedeeltePnl.add(sliderPnl,BorderLayout.NORTH);
                ondersteGedeeltePnl.add(bevestigenKnopPnl,BorderLayout.CENTER);


                JPanel borderPnl = new JPanel(new BorderLayout());
                borderPnl.add(profielNaamPnl, BorderLayout.NORTH);
                borderPnl.add(ondersteGedeeltePnl,BorderLayout.CENTER);
                borderPnl.add(errorPanel,BorderLayout.SOUTH);

                add(borderPnl);
                setVisible(true);
            }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbBevestigenKnop)
        {
            profielnaam=jtProfielNaam.getText();

            try {
                vanWaarde = LocalTime.parse(jtVan.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                totWaarde = LocalTime.parse(jtTot.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            }catch(DateTimeParseException dtEx){
                errorCheck = true;
            }

               if(!errorCheck) {
                   try {
                       Class.forName("com.mysql.cj.jdbc.Driver");

                       String url = "jdbc:mysql://localhost/domotica_database";
                       String username = "root", password = "";

                       Connection connection = DriverManager.getConnection(url, username, password);

                       PreparedStatement statement = connection.prepareStatement("Insert into temperature_profile (temp_profile_name, temp_start_time, temp_end_time, profile_temperature) Values (?,?,?,?);");

                       statement.setString(1, profielnaam);
                       statement.setTime(2, Time.valueOf(vanWaarde));
                       statement.setTime(3, Time.valueOf(totWaarde));
                       statement.setInt(4, waardeSlider);

                       int i = statement.executeUpdate();
                       System.out.println(i + " records inserted");

                       connection.close();

                   } catch (SQLException sqle) {
                       System.out.println(sqle.getMessage());
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


        if(errorCheck){
            jlErrorMessage.setText("je hebt ongeldige data ingevuld");
            SwingUtilities.updateComponentTreeUI(this);
            errorCheck = false;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource()==jsSlider)
        {
            jlWaardeSlider.setText(String.valueOf(waardeSlider=jsSlider.getValue())+" °C");
        }
    }
}
