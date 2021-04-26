package MuziekSpeler;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AfspeellijstBeheer extends JFrame implements ActionListener{
  //definitions for labels, buttons and layouts
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
    //set standard data
    setTitle("Afspeellijst beheren");
    setLayout(new FlowLayout(FlowLayout.CENTER));
    setSize(800, 600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    //temperature panel
    JPanel musicTxtPnl = new JPanel(standard);
    musicTxtPnl.add(music);
    music.setFont(music.getFont().deriveFont(16.0f));

    //manage panel
    JPanel manageTxtPnl = new JPanel(standard);
    manageTxtPnl.add(manage);
    manage.setFont(manage.getFont().deriveFont(16.0f));

    //collection panel for temperature and managa texts
    JPanel musicPnl = new JPanel(Bname);
    musicPnl.add(musicTxtPnl, BorderLayout.NORTH);
    musicPnl.add(manageTxtPnl, BorderLayout.SOUTH);

    //panel to give the playlist new name
    JPanel addPnl = new JPanel(add);
    add.setVgap(75);
    addPnl.add(name);
    addPnl.add(pName);
    addPnl.add(makeP);

    //sharing of the playlists 
    JPanel shareA = new JPanel(standard);
    shareA.add(share);
    share.setFont(share.getFont().deriveFont(16.0f));

    //adding songs to playlist
    JPanel addM = new JPanel(standard);
    addM.add(addMusic);
    addMusic.setFont(addMusic.getFont().deriveFont(16.0f));

    //collection panel for sharing and adding songs panels
    JPanel bottom = new JPanel(select);
    bottom.add(shareA);
    bottom.add(addM);

    //collection panel for all elements
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
