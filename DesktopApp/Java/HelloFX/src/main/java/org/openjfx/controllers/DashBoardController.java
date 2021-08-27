package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.openjfx.models.Employee;
import org.openjfx.models.EmployeeHolder;


import java.net.URL;

import java.util.ResourceBundle;

public class DashBoardController implements Initializable, ControlledScreen {

    ScreensController myController;

    public static String screenNavigationID = "navigation";
    public static String screenNavigationFile = "ui/navigation.fxml";

    public static String screenBusListID = "busList";
    public static String screenBusListFile = "ui/busList.fxml";

    public static String screenChartID = "chart";
    public static String screenChartFile = "ui/chart.fxml";

    public static String screenReportID = "report";
    public static String screenReportFile = "ui/report.fxml";

    public static String screenAdminID = "admin";
    public static String screenAdminFile = "ui/employee.fxml";

    public static String screenUserProfileID = "userProfile";
    public static String screenUserProfileFile = "ui/userProfile.fxml";

    static EmployeeHolder employeeHolder = EmployeeHolder.getInstance();

    @FXML
    private HBox homeHBox;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button dataButton;

    @FXML
    private Button chartButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button adminButton;

    @FXML
    private HBox userHBox;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void homeHBoxOnMouseEntered() {
        homeHBox.setStyle("-fx-background-color:#D4D4D4;" +
                "-fx-background-radius: 5px;" +
                "fx-border-radius: 5px;");
    }

    @FXML
    public void homeHBoxOnMouseExited() {
        homeHBox.setStyle("-fx-background-color:transparent;"
        );
    }


    @FXML
    public void dataButtonOnMouseEntered() {
        dataButton.setStyle("-fx-background-color:#D4D4D4;" +
                "-fx-background-radius: 5px;" +
                "fx-border-radius: 5px;");
    }

    @FXML
    public void dataButtonOnMouseExited() {
        dataButton.setStyle("-fx-background-color:transparent;"

        );
    }

    @FXML
    public void chartButtonOnMouseEntered() {
        chartButton.setStyle("-fx-background-color:#D4D4D4;" +
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
        reportButton.setStyle("-fx-background-color:#D4D4D4;" +
                "-fx-background-radius: 5px;" +
                "fx-border-radius: 5px;");
    }

    @FXML
    public void reportButtonOnMouseExited() {
        reportButton.setStyle("-fx-background-color:transparent;"
        );
    }

    @FXML
    public void adminButtonOnMouseEntered() {
        adminButton.setStyle("-fx-background-color:#D4D4D4;" +
                "-fx-background-radius: 5px;" +
                "fx-border-radius: 5px;");
    }

    @FXML
    public void adminButtonOnMouseExited() {
        adminButton.setStyle("-fx-background-color:transparent;"
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

        Employee employee = employeeHolder.getUser();
        if (employee != null) {
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            userNameLabel.setText(firstName + " " + lastName);
        } else {
            userNameLabel.setText("label");
        }
    }

    private boolean checkIsAdmin() {

        Employee employee = employeeHolder.getUser();
        if (employee.getRule().equals("ADMIN")) {
            return true;
        } else
            return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("dashboard controller running ...");

        //cho phép ẩn hoặc hiện chức năng của riêng admin
        if(checkIsAdmin()){
            adminButton.setVisible(true);
        }else
        {
            adminButton.setVisible(false);
        }
        disPlayUserInfo();

        //add all screen to dashboard

        myController = new ScreensController();

        myController.loadScreens(DashBoardController.screenNavigationID, DashBoardController.screenNavigationFile);
        myController.loadScreens(DashBoardController.screenBusListID, DashBoardController.screenBusListFile);
        myController.loadScreens(DashBoardController.screenChartID, DashBoardController.screenChartFile);
        myController.loadScreens(DashBoardController.screenReportID, DashBoardController.screenReportFile);
        myController.loadScreens(DashBoardController.screenAdminID, DashBoardController.screenAdminFile);
        myController.loadScreens(DashBoardController.screenUserProfileID, DashBoardController.screenUserProfileFile);

        mainBorderPane.setCenter(myController.getScreen(DashBoardController.screenNavigationID));

        homeHBox.setOnMousePressed(e->{
//            mainController.setScreen(DashBoardController);
             mainBorderPane.setCenter(myController.getScreen(DashBoardController.screenNavigationID));
        });
        //
        dataButton.setOnAction(event -> {
            //mainController.setScreen(DashBoardController.screenHomeID);
            mainBorderPane.setCenter(myController.getScreen(DashBoardController.screenBusListID));
        });

        //
        chartButton.setOnAction(event -> {
            myController.setScreen(DashBoardController.screenChartID);
            mainBorderPane.setCenter(myController);
        });

        //
        reportButton.setOnAction(event -> {
            myController.setScreen(DashBoardController.screenReportID);
            mainBorderPane.setCenter(myController);
        });
        //
        adminButton.setOnAction(event -> {
            myController.setScreen(DashBoardController.screenAdminID);
            mainBorderPane.setCenter(myController);
        });
        //
        userHBox.setOnMousePressed(event -> {
            myController.setScreen(DashBoardController.screenUserProfileID);
            mainBorderPane.setCenter(myController);
        });
        //print test ....
        System.out.println("load done !!!!!");
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
