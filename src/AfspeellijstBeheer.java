import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.event.*;

public class AfspeellijstBeheer extends JFrame implements ActionListener{
  //definitions for labels, buttons and layouts
  JLabel music = new JLabel("Muziekspeler");
  JLabel manage = new JLabel("Afspeellijst beheren");
  JLabel name = new JLabel("Afspeellijst naam");
  JLabel share = new JLabel("Afspeellijst delen");
  JLabel addMusic = new JLabel("Muziek toevoegen aan afspeellijst");
  private JRadioButton jrbAfspeelllijst1;
  private JRadioButton jrbAfspeelllijst2;
  private BasicArrowButton backButton;
  private JButton jbNummer1;
  private JButton jbNummer2;
  private JButton jbNummer3;
  private JButton jbNummer4;
  private JButton jbNummer5;

  JTextField pName = new JTextField(10);

  JButton jbMakeP = new JButton("Afspeellijst aanmaken");

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
    musicTxtPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
    backButton.addActionListener(this);
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
    addPnl.add(jbMakeP);
    jbMakeP.addActionListener(this);

    //sharing of the playlists
    JPanel afspeellijstListPnl = new JPanel(new GridLayout(2,1));
    afspeellijstListPnl.add(jrbAfspeelllijst1 = new JRadioButton("Afspeellijst1"));
    jrbAfspeelllijst1.addActionListener(this);
    afspeellijstListPnl.add(jrbAfspeelllijst2 = new JRadioButton("Afspeellijst2"));
    jrbAfspeelllijst2.addActionListener(this);

    JPanel shareA = new JPanel(new BorderLayout());
    shareA.add(share,BorderLayout.NORTH);
    share.setFont(share.getFont().deriveFont(16.0f));
    shareA.add(afspeellijstListPnl,BorderLayout.CENTER);

    //adding songs to playlist

    JPanel lijstMusicAanAfspeellijst = new JPanel(new GridLayout(5,2));
    lijstMusicAanAfspeellijst.add(new JLabel("nummer 1 "));
    lijstMusicAanAfspeellijst.add(jbNummer1 = new JButton("+"));
    jbNummer1.addActionListener(this);
    lijstMusicAanAfspeellijst.add(new JLabel("nummer 2 "));
    lijstMusicAanAfspeellijst.add(jbNummer2 = new JButton("+"));
    jbNummer2.addActionListener(this);
    lijstMusicAanAfspeellijst.add(new JLabel("nummer 3 "));
    lijstMusicAanAfspeellijst.add(jbNummer3 = new JButton("+"));
    jbNummer3.addActionListener(this);
    lijstMusicAanAfspeellijst.add(new JLabel("nummer 4 "));
    lijstMusicAanAfspeellijst.add(jbNummer4 = new JButton("+"));
    jbNummer4.addActionListener(this);
    lijstMusicAanAfspeellijst.add(new JLabel("nummer 5 "));
    lijstMusicAanAfspeellijst.add(jbNummer5 = new JButton("+"));
    jbNummer5.addActionListener(this);

    JPanel addM = new JPanel(new BorderLayout());
    addM.add(addMusic,BorderLayout.NORTH);
    addMusic.setFont(addMusic.getFont().deriveFont(16.0f));
    addM.add(lijstMusicAanAfspeellijst,BorderLayout.CENTER);
    addM.setBorder(BorderFactory.createLineBorder(Color.black));

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
    if (e.getSource()==jbMakeP)
    {
      System.out.println("doe nog iets met knop");
    }
    if (e.getSource()==backButton)
    {
      MuziekSpeler musicPlayerGUI = new MuziekSpeler();
      this.dispose();
    }
    if (e.getSource()==jrbAfspeelllijst1)
    {
      if (jrbAfspeelllijst1.isSelected()==true)
      {
        jrbAfspeelllijst1.setText("Afspeellijst 1 (gedeeld)");
      }
      if (jrbAfspeelllijst1.isSelected()==false)
      {
        jrbAfspeelllijst1.setText("Afspeellijst 1");
      }
    }
    if (e.getSource()==jrbAfspeelllijst2)
    {
      if (jrbAfspeelllijst2.isSelected()==true)
      {
        jrbAfspeelllijst2.setText("Afspeellijst 2 (gedeeld)");
      }
      if (jrbAfspeelllijst2.isSelected()==false)
      {
        jrbAfspeelllijst2.setText("Afspeellijst 2");
      }
    }
    if (e.getSource()==jbNummer1)
    {
      AfspeelllijstBeheerDialoog dialoog = new AfspeelllijstBeheerDialoog(this, true);
    }
    if (e.getSource()==jbNummer2)
    {
      AfspeelllijstBeheerDialoog dialoog = new AfspeelllijstBeheerDialoog(this, true);
    }
    if (e.getSource()==jbNummer3)
    {
      AfspeelllijstBeheerDialoog dialoog = new AfspeelllijstBeheerDialoog(this, true);
    }
    if (e.getSource()==jbNummer4)
    {
      AfspeelllijstBeheerDialoog dialoog = new AfspeelllijstBeheerDialoog(this, true);
    }
    if (e.getSource()==jbNummer5)
    {
      AfspeelllijstBeheerDialoog dialoog = new AfspeelllijstBeheerDialoog(this, true);
    }
  }

  public static void main(String[] args) {
    AfspeellijstBeheer playlist = new AfspeellijstBeheer();
  }
}
