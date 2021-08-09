package org.openjfx;

import java.io.IOException;
import java.util.Objects;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.model.Indexes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.db.MongoDBConnection;

public class LoginController {

    static Stage stage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private void goToDashBoard(ActionEvent event, Employee employee) throws IOException {
//        App.setRoot("d1");
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        EmployeeHolder employeeHolder = EmployeeHolder.getInstance();
        employeeHolder.setUser(employee);
        stage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboard.fxml")));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    private void checkUserLogin(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = String.valueOf(passwordTextField.getText());

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo lỗi");
            alert.setContentText("Vui lòng điền vào đầy đủ thông tin đăng nhập");
            alert.show();
        } else {

            MongoDatabase db = MongoDBConnection.GetDatabase();

            MongoCollection<Employee> employeeCollection = db.getCollection("employees", Employee.class);

            employeeCollection.createIndex(Indexes.ascending("username"));

            FindIterable<Employee> result = employeeCollection.find(and(eq("username", username), eq("password", password)));

            if (result.cursor().hasNext()) {

                //login OK
                Employee employee = new Employee(result.cursor().next().getFirstName(),
                        result.cursor().next().getLastName(),
                        result.cursor().next().getUsername(),
                        result.cursor().next().getPassword()
                        );

                goToDashBoard(event,employee);
            } else {
                //login fail

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Tài khoản hoặc mật khẩu không chính xác");
                errorAlert.show();
            }

        }

    }
}
