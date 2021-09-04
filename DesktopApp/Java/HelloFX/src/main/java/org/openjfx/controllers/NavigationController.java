package org.openjfx.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable, ControlledScreen{

    ScreensController myController;

    @FXML
    private BorderPane navBorderPanel;

    @FXML
    private HBox dataHBox;

    @FXML
    private HBox chartHBox;

    @FXML
    public void onChartHBoxEntered(Event event){
        myController = new ScreensController();

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dataHBox.setOnMousePressed(event -> {
            //System.out.println("current screen: "+myController.getScreen(DashBoardController.screenNavigationID));
            //navBorderPanel.getChildren().add(myController.getScreen(DashBoardController.screenBusListID));

            BorderPane node = (BorderPane) navBorderPanel.getScene().getRoot();
            node.setCenter(myController.getScreen(DashBoardController.screenDataID));
            //System.out.println("node: "+node);
        });

        chartHBox.setOnMousePressed(event -> {
            //System.out.println("current screen: "+myController.getScreen(DashBoardController.screenNavigationID));
            //navBorderPanel.getChildren().add(myController.getScreen(DashBoardController.screenBusListID));

            BorderPane root = (BorderPane) navBorderPanel.getScene().getRoot();
            root.setCenter(myController.getScreen(DashBoardController.screenChartID));
            //System.out.println("node: "+node);
        });
    }
}
