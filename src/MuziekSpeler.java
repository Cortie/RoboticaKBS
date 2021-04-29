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

        JPanel TitelPanel = new JPanel(new FlowLayout());
        jlMusicPlayer = new JLabel("Muziekspeler");
        TitelPanel.add(jlMusicPlayer);

        JPanel menuPanel = new JPanel(new FlowLayout());
        jlMenu = new JLabel("Menu");
        menuPanel.add(jlMenu);


        //dropdown menu panel voor het selecteren van een afspeellijst
        FlowLayout dropDownLayout = new FlowLayout();
        JPanel dropdownPanel = new JPanel(dropDownLayout);
        selectPlaylist = new String[] {"Afspeellijst selecteren","liedje 1", "liedje2"};
        jcPlaylist = new JComboBox(selectPlaylist);
        dropdownPanel.add(jcPlaylist);
        jcPlaylist.addActionListener(this);

        //panel voor de knoppen

        BorderLayout knopLayout = new BorderLayout();
        JPanel buttonPanel = new JPanel(knopLayout);
        knopLayout.setVgap(5);
        jbManagePlaylist = new JButton("Afspeellijst beheren");
        jbManageMusic = new JButton("Muziek beheren");
        buttonPanel.add(jbManageMusic,BorderLayout.NORTH);
        buttonPanel.add(jbManagePlaylist,BorderLayout.CENTER);
        jbManageMusic.addActionListener(this);
        jbManagePlaylist.addActionListener(this);



        JPanel borderPanel1 = new JPanel(new BorderLayout());
        borderPanel1.add(TitelPanel,BorderLayout.NORTH);
        borderPanel1.add(menuPanel,BorderLayout.CENTER);


        JPanel borderPanel2 = new JPanel(new BorderLayout());
        borderPanel2.add(dropdownPanel,BorderLayout.NORTH);
        borderPanel2.add(buttonPanel,BorderLayout.CENTER);



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
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==jbManageMusic)
        {
            System.out.println("link naar muziek beheren");
            MuziekBeheren muziekBeherenscher = new MuziekBeheren();
            this.dispose();
        }
        if (e.getSource()==jbManagePlaylist)
        {
            System.out.println("link naar afspeellijst beheren");
            AfspeellijstBeheer playlist = new AfspeellijstBeheer();
            this.dispose();
        }
        if (e.getSource()==jcPlaylist)
        {
            System.out.println("link naar de echte muziekspeler");
            MuziekAfspeler muziekAfspelerscherm = new MuziekAfspeler();
            this.dispose();
        }
    }
}
