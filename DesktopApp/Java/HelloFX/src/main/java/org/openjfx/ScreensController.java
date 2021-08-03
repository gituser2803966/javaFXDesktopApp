package org.openjfx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;

public class ScreensController extends StackPane {

    private HashMap<String, Node> screens = new HashMap<>();

    public ScreensController() {
        super();
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }


    public void loadScreens(String name, String resource) {

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(resource));
            Pane loadScreen = (Pane) loader.load();
            ControlledScreen myScreenController = ((ControlledScreen) loader.getController());
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
        } catch (Exception e) {
            System.out.println("load screens error");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void setScreen(final String name) {
        if (screens.get(name) != null) {
            final DoubleProperty opacity = opacityProperty();
            if (!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));
//                Timeline fade = new Timeline(
//                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
//                        new KeyFrame(new Duration(500), t -> {
//                            getChildren().remove(0);
//                            getChildren().add(0, screens.get(name));
//                            Timeline fadeIn = new Timeline(
//                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
//                                    new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
//                            fadeIn.play();
//                        }, new KeyValue(opacity, 0.0)));
//                fade.play();
            } else {
//                setOpacity(0.0);
                getChildren().add(screens.get(name));
//                Timeline fadeIn = new Timeline(
//                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
//                        new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
//                fadeIn.play();
            }
        } else {
            System.out.println("screen hasn't been loaded");
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

}
