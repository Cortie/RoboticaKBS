import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AfspeelllijstBeheerDialoog extends JDialog implements ActionListener {
    private JButton jbAfspeellijst1;
    private JButton jbAfspeellijst2;
    private JButton jbAfspeellijst3;
    private JButton jbAfspeellijst4;
    private JButton jbAfspeellijst5;

    public AfspeelllijstBeheerDialoog(JFrame frame, boolean modal) {
        super(frame, modal);
        setSize(800, 500);
        setTitle("Muziek toevoegen aan afspeellijst pop-up");

        JPanel lijstMusicAanAfspeellijst = new JPanel(new GridLayout(2, 2));
        lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 1 "));
        lijstMusicAanAfspeellijst.add(jbAfspeellijst1 = new JButton("+"));
        jbAfspeellijst1.addActionListener(this);
        lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 2 "));
        lijstMusicAanAfspeellijst.add(jbAfspeellijst2 = new JButton("+"));
        jbAfspeellijst2.addActionListener(this);
        // lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 3 "));
        // lijstMusicAanAfspeellijst.add(jbNummer3 = new JButton("+"));
        // jbNummer3.addActionListener(this);
        // lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 4 "));
        // lijstMusicAanAfspeellijst.add(jbNummer4 = new JButton("+"));
        // jbNummer4.addActionListener(this);
        // lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 5 "));
        // lijstMusicAanAfspeellijst.add(jbNummer5 = new JButton("+"));
        // jbNummer5.addActionListener(this);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(lijstMusicAanAfspeellijst, BorderLayout.NORTH);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbAfspeellijst1) {
            System.out.println("voeg nummer toe aan afspeellijst");
            dispose();
        }
        if (e.getSource() == jbAfspeellijst2) {
            System.out.println("voeg nummer toe aan afspeellijst");
            dispose();
        }
        if (e.getSource() == jbAfspeellijst3) {
            System.out.println("voeg nummer toe aan afspeellijst");
            dispose();
        }
        if (e.getSource() == jbAfspeellijst4) {
            System.out.println("voeg nummer toe aan afspeellijst");
            dispose();
        }
        if (e.getSource() == jbAfspeellijst5) {
            System.out.println("voeg nummer toe aan afspeellijst");
            dispose();
        }
    }
}
