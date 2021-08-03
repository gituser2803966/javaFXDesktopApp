package org.openjfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import java.net.URL;

import java.util.ResourceBundle;

public class DashBoardController implements Initializable, ControlledScreen {

    ScreensController myController;

    public static String screenHomeID = "home";
    public static String screenHomeFile = "home.fxml";
    public static String screenChartID = "chart";
    public static String screenChartFile = "chart.fxml";
    public static String screenReportID = "report";
    public static String screenReportFile = "report.fxml";
    public static String screenUserProfileID = "userProfile";
    public static String screenUserProfileFile = "userProfile.fxml";

    @FXML
    private Label userNameLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button chartButton;

    @FXML
    private Button reportButton;

    @FXML
    private HBox userHBox;

    @FXML
    private BorderPane mainBorderPane;


    @FXML
    public void homeButtonOnMouseEntered() {
        homeButton.setStyle("-fx-background-color:#dedfe1;" +
                "-fx-background-radius: 5px;" +
                "fx-border-radius: 5px;");
    }

    @FXML
    public void homeButtonOnMouseExited() {
        homeButton.setStyle("-fx-background-color:transparent;"

        );
    }

    @FXML
    public void chartButtonOnMouseEntered() {
        chartButton.setStyle("-fx-background-color:#dedfe1;" +
                "-fx-background-radius: 5px;" +
                "fx-border-radius: 5px;");
    }

    @FXML
    public void chartButtonOnMouseExited() {
        chartButton.setStyle("-fx-background-color:transparent;"
        );
    }

    @FXML
    public void reportButtonOnMouseEntered() {
        reportButton.setStyle("-fx-background-color:#dedfe1;" +
                "-fx-background-radius: 5px;" +
                "fx-border-radius: 5px;");
    }

    @FXML
    public void reportButtonOnMouseExited() {
        reportButton.setStyle("-fx-background-color:transparent;"
        );
    }

    @FXML
    public void userHBoxOnMouseEntered() {
        userHBox.setStyle("-fx-background-color:#dedfe1;");
    }

    @FXML
    public void userHBoxOnMouseExited() {
        userHBox.setStyle("-fx-background-color:transparent;");
    }

    //display employee data
    @FXML
    private void disPlayUserInfo() {
        EmployeeHolder employeeHolder = EmployeeHolder.getInstance();

        Employee employee = employeeHolder.getUser();
        if (employee != null) {
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            userNameLabel.setText(firstName + " " + lastName);
        } else {
            userNameLabel.setText("label");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //print test ....
        System.out.println("dashboard controller running ...");
        disPlayUserInfo();

        //add all screen to dashboard
        ScreensController mainController = new ScreensController();
        mainController.loadScreens(DashBoardController.screenHomeID, DashBoardController.screenHomeFile);
        mainController.loadScreens(DashBoardController.screenChartID, DashBoardController.screenChartFile);
        mainController.loadScreens(DashBoardController.screenReportID, DashBoardController.screenReportFile);
        mainController.loadScreens(DashBoardController.screenUserProfileID, DashBoardController.screenUserProfileFile);
        //display first screen
        mainController.setScreen(DashBoardController.screenHomeID);

        //display component for center of border layout
        mainBorderPane.setCenter(mainController);

        //
        homeButton.setOnAction(event -> {
            mainController.setScreen(DashBoardController.screenHomeID);
            mainBorderPane.setCenter(mainController);
        });

        //
        chartButton.setOnAction(event -> {
            mainController.setScreen(DashBoardController.screenChartID);
            mainBorderPane.setCenter(mainController);
        });

        //
        reportButton.setOnAction(event -> {
            mainController.setScreen(DashBoardController.screenReportID);
            mainBorderPane.setCenter(mainController);
        });

        //
        userHBox.setOnMousePressed(event -> {
            mainController.setScreen(DashBoardController.screenUserProfileID);
            mainBorderPane.setCenter(mainController);
        });

        //print test ....
        System.out.println("load done !!!!!");
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
