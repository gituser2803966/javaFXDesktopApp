package Views;

import javax.swing.*;
import java.awt.*;

public class Dashboard {

    JFrame frame = new JFrame();
    JPanel topContainerPanel;
    JPanel sideBarPanel;
    JPanel searchPanel;
    JPanel routersContainerPanel;
    JPanel filterLabelPanel;
    JPanel mainPanel;

    final BorderLayout containerBorderLayout = new BorderLayout();

    public Dashboard() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(new Dimension(screenSize.width - 100, screenSize.height - 100));
            frame.setBackground(Color.decode("#E0E0E0"));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Dashboard");
            frame.setLayout(containerBorderLayout);

            // ****HEADER OR TOP****
            frame.add(new SidebarHeader().createAndShowUI(topContainerPanel), BorderLayout.PAGE_START);

            // ****SIDEBAR****
            sideBarPanel = new JPanel();
            sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));
            sideBarPanel.setBackground(Color.decode("#FFFFFF"));
            sideBarPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.decode("#ECE5E5")));

            //\\set up for filter label
            sideBarPanel.add(new SideBarFilterText().createAndShowUI(filterLabelPanel));

            //\\create padding Top and bottom for searchPanel

            sideBarPanel.add(Box.createRigidArea(new Dimension(0,10)));
            sideBarPanel.add(new SideBarSearchInput().SetupSearchField(searchPanel));
            sideBarPanel.add(Box.createRigidArea(new Dimension(0,10)));

            //\\@ui routes filter set up
            sideBarPanel.add(new SideBarFilterRoute().createAndShowUI(routersContainerPanel));

            //@ ADD UI FOR SIDEBAR PANEL
            frame.add(sideBarPanel, BorderLayout.LINE_START);

            // ****CENTER****
            mainPanel = new JPanel();
            //mainPanel.setBackground(Color.decode("#e74c3c"));
            frame.add(new CenterContent().createAndShowGUI(mainPanel), BorderLayout.CENTER);

            //display frame center
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        // set up for root container

    }
}
