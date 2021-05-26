import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;


public class MuziekAfspeler extends JFrame implements ActionListener, MouseListener
{
    private final BasicArrowButton backButton;
    private final JLabel jlTitel;
    private final JLabel jlAfspeellijst;
    private JLabel jlNummer;
//    private JSlider jsNumerTijd;
//    private JLabel jlNummerTijdWaarde;
//    private int nummerTijdWaarde;
    private final JButton jbVorigeAfspelen;
    private final JButton jbPauzeAfspelen;
    private final JButton jbVolgendeAfspelen;
    private final JButton jbAfspeellijstBeheren;
    private final JButton jbMuziekBeheren;
    private final JTable jtTempSong;
    private final String[] TempcolumnNames = {"Muziek nummers"};
    public int finalSong;
    DefaultTableModel tempTableModel = new DefaultTableModel(TempcolumnNames, 0);
    private final Music listener = new Music(this);
    private boolean play;
    private int thisNote;
    private Thread listenerThread;
    
    public int getThisNote()
    {
        return thisNote;
    }
    
    public void setThisNote(int thisNote)
    {
        this.thisNote = thisNote;
    }
    
    public boolean isPlay()
    {
        return play;
    }
    
    public MuziekAfspeler() {
        songData();
        setTitle("Klimaat systeem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel titelPnl = new JPanel(new FlowLayout());
        titelPnl.add(backButton = new BasicArrowButton(BasicArrowButton.WEST));
        titelPnl.add(jlTitel = new JLabel("Dashboard"));
        backButton.addActionListener(this);

        JPanel subTitels = new JPanel(new BorderLayout());
        subTitels.add(jlAfspeellijst = new JLabel("naam afspeellijst"), BorderLayout.NORTH);
        GridLayout tabelLayout = new GridLayout(1, 2);
        JPanel tablePanel = new JPanel(tabelLayout);
        tabelLayout.setHgap(15);
        jtTempSong = new JTable(tempTableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        Border blackline = BorderFactory.createLineBorder(Color.black);
        jtTempSong.setShowGrid(true);
        jtTempSong.getCellSelectionEnabled();
        jtTempSong.setRowHeight(50);
        jtTempSong.setRowSelectionAllowed(true);
        jtTempSong.setBorder(blackline);
        jtTempSong.addMouseListener(this);
        
        subTitels.add(jtTempSong);
        JPanel titelsPnl = new JPanel(new BorderLayout());
        titelsPnl.add(titelPnl, BorderLayout.NORTH);
        titelsPnl.add(subTitels, BorderLayout.CENTER);

//        JPanel nummertijdPnl = new JPanel(new FlowLayout());
//        nummertijdPnl.add(jsNumerTijd = new JSlider());
//        nummertijdPnl.add(
//                jlNummerTijdWaarde = new JLabel(String.valueOf(nummerTijdWaarde = jsNumerTijd.getValue()) + " sec"));

        JPanel nummerKnoppenPnl = new JPanel(new FlowLayout());
        nummerKnoppenPnl.add(jbVorigeAfspelen = new JButton("Vorige afspelen"));
        jbVorigeAfspelen.addActionListener(this);
        nummerKnoppenPnl.add(jbPauzeAfspelen = new JButton("Pauzeren/afspelen"));
        jbPauzeAfspelen.addActionListener(this);
        nummerKnoppenPnl.add(jbVolgendeAfspelen = new JButton("Volgende afspelen"));
        jbVolgendeAfspelen.addActionListener(this);

        JPanel muziekKnoppenPnl = new JPanel(new FlowLayout());
        muziekKnoppenPnl.add(jbAfspeellijstBeheren = new JButton("Afspeellijst beheren"));
        jbAfspeellijstBeheren.addActionListener(this);
        muziekKnoppenPnl.add(jbMuziekBeheren = new JButton("Muziek beheren"));
        jbMuziekBeheren.addActionListener(this);

        JPanel knoppenPnl = new JPanel(new BorderLayout());
        knoppenPnl.add(nummerKnoppenPnl, BorderLayout.NORTH);
        knoppenPnl.add(muziekKnoppenPnl, BorderLayout.CENTER);

        JPanel ondersteGedeeltePnl = new JPanel(new BorderLayout());
//        ondersteGedeeltePnl.add(nummertijdPnl, BorderLayout.NORTH);
        ondersteGedeeltePnl.add(knoppenPnl, BorderLayout.CENTER);

        JPanel borderPnl = new JPanel(new BorderLayout());
        borderPnl.add(titelsPnl, BorderLayout.NORTH);
        borderPnl.add(ondersteGedeeltePnl, BorderLayout.SOUTH);

        add(borderPnl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbVorigeAfspelen) {
            previousSong();
        }
        if (e.getSource() == jbPauzeAfspelen) {
            pauseButton();
        }
        if (e.getSource() == jbVolgendeAfspelen) {
            nextSong();
        }
        if (e.getSource() == jbAfspeellijstBeheren) {
            AfspeellijstBeheer playlist = new AfspeellijstBeheer();
            this.dispose();
        }
        if (e.getSource() == jbMuziekBeheren) {
            MuziekBeheren muziekBeherenscher = new MuziekBeheren();
            this.dispose();
        }
        if (e.getSource() == backButton) {
            Dashboard musicPlayerGUI = new Dashboard();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        MuziekAfspeler muziekAfspelerscherm = new MuziekAfspeler();
        
    }
    public void nextSong()
    {
        if(listener.currentSong < listener.songLength)
        {
            listener.currentSong++;
        }else
        {
            listener.currentSong = 1;
        }
        thisNote = 1;
    }
    public void previousSong()
    {
        if(listener.currentSong > 1)
        {
            listener.currentSong--;
        }else
        {
            listener.currentSong = listener.songLength;
        }
        thisNote =1;
    }
    public void pauseButton()
    {
        play = !play;
        if(play)
        {
            listenerThread = new Thread(this.listener);
            listenerThread.setDaemon(true);
            listenerThread.start();
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }
        }
        if(!play)
        {
            listener.port.closePort();
            listenerThread.stop();
        }
    }
    public void songData()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            String url = "jdbc:mysql://localhost/domotica_database";
            String username = "root", password = "";
        
            Connection connection = DriverManager.getConnection(url, username, password);
        
            PreparedStatement userstmt = connection.prepareStatement("select song_name from song ORDER BY song_id ASC");
            ResultSet songs = userstmt.executeQuery();
            while(songs.next())
            {
                String tempTitle = songs.getString("song_name");
                String[] tempData = { tempTitle } ;
            
                tempTableModel.addRow(tempData);
            }
            songs.close();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(song_id) FROM song");
            while(rs.next())
            {
                finalSong = rs.getInt(1);
            }
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void setSong(int num)
    {
        listener.currentSong = num;
        thisNote = 1;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == jtTempSong)
        {
            if(jtTempSong.getSelectedColumn() == 0)
            {
                if(jtTempSong.getSelectedRow() == 1)
                {
                    setSong(2);
                }
                switch (jtTempSong.getSelectedRow())
                {
                    case 0 -> setSong(1);
                    case 1 -> setSong(2);
                    case 2 -> setSong(3);
                    case 3 -> setSong(4);
                    case 4 -> setSong(5);
                }
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
    
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
    
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {
    
    }
    
    @Override
    public void mouseExited(MouseEvent e)
    {
    
    }
}
