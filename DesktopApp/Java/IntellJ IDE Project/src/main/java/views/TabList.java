package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TabList {
    public Component createAndShowUI() {

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(1, 1));
        //containerPanel.setOpaque(true);

        JTabbedPane tabbedPane = new JTabbedPane();
        //tabbedPane.setOpaque(true);

        //JComponent busListPanel = new JPanel();
        //busListPanel.add(new BusListTable().createAndShowUI());
        tabbedPane.addTab("Bus List", new BusTable());

        //\\ @create ui for listPanel
        //busListPanel.setBackground(Color.decode("#B3E5FC"));

        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = new JPanel();
        JLabel labelPanel2 = new JLabel("label panel");
        panel2.add(labelPanel2);
        tabbedPane.addTab("Tab 2", panel2);

        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = setContent("Panel #3");
        tabbedPane.addTab("Tab 3", panel3);

        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent panel4 = setContent(
                "Panel #4 (has a preferred size of 410 x 50).");
        panel4.setOpaque(true);
        panel4.setBackground(Color.yellow);
        //panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Tab 4", panel4);

        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        containerPanel.add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        return containerPanel;
    }

    protected JComponent setContent(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        //panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
