import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

  //definitions for labels, buttons and layouts
  JLabel temp = new JLabel("Temperatuur: ");
  JButton player = new JButton("Muziekspeler");
  JButton climate = new JButton("Klimaatbeheer");
  JButton settings = new JButton("Instellingen");
  JButton out = new JButton("Uitloggen");

  FlowLayout buttons = new FlowLayout(FlowLayout.RIGHT, 75, 0);
  FlowLayout standard = new FlowLayout();
  BorderLayout collection = new BorderLayout();
  

  public Dashboard() {
    //set standard data
    setTitle("Dashboard");
    setLayout(new FlowLayout(FlowLayout.CENTER));
    setSize(800, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    //temperature panel
    JPanel tempPnl = new JPanel(standard);
    tempPnl.add(temp);
    temp.setFont(temp.getFont().deriveFont(32.0f));
    tempPnl.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
    
    //panel for the middle buttons
    JPanel btnPnl = new JPanel(buttons);
    btnPnl.add(player);
    player.setPreferredSize(new Dimension(150,50));
    player.setFont(player.getFont().deriveFont(16.0f));
    btnPnl.add(climate);
    climate.setPreferredSize(new Dimension(150,50));
    climate.setFont(climate.getFont().deriveFont(16.0f));
    btnPnl.add(settings);
    settings.setPreferredSize(new Dimension(150,50));
    settings.setFont(settings.getFont().deriveFont(16.0f));

    //panel for logging out
    JPanel outPnl = new JPanel(standard);
    outPnl.add(out);
    out.setPreferredSize(new Dimension(150,50));
    out.setFont(out.getFont().deriveFont(16.0f));

    //border panel serving as collection for the elements
    JPanel borderPnl = new JPanel(collection);
    collection.setVgap(100);
    borderPnl.add(tempPnl, BorderLayout.NORTH);
    borderPnl.add(btnPnl, BorderLayout.CENTER);
    borderPnl.add(outPnl, BorderLayout.SOUTH);

    add(borderPnl);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub

  }

  public static void main(String[] args) {
    Dashboard dash = new Dashboard();
  }
}
