package MuziekSpeler;

import Inloggen.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuziekSpeler extends JFrame implements ActionListener {

    private JLabel jlMusicPlayer;
    private JLabel jlMenu;
    private JComboBox jcPlaylist;
    private JButton jbManagePlaylist;
    private JButton jbManageMusic;

    private String[] selectPlaylist;

    public MuziekSpeler(){
        setTitle("Klimaat Systeem");
        setSize(800,600);
        setLayout(new FlowLayout());

        JPanel musicPlayerPanel = new JPanel(new FlowLayout());
        jlMusicPlayer = new JLabel("Muziekspeler");
        musicPlayerPanel.add(jlMusicPlayer);

        JPanel menuPanel = new JPanel(new FlowLayout());
        jlMenu = new JLabel("Menu");
        menuPanel.add(jlMenu);

        //dropdown menu voor het selecteren van een afspeellijst
        JPanel dropdownPanel = new JPanel(new FlowLayout());
        selectPlaylist = new String[] {"Afspeellijst selecteren","liedje 1", "liedje2"};
        jcPlaylist = new JComboBox(selectPlaylist);
        dropdownPanel.add(jcPlaylist);

        JPanel managePlaylistPanel = new JPanel(new FlowLayout());
        jbManagePlaylist = new JButton("Afspeellijst beheren");
        managePlaylistPanel.add(jbManagePlaylist);


        JPanel manageMusicPanel = new JPanel(new FlowLayout());
        jbManageMusic = new JButton("Muziek beheren");
        manageMusicPanel.add(jbManageMusic);


        JPanel borderPanel1 = new JPanel(new BorderLayout());
        borderPanel1.add(musicPlayerPanel,BorderLayout.NORTH);
        borderPanel1.add(menuPanel,BorderLayout.CENTER);

        JPanel borderPanel2 = new JPanel(new BorderLayout());
        borderPanel2.add(dropdownPanel,BorderLayout.NORTH);
        borderPanel2.add(managePlaylistPanel,BorderLayout.CENTER);
        borderPanel2.add(manageMusicPanel,BorderLayout.SOUTH);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        borderPanel1.add(borderPanel2,BorderLayout.SOUTH);
        add(borderPanel1);

        setVisible(true);






    }


    public static void main(String[] args)
    {
       MuziekSpeler musicPlayerGUI = new MuziekSpeler();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
