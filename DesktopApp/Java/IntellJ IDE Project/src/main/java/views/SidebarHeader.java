package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class SidebarHeader {
    JPanel topLeftPanel;
    JPanel topRightPanel;
    final FlowLayout leftPanelFlowLayout = new FlowLayout();
    final FlowLayout rightPanelFlowLayout = new FlowLayout();

    public Component createAndShowUI(JPanel panel){
        try {
            //set up Top Left Panel
            topLeftPanel = new JPanel();
            topLeftPanel.setOpaque(false);
            // set layout horizontally
            topLeftPanel.setLayout(leftPanelFlowLayout);
            // Alignment LEADING => Left to right, TRAILING => right to left
            leftPanelFlowLayout.setAlignment(leftPanelFlowLayout.LEADING);
            JLabel label = new JLabel("this is menu item");
            label.setOpaque(false);
            label.setBorder(null);
            //\\
            topLeftPanel.add(label);

            //\\set up top right panel
            topRightPanel = new JPanel();
            topRightPanel.setOpaque(false);
            topRightPanel.setLayout(rightPanelFlowLayout);
            rightPanelFlowLayout.setAlignment(rightPanelFlowLayout.TRAILING);
            //\\set up for username label
            JLabel usernameLabel = new JLabel("Lionel Messi");
            usernameLabel.setForeground(Color.white);
            //set up for arrow button
            JButton downArrowButton = new JButton();
            downArrowButton.setPreferredSize(new Dimension(24, 24));
            downArrowButton.setOpaque(false);
            downArrowButton.setContentAreaFilled(false);
            downArrowButton.setBorderPainted(false);

            //\\add icon for down arrow button
            Image img = ImageIO.read(getClass().getResource("/images/account-icon-24.png"));
            downArrowButton.setIcon(new ImageIcon(img));

            //\\add username label and down arrow button to right panel
            topRightPanel.add(downArrowButton);
            topRightPanel.add(usernameLabel);

            //\\set up Top Panel
            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBackground(Color.decode("#2D9CDB"));
            panel.add(topLeftPanel, BorderLayout.LINE_START);
            panel.add(topRightPanel, BorderLayout.LINE_END);

        }catch (Exception ex){
            System.out.println("image not found login");
            System.out.println(ex);
        }
        return panel;
    }
}
