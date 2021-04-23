package MuziekSpeler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuziekBeheren extends JFrame implements ActionListener
{
    private JLabel jlTitel;
    private JLabel jlSubTitel;
    private JButton jbAfspeellijstBeheren;


    public MuziekBeheren()
    {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(jlTitel=new JLabel("Muziekspeler"));

        JPanel subtitel = new JPanel(new FlowLayout());
        subtitel.add(jlSubTitel = new JLabel("Muziek beheren"));

        JPanel titelsPnl = new JPanel(new BorderLayout());
        titelsPnl.add(titelPnl,BorderLayout.NORTH);
        titelsPnl.add(subtitel,BorderLayout.CENTER);

        JPanel afspeellijstBeherenKnopPnl = new JPanel(new FlowLayout());
        afspeellijstBeherenKnopPnl.add(jbAfspeellijstBeheren = new JButton("Afspeellijst beheren"));

        JPanel luisteractivitetPnl = new JPanel(new FlowLayout());
        //luisteractivitetPnl.add();

        JPanel knopEnLuisteractiviteitenPnl = new JPanel(new BorderLayout());
        knopEnLuisteractiviteitenPnl.add(afspeellijstBeherenKnopPnl,BorderLayout.NORTH);
        knopEnLuisteractiviteitenPnl.add(luisteractivitetPnl,BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelsPnl, BorderLayout.NORTH);
        borderPnl.add(knopEnLuisteractiviteitenPnl,BorderLayout.CENTER);



        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    public static void main(String[] args)
    {
        MuziekBeheren muziekBeherenscherm = new MuziekBeheren();
    }
}
