import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.event.*;

public class PersoonlijkeInstellingen extends JFrame implements ActionListener, MouseListener {
  // definitions for labels, buttons and layouts
  JLabel settings = new JLabel("Persoonlijke instellingen");
  JLabel whitespace = new JLabel(" ");
  JLabel username = new JLabel("Gebruikersnaam");
  JLabel about = new JLabel("About tekst");
  JLabel song1 = new JLabel("Nummer 1");
  private BasicArrowButton backButton;
  private JTable jtTableNum;
  private JTable jtTablePlaylist;

  String[][] num = { { "nummer 1", "knop" }, { "nummer 2", "knop" }, { "nummer 3", "knop" }, { "nummer 4", "knop" },
      { "nummer 5", "knop" }, };

  String[][] play = { { "playlist 1", "knop" }, { "playlist 2", "knop" }, { "playlist 3", "knop" },
      { "playlist 4", "knop" }, { "playlist 5", "knop" }, };

  JLabel playlist1 = new JLabel("Afspeellijst 1");
  JLabel profiles = new JLabel("Klimaatbeheer profielen bewerken");

  JButton jbEdit = new JButton("Profielen aanpassen");

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
    String[] headerN = { "nummers", "Knoppen" };
    jtTableNum = new JTable(num, headerN) {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    jtTableNum.addMouseListener(this);
    jtTableNum.setCellSelectionEnabled(false);
    jtTableNum.setShowGrid(false);
    // jtTableNum.setEnabled(false);
    jtTableNum.setRowHeight(30);

    NumbersPanel.add(jtTableNum);

    // panel for list of playlists
    JPanel playlistsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    playlistsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    String[] headerP = { "Playlists", "Knoppen" };
    jtTablePlaylist = new JTable(play, headerP) {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    jtTablePlaylist.addMouseListener(this);
    jtTablePlaylist.setCellSelectionEnabled(false);
    jtTablePlaylist.setShowGrid(false);
    // jtTablePlaylist.setEnabled(false);
    jtTablePlaylist.setRowHeight(30);
    playlistsPanel.add(jtTablePlaylist);

    // borderpanel for the collection of both lists
    JPanel privacySettings = new JPanel(privacydetailBorder);
    privacydetailBorder.setVgap(30);
    privacySettings.add(NumbersPanel, BorderLayout.NORTH);
    privacySettings.add(playlistsPanel, BorderLayout.SOUTH);

    // borderpanel for the left section of the GUI
    JPanel left = new JPanel(privacyBorder);
    privacyBorder.setVgap(20);
    left.setBorder(new EmptyBorder(50, 0, 0, 0));
    left.add(backButton = new BasicArrowButton(BasicArrowButton.WEST), BorderLayout.WEST);
    left.add(settings, BorderLayout.CENTER);
    left.add(privacySettings, BorderLayout.SOUTH);

    // username panel
    JPanel userName = new JPanel(aboutSpacing);
    userName.add(username);

    // about text panel
    JPanel userAbout = new JPanel(aboutSpacing);
    userAbout.add(about);

    // userdetails panel to contain both username and about text
    JPanel userDetails = new JPanel(userdata);
    userDetails.setBorder(new EmptyBorder(100, 0, 0, 0));
    userDetails.add(userName, BorderLayout.NORTH);
    userDetails.add(userAbout);

    // panel of the header for the climate profiles
    JPanel climate = new JPanel(standard);
    climate.add(profiles);

    // edit profiles button panel
    JPanel climateButton = new JPanel(standard);
    climateButton.add(jbEdit);

    // collection borderpanel for climate profiles
    JPanel profile = new JPanel(profiledata);
    profile.setBorder(new EmptyBorder(0, 0, 100, 0));
    profile.add(climate, BorderLayout.NORTH);
    profile.add(climateButton, BorderLayout.SOUTH);

    // collection borderpanel for all panels on the right of the GUI
    JPanel changeViewSettings = new JPanel(userdetails);
    changeViewSettings.add(whitespace, BorderLayout.NORTH);
    changeViewSettings.add(userDetails, BorderLayout.CENTER);
    changeViewSettings.add(profile, BorderLayout.SOUTH);

    // collection panel for all elements
    JPanel borderPnl = new JPanel(collection);
    borderPnl.setBorder(new EmptyBorder(0, 50, 50, 0));
    collection.setHgap(150);
    borderPnl.add(left, BorderLayout.WEST);
    borderPnl.add(changeViewSettings, BorderLayout.EAST);

    add(borderPnl);
    setVisible(true);
    jbEdit.addActionListener(this);
    backButton.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if (e.getSource() == jbEdit) {
      System.out.println("link naar klimaat profielen");
      KlimaatProfiel klimaatProfiel = new KlimaatProfiel();
      this.dispose();
    }
    if (e.getSource() == backButton) {
      Dashboard dash = new Dashboard();
      this.dispose();
    }
  }

  public static void main(String[] args) {
    PersoonlijkeInstellingen first = new PersoonlijkeInstellingen();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getSource() == jtTablePlaylist) {
      if (jtTablePlaylist.getSelectedColumn() == 1) {
        System.out.println("playlist table");
        System.out.println("knop column");
        if (jtTablePlaylist.getSelectedRow() == 0) {
          System.out.println("row 1");
        }
        if (jtTablePlaylist.getSelectedRow() == 1) {
          System.out.println("row 2");
        }
        if (jtTablePlaylist.getSelectedRow() == 2) {
          System.out.println("row 3");
        }
        if (jtTablePlaylist.getSelectedRow() == 3) {
          System.out.println("row 4");
        }
        if (jtTablePlaylist.getSelectedRow() == 4) {
          System.out.println("row 5");
        }
      }
    }
    if (e.getSource() == jtTableNum) {

      if (jtTableNum.getSelectedColumn() == 1) {
        System.out.println("nummer table");
        System.out.println("knop column");
        if (jtTableNum.getSelectedRow() == 0) {
          System.out.println("row 1");
        }
        if (jtTableNum.getSelectedRow() == 1) {
          System.out.println("row 2");
        }
        if (jtTableNum.getSelectedRow() == 2) {
          System.out.println("row 3");
        }
        if (jtTableNum.getSelectedRow() == 3) {
          System.out.println("row 4");
        }
        if (jtTableNum.getSelectedRow() == 4) {
          System.out.println("row 5");

        }
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
