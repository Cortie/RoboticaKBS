import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuziekAfspeler extends JFrame implements ActionListener
{
    private BasicArrowButton backButton;
    private JLabel jlTitel;
    private JLabel jlAfspeellijst;
    private JLabel jlNummer;
    private JSlider jsNumerTijd;
    private JLabel jlNummerTijdWaarde;
    private int nummerTijdWaarde;
    private JButton jbVorigeAfspelen;
    private JButton jbPauzeAfspelen;
    private JButton jbVolgendeAfspelen;
    private JButton jbAfspeellijstBeheren;
    private JButton jbMuziekBeheren;

    public MuziekAfspeler()
    {
        setTitle("Klimaat systeem");
                setSize(800, 600);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel titelPnl = new JPanel(new FlowLayout());
                titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
                titelPnl.add(jlTitel=new JLabel("Muziekspeler"));
                backButton.addActionListener(this);

                JPanel subTitels = new JPanel(new BorderLayout());
                subTitels.add(jlAfspeellijst=new JLabel("naam afspeellijst"),BorderLayout.NORTH);
                subTitels.add(jlNummer=new JLabel("naam nummer"),BorderLayout.CENTER);

                JPanel titelsPnl = new JPanel(new BorderLayout());
                titelsPnl.add(titelPnl,BorderLayout.NORTH);
                titelsPnl.add(subTitels,BorderLayout.CENTER);

                JPanel nummertijdPnl = new JPanel(new FlowLayout());
                nummertijdPnl.add(jsNumerTijd=new JSlider());
                nummertijdPnl.add(jlNummerTijdWaarde= new JLabel(String.valueOf(nummerTijdWaarde=jsNumerTijd.getValue())+" sec"));

                JPanel nummerKnoppenPnl = new JPanel(new FlowLayout());
                nummerKnoppenPnl.add(jbVorigeAfspelen=new JButton("Vorige afspelen"));
                jbVorigeAfspelen.addActionListener(this);
                nummerKnoppenPnl.add(jbPauzeAfspelen=new JButton("Pauzeren/afspelen"));
                jbPauzeAfspelen.addActionListener(this);
                nummerKnoppenPnl.add(jbVolgendeAfspelen=new JButton("Volgende afspelen"));
                jbVolgendeAfspelen.addActionListener(this);

                JPanel muziekKnoppenPnl = new JPanel(new FlowLayout());
                muziekKnoppenPnl.add(jbAfspeellijstBeheren=new JButton("Afspeellijst beheren"));
                jbAfspeellijstBeheren.addActionListener(this);
                muziekKnoppenPnl.add(jbMuziekBeheren=new JButton("Muziek beheren"));
                jbMuziekBeheren.addActionListener(this);

                JPanel knoppenPnl = new JPanel(new BorderLayout());
                knoppenPnl.add(nummerKnoppenPnl,BorderLayout.NORTH);
                knoppenPnl.add(muziekKnoppenPnl,BorderLayout.CENTER);

                JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
                ondersteGedeeltePnl.add(nummertijdPnl,BorderLayout.NORTH);
                ondersteGedeeltePnl.add(knoppenPnl, BorderLayout.CENTER);

                JPanel borderPnl = new JPanel(new BorderLayout());
                borderPnl.add(titelsPnl, BorderLayout.NORTH);
                borderPnl.add(ondersteGedeeltePnl,BorderLayout.SOUTH);

                add(borderPnl);
                setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbVorigeAfspelen)
        {
            System.out.println("ga naar vorige nummer");
        }
        if (e.getSource()==jbPauzeAfspelen)
        {
            System.out.println("pauzeren/afspelen nummer");
        }
        if (e.getSource()==jbVolgendeAfspelen)
        {
            System.out.println("ga naar volgende nummer");
        }
        if (e.getSource()==jbAfspeellijstBeheren)
        {
            AfspeellijstBeheer playlist = new AfspeellijstBeheer();
            this.dispose();
        }
        if (e.getSource()==jbMuziekBeheren)
        {
            MuziekBeheren muziekBeherenscher = new MuziekBeheren();
            this.dispose();
        }
        if (e.getSource()==backButton)
        {
            MuziekSpeler musicPlayerGUI = new MuziekSpeler();
            this.dispose();
        }
    }

    public static void main(String[] args)
    {
        MuziekAfspeler muziekAfspelerscherm = new MuziekAfspeler();
    }
}
