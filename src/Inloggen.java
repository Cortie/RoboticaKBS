import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inloggen extends JFrame implements ActionListener
{
    private JLabel jlTitel;
    private JLabel jlGebruikersnaam;
    private JLabel jlWachtwoord;
    private JTextField jtGebruikersnaam;
    private JPasswordField jpWachtwoord;
    private JButton jbInloggen;
    private JButton jbNieuwAccount;
    private JButton jbVergetenWachtwoord;

    public Inloggen()
    {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(jlTitel=new JLabel("Inloggen Klimaat systeem"));

        JPanel gebruikersnaamPnl = new JPanel(new FlowLayout());
        gebruikersnaamPnl.add(jlGebruikersnaam=new JLabel("Gebruikersnaam"));
        gebruikersnaamPnl.add(jtGebruikersnaam =new JTextField(8));

        JPanel wachtwoordPnl = new JPanel(new FlowLayout());
        wachtwoordPnl.add(jlWachtwoord=new JLabel("Wachtwoord"));
        wachtwoordPnl.add(jpWachtwoord =new JPasswordField(8));

        JPanel inlogknopPnl = new JPanel(new FlowLayout());
        inlogknopPnl.add(jbInloggen = new JButton("Inloggen"));
        jbInloggen.addActionListener(this);

        JPanel newAccountknopPnl = new JPanel(new FlowLayout());
        newAccountknopPnl.add(jbNieuwAccount = new JButton("Nieuw account aanmaken"));
        jbNieuwAccount.addActionListener(this);

        JPanel accountknoppenPnl = new JPanel(new BorderLayout());
        accountknoppenPnl.add(inlogknopPnl,BorderLayout.NORTH);
        accountknoppenPnl.add(newAccountknopPnl, BorderLayout.CENTER);

        JPanel wachtwoordknopPnl = new JPanel(new FlowLayout());
        wachtwoordknopPnl.add(jbVergetenWachtwoord=new JButton("Wachtwoord vergeten"));
        jbVergetenWachtwoord.addActionListener(this);

        JPanel knoppenPnl = new JPanel(new BorderLayout());
        knoppenPnl.add(accountknoppenPnl, BorderLayout.NORTH);
        knoppenPnl.add(wachtwoordknopPnl,BorderLayout.CENTER);

        JPanel gebruikergegevensPnl = new JPanel(new GridLayout(2,2));
        gebruikergegevensPnl.add(gebruikersnaamPnl,BorderLayout.NORTH);
        gebruikergegevensPnl.add(wachtwoordPnl, BorderLayout.CENTER);

        JPanel inlogPnl = new JPanel(new BorderLayout());
        inlogPnl.add(gebruikergegevensPnl,BorderLayout.NORTH);
        inlogPnl.add(knoppenPnl,BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelPnl, BorderLayout.NORTH);
        borderPnl.add(inlogPnl, BorderLayout.CENTER);



        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbInloggen)
        {
            System.out.println("link naar dashboard");
            Dashboard dash = new Dashboard();
            this.dispose();
        }
        if (e.getSource()==jbNieuwAccount)
        {
            System.out.println("link naar account");
            Account CreateAccountGUI = new Account();
            this.dispose();
        }
        if (e.getSource()==jbVergetenWachtwoord)
        {
            System.out.println("link naar wachtwoord wijzigen");
            WachtwoordWijzigen wachtwoordWijzigenscherm = new WachtwoordWijzigen();
            this.dispose();
        }

    }

    public static void main(String[] args)
    {
        Inloggen inloggenscherm= new Inloggen();
    }
}
