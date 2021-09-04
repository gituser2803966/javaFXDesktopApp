package org.openjfx.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SlideMenu implements Initializable {

    @FXML
    private Button buttonShow;

    @FXML
    private BorderPane menuBorderPane;

    StackPane root;

    Button backBtn;

    HBox fileRoot;

    VBox menu;


    private void createMenu(){
        root = new StackPane();
        AnchorPane editorRoot = new AnchorPane();
//        editorRoot.getChildren().add(btn);
        root.getChildren().add(menuBorderPane);

        fileRoot = new HBox();
        menu = new VBox();
        menu.setStyle("-fx-background-color: blue;");
        menu.setFillWidth(true);
        backBtn = new Button("Left Arrow");
        backBtn.setPrefWidth(100);
        backBtn.getStyleClass().add("custom-menu-button");


        Button infoBtn = new Button("Info");
        infoBtn.setPrefWidth(100);
        infoBtn.getStyleClass().add("custom-menu-button");
        Button newBtn = new Button("New");
        newBtn.setPrefWidth(100);
        newBtn.getStyleClass().add("custom-menu-button");
        Button openBtn = new Button("Open");
        openBtn.setPrefWidth(100);
        openBtn.getStyleClass().add("custom-menu-button");
        menu.getChildren().addAll(backBtn,infoBtn, newBtn, openBtn);
        VBox.setVgrow(infoBtn, Priority.ALWAYS);
        fileRoot.getChildren().add(menu);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        buttonShow.setOnAction(event -> {
//            root.getChildren().add(fileRoot);
//            FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
//            hideEditorRootTransition.setFromValue(1.0);
//            hideEditorRootTransition.setToValue(0.0);
//
//            FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
//            showFileRootTransition.setFromValue(0.0);
//            showFileRootTransition.setToValue(1.0);
//            hideEditorRootTransition.play();
//            showFileRootTransition.play();
//        });



        backBtn.setOnAction(event -> {
            FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
            hideFileRootTransition.setFromValue(1.0);
            hideFileRootTransition.setToValue(0.0);

            FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), menuBorderPane);
            showEditorRootTransition.setFromValue(0.0);
            showEditorRootTransition.setToValue(1.0);

            showEditorRootTransition.play();
            hideFileRootTransition.play();
            root.getChildren().remove(fileRoot);
        });
    }
}
