package org.openjfx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.model.Indexes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.models.Employee;
import org.openjfx.models.EmployeeHolder;
import org.openjfx.service.MongoDBConnection;
import org.openjfx.util.MD5Hashing;

public class LoginController implements Initializable {

    static Stage stage;

    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button buttonLogin;
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

        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("ui/dashboard.fxml")));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    private void login(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = String.valueOf(passwordTextField.getText());

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Th??ng b??o l???i");
            alert.setContentText("Vui l??ng ??i???n v??o ?????y ????? th??ng tin ????ng nh???p");
            alert.show();
        } else {

            MongoDatabase db = MongoDBConnection.getConnection();

            MongoCollection<Employee> employeeCollection = db.getCollection("employees", Employee.class);

            employeeCollection.createIndex(Indexes.ascending("username"));

            FindIterable<Employee> result = employeeCollection.find(and(eq("username", username), eq("password", MD5Hashing.getMD5(password))));

            if (result.cursor().hasNext()) {

                //login OK
                Employee employee = new Employee();
                employee.setFirstName(result.cursor().next().getFirstName());
                employee.setLastName(result.cursor().next().getLastName());
                employee.setPhotoURL(result.cursor().next().getPhotoURL());
                employee.setUsername(result.cursor().next().getUsername());
                employee.setPassword(result.cursor().next().getPassword());
                employee.setDepartment(result.cursor().next().getDepartment());
                employee.setRule(result.cursor().next().getRule());
                goToDashBoard(event, employee);
            } else {
                //login fail

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("T??i kho???n ho???c m???t kh???u kh??ng ch??nh x??c");
                errorAlert.show();
                return;
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameTextField.setStyle("-fx-background-color: #57534E;"+"-fx-text-inner-color: #fff;");
        passwordTextField.setStyle("-fx-background-color: #57534E;"+"-fx-text-inner-color: #fff;");
    }
}
