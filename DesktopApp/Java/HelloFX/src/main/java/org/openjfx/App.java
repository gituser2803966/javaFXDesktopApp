package org.openjfx;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.bson.conversions.Bson;
import org.openjfx.models.OnJobBusDaoImplement;

import java.io.IOException;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        //OnJobBusDaoImplement oj  = new OnJobBusDaoImplement();
        //oj.addNewFieldToDocument("onJobEndDay");
        String css = App.class.getResource("css/login.css").toExternalForm();

        Scene scene = new Scene(loadFXML("login"));
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/appIcon.png")));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

//    static void setRoot(String fxml) throws IOException {
//        scene.setRoot(loadFXML(fxml));
//    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}