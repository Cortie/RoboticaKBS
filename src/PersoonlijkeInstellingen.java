import com.mysql.cj.protocol.Resultset;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.*;

public class PersoonlijkeInstellingen extends JFrame implements ActionListener, MouseListener {
  // definitions for labels, buttons and layouts
  private JLabel settings = new JLabel("Persoonlijke instellingen");
  private JLabel username = new JLabel("accountnaam: " + Inloggen.getAccountName());
  private JLabel tempProfile;
  private JLabel lightProfile;
  private BasicArrowButton backButton;
  private JTable jtTempProfile;
  private JTable jtLightProfile;
  private String[] TempcolumnNames = {"Temperatuur profielen"};
  private String[] lightColumnNames = {"Lichtsterkte profielen"};
  private String profilename;
  public static int lightSetting;
  public static int tempSetting;

  DefaultTableModel tempTableModel = new DefaultTableModel(TempcolumnNames, 0);
  DefaultTableModel lightTableModel = new DefaultTableModel(lightColumnNames, 0);

  JButton jbEdit = new JButton("Profielen aanpassen");


  BorderLayout privacyBorder = new BorderLayout();


  public PersoonlijkeInstellingen() {
    // set standard data
    setTitle("Persoonlijke instellingen");
    setLayout(new GridLayout(4,1));
    setSize(800, 600);
    setLocationRelativeTo(null);
      Image icon = Toolkit.getDefaultToolkit().getImage("logo.PNG");
          this.setIconImage(icon);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // borders
    Border blackline = BorderFactory.createLineBorder(Color.black);

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost/domotica_database";
        String username = "root", password = "";

        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement lightStmt = connection.prepareStatement("select light_strength_profile_name, profile_light_strength from light_strength_profile where is_selected = 1");
        ResultSet rs = lightStmt.executeQuery();
        rs.next();
        String defaultLightProfile = rs.getString(1);
        int defaultLightValue = rs.getInt(2);
        lightProfile = new JLabel("geselecteerde lichtsterkte profiel: "+defaultLightProfile );
        lightSetting = defaultLightValue;

        rs.close();

        PreparedStatement tempStmt = connection.prepareStatement("select temp_profile_name ,profile_temperature from temperature_profile where is_selected = 1");
        ResultSet tempRs = tempStmt.executeQuery();
        tempRs.next();
        String defaultTempProfile = tempRs.getString(1);
        int defaultTempValue = tempRs.getInt(2);
        tempProfile = new JLabel("geselecteerde temperatuur profiel: "+ defaultTempProfile);
        tempSetting =defaultTempValue;

        tempRs.close();
        connection.close();

    }catch(SQLException sqle){

        lightProfile = new JLabel("geselecteerde lichtsterkte profiel: geen profiel geselecteerd");
        tempProfile = new JLabel("geselecteerde temperatuur profiel: geen profiel geselecteerd");
    }catch(Exception e){
        System.out.println(e.getMessage());
    }



      // userdetails panel to contain both username and about text
      settings.setFont(new Font("Arial",Font.BOLD,20 ));

      JPanel left = new JPanel(privacyBorder);

      left.add(backButton = new BasicArrowButton(BasicArrowButton.WEST), BorderLayout.WEST);
      left.add(settings, BorderLayout.CENTER);


      JPanel userDetails = new JPanel(new GridLayout(3,1));
      userDetails.add(username);
      userDetails.add(tempProfile);
      userDetails.add(lightProfile);



      JPanel titelPanel = new JPanel(new BorderLayout());
      titelPanel.add(left, BorderLayout.NORTH);


      add(titelPanel);
      JPanel midPanel = new JPanel(new BorderLayout());
      midPanel.add(userDetails,BorderLayout.NORTH);
      JPanel buttonPanel = new JPanel(new FlowLayout());
      buttonPanel.add(jbEdit);
      midPanel.add(buttonPanel,BorderLayout.SOUTH);

      add(midPanel);


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
            jtTempProfile.addMouseListener((MouseListener) this);

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
           jtLightProfile.addMouseListener((MouseListener) this);

            JScrollPane tempscrollpane = new JScrollPane(jtTempProfile);
            JScrollPane lightscrollpane = new JScrollPane(jtLightProfile);
            tempscrollpane.setPreferredSize(new Dimension(200,150));
            lightscrollpane.setPreferredSize(new Dimension(200,150));


            JPanel southPanel = new JPanel(new BorderLayout());
            tablePanel.add(tempscrollpane);
            tablePanel.add(lightscrollpane);

            southPanel.add(tablePanel, BorderLayout.CENTER);
            add(southPanel);



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

      if(e.getSource() == jtTempProfile){
          int column = jtTempProfile.getSelectedColumn();
          int row = jtTempProfile.getSelectedRow();
          profilename = jtTempProfile.getModel().getValueAt(row, column).toString();


          try{
              Class.forName("com.mysql.cj.jdbc.Driver");

              String url = "jdbc:mysql://localhost/domotica_database";
              String username = "root", password = "";

              Connection connection = DriverManager.getConnection(url, username, password);

              PreparedStatement selectStmt = connection.prepareStatement("Update temperature_profile set is_selected = 1 where temp_profile_name = ? ");

              selectStmt.setString(1,profilename);

              int i = selectStmt.executeUpdate();
              System.out.println(i + "temperatuur profiel geupdated");

              selectStmt.close();

              PreparedStatement setFalseStmt = connection.prepareStatement("Update temperature_profile set is_selected = 0 where temp_profile_name not like ? ");
              setFalseStmt.setString(1,profilename);

              int falseStatement = setFalseStmt.executeUpdate();

              System.out.println(falseStatement + "temperatuur profielen geupdated");

              setFalseStmt.close();


              PreparedStatement getIngesteldeTempwaarde = connection.prepareStatement("select profile_temperature from temperature_profile where is_selected = 1");
              ResultSet rs =  getIngesteldeTempwaarde.executeQuery();
              rs.next();
              int tempWaarde = rs.getInt(1);
              tempSetting =tempWaarde;

              rs.close();
              getIngesteldeTempwaarde.close();
              connection.close();

          }catch(SQLException sqlEx){
              System.out.println(sqlEx.getMessage());
          }catch(Exception ex){
              System.out.println(ex.getMessage());
          }

          tempProfile.setText("geselecteerde temperatuur profiel: " + profilename);
          SwingUtilities.updateComponentTreeUI(this);
      }

      if(e.getSource() == jtLightProfile){
          int column = jtLightProfile.getSelectedColumn();
          int row = jtLightProfile.getSelectedRow();
          profilename = jtLightProfile.getModel().getValueAt(row, column).toString();


          try{
              Class.forName("com.mysql.cj.jdbc.Driver");

              String url = "jdbc:mysql://localhost/domotica_database";
              String username = "root", password = "";

              Connection connection = DriverManager.getConnection(url, username, password);

              PreparedStatement selectStmt = connection.prepareStatement("Update light_strength_profile set is_selected = 1 where light_strength_profile_name = ? ");
              selectStmt.setString(1,profilename);

              int i = selectStmt.executeUpdate();
              System.out.println(i + "lichtsterkte profiel geupdated");

              selectStmt.close();

              PreparedStatement setFalseStmt = connection.prepareStatement("Update light_strength_profile set is_selected = 0 where light_strength_profile_name not like ? ");
              setFalseStmt.setString(1,profilename);

              int falseStatement = setFalseStmt.executeUpdate();

              System.out.println(falseStatement + "lichtsterkte profielen geupdated");

              setFalseStmt.close();

              PreparedStatement getIngesteldeLichtwaarde = connection.prepareStatement("select profile_light_strength from light_strength_profile where is_selected = 1");
              ResultSet rs =  getIngesteldeLichtwaarde.executeQuery();
              rs.next();
              int lichtWaarde = rs.getInt(1);
              lightSetting = lichtWaarde;

              rs.close();
              getIngesteldeLichtwaarde.close();

              connection.close();

          }catch(SQLException sqlEx){
              System.out.println(sqlEx.getMessage());
          }catch(Exception ex){
              System.out.println(ex.getMessage());
          }

          lightProfile.setText("geselecteerde lichtsterkte profiel: " + profilename);
          SwingUtilities.updateComponentTreeUI(this);
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
