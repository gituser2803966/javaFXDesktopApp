package Views;

import db.utilities.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Scanner;

public class Login implements ActionListener {

    HashMap<String, String> userLoginInfo = new HashMap<String, String>();

    private JFrame frame;
    private JPanel loginPanel;

    Scanner scanner;

    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JButton loginButton;

    private JTextField usernameTextField;
    private JPasswordField passwordTextField;


    private JPanel containerPane;
    private JPanel topPane;
    private JPanel bottomPane;
    private JPanel labelPanel;
    private JPanel textFieldPanel;

    public void createAndShowGUI() {
        frame = new JFrame();
        frame.setTitle("PHẦN MỀM QUẢN LÍ DATA BUS");
        frame.setResizable(false);

        //This creates one row and two equally divided columns
        GridLayout gridLayout = new GridLayout(0, 2);
        frame.setLayout(gridLayout);
        gridLayout.layoutContainer(frame);

        //formatted Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.decode("#3498db"));

        frame.add(leftPanel);

        // formatted right panel
        JPanel rightPanel = new JPanel();
        //rightPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // username label
        usernameLabel = new JLabel("tài khoản");
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        rightPanel.add(usernameLabel, c);

        //username textField
        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(250, 40));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        //c.ipadx = 70;
        //c.ipady = 10;
        c.anchor = GridBagConstraints.CENTER;
        rightPanel.add(usernameTextField, c);

        //password label
        passwordLabel = new JLabel("Mật khẩu");
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        rightPanel.add(passwordLabel, c);
//
//        //passwordTextField
        passwordTextField = new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(250, 40));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        rightPanel.add(passwordTextField, c);
//
//        //login button
        loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(this);
        loginButton.setOpaque(true);
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(Color.decode("#007AA6"));
        loginButton.setBorderPainted(false);
//
        loginButton.setPreferredSize(new Dimension(250, 35));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 4;
        //top padding
        c.insets = new Insets(10,0,0,0);
        rightPanel.add(loginButton, c);

        //Lay out the textField in a panel.
        frame.add(rightPanel);

        frame.setSize(new Dimension(750, 650));
        // app display on center window
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    Login() {
        createAndShowGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameTextField.getText();
            String password = String.valueOf(passwordTextField.getPassword());

            try {
                Connection connection = DBConnection.getConnection();
                String query = "SELECT * FROM Users WHERE username=? AND password=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet rs = preparedStatement.executeQuery();

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập vào tài khoản", "Lỗi đăng nhập", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập vào mật khẩu", "Lỗi đăng nhập", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (rs.next()) {
                    //login success
                    frame.dispose();
                    Dashboard dashboard = new Dashboard();
                } else {
                    //login fail
                    JOptionPane.showMessageDialog(frame, "Thông tin đăng nhập không chính xác", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
        //end event login
    }
}



