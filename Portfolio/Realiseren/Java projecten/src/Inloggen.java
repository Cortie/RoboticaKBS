import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Inloggen extends JFrame implements ActionListener {
    private JLabel jlTitel;
    private JLabel jlGebruikersnaam;
    private JLabel jlWachtwoord;
    private JTextField jtGebruikersnaam;
    private JPasswordField jpWachtwoord;
    private JButton jbInloggen;
    private JButton jbNieuwAccount;


    private String gebruikersnaam = "";
    private String wachtwoord = "";

    private String hashdWachtwoord;
    private String checkHashedPassword;

    private static int accountID = 0;
    private static String accountname = "";
    private Boolean errorCheck = false;
    private JLabel jlErrorMessage;

    public Inloggen() {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(jlTitel = new JLabel("Inloggen Klimaat systeem"));
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
            this.setIconImage(icon);

        JPanel gebruikersnaamPnl = new JPanel(new FlowLayout());
        gebruikersnaamPnl.add(jlGebruikersnaam = new JLabel("Gebruikersnaam"));
        gebruikersnaamPnl.add(jtGebruikersnaam = new JTextField(8));

        JPanel wachtwoordPnl = new JPanel(new FlowLayout());
        wachtwoordPnl.add(jlWachtwoord = new JLabel("Wachtwoord"));
        wachtwoordPnl.add(jpWachtwoord = new JPasswordField(8));

        JPanel inlogknopPnl = new JPanel(new FlowLayout());
        inlogknopPnl.add(jbInloggen = new JButton("Inloggen"));
        jbInloggen.addActionListener(this);

        JPanel newAccountknopPnl = new JPanel(new FlowLayout());
        newAccountknopPnl.add(jbNieuwAccount = new JButton("Nieuw account aanmaken"));
        jbNieuwAccount.addActionListener(this);

        JPanel accountknoppenPnl = new JPanel(new BorderLayout());
        accountknoppenPnl.add(inlogknopPnl, BorderLayout.NORTH);
        accountknoppenPnl.add(newAccountknopPnl, BorderLayout.CENTER);


        JPanel knoppenPnl = new JPanel(new BorderLayout());
        knoppenPnl.add(accountknoppenPnl, BorderLayout.NORTH);


        JPanel gebruikergegevensPnl = new JPanel(new GridLayout(2, 2));
        gebruikergegevensPnl.add(gebruikersnaamPnl, BorderLayout.NORTH);
        gebruikergegevensPnl.add(wachtwoordPnl, BorderLayout.CENTER);

        JPanel errorPanel = new JPanel(new FlowLayout());
        jlErrorMessage = new JLabel();
        errorPanel.add(jlErrorMessage);

        JPanel inlogPnl = new JPanel(new BorderLayout());
        inlogPnl.add(gebruikergegevensPnl, BorderLayout.NORTH);
        inlogPnl.add(knoppenPnl, BorderLayout.CENTER);
        inlogPnl.add(errorPanel,BorderLayout.SOUTH);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelPnl, BorderLayout.NORTH);
        borderPnl.add(inlogPnl, BorderLayout.CENTER);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbInloggen) {

            gebruikersnaam = jtGebruikersnaam.getText();
            wachtwoord = jpWachtwoord.getText();
            System.out.println(gebruikersnaam);
            System.out.println(wachtwoord);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                String url = "jdbc:mysql://localhost/domotica_database";
                String username = "root", password = "";

                Connection connection = DriverManager.getConnection(url, username, password);

                PreparedStatement userstmt = connection.prepareStatement("select account_id,username,password,salt from account where username = ?");
                userstmt.setString(1, gebruikersnaam);
                ResultSet inlogrs = userstmt.executeQuery();
                inlogrs.next();
                int accountIdNummer = inlogrs.getInt(1);
                String checkUsername = inlogrs.getString(2);
                Blob pass = inlogrs.getBlob(3);
                byte[] passwordSalt = inlogrs.getBytes(4);
                checkHashedPassword = new String(pass.getBytes(1L, (int) pass.length()));
                System.out.println(checkHashedPassword);
                System.out.println(checkUsername);

                hashdWachtwoord = SaltedHashPasword.getSecurePassword(wachtwoord, passwordSalt);

                if (hashdWachtwoord.equals(checkHashedPassword) && gebruikersnaam.equals(checkUsername)) {

                    accountname = gebruikersnaam;
                    accountID = accountIdNummer;
                    Dashboard dash = new Dashboard();
                    this.dispose();
                }
                if (!hashdWachtwoord.equals(checkHashedPassword) || !gebruikersnaam.equals(checkUsername)) {
                    System.out.println("onjuiste gegevens");
                }

                inlogrs.close();
                connection.close();
            } catch (SQLException sqle) {
                errorCheck = true;
                System.out.println(sqle);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }



        }
        if (e.getSource() == jbNieuwAccount) {
            System.out.println("link naar account");
            Account CreateAccountGUI = new Account();
            this.dispose();
        }

        if(errorCheck){

            if(jtGebruikersnaam.getText().equals("") || jpWachtwoord.getText().equals("")){
                jlErrorMessage.setText("een of meerdere velden zijn niet ingevuld!");
            }
            else {
                jlErrorMessage.setText("Er zijn geen accounts gevonden, maak eerst een account aan!");
            }
            SwingUtilities.updateComponentTreeUI(this);
            errorCheck = false;
        }

    }

    public static int getAccountID(){
        return accountID;
    }

    public static String getAccountName(){
        return accountname;
    }

    public static void main(String[] args) {
        Inloggen inloggenscherm = new Inloggen();
    }
}
