package Inloggen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Account extends JFrame implements ActionListener {
    private String name;
    private String password;
    private JLabel jlCreateAccount;
    private JLabel jlUsername;
    private JLabel jlPassword;
    private JTextField jtUsername;
    private JPasswordField jpPassword;
    private JButton jbCreateAccount;

    JPanel test = new JPanel();

    public Account(){
        setTitle("Klimaat Systeem");
        setSize(800,600);

        setLayout(new FlowLayout(FlowLayout.CENTER));
        jlCreateAccount = new JLabel("Nieuw account aanmaken");

        JPanel labelCreateAccountPanel = new JPanel(new FlowLayout());
        labelCreateAccountPanel.add(jlCreateAccount);

        jlUsername = new JLabel("Gebruikersnaam");
        jtUsername = new JTextField(8);

        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.add(jlUsername);
        userPanel.add(jtUsername);

        jlPassword = new JLabel("Wachtwoord");
        jpPassword = new JPasswordField(8);

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(jlPassword);

        passwordPanel.add(jpPassword);

        jbCreateAccount = new JButton("Account aanmaken");

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
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args)
    {
        Account CreateAccountGUI = new Account();


    }

}
