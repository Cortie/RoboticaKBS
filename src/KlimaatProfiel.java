import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;

public class KlimaatProfiel extends JFrame implements ActionListener, MouseListener {
    private JLabel jlTitel;
    private BasicArrowButton backButton;
    private JButton jbCreateTempProfile;
    private JButton jbCreateLightProfile;
    private JTable jtTempProfile;
    private JTable jtLightProfile;
    private String[] tempProfileTitel = { "Temperatuur Profielen" };
    private Object[][] tempProfile = { { "temperatuur profiel 1" }, { "temperatuur profiel 2" } };
    private String[] lightProfileTitel = { "Lichtsterkte Profielen" };
    private Object[][] lightProfile = { { "lichtsterkte profiel 1" }, { "lichtsterkte profiel 2" } };

    public KlimaatProfiel() {
        setTitle("Klimaat Systeem");
        setSize(800, 600);
        setLayout(new GridLayout(4, 3));
        // borders
        Border blackline = BorderFactory.createLineBorder(Color.black);

        // HeadPanel
        FlowLayout headLayout = new FlowLayout();
        GridLayout headLayout2 = new GridLayout(1, 2);
        JPanel headPanel = new JPanel(headLayout);

        jlTitel = new JLabel("Profielen aanpassen");
        jlTitel.setFont(new Font("arial", Font.BOLD, 20));

        backButton = new BasicArrowButton(BasicArrowButton.WEST);
        backButton.addActionListener(this);
        headLayout.setAlignment(FlowLayout.LEFT);
        headLayout.setHgap(150);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);

        headPanel.add(backButton);
        headPanel.add(jlTitel);
        add(headPanel);

        // buttonPanel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        jbCreateTempProfile = new JButton("Temperatuur profiel aanmaken");
        jbCreateLightProfile = new JButton("Lichtsterkte profiel aanmaken");
        jbCreateTempProfile.addActionListener(this);
        jbCreateLightProfile.addActionListener(this);
        buttonPanel.add(jbCreateTempProfile);
        buttonPanel.add(jbCreateLightProfile);
        add(buttonPanel);

        // tablePanel
        GridLayout tabelLayout = new GridLayout(1, 2);
        JPanel tablePanel = new JPanel(tabelLayout);
        tabelLayout.setHgap(15);
        jtTempProfile = new JTable(tempProfile, tempProfileTitel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtTempProfile.setShowGrid(false);
        jtTempProfile.getCellSelectionEnabled();
        jtTempProfile.setRowHeight(50);
        jtTempProfile.setRowSelectionAllowed(false);
        jtTempProfile.setBorder(blackline);
        jtTempProfile.addMouseListener((MouseListener) this);

        jtLightProfile = new JTable(lightProfile, lightProfileTitel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jtLightProfile.setShowGrid(false);
        jtLightProfile.getCellSelectionEnabled();
        jtLightProfile.setRowHeight(50);
        jtLightProfile.setRowSelectionAllowed(false);
        jtLightProfile.setBorder(blackline);
        jtLightProfile.getColumnModel().getColumnSelectionAllowed();
        jtLightProfile.addMouseListener((MouseListener) this);

        tablePanel.add(jtTempProfile);
        tablePanel.add(jtLightProfile);
        add(tablePanel);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();
            this.dispose();
        }
        if (e.getSource() == jbCreateTempProfile) {
            KlimaatProfielDialoogAanmakenTempProfiel dialoog = new KlimaatProfielDialoogAanmakenTempProfiel(this, true);
        }
        if (e.getSource() == jbCreateLightProfile) {
            KlimaatProfielDialoogAanmakenLichtProfiel dialoog2 = new KlimaatProfielDialoogAanmakenLichtProfiel(this,
                    true);
        }
    }

    public static void main(String[] args) {
        KlimaatProfiel klimaatProfiel = new KlimaatProfiel();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == jtTempProfile) {
            if (jtTempProfile.getSelectedColumn() == 0) {
                // System.out.println("temp table");
                // System.out.println("knop column");
                if (jtTempProfile.getSelectedRow() == 0) {
                    // System.out.println("row 1");
                    KlimaatProfielDialoogAanpassenTempProfiel dialoog1 = new KlimaatProfielDialoogAanpassenTempProfiel(
                            this, true);
                }
                if (jtTempProfile.getSelectedRow() == 1) {
                    // System.out.println("row 2");
                    KlimaatProfielDialoogAanpassenTempProfiel dialoog2 = new KlimaatProfielDialoogAanpassenTempProfiel(
                            this, true);
                }
            }
        }
        if (e.getSource() == jtLightProfile) {

            if (jtLightProfile.getSelectedColumn() == 0) {
                // System.out.println("licht table");
                // System.out.println("knop column");
                if (jtLightProfile.getSelectedRow() == 0) {
                    // System.out.println("row 1");
                    KlimaatProfielDialoogAanpassenLichtProfiel dialoog1 = new KlimaatProfielDialoogAanpassenLichtProfiel(
                            this, true);
                }
                if (jtLightProfile.getSelectedRow() == 1) {
                    // System.out.println("row 2");
                    KlimaatProfielDialoogAanpassenLichtProfiel dialoog2 = new KlimaatProfielDialoogAanpassenLichtProfiel(
                            this, true);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
