import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Account extends JFrame implements ActionListener {
    private String name;
    private String password;
    private JLabel jlCreateAccount;
    private JLabel jlUsername;
    private JLabel jlPassword;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JButton jbCreateAccount;

    private String gebruikersnaam = "";
    private String wachtwoord = "";

    private String hashdWachtwoord;
    private String checkHashdWachtwoord;
    private byte[] salt;
    public Account(){
        setTitle("Klimaat Systeem");
        setSize(800,600);
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
        borderPanel.add(userPanel,BorderLayout.CENTER);

        JPanel borderPanel2 = new JPanel(new BorderLayout());
        borderPanel2.add(passwordPanel, BorderLayout.NORTH);
        borderPanel2.add(jbCreateAccountPanel,BorderLayout.CENTER);

        borderPanel.add(borderPanel2,BorderLayout.SOUTH);
        add(borderPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbCreateAccount)
        {
            System.out.println("link naar inloggen");
            gebruikersnaam= jtUsername.getText();
            wachtwoord=jpPassword.getText();
            System.out.println(gebruikersnaam);
            System.out.println(wachtwoord);
            try
            {
                salt = SaltedHashPasword.getSalt();
            } catch (NoSuchAlgorithmException | NoSuchProviderException noSuchAlgorithmException)
            {
                noSuchAlgorithmException.printStackTrace();
            }
            hashdWachtwoord = SaltedHashPasword.getSecurePassword(wachtwoord,salt);

            System.out.println(hashdWachtwoord);
            checkHashdWachtwoord=hashdWachtwoord;//verander checkww naar die uit database
            if (hashdWachtwoord.equals(checkHashdWachtwoord))
            {
                Inloggen inloggenscherm= new Inloggen();
                this.dispose();
            }

        }
    }

    public static void main(String[] args)
    {
        Account CreateAccountGUI = new Account();


    }

}