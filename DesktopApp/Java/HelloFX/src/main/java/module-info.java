module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
    exports org.openjfx.util;
    opens org.openjfx.util to javafx.fxml;
    exports org.openjfx.controllers;
    opens org.openjfx.controllers to javafx.fxml;
    exports org.openjfx.models;
    opens org.openjfx.models to javafx.fxml;

}