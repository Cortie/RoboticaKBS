import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class KlimaatProfiel extends JFrame implements ActionListener, MouseListener {
    private JLabel jlTitel;
    private BasicArrowButton backButton;
    private JButton jbCreateTempProfile;
    private JButton jbCreateLightProfile;
    private JTable jtTempProfile;
    private JTable jtLightProfile;
    private String[] TempcolumnNames = {"Temperature profiles"};
    private String[] lightColumnNames = {"Light strength profiles"};
    private static String profilename;
    private Boolean errorCheck = false;
    private JLabel jlErrorMessage;


    DefaultTableModel tempTableModel = new DefaultTableModel(TempcolumnNames, 0);
    DefaultTableModel lightTableModel = new DefaultTableModel(lightColumnNames, 0);

    public KlimaatProfiel() {
        setTitle("Klimaat Systeem");
        setSize(800, 600);
        setLayout(new GridLayout(4, 3));
        setLocationRelativeTo(null);
        // borders
        Border blackline = BorderFactory.createLineBorder(Color.black);

        // HeadPanel
        FlowLayout headLayout = new FlowLayout();
        GridLayout headLayout2 = new GridLayout(1, 2);
        JPanel headPanel = new JPanel(headLayout);

        jlTitel = new JLabel("Profielen aanpassen");
        jlTitel.setFont(new Font("arial", Font.BOLD, 20));

        backButton = new BasicArrowButton(BasicArrowButton.WEST);
        backButton.addActionListener(this);
        headLayout.setAlignment(FlowLayout.LEFT);
        headLayout.setHgap(150);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel errorPanel = new JPanel(new FlowLayout());
        jlErrorMessage = new JLabel();
        errorPanel.add(jlErrorMessage);

        headPanel.add(backButton);
        headPanel.add(jlTitel);

        add(headPanel);

        // buttonPanel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        jbCreateTempProfile = new JButton("Temperatuur profiel aanmaken");
        jbCreateLightProfile = new JButton("Lichtsterkte profiel aanmaken");
        jbCreateTempProfile.addActionListener(this);
        jbCreateLightProfile.addActionListener(this);
        buttonPanel.add(jbCreateTempProfile);
        buttonPanel.add(jbCreateLightProfile);
        add(buttonPanel);



        //ophalen van de profielen uit de database
        if(Inloggen.getAccountID() != 0 ) {
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
        }else{
            errorCheck = true;
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

        tablePanel.add(tempscrollpane);
        tablePanel.add(lightscrollpane);
        add(tablePanel);
        add(errorPanel);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();
            this.dispose();
        }
        if (e.getSource() == jbCreateTempProfile) {

            if(Inloggen.getAccountID() != 0) {
                KlimaatProfielDialoogAanmakenTempProfiel createTempDialog = new KlimaatProfielDialoogAanmakenTempProfiel(this, true);

                if (!createTempDialog.isVisible()) {

                    KlimaatProfiel klimaatProfiel = new KlimaatProfiel();
                    this.dispose();

                }
            }else{
                errorCheck = true;
            }

        }
        if (e.getSource() == jbCreateLightProfile) {

            if(Inloggen.getAccountID() != 0) {
                KlimaatProfielDialoogAanmakenLichtProfiel createLightDialog = new KlimaatProfielDialoogAanmakenLichtProfiel(this,
                        true);
                if (!createLightDialog.isVisible()) {

                    KlimaatProfiel klimaatProfiel = new KlimaatProfiel();
                    this.dispose();

                }
            }else{
                errorCheck = true;
            }
        }

        if(errorCheck){
            jlErrorMessage.setText("je bent niet ingelogd");
            SwingUtilities.updateComponentTreeUI(this);
            errorCheck = false;
        }
    }

    public static void main(String[] args) {
        KlimaatProfiel klimaatProfiel = new KlimaatProfiel();

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == jtTempProfile) {

           int column = jtTempProfile.getSelectedColumn();
           int row = jtTempProfile.getSelectedRow();
           profilename = jtTempProfile.getModel().getValueAt(row, column).toString();
            KlimaatProfielDialoogAanpassenTempProfiel changeTempDialog = new KlimaatProfielDialoogAanpassenTempProfiel(
                    this, true);

            if(!changeTempDialog.isVisible()){

                KlimaatProfiel klimaatProfiel = new KlimaatProfiel();
                this.dispose();

            }

        }
        if (e.getSource() == jtLightProfile) {

            int column = jtLightProfile.getSelectedColumn();
            int row = jtLightProfile.getSelectedRow();
            profilename = jtLightProfile.getModel().getValueAt(row, column).toString();
            KlimaatProfielDialoogAanpassenLichtProfiel changeLightDialog = new KlimaatProfielDialoogAanpassenLichtProfiel(
                    this, true);

            if(!changeLightDialog.isVisible()){

                KlimaatProfiel klimaatProfiel1 = new KlimaatProfiel();
                this.dispose();


            }
        }
    }

    public static String getProfilename() {
        return profilename;
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
