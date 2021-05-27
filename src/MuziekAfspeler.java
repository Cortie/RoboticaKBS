import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
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
    public JList<String> userPlaylists = new JList<>();
    private final DefaultListModel demoPlaylists = new DefaultListModel();
    private final BasicArrowButton backButton;
    private final JButton jbVorigeAfspelen;
    private JButton jbPauzeAfspelen;
    private final JButton jbVolgendeAfspelen;
    private final JButton jbAfspeellijstBeheren;
    private String playlistName;
    private final JButton jbMuziekBeheren;
    public int finalSong;
    public int firstSong;
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
        listener.currentSong = 1;
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
            this.setIconImage(icon);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        JLabel jlTitel;
        titelPnl.add(jlTitel = new JLabel("Dashboard"));
        backButton.addActionListener(this);

        JPanel subTitels = new JPanel(new BorderLayout());
        subTitels.add(new JLabel("Afspeellijsten"), BorderLayout.NORTH);
        subTitels.add(userPlaylists, BorderLayout.CENTER);
        JScrollPane outputPane = new JScrollPane(userPlaylists,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        subTitels.add(outputPane);
        userPlaylists.addListSelectionListener(new ListSelectionListener() {
        
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting())
                {
                    userPlaylists.setSelectionMode(
                            ListSelectionModel.SINGLE_SELECTION);
                    playlistName = userPlaylists.getSelectedValue();
                    songData(playlistName);
                    jtTempSong.setRowSelectionInterval(0,0);
                }
            }
        });
        JPanel songs = new JPanel(new BorderLayout());
        songs.add(new JLabel("Nummers"), BorderLayout.NORTH);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        jtTempSong = new JTable(tempTableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        jtTempSong.setRowSelectionInterval(0,0);
        jtTempSong.setShowGrid(true);
        jtTempSong.getCellSelectionEnabled();
        jtTempSong.setRowHeight(50);
        jtTempSong.setRowSelectionAllowed(true);
        jtTempSong.setBorder(blackline);
        jtTempSong.addMouseListener(this);
        songs.add(jtTempSong);
        JPanel titelsPnl = new JPanel(new BorderLayout());
        titelsPnl.add(titelPnl, BorderLayout.NORTH);
        titelsPnl.add(subTitels, BorderLayout.CENTER);
        titelsPnl.add(songs, BorderLayout.SOUTH);

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
        if(listener.currentSong == finalSong)
        {
            jtTempSong.setRowSelectionInterval(0,0);
            listener.currentSong = firstSong;
        }
        else{
            if(playlistName == null)
            {
                jtTempSong.setRowSelectionInterval(jtTempSong.getSelectedRow() + 1,jtTempSong.getSelectedRow() + 1);
                listener.currentSong++;
            }
            else
            {
                jtTempSong.setRowSelectionInterval(jtTempSong.getSelectedRow() + 1,jtTempSong.getSelectedRow() + 1);
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
        
                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";
        
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT song_id FROM song WHERE song_name='" + jtTempSong.getValueAt(jtTempSong.getSelectedRow(), 0) + "'");
                    while(rs.next())
                    {
                        setSong(rs.getInt(1));
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        thisNote = 1;
    }
    public void previousSong()
    {
        if(listener.currentSong == firstSong)
        {
            jtTempSong.setRowSelectionInterval(jtTempSong.getRowCount() -1,jtTempSong.getRowCount() -1);
            listener.currentSong = finalSong;
        }
        else{
            if(playlistName == null)
            {
                jtTempSong.setRowSelectionInterval(jtTempSong.getSelectedRow() - 1,jtTempSong.getSelectedRow() - 1);
                listener.currentSong--;
            }
            else
            {
                jtTempSong.setRowSelectionInterval(jtTempSong.getSelectedRow() - 1,jtTempSong.getSelectedRow() - 1);
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                
                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";
                
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT song_id FROM song WHERE song_name='" + jtTempSong.getValueAt(jtTempSong.getSelectedRow(), 0) + "'");
                    while(rs.next())
                    {
                        setSong(rs.getInt(1));
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        thisNote = 1;
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
                demoPlaylists.addElement(tempTitle);
            }
            userPlaylists = new JList<String>(demoPlaylists);
            playlists.close();
            connection.close();
        }  catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void songData()
    {
        try {
            tempTableModel.setRowCount(0);
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
            ResultSet rs = stmt.executeQuery("SELECT MAX(song_id) FROM song");
            while(rs.next())
            {
                finalSong = rs.getInt(1);
            }
            firstSong = 1;
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        setSong(firstSong);
    }
    public void songData(String naam)
    {
        try {
            tempTableModel.setRowCount(0);
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
            
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql3 = "SELECT Playlist_id from playlist " +
                    "WHERE account_id=" + Inloggen.getAccountID() + " AND Playlist_name='" + naam + "'";
            PreparedStatement stmt2 = connection.prepareStatement(sql3);
            ResultSet plID = stmt2.executeQuery();
            int i = 0;
            while(plID.next())
            {
                i = plID.getInt(1);
            }
            PreparedStatement userstmt = connection.prepareStatement("select song_name from song WHERE song_id IN(SELECT song_id FROM playlist_song WHERE Playlist_id = "  + i+ ") ORDER BY song_id ASC");
            ResultSet songs = userstmt.executeQuery();
            while(songs.next())
            {
                String tempTitle = songs.getString("song_name");
                String[] tempData = { tempTitle } ;
            
                tempTableModel.addRow(tempData);
            }
            songs.close();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(song_id) FROM song WHERE song_id IN(SELECT song_id from playlist_song WHERE Playlist_id =" + i + ")");
            while(rs.next())
            {
                finalSong = rs.getInt(1);
            }
            Statement stmt4 = connection.createStatement();
            ResultSet rs4 = stmt4.executeQuery("SELECT MIN(song_id) FROM song WHERE song_id IN(SELECT song_id from playlist_song WHERE Playlist_id =" + i + ")");
            while(rs4.next())
            {
                firstSong = rs4.getInt(1);
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        setSong(firstSong);
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
        }catch (Exception ex) {
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
                String name = (String)jtTempSong.getValueAt(jtTempSong.getSelectedRow(), 0);
    
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";
        
                    Connection connection = DriverManager.getConnection(url, username, password);
                    String sql = "SELECT song_id from song " +
                            "WHERE song_name ='" + name + "'";
                    PreparedStatement userstmt = connection.prepareStatement(sql);
                    ResultSet rs = userstmt.executeQuery();
                    int i = 0;
                    while(rs.next())
                    {
                        i = rs.getInt(1);
                    }
                    setSong(i);
                } catch (Exception ex)
                {
                    ex.printStackTrace();
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
