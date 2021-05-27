import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.event.*;
import java.sql.*;

public class AfspeellijstBeheer extends JFrame implements ActionListener, MouseListener {
  // definitions for labels, buttons and layouts
  JLabel music = new JLabel("Muziekspeler");
  JLabel manage = new JLabel("Afspeellijst beheren");
  JLabel name = new JLabel("Nieuw Afspeellijst");
  JLabel share = new JLabel("Afspeellijst Selecteren");
  JLabel addMusic = new JLabel("Muziek verwijderen");
  private BasicArrowButton backButton;
  private JList<String> userPlaylists = new JList<>();
  private JList<String> playlistSongs = new JList<>();
  private DefaultListModel demoPlaylists = new DefaultListModel();
  private DefaultListModel demoSongs = new DefaultListModel();
  JTextField pName = new JTextField(10);
  JButton jbMakeP = new JButton("Afspeellijst aanmaken");
  FlowLayout standard = new FlowLayout();
  FlowLayout add = new FlowLayout(FlowLayout.RIGHT, 25, 0);
  FlowLayout select = new FlowLayout(FlowLayout.RIGHT, 50, 0);
  BorderLayout Bname = new BorderLayout();
  BorderLayout collection = new BorderLayout();
  private JButton jbNummerToevoegen = new JButton("Muziek toevoegen");
  private JButton jbNummerVerwijderen = new JButton("Muziek verwijderen");
  private JButton jbAfspeellijstVerwijderen = new JButton("Afspeellijst verwijderen");

  public AfspeellijstBeheer() {
    // set standard data
    setTitle("Afspeellijst beheren");
    setLayout(new FlowLayout(FlowLayout.CENTER));
    setSize(800, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
        this.setIconImage(icon);

    // temperature panel
    JPanel musicTxtPnl = new JPanel(new BorderLayout());
    musicTxtPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST),BorderLayout.WEST);
    backButton.addActionListener(this);
    musicTxtPnl.add(music,BorderLayout.CENTER);
    music.setFont(music.getFont().deriveFont(16.0f));

    // manage panel
    JPanel manageTxtPnl = new JPanel(standard);
    manageTxtPnl.add(manage);
    manage.setFont(manage.getFont().deriveFont(16.0f));

    // collection panel for temperature and managa texts
    JPanel musicPnl = new JPanel(Bname);
    musicPnl.add(musicTxtPnl, BorderLayout.NORTH);
    musicPnl.add(manageTxtPnl, BorderLayout.SOUTH);

    // panel to make a new playlist
    JPanel addPnl = new JPanel(add);
    add.setVgap(75);
    addPnl.add(name);
    addPnl.add(pName);
    addPnl.add(jbMakeP);
    jbMakeP.addActionListener(this);

    // Select a playlist
    getData();
    JPanel shareA = new JPanel(new BorderLayout());
    shareA.add(share, BorderLayout.NORTH);
    share.setFont(share.getFont().deriveFont(16.0f));
    shareA.add(userPlaylists, BorderLayout.CENTER);
    JScrollPane outputPane = new JScrollPane(userPlaylists,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    shareA.add(outputPane);
    userPlaylists.addListSelectionListener(new ListSelectionListener() {
  
      public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting())
        {
          userPlaylists.setSelectionMode(
                  ListSelectionModel.SINGLE_SELECTION);
          String name = userPlaylists.getSelectedValue();
          getSongs(name);
        }
      }

    });

    JPanel knoppenPnl = new JPanel(new FlowLayout());
    knoppenPnl.add(jbAfspeellijstVerwijderen);
        jbAfspeellijstVerwijderen.addActionListener(this);
        knoppenPnl.add(jbNummerToevoegen);
        jbNummerToevoegen.addActionListener(this);
    shareA.add(knoppenPnl,BorderLayout.SOUTH);

    // adding songs to playlist
    JPanel addM = new JPanel(new BorderLayout());
    addM.add(addMusic, BorderLayout.NORTH);
    addMusic.setFont(addMusic.getFont().deriveFont(16.0f));
    addM.add(playlistSongs, BorderLayout.CENTER);
    addM.setBorder(BorderFactory.createLineBorder(Color.black));
    addM.add(jbNummerVerwijderen,BorderLayout.SOUTH);
    jbNummerVerwijderen.addActionListener(this);
    
    // collection panel for sharing and adding songs panels
    JPanel bottom = new JPanel(select);
    bottom.add(shareA);
    bottom.add(addM);

    // collection panel for all elements
    JPanel borderPnl = new JPanel(collection);
    borderPnl.add(musicPnl, BorderLayout.NORTH);
    borderPnl.add(addPnl, BorderLayout.CENTER);
    borderPnl.add(bottom, BorderLayout.SOUTH);

    add(borderPnl);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if (e.getSource() == jbMakeP) {
      String speellijstnaam = pName.getText();
      afspeellijstAanmaken(speellijstnaam);
    }
    if (e.getSource() == backButton) {
      MuziekAfspeler musicPlayerGUI = new MuziekAfspeler();
      this.dispose();
    }
    if (e.getSource() == jbAfspeellijstVerwijderen)
    {
      System.out.println("doe iets");
    }
    if (e.getSource() == jbNummerToevoegen)
        {
          System.out.println("doe iets");
        }
    if (e.getSource() == jbNummerVerwijderen)
        {
          System.out.println("doe iets");
        }
  }
  public void afspeellijstAanmaken(String naam)
  {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    
      String url = "jdbc:mysql://localhost/domotica_database";
      String username = "root", password = "";
    
      Connection connection = DriverManager.getConnection(url, username, password);
      String sql = "INSERT INTO playlist (account_id, Playlist_name) VALUES (?,?)";
      PreparedStatement userstmt = connection.prepareStatement(sql);
      userstmt.setInt(1, Inloggen.getAccountID());
      userstmt.setString(2, naam);
      int i = userstmt.executeUpdate();
      System.out.println(i + " records inserted");
      demoPlaylists.removeAllElements();
      getData();
      connection.close();
    } catch (SQLException sqle) {
      System.out.println(sqle.getMessage());
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
  
  public void getData()
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
    } catch (SQLException sqle) {
      System.out.println(sqle.getMessage());
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
  public static void main(String[] args) {
    AfspeellijstBeheer playlist = new AfspeellijstBeheer();
  }
  
  public void getSongs(String naam)
  {
    try {
      demoSongs.removeAllElements();
      Class.forName("com.mysql.cj.jdbc.Driver");
      String url = "jdbc:mysql://localhost/domotica_database";
      String username = "root", password = "";
    
      Connection connection = DriverManager.getConnection(url, username, password);
      String sql = "select song_name from song WHERE song_id IN(SELECT song_id FROM playlist_song WHERE Playlist_id IN(SELECT Playlist_id FROM playlist WHERE Playlist_name='"+naam+"' AND account_id = "+Inloggen.getAccountID()+"));";
      PreparedStatement userstmt = connection.prepareStatement(sql);
      ResultSet songs = userstmt.executeQuery();
      while(songs.next())
      {
        String tempTitle = songs.getString("song_name");
        System.out.println(tempTitle);
        demoSongs.addElement(tempTitle);
      }
      playlistSongs = new JList<String>(demoSongs);
      songs.close();
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
