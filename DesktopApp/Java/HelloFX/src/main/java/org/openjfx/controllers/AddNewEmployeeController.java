package org.openjfx.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.openjfx.models.EDepartment;
import org.openjfx.models.ERule;
import org.openjfx.models.Employee;
import org.openjfx.models.EmployeeDaoImplementation;
import org.openjfx.service.MongoDBConnection;
import org.openjfx.util.MD5Hashing;

import java.net.URL;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class AddNewEmployeeController implements Initializable, ControlledScreen {

    ScreensController myController;

    EmployeeDaoImplementation emlDaoImp = new EmployeeDaoImplementation();

    @FXML
    private Label labelNotification;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private ComboBox<EDepartment> departmentComboBox;

    @FXML
    private ComboBox<ERule> ruleComboBox;

    @FXML
    private Button addNewButton;

    @FXML
    public void checkWhenAddNewEmployee(ActionEvent event) {


        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = String.valueOf(passwordTextField.getText());
        String confirmPassword = String.valueOf(confirmPasswordTextField.getText());
        String department = departmentComboBox.getSelectionModel().getSelectedItem().toString();
        String rule = ruleComboBox.getSelectionModel().getSelectedItem().toString();

        if (firstName.isBlank() || firstName.isEmpty() ||
                lastName.isBlank() || lastName.isEmpty() ||
                username.isEmpty() || username.isBlank() ||
                password.isEmpty() || password.isBlank() ||
                confirmPassword.isEmpty() || password.isBlank() ||
                rule.equals(null) ||
                department.equals(null)
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo lỗi");
            alert.setContentText("Bạn phải nhập đầy đủ thông tin, chọn phòng ban và thêm quyền để có thể thêm 1 nhân viên mới");
            alert.show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo lỗi");
            alert.setContentText("Hai mật khẩu không giống nhau !!! Vui lòng kiểm tra lại");
            alert.show();
            return;
        }

        //insert new employee
        Employee el = new Employee();
        el.setFirstName(firstName);
        el.setLastName(lastName);
        el.setPhotoURL("");
        el.setUsername(username);
        el.setPassword(MD5Hashing.getMD5(password));
        el.setDepartment(departmentComboBox.getValue().toString());
        el.setRule(ruleComboBox.getValue().toString());
        el.setActive(true);

        insertNewEmployeeInBackground(el);
        event.consume();

    }


    private void showMessages(String message, Boolean b) {
        new Thread(() -> {
            Platform.runLater(() -> {
                labelNotification.setVisible(true);
                labelNotification.setText(message);
                if (b) {
                    labelNotification.setStyle("-fx-text-fill:#04b034;");
                } else {
                    labelNotification.setStyle("-fx-text-fill:#d40d0d;");
                }

            });

        }).start();
    }


    public void insertNewEmployeeInBackground(Employee eml) {

        Task task = new Task<Void>() {
            //
            @Override
            protected Void call() {

                progressIndicator.setVisible(true);
                progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                addNewButton.setDisable(true);
                labelNotification.setVisible(false);
//                MongoDatabase db = MongoDBConnection.getConnection();
//                MongoCollection<Employee> employeeMongoCollection = db.getCollection("employees", Employee.class);
//
//                FindIterable<Employee> result = employeeMongoCollection.find(eq("username", el.getUsername()));

                boolean result = emlDaoImp.findEmployeeWithUsername(eml.getUsername());

                if (result) {
                    addNewButton.setDisable(false);
                    progressIndicator.setVisible(false);
                    showMessages("Tài khoản này hiện đã tồn tại", false);
                } else {
                    emlDaoImp.add(eml);
                    progressIndicator.setVisible(false);
                    addNewButton.setDisable(false);
                    showMessages("Đã thêm nhân viên mới thành công", true);
                }

                return null;
            }


//
////            @Override
////            protected void succeeded() {
////                //
////                System.out.println(" succeeded....");
//////                progressIndicator.setVisible(false);
//////                addNewButton.setDisable(false);
//////                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//////                alert.setTitle("Thông báo");
//////                alert.setContentText("Đã thêm nhân viên mới thành công");
//////                alert.show();
////            }
//
////            @Override
////            protected void failed() {
////                //
////                System.out.println("ham failed runnnnnnnn.....");
//////                addNewButton.setDisable(false);
//////                progressIndicator.setVisible(false);
//////                Alert alert = new Alert(Alert.AlertType.ERROR);
//////                alert.setTitle("Thông báo lỗi");
//////                alert.setContentText("Tài khoản này hiện đã tồn tại");
////////                alert.show();
////////                Alert alert = new Alert(Alert.AlertType.ERROR, getException().getMessage());
//////                alert.showAndWait();
////            }
        };
        new Thread(task).start();

    }


//    private ObservableList<String> getAllDepartment(){
//        MongoDatabase db = MongoDBConnection.getConnection();
//        //busCollection.updateOne(eq("name", "Ada Byron"), combine(set("age", 23), set("name", "Ada Lovelace")));
//        DistinctIterable<String> iterable = db.getCollection("departments", Department.class).distinct("name", String.class);
//
//        for (String s:iterable){
//            departmentComboBoxData.add(s);
//        }
//        return departmentComboBoxData;
//    }

    private void runTask() {

        final double wndwWidth = 300.0d;
        Label updateLabel = new Label("Running tasks...");
        updateLabel.setPrefWidth(wndwWidth);
        ProgressBar progress = new ProgressBar();
        progress.setPrefWidth(wndwWidth);

        VBox updatePane = new VBox();
        updatePane.setPadding(new Insets(10));
        updatePane.setSpacing(5.0d);
        updatePane.getChildren().addAll(updateLabel, progress);

        Stage taskUpdateStage = new Stage(StageStyle.UTILITY);
        taskUpdateStage.setScene(new Scene(updatePane));
        taskUpdateStage.show();

        Task longTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max = 50;
                for (int i = 1; i <= max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);
                    updateMessage("Task part " + String.valueOf(i) + " complete");

                    Thread.sleep(100);
                }
                return null;
            }
        };

        longTask.setOnSucceeded((EventHandler<WorkerStateEvent>) t -> taskUpdateStage.hide());
        progress.progressProperty().bind(longTask.progressProperty());
        updateLabel.textProperty().bind(longTask.messageProperty());

        taskUpdateStage.show();
        new Thread(longTask).start();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        labelNotification.setVisible(false);
//        progressIndicator.setVisible(false);
//        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
//        departmentComboBox.setItems(FXCollections.observableArrayList(EDepartment.values()));
//        departmentComboBox.getSelectionModel().selectFirst();
//        departmentComboBox.getSelectionModel().selectedItemProperty()
//                .addListener((observable, oldValue, newValue) -> System.out.println("Value is: " + newValue));
//
//        ruleComboBox.setItems(FXCollections.observableArrayList(ERule.values()));
//        ruleComboBox.getSelectionModel().selectFirst();
//        ruleComboBox.getSelectionModel().selectedItemProperty()
//                .addListener((observable, oldValue, newValue) -> System.out.println("Value is: " + newValue));
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
