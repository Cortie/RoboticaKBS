import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KlimaatProfiel extends JFrame implements ActionListener {
    private JLabel jlTitel;
    private BasicArrowButton backButton;
    private JButton jbCreateTempProfile;
    private JButton jbCreateLightProfile;
    private JTable jtTempProfile;
    private JTable jtLightProfile;
    private String[] tempProfileTitel = {"Temperatuur Profielen"};
    private Object[][] tempProfile ={{"temperatuur profiel 1"},{"temperatuur profiel 2"}};
    private String[] lightProfileTitel = {"Lichtsterkte Profielen"};
    private Object[][] lightProfile ={{"lichtsterkte profiel 1"},{"lichtsterkte profiel 2"}};

    public KlimaatProfiel(){
        setTitle("Klimaat Systeem");
        setSize(800,600);
        setLayout(new GridLayout(4,3));

        //HeadPanel
        JPanel headPanel = new JPanel(new FlowLayout());
        jlTitel = new JLabel("Profielen aanpassen");
        backButton = new BasicArrowButton(BasicArrowButton.WEST);

        headPanel.add(backButton,RIGHT_ALIGNMENT);
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
        jtTempProfile = new JTable(tempProfile,tempProfileTitel);
        jtTempProfile.setEnabled(false);
        jtTempProfile.setShowGrid(false);
        jtTempProfile.getCellSelectionEnabled();
        jtTempProfile.setRowHeight(50);

        jtLightProfile = new JTable(lightProfile,lightProfileTitel);

        jtLightProfile.setEnabled(true);
        jtLightProfile.setShowGrid(false);
        jtLightProfile.getCellSelectionEnabled();
        jtLightProfile.setRowHeight(50);
        tablePanel.add(jtTempProfile);
        tablePanel.add(jtLightProfile);
        add(tablePanel);





        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public static void main(String[] args)
    {
        KlimaatProfiel klimaatProfiel = new KlimaatProfiel();

    }
}
