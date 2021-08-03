package Views;



import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class SideBarSearchInput {
    final  GridBagConstraints gbc = new GridBagConstraints();
    public Component SetupSearchField(JPanel panel) {
        try {
            panel = new JPanel();
            //panel.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel.setOpaque(true);
            panel.setBackground(Color.decode("#F5F5F5"));
            panel.setLayout(new GridBagLayout());
            panel.setPreferredSize(new Dimension(230,35));
            panel.setMaximumSize(new Dimension(230,35));

            //set up icon for search panel
            JLabel searchIconLabel = new JLabel();
            Image searchIconImage = ImageIO.read(getClass().getResource("images/search-icon-24.png"));
            Image searchIcon = searchIconImage.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

            searchIconLabel.setIcon(new ImageIcon(searchIcon));
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0,5,0,5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(searchIconLabel,gbc);

            //set up searchField for search panel
            JTextField searchField = new JTextField();
            searchField.setBorder(null);
            searchField.setOpaque(false);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            // add components for searchPanel
             panel.add(searchField,gbc);
        } catch (IOException e) {
            System.out.println("**** not found search icon in search field ...");
            e.printStackTrace();
        }
        return panel;
    }


}
