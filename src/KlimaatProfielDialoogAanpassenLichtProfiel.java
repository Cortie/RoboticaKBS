import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KlimaatProfielDialoogAanpassenLichtProfiel extends JDialog implements ActionListener, ChangeListener {
    private JTextField jtProfielNaam;// haal waarde uit database
    private JButton jbBevestigenKnop;
    private JTextField jtVan;
    private JTextField jtTot;
    private String waardeVan = "1900";// haal waarde uit database
    private String waardeTot = "2300";// haal waarde uit database
    private JLabel jlWaardeSlider;
    private int waardeSlider = 10;// haal waarde uit database
    private JSlider jsSlider;
    private String profielnaam;
    private String vanWaarde;
    private String totWaarde;

    public KlimaatProfielDialoogAanpassenLichtProfiel(JFrame frame, boolean modal) {
        super(frame, modal);
        setSize(800, 500);
        setTitle("Lichtsterkte profiel aanpassen pop-up");

        JPanel profielNaamPnl = new JPanel(new FlowLayout());
        profielNaamPnl.add(new JLabel("Profiel naam"));
        profielNaamPnl.add(jtProfielNaam = new JTextField("lichtsterkte profiel 1"));

        JPanel bevestigenKnopPnl = new JPanel(new FlowLayout());
        bevestigenKnopPnl.add(jbBevestigenKnop = new JButton("Bevestigen"));
        jbBevestigenKnop.addActionListener(this);

        JPanel sliderPnl = new JPanel(new FlowLayout());
        sliderPnl.add(jtVan = new JTextField(waardeVan));
        sliderPnl.add(jtTot = new JTextField(waardeTot));
        sliderPnl.add(jsSlider = new JSlider());
        jsSlider.setValue(waardeSlider);
        jsSlider.addChangeListener(this);
        sliderPnl.add(jlWaardeSlider = new JLabel(String.valueOf(waardeSlider = jsSlider.getValue()) + " LM"));

        JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
        ondersteGedeeltePnl.add(sliderPnl, BorderLayout.NORTH);
        ondersteGedeeltePnl.add(bevestigenKnopPnl, BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(profielNaamPnl, BorderLayout.NORTH);
        borderPnl.add(ondersteGedeeltePnl, BorderLayout.CENTER);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbBevestigenKnop) {
            profielnaam = jtProfielNaam.getText();
            vanWaarde = jtVan.getText();
            totWaarde = jtTot.getText();
            System.out.println(profielnaam);
            System.out.println(vanWaarde);
            System.out.println(totWaarde);
            System.out.println(waardeSlider);
            dispose();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == jsSlider) {
            jlWaardeSlider.setText(String.valueOf(waardeSlider = jsSlider.getValue()) + " LM");
        }
    }
}
