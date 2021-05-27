import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MuziekBeheren extends JFrame implements ActionListener {
    private JLabel jlTitel;
    private JLabel jlSubTitel;
    private JButton jbAfspeellijstBeheren;
    private JTable jtLuisteractiviteiten;
    private JLabel jlLuisteractiviteit;
    private final JTable jtTempSong;
    private final String[] TempcolumnNames = {"Muziek nummers", "Datum"};
    DefaultTableModel tempTableModel = new DefaultTableModel(TempcolumnNames, 0);
    private BasicArrowButton backButton;

    public MuziekBeheren() {
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
            this.setIconImage(icon);
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
        jtTempSong = new JTable(tempTableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        
        };
        jtTempSong.setShowGrid(true);
        jtTempSong.setRowHeight(50);
        getLog();
        luisteractivitetPnl.add(jtTempSong);
        JScrollPane outputPane = new JScrollPane(jtTempSong,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        luisteractivitetPnl.add(outputPane);

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
    public void getLog()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
        
            Connection connection = DriverManager.getConnection(url, username, password);
        
            PreparedStatement userstmt = connection.prepareStatement("select song_name, date from song_log WHERE account_id = "+ Inloggen.getAccountID() +" ORDER BY date DESC");
            ResultSet songs = userstmt.executeQuery();
            while(songs.next())
            {
                String tempTitle = songs.getString("song_name");
                Timestamp time = songs.getTimestamp("date");
                String[] tempData = { tempTitle , time.toString()} ;
                tempTableModel.addRow(tempData);
            }
            
            songs.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
