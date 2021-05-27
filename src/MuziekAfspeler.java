import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MuziekAfspeler extends JFrame implements ActionListener, MouseListener
{
    private JLabel jlTitel;
    private final BasicArrowButton backButton;
    private final JButton jbVorigeAfspelen;
    private JButton jbPauzeAfspelen;
    private final JButton jbVolgendeAfspelen;
    private final JButton jbAfspeellijstBeheren;
    private final JButton jbMuziekBeheren;
    public int finalSong;
    private final JTable jtTempSong;
    private final String[] TempcolumnNames = {"Muziek nummers"};
    DefaultTableModel tempTableModel = new DefaultTableModel(TempcolumnNames, 0);
    private JTable jtPlaylists;
    private final String[] playlistColumns = {"Playlists"};
    DefaultTableModel plTableModel = new DefaultTableModel(playlistColumns, 0);
    private final Music listener = new Music(this);
    private boolean play;
    private int thisNote;
    private Thread listenerThread;
    
    public int getThisNote()
    {
        return thisNote;
    }
    
    public void setThisNote(int thisNote)
    {
        this.thisNote = thisNote;
    }
    
    public boolean isPlay()
    {
        return play;
    }
    
    public MuziekAfspeler() {
        songData();
        playlistData();
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
            this.setIconImage(icon);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        titelPnl.add(jlTitel = new JLabel("Dashboard"));
        backButton.addActionListener(this);

        JPanel subTitels = new JPanel(new BorderLayout());
        //subTitels.add(jlAfspeellijst = new JLabel("naam afspeellijst"), BorderLayout.NORTH);
        GridLayout tabelLayout = new GridLayout(1, 2);
        JPanel tablePanel = new JPanel(tabelLayout);
        tabelLayout.setHgap(15);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        jtPlaylists = new JTable(plTableModel)
        {
            public boolean isCellEditable(int row, int collumn){
                return false;
            }
        };
        jtPlaylists.setShowGrid(true);
        jtPlaylists.getCellSelectionEnabled();
        jtPlaylists.setRowHeight(50);
        jtPlaylists.setRowSelectionAllowed(true);
        jtPlaylists.setBorder(blackline);
        jtPlaylists.addMouseListener(this);

        subTitels.add(jtPlaylists);
        jtTempSong = new JTable(tempTableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        jtTempSong.setShowGrid(true);
        jtTempSong.getCellSelectionEnabled();
        jtTempSong.setRowHeight(50);
        jtTempSong.setRowSelectionAllowed(true);
        jtTempSong.setBorder(blackline);
        jtTempSong.addMouseListener(this);
        
        subTitels.add(jtTempSong);
        JPanel titelsPnl = new JPanel(new BorderLayout());
        titelsPnl.add(titelPnl, BorderLayout.NORTH);
        titelsPnl.add(subTitels, BorderLayout.CENTER);

        JPanel nummerKnoppenPnl = new JPanel(new FlowLayout());
        nummerKnoppenPnl.add(jbVorigeAfspelen = new JButton("Vorige afspelen"));
        jbVorigeAfspelen.addActionListener(this);
        nummerKnoppenPnl.add(jbPauzeAfspelen = new JButton("Afspelen"));
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
            previousSong();
        }
        if (e.getSource() == jbPauzeAfspelen) {
            pauseButton();
        }
        if (e.getSource() == jbVolgendeAfspelen) {
            nextSong();
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
            Dashboard musicPlayerGUI = new Dashboard();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        MuziekAfspeler muziekAfspelerscherm = new MuziekAfspeler();
        
    }
    public void nextSong()
    {
        if(listener.currentSong < listener.songLength)
        {
            listener.currentSong++;
        }else
        {
            listener.currentSong = 1;
        }
        thisNote = 1;
    }
    public void previousSong()
    {
        if(listener.currentSong > 1)
        {
            listener.currentSong--;
        }else
        {
            listener.currentSong = listener.songLength;
        }
        thisNote =1;
    }
    public void pauseButton()
    {
        play = !play;
        if(play)
        {
            listenerThread = new Thread(this.listener);
            listenerThread.setDaemon(true);
            listenerThread.start();
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }
            jbPauzeAfspelen.setText("Pauze");
        }
        if(!play)
        {
            listener.port.closePort();
            jbPauzeAfspelen.setText("Spelen");
        }
    }
    public void playlistData()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement userstmt = connection.prepareStatement("select playlist_name from playlist WHERE account_id =" + Inloggen.getAccountID() +" ORDER BY playlist_name ASC ");
            ResultSet playlists = userstmt.executeQuery();
            while(playlists.next())
            {
                String tempTitle = playlists.getString("playlist_name");
                String[] tempData = { tempTitle } ;

                plTableModel.addRow(tempData);
            }
            playlists.close();
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void songData()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
        
            Connection connection = DriverManager.getConnection(url, username, password);
        
            PreparedStatement userstmt = connection.prepareStatement("select song_name from song ORDER BY song_id ASC");
            ResultSet songs = userstmt.executeQuery();
            while(songs.next())
            {
                String tempTitle = songs.getString("song_name");
                String[] tempData = { tempTitle } ;
            
                tempTableModel.addRow(tempData);
            }
            songs.close();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(song_id) FROM song");
            while(rs.next())
            {
                finalSong = rs.getInt(1);
            }
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void setSong(int num)
    {
        listener.currentSong = num;
        thisNote = 1;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
        
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql2 = "SELECT song_name FROM song WHERE song_id ='"+ num +"'";
            Statement stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(sql2);
            String naam;
            if(r.next())
            {
                naam = r.getString(1);
                stmt.close();
                String sql = "INSERT INTO song_log (account_id, song_name, date) VALUES (?,?, ?)";
                PreparedStatement userstmt = connection.prepareStatement(sql);
                userstmt.setInt(1, Inloggen.getAccountID());
                userstmt.setTimestamp(3, timestamp);
                userstmt.setString(2, naam);
                int i = userstmt.executeUpdate();
                System.out.println(i + " records inserted");
            }
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == jtTempSong)
        {
            if(jtTempSong.getSelectedColumn() == 0)
            {
                switch (jtTempSong.getSelectedRow())
                {
                    case 0 -> setSong(1);
                    case 1 -> setSong(2);
                    case 2 -> setSong(3);
                    case 3 -> setSong(4);
                    case 4 -> setSong(5);
                }
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
    
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
    
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {
    
    }
    
    @Override
    public void mouseExited(MouseEvent e)
    {
    
    }
}
