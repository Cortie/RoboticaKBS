import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KlimaatBeheer extends JFrame implements ActionListener, ChangeListener
{
    private JLabel jlTitel;
    private JButton jbProfielKnop;
    private JLabel jlTempAanpassen;
    private JLabel jlLichtsterkteAanpassen;
    private JSlider jsTempWaarde;
    private JSlider jsLichtsterkte;
    private JLabel jlEmpty;
    private JLabel jlTempWaarde;
    private JLabel jlLichtsterkteWaarde;
    private int tempWaarde;
    private int lichtsterkteWaarde;
    private BasicArrowButton backButton;

    public KlimaatBeheer()
    {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        backButton.addActionListener(this);
        titelPnl.add(jlTitel = new JLabel("Klimaatbeheer"));

        JPanel profielKnopPnl = new JPanel(new FlowLayout());
        profielKnopPnl.add(jbProfielKnop = new JButton("Profielen aanpassen"));
        jbProfielKnop.addActionListener(this);

        JPanel slidersGedeeltePnl = new JPanel(new GridLayout(4,2));
        slidersGedeeltePnl.add(jlTempAanpassen = new JLabel("Temperatuur aanpassen"));
        slidersGedeeltePnl.add(jlEmpty= new JLabel(""));
        slidersGedeeltePnl.add(jsTempWaarde= new JSlider());
        tempWaarde = jsTempWaarde.getValue();
        jsTempWaarde.addChangeListener(this);
        slidersGedeeltePnl.add(jlTempWaarde= new JLabel(String.valueOf(tempWaarde)+" °C"));
        slidersGedeeltePnl.add(jlLichtsterkteAanpassen = new JLabel("Lichtsterkte aanpassen"));
        slidersGedeeltePnl.add(jlEmpty= new JLabel(""));
        slidersGedeeltePnl.add(jsLichtsterkte = new JSlider());
        jsLichtsterkte.addChangeListener(this);
        lichtsterkteWaarde= jsLichtsterkte.getValue();
        slidersGedeeltePnl.add(jlLichtsterkteWaarde= new JLabel(String.valueOf(lichtsterkteWaarde)+" LM"));

        JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
        ondersteGedeeltePnl.add(slidersGedeeltePnl,BorderLayout.WEST);
        ondersteGedeeltePnl.add(profielKnopPnl,BorderLayout.EAST);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelPnl, BorderLayout.NORTH);
        borderPnl.add(ondersteGedeeltePnl,BorderLayout.CENTER);

        add(borderPnl);
        setVisible(true);


    }





    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbProfielKnop)
        {
            System.out.println("link naar profielen aanpassen");
            KlimaatProfiel klimaatProfiel = new KlimaatProfiel();
            this.dispose();
        }
        if (e.getSource()==backButton)
        {
            Dashboard dash = new Dashboard();
            this.dispose();
        }
    }

    public static void main(String[] args)
    {
        KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();


    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource()==jsTempWaarde)
        {
            jlTempWaarde.setText(String.valueOf(tempWaarde=jsTempWaarde.getValue())+" °C");
        }
        if (e.getSource()==jsLichtsterkte)
        {
            jlLichtsterkteWaarde.setText(String.valueOf(lichtsterkteWaarde=jsLichtsterkte.getValue())+" LM");
        }
    }
}
