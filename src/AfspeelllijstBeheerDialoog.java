import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AfspeelllijstBeheerDialoog extends JDialog implements ActionListener
{
    private JButton jbNummer1;
    private JButton jbNummer2;
    private JButton jbNummer3;
    private JButton jbNummer4;
    private JButton jbNummer5;
        public AfspeelllijstBeheerDialoog(JFrame frame, boolean modal){
            super(frame, modal);
            setSize(800, 500);
            setTitle("Muziek toevoegen aan afspeellijst pop-up");

            JPanel lijstMusicAanAfspeellijst = new JPanel(new GridLayout(5,2));
            lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 1 "));
            lijstMusicAanAfspeellijst.add(jbNummer1 = new JButton("+"));
            jbNummer1.addActionListener(this);
            lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 2 "));
            lijstMusicAanAfspeellijst.add(jbNummer2 = new JButton("+"));
            jbNummer2.addActionListener(this);
//            lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 3 "));
//            lijstMusicAanAfspeellijst.add(jbNummer3 = new JButton("+"));
//            jbNummer3.addActionListener(this);
//            lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 4 "));
//            lijstMusicAanAfspeellijst.add(jbNummer4 = new JButton("+"));
//            jbNummer4.addActionListener(this);
//            lijstMusicAanAfspeellijst.add(new JLabel("Afspeellijst 5 "));
//            lijstMusicAanAfspeellijst.add(jbNummer5 = new JButton("+"));
//            jbNummer5.addActionListener(this);

            JPanel borderPnl = new JPanel(new BorderLayout());
            borderPnl.add(lijstMusicAanAfspeellijst, BorderLayout.NORTH);

            add(borderPnl);
            setVisible(true);
        }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbNummer1)
        {
            dispose();
        }
        if (e.getSource()==jbNummer2)
        {
            dispose();
        }
        if (e.getSource()==jbNummer3)
        {
            dispose();
        }
        if (e.getSource()==jbNummer4)
        {
            dispose();
        }
        if (e.getSource()==jbNummer5)
        {
            dispose();
        }
    }
}

