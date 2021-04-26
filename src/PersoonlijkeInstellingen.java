import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PersoonlijkeInstellingen extends JFrame implements ActionListener {
  //definitions for labels, buttons and layouts
  JLabel settings = new JLabel("Privacy instellingen");
  JLabel whitespace = new JLabel(" ");
  JLabel username = new JLabel("Gebruikersnaam");
  JLabel about = new JLabel("About tekst");
  JLabel song1 = new JLabel("Nummer 1");
  JLabel playlist1 = new JLabel("Afspeellijst 1");
  JLabel profiles = new JLabel("Klimaatbeheer profielen bewerken");

  JButton edit = new JButton("Profielen aanpassen");

  FlowLayout standard = new FlowLayout();
  FlowLayout spacing = new FlowLayout(FlowLayout.RIGHT, 100, 0);
  FlowLayout aboutSpacing = new FlowLayout();

  BorderLayout userdetails = new BorderLayout();
  BorderLayout userdata = new BorderLayout();
  BorderLayout profiledata = new BorderLayout();
  BorderLayout privacyBorder = new BorderLayout();
  BorderLayout privacydetailBorder = new BorderLayout(50,0);
  BorderLayout collection = new BorderLayout();

  public PersoonlijkeInstellingen(){
    //set standard data
    setTitle("Persoonlijke instellingen");
    setLayout(new FlowLayout(FlowLayout.LEFT));
    setSize(800, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    
    JPanel songs = new JPanel(spacing);
    songs.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    songs.add(song1);

    JPanel playlists = new JPanel(spacing);
    playlists.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    playlists.add(playlist1);

    JPanel privacySettings = new JPanel(privacydetailBorder);
    privacydetailBorder.setVgap(50);
    privacySettings.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    privacySettings.add(songs, BorderLayout.NORTH);
    privacySettings.add(playlists, BorderLayout.SOUTH);

    JPanel left = new JPanel(privacyBorder);
    left.add(settings, BorderLayout.NORTH);
    left.add(privacySettings, BorderLayout.SOUTH);


    JPanel userName = new JPanel(spacing);
    userName.add(username);

    JPanel userAbout = new JPanel(aboutSpacing);
    aboutSpacing.setVgap(40);
    userAbout.add(about);

    JPanel userDetails = new JPanel(userdata);
    userDetails.add(userName, BorderLayout.NORTH);
    userDetails.add(userAbout, BorderLayout.SOUTH);

    JPanel climate = new JPanel(standard);
    climate.add(profiles);

    JPanel climateButton = new JPanel(standard);
    climateButton.add(edit);  
    
    JPanel profile = new JPanel(profiledata);
    profile.add(climate, BorderLayout.NORTH);
    profile.add(climateButton, BorderLayout.SOUTH);

    JPanel changeViewSettings = new JPanel(userdetails);
    userdetails.setHgap(25);
    changeViewSettings.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    changeViewSettings.add(whitespace, BorderLayout.NORTH);
    changeViewSettings.add(userDetails);
    changeViewSettings.add(profile, BorderLayout.SOUTH);

    

    //collection panel for all elements
    JPanel borderPnl = new JPanel(collection);
    borderPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    borderPnl.add(left, BorderLayout.WEST);
    borderPnl.add(changeViewSettings, BorderLayout.EAST);

    add(borderPnl);

    setVisible(true);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    
  }

  public static void main(String[] args) {
    PersoonlijkeInstellingen first = new PersoonlijkeInstellingen();
  }
}
