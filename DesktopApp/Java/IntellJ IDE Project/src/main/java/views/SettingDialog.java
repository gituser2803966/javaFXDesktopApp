package views;

import javax.swing.*;
import java.awt.*;

public class Setting {
    private JPanel topContainer;
    private JLabel accountDisplayName;
    private JLabel getAccountNameLabel;
    private BoxLayout boxLayout;

    public Container createAndShowGUI(Container container){
        container = new JPanel();
        container.setLayout(new BorderLayout());

        topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer,BoxLayout.Y_AXIS));

        accountDisplayName = new JLabel("Account");
        accountDisplayName.setFont(new Font("",Font.BOLD,16));
        getAccountNameLabel = new JLabel("@vantan2803");
        getAccountNameLabel.setFont(new Font("",Font.PLAIN,13));

        topContainer.add(Box.createHorizontalGlue());
        topContainer.add(getAccountNameLabel);
        topContainer.add(accountDisplayName);
        topContainer.add(Box.createVerticalGlue());

        container.add(topContainer,BorderLayout.PAGE_START);
        return container;
    }
}
