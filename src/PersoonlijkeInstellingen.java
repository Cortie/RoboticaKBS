import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

public class PersoonlijkeInstellingen extends JFrame implements ActionListener {
  // definitions for labels, buttons and layouts
  JLabel settings = new JLabel("Persoonlijke instellingen");
  JLabel whitespace = new JLabel(" ");
  JLabel username = new JLabel("Gebruikersnaam");
  JLabel about = new JLabel("About tekst");
  JLabel song1 = new JLabel("Nummer 1");

  String[][] num = {
    {"nummer 1"},
    {"nummer 2"},
    {"nummer 3"},
    {"nummer 4"},
    {"nummer 5"},
  };

  String[][] play = {
    {"playlist 1"},
    {"playlist 2"},
    {"playlist 3"},
    {"playlist 4"},
    {"playlist 5"},
  };

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
  BorderLayout privacydetailBorder = new BorderLayout();
  BorderLayout collection = new BorderLayout();

  public PersoonlijkeInstellingen() {
    // set standard data
    setTitle("Persoonlijke instellingen");
    setLayout(new FlowLayout(FlowLayout.LEFT));
    setSize(800, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // panel for list of songs
    JPanel NumbersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    NumbersPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    String[] headerN = {"nummers"};
    JTable tableNum = new JTable(num, headerN);
    tableNum.setCellSelectionEnabled(false);
    tableNum.setShowGrid(false);
    tableNum.setEnabled(false);
    tableNum.setRowHeight(30);
    NumbersPanel.add(tableNum);

    // panel for list of playlists
    JPanel playlistsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    playlistsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    String[] headerP = {"Playlists"};
    JTable tablePlay = new JTable(play, headerP);
    tablePlay.setCellSelectionEnabled(false);
    tablePlay.setShowGrid(false);
    tablePlay.setEnabled(false);
    tablePlay.setRowHeight(30);
    playlistsPanel.add(tablePlay);

    // borderpanel for the collection of both lists
    JPanel privacySettings = new JPanel(privacydetailBorder);
    privacydetailBorder.setVgap(30);
    privacySettings.add(NumbersPanel, BorderLayout.NORTH);
    privacySettings.add(playlistsPanel, BorderLayout.SOUTH);

    // borderpanel for the left section of the GUI
    JPanel left = new JPanel(privacyBorder);
    privacyBorder.setVgap(20);
    left.setBorder(new EmptyBorder(50,0,0,0));
    left.add(settings, BorderLayout.NORTH);
    left.add(privacySettings, BorderLayout.SOUTH);

    // username panel
    JPanel userName = new JPanel(aboutSpacing);
    userName.add(username);

    // about text panel
    JPanel userAbout = new JPanel(aboutSpacing);
    userAbout.add(about);

    // userdetails panel to contain both username and about text
    JPanel userDetails = new JPanel(userdata);
    userDetails.setBorder(new EmptyBorder(100,0,0,0));
    userDetails.add(userName, BorderLayout.NORTH);
    userDetails.add(userAbout);

    // panel of the header for the climate profiles
    JPanel climate = new JPanel(standard);
    climate.add(profiles);

    // edit profiles button panel
    JPanel climateButton = new JPanel(standard);
    climateButton.add(edit);

    // collection borderpanel for climate profiles
    JPanel profile = new JPanel(profiledata);
    profile.setBorder(new EmptyBorder(0,0,100,0));
    profile.add(climate, BorderLayout.NORTH);
    profile.add(climateButton, BorderLayout.SOUTH);

    // collection borderpanel for all panels on the right of the GUI
    JPanel changeViewSettings = new JPanel(userdetails);
    changeViewSettings.add(whitespace, BorderLayout.NORTH);
    changeViewSettings.add(userDetails, BorderLayout.CENTER);
    changeViewSettings.add(profile, BorderLayout.SOUTH);

    // collection panel for all elements
    JPanel borderPnl = new JPanel(collection);
    borderPnl.setBorder(new EmptyBorder(0,50,50,0));
    collection.setHgap(150);
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
