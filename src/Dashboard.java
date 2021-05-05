import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

  //defenitions for getting the current temperature
  static getSensors Temp1 = new getSensors();
  static String piTemp = Temp1.Temperature;

  // definitions for labels, buttons and layouts
  JLabel temp = new JLabel("Temperatuur: " + piTemp);
  JButton jbPlayer = new JButton("Muziekspeler");
  JButton jbClimate = new JButton("Klimaatbeheer");
  JButton jbSettings = new JButton("Instellingen");
  JButton jbOut = new JButton("Uitloggen");

  FlowLayout buttons = new FlowLayout(FlowLayout.RIGHT, 75, 0);
  FlowLayout standard = new FlowLayout();
  BorderLayout collection = new BorderLayout();

  public Dashboard() {
    // set standard data
    setTitle("Dashboard");
    setLayout(new FlowLayout(FlowLayout.CENTER));
    setSize(800, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // temperature panel
    JPanel tempPnl = new JPanel(standard);
    tempPnl.add(temp);
    temp.setFont(temp.getFont().deriveFont(32.0f));
    tempPnl.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

    // panel for the middle buttons
    JPanel btnPnl = new JPanel(buttons);
    btnPnl.add(jbPlayer);
    jbPlayer.setPreferredSize(new Dimension(150, 50));
    jbPlayer.setFont(jbPlayer.getFont().deriveFont(16.0f));
    btnPnl.add(jbClimate);
    jbClimate.setPreferredSize(new Dimension(150, 50));
    jbClimate.setFont(jbClimate.getFont().deriveFont(16.0f));
    btnPnl.add(jbSettings);
    jbSettings.setPreferredSize(new Dimension(150, 50));
    jbSettings.setFont(jbSettings.getFont().deriveFont(16.0f));

    // panel for logging out
    JPanel outPnl = new JPanel(standard);
    outPnl.add(jbOut);
    jbOut.setPreferredSize(new Dimension(150, 50));
    jbOut.setFont(jbOut.getFont().deriveFont(16.0f));

    // border panel serving as collection for the elements
    JPanel borderPnl = new JPanel(collection);
    collection.setVgap(100);
    borderPnl.add(tempPnl, BorderLayout.NORTH);
    borderPnl.add(btnPnl, BorderLayout.CENTER);
    borderPnl.add(outPnl, BorderLayout.SOUTH);

    add(borderPnl);
    jbPlayer.addActionListener(this);
    jbClimate.addActionListener(this);
    jbOut.addActionListener(this);
    jbSettings.addActionListener(this);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if (e.getSource() == jbPlayer) {
      System.out.println("link naar muziekspeler");
      MuziekSpeler musicPlayerGUI = new MuziekSpeler();
      this.dispose();
    }
    if (e.getSource() == jbClimate) {
      System.out.println("link naar klimaatbeheer");
      KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();
      this.dispose();
    }
    if (e.getSource() == jbSettings) {
      System.out.println("link naar persoonlijke instellingen");
      PersoonlijkeInstellingen first = new PersoonlijkeInstellingen();
      this.dispose();
    }
    if (e.getSource() == jbOut) {
      System.out.println("link naar inloggen");
      Inloggen inloggenscherm = new Inloggen();
      this.dispose();
    }
  }

  public static void main(String[] args) {
    Dashboard dash = new Dashboard();
  }
}
