import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.*;

public class PersoonlijkeInstellingen extends JFrame implements ActionListener, MouseListener {
  // definitions for labels, buttons and layouts
  JLabel settings = new JLabel("Persoonlijke instellingen");
  JLabel whitespace = new JLabel(" ");
  JLabel username = new JLabel(Inloggen.getAccountName());
  JLabel about = new JLabel("About tekst");
  JLabel song1 = new JLabel("Nummer 1");
  private BasicArrowButton backButton;
  private JTable jtTempProfile;
  private JTable jtLightProfile;
  private String[] TempcolumnNames = {"Temperature profiles"};
  private String[] lightColumnNames = {"Light strength profiles"};
  DefaultTableModel tempTableModel = new DefaultTableModel(TempcolumnNames, 0);
  DefaultTableModel lightTableModel = new DefaultTableModel(lightColumnNames, 0);

//  String[][] num = { { "nummer 1"}, { "nummer 2"}, { "nummer 3" }, { "nummer 4" },
//      { "nummer 5" }, };
//
//  String[][] profiel = { { "profiel 1" }, { "profiel 2" }, { "profiel 3" },
//      { "profiel 4" }, { "profiel 5" }, };

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
    setSize(1900, 800);//800 600
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // borders
    Border blackline = BorderFactory.createLineBorder(Color.black);

    try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    String url = "jdbc:mysql://localhost/domotica_database";
                    String username = "root", password = "";

                    Connection connection = DriverManager.getConnection(url, username, password);

                    PreparedStatement stmt = connection.prepareStatement("select temp_profile_name from temperature_profile where account_id = ?");

                    stmt.setInt(1, Inloggen.getAccountID());
                    ResultSet temprs = stmt.executeQuery();


                    while (temprs.next()) {
                        String tempTitle = temprs.getString("temp_profile_name");
                        String[] tempData = {tempTitle};

                        tempTableModel.addRow(tempData);

                    }
                    PreparedStatement lightstmt = connection.prepareStatement("select light_strength_profile_name from light_strength_profile where account_id = ?");

                    lightstmt.setInt(1, Inloggen.getAccountID());
                    ResultSet lightrs = lightstmt.executeQuery();


                    while (lightrs.next()) {
                        String lightTitle = lightrs.getString("light_strength_profile_name");
                        String[] lightdata = {lightTitle};

                        lightTableModel.addRow(lightdata);

                    }


                    lightrs.close();
                    temprs.close();
                    connection.close();

                } catch (SQLException sqleEx) {
                    System.out.println("hier gaat het mis(1)");
                    System.out.println(sqleEx.getMessage());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
    GridLayout tabelLayout = new GridLayout(1, 2);
            JPanel tablePanel = new JPanel(tabelLayout);
            tabelLayout.setHgap(15);
            jtTempProfile = new JTable(tempTableModel) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

            };

            jtTempProfile.setShowGrid(false);
            jtTempProfile.getCellSelectionEnabled();
            jtTempProfile.setRowHeight(50);
            jtTempProfile.setRowSelectionAllowed(false);
            jtTempProfile.setBorder(blackline);

    jtLightProfile = new JTable(lightTableModel) {
               public boolean isCellEditable(int row, int column) {
                   return false;
               }
           };

           jtLightProfile.setShowGrid(false);
           jtLightProfile.getCellSelectionEnabled();
           jtLightProfile.setRowHeight(50);
           jtLightProfile.setRowSelectionAllowed(false);
           jtLightProfile.setBorder(blackline);
           jtLightProfile.getColumnModel().getColumnSelectionAllowed();
    JScrollPane tempscrollpane = new JScrollPane(jtTempProfile);
            JScrollPane lightscrollpane = new JScrollPane(jtLightProfile);

            tablePanel.add(tempscrollpane);
            tablePanel.add(lightscrollpane);
            add(tablePanel);



      // panel for list of songs
//    JPanel NumbersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//    NumbersPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//    String[] headerN = { "nummers" };
//    jtTableNum = new JTable(num, headerN) {
//      public boolean isCellEditable(int row, int column) {
//        return false;
//      }
//    };
//    jtTableNum.addMouseListener(this);
//    jtTableNum.setCellSelectionEnabled(false);
//    jtTableNum.setShowGrid(false);
//    // jtTableNum.setEnabled(false);
//    jtTableNum.setRowHeight(30);
//
//    NumbersPanel.add(jtTableNum);

    // panel for list of playlists
//    JPanel playlistsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//    playlistsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//    String[] headerP = { "profiel" };
//    jtTableProfiel = new JTable(profiel, headerP) {
//      public boolean isCellEditable(int row, int column) {
//        return false;
//      }
//    };
//    jtTableProfiel.addMouseListener(this);
//    jtTableProfiel.setCellSelectionEnabled(false);
//    jtTableProfiel.setShowGrid(false);
//    // jtTablePlaylist.setEnabled(false);
//    jtTableProfiel.setRowHeight(30);
//    playlistsPanel.add(jtTableProfiel);

    // borderpanel for the collection of both lists
    JPanel privacySettings = new JPanel(privacydetailBorder);
    privacydetailBorder.setVgap(30);
    privacySettings.add(tablePanel,BorderLayout.SOUTH);
    //privacySettings.add(NumbersPanel, BorderLayout.NORTH);
    //privacySettings.add(playlistsPanel, BorderLayout.SOUTH);

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
//    if (e.getSource() == jtTableProfiel) {
//      if (jtTableProfiel.getSelectedColumn() == 1) {
//        System.out.println("playlist table");
//        System.out.println("knop column");
//        if (jtTableProfiel.getSelectedRow() == 0) {
//          System.out.println("row 1");
//        }
//        if (jtTableProfiel.getSelectedRow() == 1) {
//          System.out.println("row 2");
//        }
//        if (jtTableProfiel.getSelectedRow() == 2) {
//          System.out.println("row 3");
//        }
//        if (jtTableProfiel.getSelectedRow() == 3) {
//          System.out.println("row 4");
//        }
//        if (jtTableProfiel.getSelectedRow() == 4) {
//          System.out.println("row 5");
//        }
//      }
//    }
//    if (e.getSource() == jtTableNum) {
//
////      if (jtTableNum.getSelectedColumn() == 1) {
////
////
//////        System.out.println("nummer table");
//////        System.out.println("knop column");
//////        if (jtTableNum.getSelectedRow() == 0) {
//////          System.out.println("row 1");
//////        }
//////        if (jtTableNum.getSelectedRow() == 1) {
//////          System.out.println("row 2");
//////        }
//////        if (jtTableNum.getSelectedRow() == 2) {
//////          System.out.println("row 3");
//////        }
//////        if (jtTableNum.getSelectedRow() == 3) {
//////          System.out.println("row 4");
//////        }
//////        if (jtTableNum.getSelectedRow() == 4) {
//////          System.out.println("row 5");
//////        }
////
////      }
//    }
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
