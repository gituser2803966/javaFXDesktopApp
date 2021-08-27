package org.openjfx.controllers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.models.Employee;
import org.openjfx.models.EmployeeDaoImplementation;
import org.openjfx.service.MongoDBConnection;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable, ControlledScreen {

    private Stage stage;

    EmployeeDaoImplementation emlDaoIml = new EmployeeDaoImplementation();

    ScreensController myController;

    @FXML
    private TableView<Employee> userTable;

    @FXML
    private TableColumn<Employee, String> number_col;

    @FXML
    private TableColumn<Employee, String> firstName_col;

    @FXML
    private TableColumn<Employee, String> lastName_col;

    @FXML
    private TableColumn<Employee, String> username_col;

    @FXML
    private TableColumn<Employee, String> password_col;

    @FXML
    private TableColumn<Employee, String> department_col;

    @FXML
    private TableColumn<Employee, Boolean> active_col;

    @FXML
    private ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();

    private ObservableList<Employee> getEmployeeObservableList() {
//        MongoDatabase db = MongoDBConnection.getConnection();
//        MongoCollection<Employee> busCollection = db.getCollection("employees", Employee.class);
//        //busCollection.updateOne(eq("name", "Ada Byron"), combine(set("age", 23), set("name", "Ada Lovelace")));
//
//        busCollection.createIndex(Indexes.ascending("username"));
//        busCollection.createIndex(Indexes.ascending("firstName", "lastName"));
//
//        employeeObservableList = busCollection.find().sort(Sorts.ascending("username"))
//                .into(FXCollections.observableArrayList());
//        return employeeObservableList;

        employeeObservableList = emlDaoIml.getEmployees();
        return employeeObservableList;
    }

    public void showDialog(ActionEvent event) {
        stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(
                    Objects.requireNonNull(App.class.getResource("ui/addNewEmployee.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        stage.setScene(new Scene(root));
//        stage.setTitle("Add a new employee");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        number_col.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(userTable.getItems().indexOf(p.getValue()) + ""));
        number_col.setSortable(false);

        firstName_col.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastName_col.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        username_col.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        password_col.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        department_col.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        active_col.setCellValueFactory(cellData -> cellData.getValue().activeProperty());

        //context menu for each row of table
        userTable.setRowFactory(
                tableView -> {
                    final TableRow<Employee> row = new TableRow<>();
                    final ContextMenu rowContextMenu = new ContextMenu();
                    rowContextMenu.setStyle("-fx-background-radius:10;" + "-fx-border-radius:10;" + "-fx-background-color:#ebebeb;");
                    rowContextMenu.setPrefWidth(200.0);

                    MenuItem deleteItem = new MenuItem("Delete Employee");
                    deleteItem.setOnAction(event -> {

                        // needs multi row selection is set to true
                        ObservableList<Employee> readOnlyItems = userTable.getSelectionModel().getSelectedItems();

                        // removes all selected elements for the table
                        readOnlyItems.forEach(employeeObservableList::remove);

                        userTable.getSelectionModel().clearSelection();

                    });

                    // only display context menu for non-empty rows:
                    row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(rowContextMenu));

                    return row;
                });

        userTable.setItems(getEmployeeObservableList());

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
