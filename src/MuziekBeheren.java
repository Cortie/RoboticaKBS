import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuziekBeheren extends JFrame implements ActionListener {
    private JLabel jlTitel;
    private JLabel jlSubTitel;
    private JButton jbAfspeellijstBeheren;
    private JTable jtLuisteractiviteiten;
    private JLabel jlLuisteractiviteit;
    private String[] titelLuisterLijst = { "Luisteractiviteit", "" };
    private Object[][] data = { { "nummer1", "5 minuten geleden" }, { "nummer2", "5 minuten geleden" } };
    private BasicArrowButton backButton;

    public MuziekBeheren() {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel titelPnl = new JPanel(new BorderLayout());
        titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST),BorderLayout.WEST);
        titelPnl.add(jlTitel = new JLabel("Muziekspeler"));
        backButton.addActionListener(this);

        JPanel subtitel = new JPanel(new FlowLayout());
        subtitel.add(jlSubTitel = new JLabel("Muziek beheren"));

        JPanel titelsPnl = new JPanel(new BorderLayout());
        titelsPnl.add(titelPnl, BorderLayout.NORTH);
        titelsPnl.add(subtitel, BorderLayout.CENTER);

        JPanel afspeellijstBeherenKnopPnl = new JPanel(new FlowLayout());
        afspeellijstBeherenKnopPnl.add(jbAfspeellijstBeheren = new JButton("Afspeellijst beheren"));
        jbAfspeellijstBeheren.addActionListener(this);

        JPanel luisteractiviteitTitelPnl = new JPanel(new FlowLayout());
        luisteractiviteitTitelPnl.add(jlLuisteractiviteit = new JLabel("Luisteractiviteit"));

        JPanel luisteractivitetPnl = new JPanel(new BorderLayout());
        luisteractivitetPnl.add(luisteractiviteitTitelPnl, BorderLayout.NORTH);
        JTable jtLuisteractiviteiten = new JTable(data, titelLuisterLijst);
        jtLuisteractiviteiten.setCellSelectionEnabled(false);
        jtLuisteractiviteiten.setShowGrid(false);
        jtLuisteractiviteiten.setEnabled(false);
        jtLuisteractiviteiten.setRowHeight(20);

        jtLuisteractiviteiten.setBorder(BorderFactory.createLineBorder(Color.black));
        luisteractivitetPnl.add(jtLuisteractiviteiten, BorderLayout.CENTER);

        JPanel knopEnLuisteractiviteitenPnl = new JPanel(new BorderLayout());
        knopEnLuisteractiviteitenPnl.add(afspeellijstBeherenKnopPnl, BorderLayout.NORTH);
        knopEnLuisteractiviteitenPnl.add(luisteractivitetPnl, BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelsPnl, BorderLayout.NORTH);
        borderPnl.add(knopEnLuisteractiviteitenPnl, BorderLayout.CENTER);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbAfspeellijstBeheren) {
            System.out.println("link naar afspeellijst beheren");
            AfspeellijstBeheer playlist = new AfspeellijstBeheer();
            this.dispose();
        }
        if (e.getSource() == backButton) {
            MuziekAfspeler musicPlayerGUI = new MuziekAfspeler();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        MuziekBeheren muziekBeherenscher = new MuziekBeheren();

    }
}
