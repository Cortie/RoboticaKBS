import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.*;

public class Account extends JFrame implements ActionListener {
    private String name;
    private String password;
    private JLabel jlCreateAccount;
    private JLabel jlUsername;
    private JLabel jlPassword;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JButton jbCreateAccount;
    private JLabel jlErrorMessage;

    private String gebruikersnaam = "";
    private String wachtwoord = "";

    private String hashdWachtwoord;
    private String checkHashdWachtwoord;
    private byte[] salt;
    private boolean errorCheck = false;
    private boolean usernameErrorCheck = false;

    public Account() {
        setTitle("Klimaat Systeem");
        setSize(800, 600);
        setLayout(new FlowLayout());
        jlCreateAccount = new JLabel("Nieuw account aanmaken");

        JPanel labelCreateAccountPanel = new JPanel(new FlowLayout());
        labelCreateAccountPanel.add(jlCreateAccount);

        jlUsername = new JLabel("Gebruikersnaam:");
        jtUsername = new JTextField(8);

        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.add(jlUsername);
        userPanel.add(jtUsername);

        jlPassword = new JLabel("Wachtwoord:");
        jpPassword = new JPasswordField(8);

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(jlPassword);
        passwordPanel.add(jpPassword);

        jbCreateAccount = new JButton("Account aanmaken");
        jbCreateAccount.addActionListener(this);
        JPanel jbCreateAccountPanel = new JPanel(new FlowLayout());
        jbCreateAccountPanel.add(jbCreateAccount);

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(labelCreateAccountPanel, BorderLayout.NORTH);
        borderPanel.add(userPanel, BorderLayout.CENTER);

        JPanel borderPanel2 = new JPanel(new BorderLayout());
        borderPanel2.add(passwordPanel, BorderLayout.NORTH);
        borderPanel2.add(jbCreateAccountPanel, BorderLayout.CENTER);

        jlErrorMessage = new JLabel("");
        borderPanel2.add(jlErrorMessage, BorderLayout.SOUTH);

        borderPanel.add(borderPanel2, BorderLayout.SOUTH);
        add(borderPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbCreateAccount) {
            gebruikersnaam = jtUsername.getText();
            wachtwoord = jpPassword.getText();


            //de gebruiker wordt pas toegevoegd als beide inlogvelden gevuld zijn.
            if (!gebruikersnaam.equals("") && !wachtwoord.equals("")) {
                System.out.println(gebruikersnaam);
                System.out.println(wachtwoord);

                //het wachtwoord wordt gehashed.
                try {
                    salt = SaltedHashPasword.getSalt();
                } catch (NoSuchAlgorithmException | NoSuchProviderException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
                hashdWachtwoord = SaltedHashPasword.getSecurePassword(wachtwoord, salt);

                //het vergelijken van de gebruikersnaam om te controleren of de gebruiker al bestaat.
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";

                    Connection connection = DriverManager.getConnection(url, username, password);

                    PreparedStatement userstmt = connection.prepareStatement("select username from account where username = ?");
                    userstmt.setString(1, gebruikersnaam);
                    ResultSet userrs = userstmt.executeQuery();
                    userrs.next();
                    String checkUsername = userrs.getString(1);

                    //als er al een gebruiker met deze gebruikersnaam in de database staat, wordt er een foutmelding weergegeven.
                    if (gebruikersnaam.equals(checkUsername)) {
                        usernameErrorCheck = true;
                    }

                    userrs.close();
                    connection.close();
                } catch (SQLException sqlEx) {
                    //als er geen gebruiker in de database is gevonden met de opgegeven gebruikersnaam, wordt er geen foutmelding weergegeven
                    //en gaat het systeem verder met het toevoegen van de gebruiker.
                    System.out.println("gebruikersnaam is nog niet bezet");
                    usernameErrorCheck = false;
                     System.out.println(sqlEx.getMessage());
                } catch (ClassNotFoundException cnfEx) {
                    System.out.println(cnfEx.getMessage());
                }
                //de accountsgegevens worden in de database gezet en er word gecontroleerd of het wachtwoord correct in de database staat,
                // mits de gebruikersnaam nog niet bestaat.
                if (!usernameErrorCheck) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        String url = "jdbc:mysql://localhost/domotica_database";
                        String username = "root", password = "";

                        Connection connection = DriverManager.getConnection(url, username, password);

                        PreparedStatement statement = connection.prepareStatement("Insert into account Values (?,?);");

                        statement.setString(1, gebruikersnaam);
                        statement.setString(2, hashdWachtwoord);

                        int i = statement.executeUpdate();
                        System.out.println(i + " records inserted");

                        //wachtwoord ophalen uit de database voor wachtwoordcheck
                        PreparedStatement stmt = connection.prepareStatement("select password from account where username = ?;");
                        stmt.setString(1, gebruikersnaam);
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        Blob pass = rs.getBlob(1);
                        checkHashdWachtwoord = new String(pass.getBytes(1L, (int) pass.length()));


                        rs.close();
                        connection.close();

                    } catch (SQLException sqle) {
                        System.out.println(sqle.getMessage());
                        System.out.println("problemen met SQL");

                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());

                    }
                }

                //als het wachtwoord correct in de database is gezet wordt de inlogpagina weergegeven.
                if (hashdWachtwoord.equals(checkHashdWachtwoord)) {
                    Inloggen inloggenscherm = new Inloggen();
                    this.dispose();
                }
            } else {
                //als een van de twee inlogvelden leeg is wordt er foutmelding weergegeven.
                errorCheck = true;

            }
        //geeft melding als een van de twee velden niet zijn ingevuld.
        if (errorCheck) {
            jlErrorMessage.setText("ongeldige inloggegevens!");
            SwingUtilities.updateComponentTreeUI(this);
            errorCheck = false;
        }
        //geeft melding als de gebruikersnaam al bestaat.
        if (usernameErrorCheck) {
            jlErrorMessage.setText("Gebruikersnaam bestaat al!");
            SwingUtilities.updateComponentTreeUI(this);
            usernameErrorCheck = false;
        }


    }

}

    public static void main(String[] args)
    {
        Account CreateAccountGUI = new Account();


    }

}
