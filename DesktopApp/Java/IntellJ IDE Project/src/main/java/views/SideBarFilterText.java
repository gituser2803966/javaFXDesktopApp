package Views;

import javax.swing.*;
import java.awt.*;

public class SideBarFilterText {
    public Component createAndShowUI(JPanel panel){

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(250,40));
        panel.setMaximumSize(new Dimension(250,40));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.decode("#ECE5E5")));

        JLabel filterLabel = new JLabel("Bộ Lọc");
        panel.add(Box.createHorizontalGlue());
        panel.add(filterLabel);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }
}
