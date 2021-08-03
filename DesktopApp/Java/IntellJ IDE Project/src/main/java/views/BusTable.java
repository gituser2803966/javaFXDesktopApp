package Views;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import models.BusModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/*
 * TableDemo.java requires no other files.
 */

/**
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class BusTable extends JPanel {
    private boolean DEBUG = false;
    BusModel busModel = new BusModel();

    public BusTable() {
        super(new GridLayout(1, 0));
        List<BusModel> busList = busModel.GetBusList();
        JTable busListTable = new JTable();
        MyTableModel myTableModel = new MyTableModel(busList);
        busListTable.setModel(myTableModel);
        busListTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        busListTable.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(busListTable);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    class MyTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Job Code",
                "Tuyến xe",
                "Số xe",
                "Tên tuyến",
                "Loại xe",
        };

        private List<BusModel> busList;

        public MyTableModel(List<BusModel> busList) {
            this.busList = busList;
        }

//        public void populateWithBusList() {
//            //jobcode,
//            // numRouter,
//            // num,
//            // routeName,
//            // category,
//            // contract
//            ArrayList<models.BusModel> busList = busModel.GetBusList();
//
//            for (models.BusModel busModel : busList) {
//                System.out.println(busModel.getJobcode());
////                data = new Object[][]{
////                        {busModel.getJobcode()},
////                        {busModel.getNumRouter()},
////                        {busModel.getNum()},
////                        {busModel.getRouteName()},
////                        {busModel.getCategory()},
////                        {busModel.getContract()},
////                };
//            }
//
//
//        }


        public int getColumnCount() {
            return columnNames.length;
        }


        public int getRowCount() {
            return busList.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int rowIndex, int colIndex) {
            switch (colIndex) {
                case 0:
                    return busList.get(rowIndex).getJobcode();
                case 1:
                    return busList.get(rowIndex).getNumRouter();
                case 2:
                    return busList.get(rowIndex).getNum();
                case 3:
                    return busList.get(rowIndex).getRouteName();
                case 4:
                    return busList.get(rowIndex).getCategory();
                case 5:
                    return busList.get(rowIndex).getContract();
                default:
                    return "null";
            }
            // return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
//        public Class getColumnClass(int c) {
//            return getValueAt(0, c).getClass();
//        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            System.out.println("setValueAt");
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            //data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
//                for (int j = 0; j < numCols; j++) {
//                    System.out.print("  " + data[i][j]);
//                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        BusTable newContentPane = new BusTable();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}