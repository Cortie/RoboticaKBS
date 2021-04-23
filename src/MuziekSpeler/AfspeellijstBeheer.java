package MuziekSpeler;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AfspeellijstBeheer extends JFrame implements ActionListener{

  JLabel music = new JLabel("Muziekspeler");
  JLabel manage = new JLabel("Afspeellijst beheren");
  JLabel name = new JLabel("Afspeellijst naam");
  JLabel share = new JLabel("Afspeellijst delen");
  JLabel addMusic = new JLabel("Muziek toevoegen aan afspeellijst");

  JTextField pName = new JTextField(10);

  JButton makeP = new JButton("Afspeellijst aanmaken");

  FlowLayout standard = new FlowLayout();
  FlowLayout add = new FlowLayout(FlowLayout.RIGHT, 25, 0);
  FlowLayout select = new FlowLayout(FlowLayout.RIGHT, 50, 0);
  BorderLayout Bname = new BorderLayout();
  BorderLayout Badd = new BorderLayout();
  BorderLayout collection = new BorderLayout();

  public AfspeellijstBeheer(){
    setTitle("Afspeellijst beheren");
    setLayout(new FlowLayout(FlowLayout.CENTER));
    setSize(800, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    JPanel musicTxtPnl = new JPanel(standard);
    musicTxtPnl.add(music);
    music.setFont(music.getFont().deriveFont(16.0f));

    JPanel manageTxtPnl = new JPanel(standard);
    manageTxtPnl.add(manage);
    manage.setFont(manage.getFont().deriveFont(16.0f));

    JPanel musicPnl = new JPanel(Bname);
    musicPnl.add(musicTxtPnl, BorderLayout.NORTH);
    musicPnl.add(manageTxtPnl, BorderLayout.SOUTH);

    JPanel addPnl = new JPanel(add);
    add.setVgap(75);
    addPnl.add(name);
    addPnl.add(pName);
    addPnl.add(makeP);

    JPanel shareA = new JPanel(standard);
    shareA.add(share);
    share.setFont(share.getFont().deriveFont(16.0f));

    JPanel addM = new JPanel(standard);
    addM.add(addMusic);
    addMusic.setFont(addMusic.getFont().deriveFont(16.0f));

    JPanel bottom = new JPanel(select);
    bottom.add(shareA);
    bottom.add(addM);

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
    
  }

  public static void main(String[] args) {
    AfspeellijstBeheer playlist = new AfspeellijstBeheer();
  }
}
