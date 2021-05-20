import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.*;

public class WachtwoordWijzigen extends JFrame implements ActionListener {
    private JLabel jlTitel;
    private JLabel jlGebruikersnaam;
    private JLabel jlWachtwoord;
    private JLabel jlWachtwoordBevestigen;
    private JTextField jtGebruikersnaam;
    private JPasswordField jpWachtwoord;
    private JPasswordField jpWachtwoordBevestigen;
    private JButton jbWachtwoordWijzigen;

    private String gebruikersnaam = "";
    private String checkGebruikersnaam;
    private String wachtwoord = "";
    private String bevestigWachtwoord = "";

    private byte[] salt;
    private String hashdWachtwoord;
    private boolean errorCheck = false;
    private JLabel jlErrorMessage;

    public WachtwoordWijzigen() {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(jlTitel = new JLabel("Wachtwoord wijzigen"));

        JPanel gebruikersnaamPnl = new JPanel(new FlowLayout());
        gebruikersnaamPnl.add(jlGebruikersnaam = new JLabel("Gebruikersnaam"));
        gebruikersnaamPnl.add(jtGebruikersnaam = new JTextField(8));

        JPanel wachtwoordPnl = new JPanel(new FlowLayout());
        wachtwoordPnl.add(jlWachtwoord = new JLabel("Wachtwoord"));
        wachtwoordPnl.add(jpWachtwoord = new JPasswordField(8));

        JPanel wachtwoordBevestigenPnl = new JPanel(new FlowLayout());
        wachtwoordBevestigenPnl.add(jlWachtwoordBevestigen = new JLabel("Wachtwoord bevestigen"));
        wachtwoordBevestigenPnl.add(jpWachtwoordBevestigen = new JPasswordField(8));

        JPanel wachtwoordWijzigenKnopPnl = new JPanel(new FlowLayout());
        wachtwoordWijzigenKnopPnl.add(jbWachtwoordWijzigen = new JButton("Wachtwoord wijzigen"));
        jbWachtwoordWijzigen.addActionListener(this);

        JPanel wachtwoordCheckPnl = new JPanel(new BorderLayout());
        wachtwoordCheckPnl.add(wachtwoordBevestigenPnl, BorderLayout.NORTH);
        wachtwoordCheckPnl.add(wachtwoordWijzigenKnopPnl, BorderLayout.CENTER);

        JPanel gebruikergegevensPnl = new JPanel(new BorderLayout());
        gebruikergegevensPnl.add(gebruikersnaamPnl, BorderLayout.NORTH);
        gebruikergegevensPnl.add(wachtwoordPnl, BorderLayout.CENTER);

        JPanel gegevensPnl = new JPanel(new BorderLayout());
        gegevensPnl.add(gebruikergegevensPnl, BorderLayout.NORTH);
        gegevensPnl.add(wachtwoordCheckPnl, BorderLayout.CENTER);

        JPanel onderstegedeelte = new JPanel(new BorderLayout());
        jlErrorMessage = new JLabel("");
        onderstegedeelte.add(jlErrorMessage, BorderLayout.SOUTH);
        onderstegedeelte.add(gegevensPnl, BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelPnl, BorderLayout.NORTH);
        borderPnl.add(onderstegedeelte, BorderLayout.CENTER);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbWachtwoordWijzigen) {
            // System.out.println("link naar inloggen");
            gebruikersnaam = jtGebruikersnaam.getText();
            wachtwoord = jpWachtwoord.getText();
            bevestigWachtwoord = jpWachtwoordBevestigen.getText();

            // checkGebruikersnaam=gebruikersnaam;//verander checkGB naar die uit database

            if (!gebruikersnaam.equals("") && !wachtwoord.equals("") && !bevestigWachtwoord.equals("")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";

                    Connection connection = DriverManager.getConnection(url, username, password);

                    PreparedStatement userstmt = connection
                            .prepareStatement("select username from account where username = ?");
                    userstmt.setString(1, gebruikersnaam);
                    ResultSet userrs = userstmt.executeQuery();
                    userrs.next();
                    String checkUsername = userrs.getString(1);

                    if (gebruikersnaam.equals(checkUsername)) {
                        if (wachtwoord.equals(bevestigWachtwoord)) {
                            try {
                                salt = SaltedHashPasword.getSalt();
                            } catch (NoSuchAlgorithmException | NoSuchProviderException noSuchAlgorithmException) {
                                noSuchAlgorithmException.printStackTrace();
                            }
                            hashdWachtwoord = SaltedHashPasword.getSecurePassword(wachtwoord, salt);
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                // String url = "jdbc:mysql://localhost/domotica_database";
                                // String username = "root", password = "";
                                // Connection connection = DriverManager.getConnection(url, username, password);
                                PreparedStatement statement = connection.prepareStatement(
                                        "UPDATE account SET password = ?, salt = ? WHERE username = ?;");
                                statement.setString(3, gebruikersnaam);
                                statement.setString(1, hashdWachtwoord);
                                statement.setBytes(2, salt);
                                int i = statement.executeUpdate();
                                System.out.println(i + " records inserted");
                                connection.close();

                            } catch (SQLException sqle) {
                                System.out.println(sqle.getMessage());
                                System.out.println("problemen met SQL");

                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());

                            }

                            Inloggen inloggenscherm = new Inloggen();
                            this.dispose();
                        } else {
                            System.out.println("wachtwoorden komen niet overeen");

                        }
                    }

                    userrs.close();
                    connection.close();
                } catch (SQLException sqlEx) {
                    // als er geen gebruiker in de database is gevonden met de opgegeven
                    // gebruikersnaam, wordt er geen foutmelding weergegeven
                    System.out.println("gebruikersnaam bestaat niet");
                    // System.out.println(hashdWachtwoord);
                } catch (ClassNotFoundException cnfEx) {
                    System.out.println(cnfEx.getMessage());
                }

            } else {
                // als een van de drie inlogvelden leeg is wordt er foutmelding weergegeven.
                errorCheck = true;
            }
            if (errorCheck) {
                jlErrorMessage.setText("ongeldige inloggegevens!");
                SwingUtilities.updateComponentTreeUI(this);
                errorCheck = false;
            }

        }
    }

    public static void main(String[] args) {
        WachtwoordWijzigen wachtwoordWijzigenscherm = new WachtwoordWijzigen();
    }
}
