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
  private String playlistName;
  private final BasicArrowButton backButton;
  public JList<String> userPlaylists = new JList<>();
  private final DefaultListModel demoPlaylists = new DefaultListModel();
  JTextField pName = new JTextField(10);
  JButton jbMakeP = new JButton("Afspeellijst aanmaken");
  FlowLayout standard = new FlowLayout();
  FlowLayout add = new FlowLayout(FlowLayout.RIGHT, 25, 0);
  FlowLayout select = new FlowLayout(FlowLayout.RIGHT, 50, 0);
  BorderLayout Bname = new BorderLayout();
  BorderLayout collection = new BorderLayout();
  private JPanel bottom;
  private Connection connection;
  private final JButton jbNummerToevoegen = new JButton("Muziek toevoegen");
  private final JButton jbNummerVerwijderen = new JButton("Muziek verwijderen");
  private final JButton jbAfspeellijstVerwijderen = new JButton("Afspeellijst verwijderen");

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

    // collection panel for temperature and manage texts
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
  
    // collection panel for sharing and adding songs panels
    bottom = new JPanel(select);
    
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
          playlistName = userPlaylists.getSelectedValue();
        }
      }
    });
    
    JPanel knoppenPnl = new JPanel(new BorderLayout());
    knoppenPnl.add(jbAfspeellijstVerwijderen,BorderLayout.NORTH);
    jbAfspeellijstVerwijderen.addActionListener(this);
    knoppenPnl.add(jbNummerToevoegen,BorderLayout.CENTER);
    jbNummerToevoegen.addActionListener(this);
    knoppenPnl.add(jbNummerVerwijderen,BorderLayout.SOUTH);
    jbNummerVerwijderen.addActionListener(this);

    shareA.add(knoppenPnl,BorderLayout.SOUTH);

    bottom = new JPanel(new FlowLayout());
    bottom.add(shareA);
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
      removePlaylist(userPlaylists.getSelectedValue());
    }
    if (e.getSource() == jbNummerToevoegen)
        {
          AfspeelllijstBeheerDialoog createTempDialog = new AfspeelllijstBeheerDialoog(this, true, playlistName);
        }
    if (e.getSource() == jbNummerVerwijderen)
        {
          AfspeellijstMuziekVerwijderen createTempDialog = new AfspeellijstMuziekVerwijderen(this, true, playlistName);
        }
  }
  public void afspeellijstAanmaken(String naam)
  {
    try {
      setConnection();
      String sql = "INSERT INTO playlist (account_id, Playlist_name) VALUES (?,?)";
      PreparedStatement userstmt = connection.prepareStatement(sql);
      userstmt.setInt(1, Inloggen.getAccountID());
      userstmt.setString(2, naam);
      int i = userstmt.executeUpdate();
      System.out.println(i + " records inserted");
      getData();
      connection.close();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
  
  public void getData()
  {
    try {
      demoPlaylists.removeAllElements();
      setConnection();
      
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
  public static void main(String[] args) {
    AfspeellijstBeheer playlist = new AfspeellijstBeheer();
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
  public void removePlaylist(String naam)
  {
    try
    {
      setConnection();
      String sql3 = "SELECT Playlist_id from playlist " +
              "WHERE account_id=" + Inloggen.getAccountID() + " AND Playlist_name='" + naam + "'";
      PreparedStatement userstmt = connection.prepareStatement(sql3);
      ResultSet rs = userstmt.executeQuery();
      int i = 0;
      while(rs.next())
      {
        i = rs.getInt(1);
      }
      String sql2 = "DELETE FROM playlist_song WHERE " +
        " Playlist_id="+ i;
      Statement stmt2 = connection.createStatement();
      stmt2.executeUpdate(sql2);
      String sql = "DELETE FROM playlist " +
              "WHERE account_id=" + Inloggen.getAccountID() + " AND Playlist_name='" + naam + "'";
      Statement stmt = connection.createStatement();
      stmt.executeUpdate(sql);
    } catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    getData();
  }
  public void setConnection()
  {
    try
    {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String url = "jdbc:mysql://localhost/domotica_database";
      String username = "root", password = "";
    
      connection = DriverManager.getConnection(url, username, password);
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
