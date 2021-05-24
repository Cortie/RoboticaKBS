import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class MuziekAfspeler extends JFrame implements ActionListener {
    private final BasicArrowButton backButton;
    private final JLabel jlTitel;
    private final JLabel jlAfspeellijst;
    private JLabel jlNummer;
//    private JSlider jsNumerTijd;
//    private JLabel jlNummerTijdWaarde;
//    private int nummerTijdWaarde;
    private final JButton jbVorigeAfspelen;
    private final JButton jbPauzeAfspelen;
    private final JButton jbVolgendeAfspelen;
    private final JButton jbAfspeellijstBeheren;
    private final JButton jbMuziekBeheren;
    private final JTable jtTempSong;
    private final String[] TempcolumnNames = {"Muziek nummers"};
    DefaultTableModel tempTableModel = new DefaultTableModel(TempcolumnNames, 0);
    private final Music listener = new Music(this);
    
    
    public MuziekAfspeler() {
        Thread listenerThread = new Thread(this.listener);
        listenerThread.setDaemon(true);
        listenerThread.start();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
        
            Connection connection = DriverManager.getConnection(url, username, password);
        
            PreparedStatement userstmt = connection.prepareStatement("select song_name from song");
            ResultSet songs = userstmt.executeQuery();
            while(songs.next())
            {
                String tempTitle = songs.getString("song_name");
                String[] tempData = { tempTitle } ;
    
                tempTableModel.addRow(tempData);
            }
            songs.close();
            
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        titelPnl.add(jlTitel = new JLabel("Muziekspeler"));
        backButton.addActionListener(this);

        JPanel subTitels = new JPanel(new BorderLayout());
        subTitels.add(jlAfspeellijst = new JLabel("naam afspeellijst"), BorderLayout.NORTH);
        GridLayout tabelLayout = new GridLayout(1, 2);
        JPanel tablePanel = new JPanel(tabelLayout);
        tabelLayout.setHgap(15);
        jtTempSong = new JTable(tempTableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        
        };
        Border blackline = BorderFactory.createLineBorder(Color.black);
        jtTempSong.setShowGrid(false);
        jtTempSong.getCellSelectionEnabled();
        jtTempSong.setRowHeight(50);
        jtTempSong.setRowSelectionAllowed(false);
        jtTempSong.setBorder(blackline);
        
        subTitels.add(jtTempSong);
        JPanel titelsPnl = new JPanel(new BorderLayout());
        titelsPnl.add(titelPnl, BorderLayout.NORTH);
        titelsPnl.add(subTitels, BorderLayout.CENTER);

//        JPanel nummertijdPnl = new JPanel(new FlowLayout());
//        nummertijdPnl.add(jsNumerTijd = new JSlider());
//        nummertijdPnl.add(
//                jlNummerTijdWaarde = new JLabel(String.valueOf(nummerTijdWaarde = jsNumerTijd.getValue()) + " sec"));

        JPanel nummerKnoppenPnl = new JPanel(new FlowLayout());
        nummerKnoppenPnl.add(jbVorigeAfspelen = new JButton("Vorige afspelen"));
        jbVorigeAfspelen.addActionListener(this);
        nummerKnoppenPnl.add(jbPauzeAfspelen = new JButton("Pauzeren/afspelen"));
        jbPauzeAfspelen.addActionListener(this);
        nummerKnoppenPnl.add(jbVolgendeAfspelen = new JButton("Volgende afspelen"));
        jbVolgendeAfspelen.addActionListener(this);

        JPanel muziekKnoppenPnl = new JPanel(new FlowLayout());
        muziekKnoppenPnl.add(jbAfspeellijstBeheren = new JButton("Afspeellijst beheren"));
        jbAfspeellijstBeheren.addActionListener(this);
        muziekKnoppenPnl.add(jbMuziekBeheren = new JButton("Muziek beheren"));
        jbMuziekBeheren.addActionListener(this);

        JPanel knoppenPnl = new JPanel(new BorderLayout());
        knoppenPnl.add(nummerKnoppenPnl, BorderLayout.NORTH);
        knoppenPnl.add(muziekKnoppenPnl, BorderLayout.CENTER);

        JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
//        ondersteGedeeltePnl.add(nummertijdPnl, BorderLayout.NORTH);
        ondersteGedeeltePnl.add(knoppenPnl, BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelsPnl, BorderLayout.NORTH);
        borderPnl.add(ondersteGedeeltePnl, BorderLayout.SOUTH);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbVorigeAfspelen) {
            System.out.println("ga naar vorige nummer");
        }
        if (e.getSource() == jbPauzeAfspelen) {
            System.out.println("pauzeren/afspelen nummer");
        }
        if (e.getSource() == jbVolgendeAfspelen) {
            System.out.println("ga naar volgende nummer");
        }
        if (e.getSource() == jbAfspeellijstBeheren) {
            AfspeellijstBeheer playlist = new AfspeellijstBeheer();
            this.dispose();
        }
        if (e.getSource() == jbMuziekBeheren) {
            MuziekBeheren muziekBeherenscher = new MuziekBeheren();
            this.dispose();
        }
        if (e.getSource() == backButton) {
            MuziekSpeler musicPlayerGUI = new MuziekSpeler();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        MuziekAfspeler muziekAfspelerscherm = new MuziekAfspeler();
        
    }
}
