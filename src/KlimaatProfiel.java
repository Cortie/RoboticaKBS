import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;

public class KlimaatProfiel extends JFrame implements ActionListener,MouseListener
{
    private JLabel jlTitel;
    private BasicArrowButton backButton;
    private JButton jbCreateTempProfile;
    private JButton jbCreateLightProfile;
    private JTable jtTempProfile;
    private JTable jtLightProfile;
    private String[] tempProfileTitel = {"Temperatuur Profielen","Knoppen"};
    private Object[][] tempProfile ={{"temperatuur profiel 1","knop"},{"temperatuur profiel 2","knop"}};
    private String[] lightProfileTitel = {"Lichtsterkte Profielen","Knoppen"};
    private Object[][] lightProfile ={{"lichtsterkte profiel 1","knop"},{"lichtsterkte profiel 2","knop"}};

    public KlimaatProfiel(){
        setTitle("Klimaat Systeem");
        setSize(800,600);
        setLayout(new GridLayout(4,3));
        //borders
        Border blackline = BorderFactory.createLineBorder(Color.black);


        //HeadPanel
        FlowLayout headLayout = new FlowLayout();
        GridLayout headLayout2 = new GridLayout(1,2);
        JPanel headPanel = new JPanel(headLayout);

        jlTitel = new JLabel("Profielen aanpassen");
        jlTitel.setFont(new Font("arial",Font.BOLD,20));

        backButton = new BasicArrowButton(BasicArrowButton.WEST);
        backButton.addActionListener(this);
        headLayout.setAlignment(FlowLayout.LEFT);
        headLayout.setHgap(150);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);

        headPanel.add(backButton);
        headPanel.add(jlTitel);
        add(headPanel);

        //buttonPanel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        jbCreateTempProfile = new JButton("Temperatuur profiel aanmaken");
        jbCreateLightProfile = new JButton("Lichtsterkte profiel aanmaken");
        buttonPanel.add(jbCreateTempProfile);
        buttonPanel.add(jbCreateLightProfile);
        add(buttonPanel);


        //tablePanel
        GridLayout tabelLayout = new GridLayout(1,2);
        JPanel tablePanel = new JPanel(tabelLayout);
        tabelLayout.setHgap(15);
        jtTempProfile = new JTable(tempProfile,tempProfileTitel){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jtTempProfile.setShowGrid(false);
        jtTempProfile.getCellSelectionEnabled();
        jtTempProfile.setRowHeight(50);
        jtTempProfile.setRowSelectionAllowed(false);
        jtTempProfile.setBorder(blackline);
        jtTempProfile.addMouseListener((MouseListener) this);


        jtLightProfile = new JTable(lightProfile,lightProfileTitel){
            public boolean isCellEditable(int row, int column){
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

        tablePanel.add(jtTempProfile);
        tablePanel.add(jtLightProfile);
        add(tablePanel);






        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==backButton)
        {
            KlimaatBeheer klimaatBeheerscherm = new KlimaatBeheer();
            this.dispose();
        }
    }


    public static void main(String[] args)
    {
        KlimaatProfiel klimaatProfiel = new KlimaatProfiel();

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource()==jtTempProfile)
        {
            if (jtTempProfile.getSelectedColumn()==1)
            {
                System.out.println("temp table");
                System.out.println("knop column");
                if (jtTempProfile.getSelectedRow()==0)
                {
                    System.out.println("row 1");
                }
                if (jtTempProfile.getSelectedRow()==1)
                {
                    System.out.println("row 2");
                }
            }
        }
        if (e.getSource()==jtLightProfile)
                {

                    if (jtLightProfile.getSelectedColumn()==1)
                    {
                        System.out.println("licht table");
                        System.out.println("knop column");
                        if (jtLightProfile.getSelectedRow()==0)
                        {
                            System.out.println("row 1");
                        }
                        if (jtLightProfile.getSelectedRow()==1)
                        {
                            System.out.println("row 2");
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
