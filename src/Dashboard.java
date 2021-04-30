import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.InputStream;

import com.jcraft.jsch.*;

public class Dashboard extends JFrame implements ActionListener {

  // definitions for labels, buttons and layouts
  JLabel temp = new JLabel("Temperatuur: ");
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
    // tempPnl.add(getTemp());
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

  public static void getTemp() {
    String host = "192.168.0.124";
    String user = "pi";
    String password = "pi";
    String command1 = "sudo python3 Desktop/read.py";

    try {
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(config);
      session.connect();
      System.out.println("Connected");

      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command1);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        while (in.available() > 0) {
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
          System.out.print(new String(tmp, 0, i));
        }
        if (channel.isClosed()) {
          System.out.println("exit-status: " + channel.getExitStatus());
          break;
        }
        try {
          Thread.sleep(1000);
        } catch (Exception ee) {
        }
      }
      channel.disconnect();
      session.disconnect();
      System.out.println("DONE");
    } catch (Exception e) {
      e.printStackTrace();
    }
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
    getTemp();
  }
}
