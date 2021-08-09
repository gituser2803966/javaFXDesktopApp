package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenProfileController implements Initializable,ControlledScreen {

    ScreensController myController;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    @FXML
    private void displayUserInfo(){
        EmployeeHolder employeeHolder = EmployeeHolder.getInstance();
        Employee employee = employeeHolder.getUser();

        if(employee != null){
            firstNameTextField.setText(employee.getFirstName());
            lastNameTextField.setText(employee.getLastName());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUserInfo();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
