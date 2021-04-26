import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WachtwoordWijzigen extends JFrame implements ActionListener
{
    private JLabel jlTitel;
    private JLabel jlGebruikersnaam;
    private JLabel jlWachtwoord;
    private JLabel jlWachtwoordBevestigen;
    private JTextField jtGebruikersnaam;
    private JTextField jtWachtwoord;
    private JTextField jtWachtwoordBevestigen;
    private JButton jbWachtwoordWijzigen;

    public WachtwoordWijzigen()
    {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(jlTitel=new JLabel("Wachtwoord wijzigen"));

        JPanel gebruikersnaamPnl = new JPanel(new FlowLayout());
        gebruikersnaamPnl.add(jlGebruikersnaam=new JLabel("Gebruikersnaam"));
        gebruikersnaamPnl.add(jtGebruikersnaam =new JTextField(8));

        JPanel wachtwoordPnl = new JPanel(new FlowLayout());
        wachtwoordPnl.add(jlWachtwoord = new JLabel("Wachtwoord"));
        wachtwoordPnl.add(jtWachtwoord = new JTextField(8));

        JPanel wachtwoordBevestigenPnl = new JPanel(new FlowLayout());
        wachtwoordBevestigenPnl.add(jlWachtwoordBevestigen = new JLabel("Wachtwoord bevestigen"));
        wachtwoordBevestigenPnl.add(jtWachtwoordBevestigen = new JTextField(8));

        JPanel wachtwoordWijzigenKnopPnl = new JPanel(new FlowLayout());
        wachtwoordWijzigenKnopPnl.add(jbWachtwoordWijzigen = new JButton("Wachtwoord wijzigen"));

        JPanel wachtwoordCheckPnl = new JPanel(new BorderLayout());
        wachtwoordCheckPnl.add(wachtwoordBevestigenPnl,BorderLayout.NORTH);
        wachtwoordCheckPnl.add(wachtwoordWijzigenKnopPnl,BorderLayout.CENTER);

        JPanel gebruikergegevensPnl =new JPanel(new BorderLayout());
        gebruikergegevensPnl.add(gebruikersnaamPnl,BorderLayout.NORTH);
        gebruikergegevensPnl.add(wachtwoordPnl,BorderLayout.CENTER);

        JPanel gegevensPnl = new JPanel(new BorderLayout());
        gegevensPnl.add(gebruikergegevensPnl,BorderLayout.NORTH);
        gegevensPnl.add(wachtwoordCheckPnl,BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelPnl, BorderLayout.NORTH);
        borderPnl.add(gegevensPnl,BorderLayout.CENTER);



        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    public static void main(String[] args)
    {
        WachtwoordWijzigen wachtwoordWijzigenscherm = new WachtwoordWijzigen();
    }
}
