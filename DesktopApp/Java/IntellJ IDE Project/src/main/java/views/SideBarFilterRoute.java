package Views;

import javax.swing.*;
import java.awt.*;

public class SideBarFilterRoute {
    public Component createAndShowUI(JPanel panel){
        //set up for search filter panel
        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbcForRoutersContainerPanel =  new GridBagConstraints();
        //routersContainerPanel.setBackground(Color.blue);
        //routersPanel.setPreferredSize(new Dimension(250,100));
//            routersContainerPanel.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(Color.red),
//                    routersContainerPanel.getBorder()));

        //set up components for routersPanel
        JLabel routerLabel = new JLabel("Theo Tuyến");
//            routerLabel.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(Color.red),
//                    routerLabel.getBorder()));
        gbcForRoutersContainerPanel.fill = GridBagConstraints.NONE;
        gbcForRoutersContainerPanel.gridx = 0;
        gbcForRoutersContainerPanel.insets = new Insets(5,15,0,0);
        gbcForRoutersContainerPanel.gridy = 0;
        gbcForRoutersContainerPanel.weightx = 1;
        gbcForRoutersContainerPanel.weighty = 0;
        gbcForRoutersContainerPanel.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(routerLabel,gbcForRoutersContainerPanel);

        //set route panel
        JPanel routerPanel = new JPanel();
        routerPanel.setOpaque(false);
        routerPanel.setLayout(new GridLayout(0,1));
        //routerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE,routerPanel.getMinimumSize().height));
        //routerPanel.setBackground(Color.yellow);
//            routerPanel.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(Color.red),
//                    routerPanel.getBorder()));
        JRadioButton radioButtonR01 = new JRadioButton("R01");
        JRadioButton radioButtonR02 = new JRadioButton("R02");

        //Group the radio buttons.
//            ButtonGroup buttonGroup = new ButtonGroup();
//            buttonGroup.add(radioButtonR01);
//            buttonGroup.add(radioButtonR02);

        routerPanel.add(radioButtonR01);
        routerPanel.add(radioButtonR02);

        //add routerPanel for routersContainerPanel
        gbcForRoutersContainerPanel.fill = GridBagConstraints.NONE;
        gbcForRoutersContainerPanel.gridx = 0;
        gbcForRoutersContainerPanel.gridy = 1;
        //gbcForRoutersContainerPanel.ipady = 10;
        gbcForRoutersContainerPanel.weightx = 1;
        gbcForRoutersContainerPanel.weighty = 1;
        gbcForRoutersContainerPanel.insets = new Insets(10,25,0,0);
        gbcForRoutersContainerPanel.anchor = GridBagConstraints.FIRST_LINE_START;

        panel.setPreferredSize(new Dimension(250, routerLabel.getMinimumSize().height + routerPanel.getMinimumSize().height));
        panel.setMaximumSize(new Dimension(250, routerLabel.getMinimumSize().height + routerPanel.getMinimumSize().height));

        panel.add(routerPanel,gbcForRoutersContainerPanel);

        return panel;
    }
}
