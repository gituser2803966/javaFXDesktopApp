package org.openjfx.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.models.Employee;
import org.openjfx.models.EmployeeHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScreenProfileController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private BorderPane profileBorderPane;

    @FXML
    private ImageView backArrowImageView;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    @FXML
    public void logOut(ActionEvent event) throws IOException {

        myController.unloadScreen(DashBoardController.screenAdminID);
        myController.unloadScreen(DashBoardController.screenDataID);
        myController.unloadScreen(DashBoardController.screenChartID);
        myController.unloadScreen(DashBoardController.screenReportID);
        myController.unloadScreen(DashBoardController.screenUserProfileID);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("ui/login" + ".fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    private void displayUserInfo() {
        EmployeeHolder employeeHolder = EmployeeHolder.getInstance();
        Employee employee = employeeHolder.getUser();

        if (employee != null) {
            firstNameTextField.setText(employee.getFirstName());
            lastNameTextField.setText(employee.getLastName());
        }
    }

    @FXML
    public void backToNavigationScreen(Event event){
        BorderPane root = (BorderPane) profileBorderPane.getScene().getRoot();
        root.setCenter(myController.getScreen(DashBoardController.screenNavigationID));
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
