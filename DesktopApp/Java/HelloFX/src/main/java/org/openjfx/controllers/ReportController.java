package org.openjfx.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable, ControlledScreen {

    ScreensController myController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
