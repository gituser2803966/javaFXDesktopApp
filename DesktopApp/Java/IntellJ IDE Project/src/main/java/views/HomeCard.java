package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;

public class CenterContent {

    JPanel centerPanel = new JPanel();
    JPanel containerTabList;
    FlowLayout flowLayout = new FlowLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagConstraints gbcForGroupButton = new GridBagConstraints();
    DefaultTableModel defaultTableModel;
    JTable busListTable = new JTable();
    //BusList Bus = new BusList();

    public Component createAndShowGUI(){

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        //flowLayout.setAlignment(FlowLayout.LEADING);

        //\\ @ **** create UI on Page start panel ****
        JPanel pageStartPanel = new JPanel();
        pageStartPanel.setLayout(new GridBagLayout());
        pageStartPanel.setBackground(Color.white);

        //\\ @create UI for showAllButton
        JButton showAllButton = new JButton("Xem tất cả");
        showAllButton.setOpaque(true);
        //showAllButton.setContentAreaFilled(false);
        showAllButton.setBorderPainted(false);
        showAllButton.setBorder(null);
        showAllButton.setForeground(Color.white);
        showAllButton.setBackground(Color.decode("#2D9CDB"));

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.weighty = 1;
        //gbc.weightx = 1;
        gbc.ipady = 15;
        gbc.ipadx = 30;
        gbc.insets = new Insets(10,20,10,20);
        gbc.anchor = GridBagConstraints.LINE_START;

        pageStartPanel.add(showAllButton,gbc);

        //\\ @create UI for next and previous button panel
        JPanel nextAndPreviousButtonPanel = new JPanel();
        nextAndPreviousButtonPanel.setOpaque(false);
        nextAndPreviousButtonPanel.setLayout(new GridBagLayout());

        //\\ @create UI for previous button
        JButton preButton = new JButton("<");
        preButton.setOpaque(true);
        preButton.setBorder(null);
        preButton.setForeground(Color.decode("#000000"));
        preButton.setBackground(Color.decode("#E0E0E0"));

        //\\ @create UI for next button
        JButton nextButton = new JButton(">");
        nextButton.setOpaque(true);
        nextButton.setBorder(null);
        nextButton.setForeground(Color.decode("#000000"));
        nextButton.setBackground(Color.decode("#E0E0E0"));

        //\\ @add previous button to nextAndPreviousButtonPanel
        gbcForGroupButton.fill = GridBagConstraints.NONE;
        gbcForGroupButton.gridx = 0;
        gbcForGroupButton.gridy = 0;
        //gbcForGroupButton.weighty = 1;
        //gbcForGroupButton.weightx = 1;
        gbcForGroupButton.ipady = 15;
        gbcForGroupButton.ipadx = 25;
        gbcForGroupButton.insets = new Insets(0,0,0,10);
        gbcForGroupButton.anchor = GridBagConstraints.LINE_START;
        nextAndPreviousButtonPanel.add(preButton,gbcForGroupButton);

        //\\ @add next button to nextAndPreviousButtonPanel
        gbcForGroupButton.fill = GridBagConstraints.NONE;
        gbcForGroupButton.gridx = 1;
        gbcForGroupButton.gridy = 0;
        gbcForGroupButton.weighty = 1;
        gbcForGroupButton.weightx = 1;
        gbcForGroupButton.ipady = 15;
        gbcForGroupButton.ipadx = 25;
        //gbcForGroupButton.insets = new Insets(10,0,10,5);
        gbcForGroupButton.anchor = GridBagConstraints.LINE_START;
        nextAndPreviousButtonPanel.add(nextButton,gbcForGroupButton);

        //\\ @create UI for nextAndPreviousButtonPanel to pageStartPanel
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipady = 0;
        gbc.ipadx = 0;
        gbc.weighty = 1;
        gbc.weightx = 1;
        //gbc.insets = new Insets(10,0,10,0);
        gbc.anchor = GridBagConstraints.LINE_START;
        pageStartPanel.add(nextAndPreviousButtonPanel,gbc);


        //\\ @ **** create UI for CENTER ****
        centerPanel.setLayout(new GridLayout(0,1));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setLayout(new GridLayout(0,1));
        //\\ @ containerTab1
        JPanel containerTab1 = new JPanel();
        //\\ @ Tab list 1
        containerTab1.setLayout(new GridLayout());
//        populateJTableWithBusList();
        //table.setPreferredScrollableViewportSize(new Dimension(100,100));
        busListTable.setFillsViewportHeight(true);

//        if (DEBUG) {
//            busListTable.addMouseListener(new MouseAdapter() {
//                public void mouseClicked(MouseEvent e) {
//                    printDebugData(busListTable);
//                }
//            });
//        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(new BusTable());
        containerTab1.add(scrollPane);

        tabbedPane.addTab("Bus List", containerTab1);

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
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        centerPanel.add(tabbedPane);

        //The following line enables to use scrolling tabs.


        //\\ @create UI for PAGE_END
        JPanel pageEndPanel = new JPanel();
        pageEndPanel.setLayout(flowLayout);
        pageEndPanel.setBorder(new EmptyBorder(10, 10, 10, 0));
        pageEndPanel.setBackground(Color.white);
        flowLayout.setAlignment(FlowLayout.LEADING);

        //\\ @create UI for Add button
        JButton addButton = new JButton("THÊM");
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBorder(null);
        addButton.setOpaque(true);
        addButton.setForeground(Color.white);
        addButton.setBackground(Color.decode("#2D9CDB"));

        //\\ @create UI for edit button
        JButton editButton = new JButton("SỬA");
        editButton.setPreferredSize(new Dimension(100, 30));
        editButton.setBorder(null);
        editButton.setOpaque(true);
        editButton.setForeground(Color.white);
        editButton.setBackground(Color.decode("#2D9CDB"));

        //\\ @create UI for delete button
        JButton deleteButton = new JButton("XÓA");
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setBorder(null);
        deleteButton.setOpaque(true);
        deleteButton.setForeground(Color.white);
        deleteButton.setBackground(Color.decode("#2D9CDB"));

        pageEndPanel.add(addButton);
        pageEndPanel.add(editButton);
        pageEndPanel.add(deleteButton);

        //\\ @ page start components
        containerPanel.add(pageStartPanel,BorderLayout.PAGE_START);
        //\\@ page center components
        containerPanel.add(centerPanel,BorderLayout.CENTER);
        //\\@ page end components
        containerPanel.add(pageEndPanel,BorderLayout.PAGE_END);

        //\\
        return containerPanel;
    }


    public void populateJTableWithBusList(){
        System.out.println("***test****");

        //ArrayList<BusList> busList = Bus.GetBusList();
        String[] columns = {"jobCode","numRouter","num","routeName","category","contract"};
        defaultTableModel = (DefaultTableModel) busListTable.getModel();

        //for (int i=0; i < )

       // for (int i=0;i< busList.size();i++){
            //.out.println("bus "+i+": "+busList.get(i).getJobcode());
            //System.out.println("bus "+i+": "+busList.get(i).getNumRouter());

//            defaultTableModel.addRow(new Object[] {
//                defaultTableModel.getRowCount() + 1,
//                    busList.get(i).getJobcode(),
//                    busList.get(i).getNumRouter(),
//                    busList.get(i).getNum(),
//                    busList.get(i).getRouteName(),
//                    busList.get(i).getCategory(),
//                    busList.get(i).getContract(),
//            });
        //}


    }
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
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
