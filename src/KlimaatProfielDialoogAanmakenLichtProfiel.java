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

public class KlimaatProfielDialoogAanmakenLichtProfiel extends JDialog implements ActionListener, ChangeListener
{
    private JTextField jtProfielNaam;
        private JButton jbBevestigenKnop;

        private JLabel jlWaardeSlider;
        private JSlider jsSlider;

        private int waardeSlider;
        private String profielnaam;
        private Boolean errorCheck = false;
        private JLabel jlErrorMessage;
        public KlimaatProfielDialoogAanmakenLichtProfiel(JFrame frame, boolean modal){
                    super(frame, modal);
                    setSize(800, 500);
                    setTitle("Lichtsterkte profiel aanmaken");
                    setLocationRelativeTo(null);
            Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
                this.setIconImage(icon);

                    JPanel profielNaamPnl = new JPanel(new FlowLayout());
                    profielNaamPnl.add(new JLabel("Profiel naam"));
                    profielNaamPnl.add(jtProfielNaam = new JTextField(5));

                    JPanel bevestigenKnopPnl = new JPanel(new FlowLayout());
                    bevestigenKnopPnl.add(jbBevestigenKnop = new JButton("Bevestigen"));
                    jbBevestigenKnop.addActionListener(this);

                    JPanel sliderPnl = new JPanel(new FlowLayout());
                    sliderPnl.add(jsSlider=new JSlider(0,500));
                    jsSlider.addChangeListener(this);
                    sliderPnl.add(jlWaardeSlider = new JLabel(String.valueOf(waardeSlider=jsSlider.getValue())+" LM"));

                    JPanel errorPanel = new JPanel(new FlowLayout());
                    jlErrorMessage = new JLabel();
                    errorPanel.add(jlErrorMessage);

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
            if (e.getSource()==jbBevestigenKnop) {
                if(!jtProfielNaam.getText().equals("") && jtProfielNaam.getText().length() <= 15) {
                    profielnaam = jtProfielNaam.getText();
                }   else{
                    errorCheck = true;
                }
                if (!errorCheck) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        String url = "jdbc:mysql://localhost/domotica_database";
                        String username = "root", password = "";

                        Connection connection = DriverManager.getConnection(url, username, password);

                        PreparedStatement statement = connection.prepareStatement("Insert into light_strength_profile (light_strength_profile_name,  profile_light_strength, account_id,is_selected) Values (?,?,?,0);");

                        statement.setString(1, profielnaam);
                        statement.setInt(2, waardeSlider);
                        statement.setInt(3,Inloggen.getAccountID());

                        int i = statement.executeUpdate();
                        System.out.println(i + " records inserted");

                        connection.close();

                    } catch (SQLException sqle) {

                        System.out.println(sqle.getMessage());
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
        public void stateChanged(ChangeEvent e)
        {
            if (e.getSource()==jsSlider)
            {
                jlWaardeSlider.setText(String.valueOf(waardeSlider=jsSlider.getValue())+" LM");
            }
        }
}
