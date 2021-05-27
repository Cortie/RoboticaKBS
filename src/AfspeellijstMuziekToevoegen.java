import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AfspeellijstMuziekToevoegen extends JDialog implements ActionListener {
    private String name;
    public JList<String> userPlaylists = new JList<>();
    private DefaultListModel demoPlaylists = new DefaultListModel();
    int num= 0;
    
    public AfspeellijstMuziekToevoegen(JFrame frame, boolean modal, String name) {
        super(frame, modal);
        setSize(800, 500);
        this.name = name;
        setTitle("Muziek toevoegen aan afspeellijst: " + this.name);
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
        this.setIconImage(icon);
        createTable();
        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(userPlaylists, BorderLayout.CENTER);
        userPlaylists.setFixedCellHeight(50);
        JScrollPane outputPane = new JScrollPane(userPlaylists,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        borderPnl.add(outputPane);
        userPlaylists.addListSelectionListener(new ListSelectionListener() {
            
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting())
                {
                    userPlaylists.setSelectionMode(
                            ListSelectionModel.SINGLE_SELECTION);
                    String name = userPlaylists.getSelectedValue();
                    addSong(name);
                }
            }
            
        });
        
        add(borderPnl);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    public void createTable()
    {
        try {
            demoPlaylists.removeAllElements();
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
            
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = connection.prepareStatement("SELECT Playlist_id from playlist WHERE account_id =" + Inloggen.getAccountID() + " AND Playlist_name='" +name +"'");
            ResultSet playlist = stmt.executeQuery();
            while(playlist.next())
            {
                num = playlist.getInt("Playlist_id");
            }
            PreparedStatement userstmt = connection.prepareStatement("select song_name from song WHERE song_id IN(SELECT song_id FROM playlist_song WHERE Playlist_id =" + num +")");
            ResultSet songs = userstmt.executeQuery();
            while(songs.next())
            {
                String tempTitle = songs.getString("song_name");
                demoPlaylists.addElement(tempTitle);
            }
            userPlaylists = new JList<String>(demoPlaylists);
            songs.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void removeSong(String name)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
            
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql2 = "SELECT song_id FROM song WHERE song_name='" + name + "'";
            PreparedStatement stmt = connection.prepareStatement(sql2);
            ResultSet songID = stmt.executeQuery();
            int id = 0;
            while(songID.next())
            {
                id = songID.getInt("song_id");
            }
            stmt.close();
            String sql = "DELETE FROM playlist_song WHERE Playlist_id =? AND song_id =?";
            PreparedStatement userstmt = connection.prepareStatement(sql);
            userstmt.setInt(1, num);
            userstmt.setInt(2, id);
            int i = userstmt.executeUpdate();
            System.out.println(i + " records removed");
            connection.close();
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        this.dispose();
    }
}
