package org.openjfx;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.openjfx.db.MongoDBConnection;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class HomeController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private Button showAllButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> routersComboBox;

    @FXML
    private TableView<Bus> busTable;

    @FXML
    private TableColumn<Bus, String> jobCode_col;

    @FXML
    private TableColumn<Bus, String> brand_col;

    @FXML
    private TableColumn<Bus, String> routeNumber_col;

    @FXML
    private TableColumn<Bus, String> routeName_col;

    @FXML
    private TableColumn<Bus, String> numberPlate_col;

    @FXML
    private TableColumn<Bus, Boolean> contract_col;

    @FXML
    private TableColumn<Bus, Date> startDay_col;

    @FXML
    private TableColumn<Bus, Date> endDay_col;

    @FXML
    private TableColumn<Bus, String> note_col;

    @FXML
    private ObservableList<Bus> busData = FXCollections.observableArrayList();

    private static FilteredList<Bus> busFilteredList = null;

    //get all distinct route
    private void getAllRouteForComboBox(MongoDatabase db) {
        DistinctIterable<String> iterable = db.getCollection("bus", Bus.class).distinct("routeNumber", String.class);

        for (String s : iterable) {
            routersComboBox.getItems().add(s);
        }
    }

    //get data for table
    public ObservableList<Bus> getBusList() {
        System.out.println("getBusList run ......");
        //ObservableList<Bus> buses = FXCollections.observableArrayList();
        MongoDatabase db = MongoDBConnection.GetDatabase();
        MongoCollection<Bus> busCollection = db.getCollection("bus", Bus.class);
        //busCollection.updateOne(eq("name", "Ada Byron"), combine(set("age", 23), set("name", "Ada Lovelace")));

        getAllRouteForComboBox(db);

        busCollection.createIndex(Indexes.ascending("routeNumber"));
        busCollection.createIndex(Indexes.ascending("routeNumber", "numberPlate"));

        busData = busCollection.find().sort(Sorts.ascending("routeNumber", "numberPlate"))
                .into(FXCollections.observableArrayList());
        return busData;
    }

    public void setSearchTextFieldOnMousePressed() {
        searchTextField.setStyle("-fx-border-color: #00AFF0;");
    }

    public void setSearchTextFieldOnMouseExited() {
        searchTextField.setStyle("-fx-border-color: transparent;" + "-fx-background-color: #d6d6d6;");
    }


//    private void validate(CheckBox checkBox, Bus item, Event event) {
//        // Validate here
////        event.consume();
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirm change !!!!");
//        alert.setHeaderText("Look, a Confirmation Dialog");
//        alert.setContentText("Are you ok with this?");
//
//        // Set the checkbox if the user want to continue
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK)
//            checkBox.setSelected(!checkBox.isSelected());
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Home controller start run ...");
        busTable.setEditable(true);

        Callback<TableColumn<Bus, String>, TableCell<Bus, String>> textFieldCellFactory
                = (TableColumn<Bus, String> param) -> new EditingCell();

        Callback<TableColumn<Bus, Date>, TableCell<Bus, Date>> dateCellFactory
                = (TableColumn<Bus, Date> param) -> new DateEditingCell();

        jobCode_col.setCellValueFactory(cellData -> cellData.getValue().jobCodeProperty());
        jobCode_col.setCellFactory(textFieldCellFactory);
        jobCode_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, String> t) -> {
                    System.out.println("------job code update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    System.out.println("_id " + bus.getId());
                    String oldJobCode = bus.getJobCode();
                    System.out.println("+++job code cu: " + oldJobCode);
                    String newJobCode = t.getNewValue();
                    System.out.println("+++job code hien tai: " + newJobCode);

                    if (!oldJobCode.equals(newJobCode)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setJobCode(t.getNewValue());

                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("jobCode", newJobCode);

                        updateInBackground(filters, updateValue);
                        System.out.println("job code moi nhap khac job code cu ***** nen update" + bus.getJobCode());
                    } else {
                        System.out.println("----khong can update jobcode----");
                    }

                });

        brand_col.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        brand_col.setCellFactory(textFieldCellFactory);
        brand_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, String> t) -> {
                    System.out.println("------brand update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    String oldBrand = bus.getBrand();
                    System.out.println("+++brand cu: " + oldBrand);
                    String newBrand = t.getNewValue();
                    System.out.println("+++brand hien tai: " + newBrand);

                    if (!oldBrand.equals(newBrand)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setBrand(t.getNewValue());
                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("brand", newBrand);

                        updateInBackground(filters, updateValue);
                        System.out.println("brand moi khac brand cu ***** nen update");
                    } else {
                        System.out.println("----khong can update brand----");
                    }
                });

        routeNumber_col.setCellValueFactory(cellData -> cellData.getValue().routeNumberProperty());
        routeNumber_col.setCellFactory(textFieldCellFactory);
        routeNumber_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, String> t) -> {
                    System.out.println("------ma so tuyen update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    String oldRouteNumber = bus.getRouteNumber();
                    System.out.println("+++mst cu: " + oldRouteNumber);
                    String newRouteNumber = t.getNewValue();
                    System.out.println("+++mst hien tai: " + newRouteNumber);

                    if (!oldRouteNumber.equals(newRouteNumber)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setRouteNumber(t.getNewValue());

                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("routeNumber", newRouteNumber);

                        updateInBackground(filters, updateValue);
                        System.out.println("mst moi nhap khac mst cu ***** nen update");
                    } else {
                        System.out.println("----khong can update mst----");
                    }

                });

        routeName_col.setCellValueFactory(cellData -> cellData.getValue().routeNameProperty());
        routeName_col.setCellFactory(textFieldCellFactory);
        routeName_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, String> t) -> {
                    System.out.println("------ten tuyen update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    String oldRouteName = bus.getRouteName();
                    System.out.println("+++ten tuyen cu: " + oldRouteName);
                    String newRouteName = t.getNewValue();
                    System.out.println("+++ten tuyen hien tai: " + newRouteName);

                    if (!oldRouteName.equals(newRouteName)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setRouteName(t.getNewValue());
                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("routeName", newRouteName);

                        updateInBackground(filters, updateValue);
                        System.out.println("ten tuyen moi nhap khac ten tuyen cu ***** nen update");
                    } else {
                        System.out.println("----khong can update ten tuyen----");
                    }

                });

        numberPlate_col.setCellValueFactory(cellData -> cellData.getValue().numberPlateProperty());
        numberPlate_col.setCellFactory(textFieldCellFactory);
        numberPlate_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, String> t) -> {
                    System.out.println("------bsx update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    String oldNumPlate = bus.getNumberPlate();
                    System.out.println("+++bsx cu: " + oldNumPlate);
                    String newNumPlate = t.getNewValue();
                    System.out.println("+++bsx moi: " + newNumPlate);

                    if (!oldNumPlate.equals(newNumPlate)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setNumberPlate(t.getNewValue());
                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("numberPlate", newNumPlate);

                        updateInBackground(filters, updateValue);
                        System.out.println("bsx moi khac bsx cu ***** nen update");
                    } else {
                        System.out.println("----khong can update bsx----");
                    }

                });


        contract_col.setCellValueFactory(cellData -> cellData.getValue().contractProperty());

//        contract_col.setCellFactory(p -> {
//            CheckBox checkBox = new CheckBox();
//            TableCell<Bus, Boolean> tableCell = new TableCell<Bus, Boolean>() {
//
//                @Override
//                protected void updateItem(Boolean item, boolean empty) {
//
//                    super.updateItem(item, empty);
//                    if (empty || item == null)
//                        setGraphic(null);
//                    else {
//                        setGraphic(checkBox);
//                        checkBox.setSelected(item);
//                    }
//                }
//            };
//
//            checkBox.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->
//                    validate(checkBox, (Bus) tableCell.getTableRow().getItem(), event));
//
////            checkBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
////                if(event.getCode() == KeyCode.SPACE)
////                    validate(checkBox, (Person) cell.getTableRow().getItem(), event);
////            });
//
//            tableCell.setAlignment(Pos.CENTER);
//            tableCell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//
//            return tableCell;
//        });


        contract_col.setCellFactory(p -> {
            final CheckBoxTableCell<Bus, Boolean> ctCell = new CheckBoxTableCell<>();
            ctCell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
//                validate(checkBox, (Bus) tableCell.getTableRow().getItem(), event));
                System.out.println("**** test ****");
                System.out.println(!ctCell.getItem());
                Bus bus = ctCell.getTableRow().getItem();
                System.out.println("_id: " + bus.getId() + ", job Code: " + bus.getJobCode());
                Bson filter = eq("_id", bus.getId());
                Bson updateValue = set("contract", !ctCell.getItem());
                updateInBackground(filter, updateValue);
            });
//            final BooleanProperty selected = new SimpleBooleanProperty();
            ctCell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(Integer index) {
                    return busTable.getItems().get(index).contractProperty();
                }
            });

            return ctCell;

        });

        startDay_col.setCellValueFactory(cellData -> cellData.getValue().startDayProperty());
        startDay_col.setCellFactory(dateCellFactory);
        startDay_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, Date> t) -> {
                    System.out.println("------start day update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    Date oldStartDay = bus.getStartDay();
                    System.out.println("+++ngay cu: " + oldStartDay);
                    Date newStartDay = t.getNewValue();
                    System.out.println("+++ngay moi: " + newStartDay);

                    if (!oldStartDay.equals(newStartDay)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setStartDay(t.getNewValue());
                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("startDay", newStartDay);

                        updateInBackground(filters, updateValue);
                        System.out.println("start day moi khac start day cu ***** nen update");
                    } else {
                        System.out.println("----khong can update start day----");
                    }
                });


        endDay_col.setCellValueFactory(cellData -> cellData.getValue().endDayProperty());
        endDay_col.setCellFactory(dateCellFactory);
        endDay_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, Date> t) -> {
                    System.out.println("------end day update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    Date oldEndDay = bus.getEndDay();
                    System.out.println("+++end ngay cu: " + oldEndDay);
                    Date newEndDay = t.getNewValue();
                    System.out.println("+++end ngay moi: " + newEndDay);

                    if (!oldEndDay.equals(newEndDay)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setEndDay(t.getNewValue());
                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("endDay", newEndDay);

                        updateInBackground(filters, updateValue);
                        System.out.println("end day moi khac end day cu ***** nen update");
                    } else {
                        System.out.println("----khong can update end day----");
                    }
                });


        note_col.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        note_col.setCellFactory(textFieldCellFactory);
        note_col.setOnEditCommit(
                (TableColumn.CellEditEvent<Bus, String> t) -> {
                    System.out.println("------note update------....");
                    Bus bus = busTable.getSelectionModel().getSelectedItem();
                    String oldNote = bus.getNote();
                    System.out.println("+++note cu: " + oldNote);
                    String newNote = t.getNewValue();
                    System.out.println("+++note moi: " + newNote);

                    if (!oldNote.equals(newNote)) {
                        //update new job code
                        ((Bus) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setNote(t.getNewValue());
                        Bson filters = eq("_id", bus.getId());
                        Bson updateValue = set("note", newNote);

                        updateInBackground(filters, updateValue);
                        System.out.println("note moi khac note cu ***** nen update");
                    } else {
                        System.out.println("----khong can update note----");
                    }
                });

        //context menu for each row of table
        busTable.setRowFactory(
                tableView -> {
                    final TableRow<Bus> row = new TableRow<>();
                    final ContextMenu rowContextMenu = new ContextMenu();
                    rowContextMenu.setStyle("-fx-background-radius:10;" + "-fx-border-radius:10;" + "-fx-background-color:#ebebeb;");
                    rowContextMenu.setPrefWidth(100.0);
                    MenuItem editItem = new MenuItem("Edit row");
                    editItem.setOnAction(event -> {
                        //edit row
                    });
                    MenuItem delelteItem = new MenuItem("Delete row");
                    delelteItem.setOnAction(event -> {
                        //delete item
                        //Bus selectedItem = busTable.getSelectionModel().getSelectedItem();
                        //System.out.println(selectedItem.getId());
                        //ObservableList<Bus> tableItems = busTable.getItems();

                        // needs multirowselection is set to true
                        ObservableList<Bus> readOnlyItems = busTable.getSelectionModel().getSelectedItems();

                        // removes all selected elements for the table
                        readOnlyItems.forEach(busData::remove);

                        // clear the selection
                        busTable.getSelectionModel().clearSelection();

                    });

                    MenuItem insertNewRowItem = new MenuItem("Insert in the last row");

                    insertNewRowItem.setOnAction(event -> {
//                        busFilteredList.setPredicate(Bus -> true);
                        refreshTable(busFilteredList);
                        insertInBackground(new Bus());
//                        System.out.println("111111111111");

                    });

                    rowContextMenu.getItems().addAll(editItem, delelteItem, insertNewRowItem);

                    // only display context menu for non-empty rows:
                    row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(rowContextMenu));

                    return row;
                });


        //context menu of table
        ContextMenu tableContextMenu = new ContextMenu();
        tableContextMenu.setStyle("-fx-background-radius:10;" + "-fx-border-radius:10;" + "-fx-background-color:#ebebeb;");
        MenuItem insertNewRowItem = new MenuItem("Insert New Row");
        insertNewRowItem.setOnAction(event -> {

            refreshTable(busFilteredList);
            insertInBackground(new Bus());

        });
        tableContextMenu.getItems().add(insertNewRowItem);

        MenuItem refreshAllRowInTableITem = new MenuItem("Refresh all Row in Table");
        refreshAllRowInTableITem.setOnAction(event -> refreshTable(busFilteredList));

        tableContextMenu.getItems().add(refreshAllRowInTableITem);

        busTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                busTable.contextMenuProperty().bind(Bindings.when(busTable.disabledProperty()).then((ContextMenu) null).otherwise(tableContextMenu));
            }
        });

        //populate data table
        busTable.setItems(getBusList());

        busFilteredList = new FilteredList<>(busData, b -> true);

        // search text field with num plate and num route
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> busFilteredList.setPredicate(Bus -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }

            String searchKeyWord = newValue.toLowerCase();

            if (Bus.getNumberPlate().toLowerCase().contains(searchKeyWord)) {
                return true;
            } else return Bus.getRouteNumber().toLowerCase().contains(searchKeyWord);

        }));

        //select route comboBox
        routersComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(newValue);
            busFilteredList.setPredicate(Bus -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String router = newValue.toLowerCase();

                return Bus.getRouteNumber().toLowerCase().contains(router);
            });

        });

        //show all button click
        showAllButton.setOnAction(event -> {
            searchTextField.setText("");
            routersComboBox.setPromptText("Chọn tuyến");
            busFilteredList.setPredicate(Bus -> true);
        });

        System.out.println("0");
        //
        SortedList<Bus> sortedList = new SortedList<>(busFilteredList);
        sortedList.comparatorProperty().bind(busTable.comparatorProperty());

        System.out.println("1");
        //
        busTable.setItems(sortedList);

    }

    //fresh all row of table
    private void refreshTable(FilteredList<Bus> filteredList) {
        filteredList.setPredicate(Bus -> true);
    }


    //insert data in new thread
    public synchronized void insertInBackground(Bus bus) {

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {

                MongoDatabase db = MongoDBConnection.GetDatabase();
                MongoCollection<Bus> busCollection = db.getCollection("bus", Bus.class);

                System.out.println("start insert new bus");

                busCollection.insertOne(bus);

                //get last ObjectId
                ObjectId id = bus.getId();
                System.out.println("last ObjectId: " + id);
                Bus bus = new Bus(id, new SimpleStringProperty("jobCode"),
                        new SimpleStringProperty("brand"),
                        new SimpleStringProperty("routeNumber"),
                        new SimpleStringProperty("routeName"),
                        new SimpleStringProperty("numberPlate"),
                        new SimpleBooleanProperty(true),
                        new SimpleObjectProperty<>(new Date()),
                        new SimpleObjectProperty<>(new Date()),
                        new SimpleStringProperty("note"));
                int lastIndex = busTable.getItems().size();
                System.out.println("last index: " + lastIndex);
                busData.add(lastIndex, bus);
                busTable.getSelectionModel().select(lastIndex);
                busTable.edit(lastIndex, jobCode_col);

                return null;
            }

            @Override
            protected void succeeded() {
                //
                System.out.println("insert done ....");
            }

            @Override
            protected void failed() {
                //
                Alert alert = new Alert(Alert.AlertType.ERROR, getException().getMessage());
                alert.showAndWait();
            }
        };

        new Thread(task).start();

    }

    //update data in new Thread
    public synchronized void updateInBackground(Bson filter, Bson updateValue) {

        Task<Long> task = new Task<>() {

            @Override
            protected Long call() {

                MongoDatabase db = MongoDBConnection.GetDatabase();
                MongoCollection<Bus> busCollection = db.getCollection("bus", Bus.class);

                UpdateResult result = busCollection.updateOne(filter, updateValue);

                //check update
                System.out.println(result.getModifiedCount());

                return result.getModifiedCount();
            }

            @Override
            protected void succeeded() {
                //
                System.out.println("update done ....");
            }

            @Override
            protected void failed() {
                //
                Alert alert = new Alert(Alert.AlertType.ERROR, getException().getMessage());
                alert.showAndWait();
            }
        };

        new Thread(task).start();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    // format date cell
    static class DateEditingCell extends TableCell<Bus, Date> {

        private DatePicker datePicker;

        private DateEditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createDatePicker();
                setText(null);
                setGraphic(datePicker);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getDate().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (datePicker != null) {
                        datePicker.setValue(getDate());
                    }
                    setText(null);
                    setGraphic(datePicker);
                } else {
                    setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                    setGraphic(null);
                }
            }
        }

        private void createDatePicker() {
            datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                System.out.println("Committed: " + datePicker.getValue().toString());
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
        }

        private LocalDate getDate() {
            return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }


    static class EditingCell extends TableCell<Bus, String> {

        private TextField textField;

        private EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(item);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
//                        setGraphic(null);
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnAction((e) -> commitEdit(textField.getText()));
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

                if (!newValue) {
                    System.out.println("Committing " + textField.getText());
                    commitEdit(textField.getText());
                }

            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }
}
