//package org.openjfx;
//
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Aggregates;
//import com.mongodb.client.model.Indexes;
//import com.mongodb.client.model.Projections;
//import com.mongodb.client.model.Sorts;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.CheckBoxTableCell;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.util.Callback;
//import org.openjfx.db.MongoDBConnection;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.time.format.FormatStyle;
//import java.util.Date;
//import java.util.ResourceBundle;
//
//public class Screen1Controller implements Initializable,ControlledScreen {
//    ScreensController myController;
//
//    @FXML
//    private TableView<Bus> busTable;
//
//    @FXML
//    private TableColumn<Bus, String> jobCode_col;
//
//    @FXML
//    private TableColumn<Bus, String> brand_col;
//
//    @FXML
//    private TableColumn<Bus, String> routeNumber_col;
//
//    @FXML
//    private TableColumn<Bus, String> routeName_col;
//
//    @FXML
//    private TableColumn<Bus, String> numberPlate_col;
//
//    @FXML
//    private TableColumn<Bus, CheckBox> contract_col;
//
//    @FXML
//    private TableColumn<Bus, Date> startDay_col;
//
//    @FXML
//    private TableColumn<Bus, Date> endDay_col;
//
//    @FXML
//    private TableColumn<Bus, String> note_col;
//
//    @FXML
//    private ObservableList<Bus> busRouters = FXCollections.observableArrayList();
//
//    @FXML
//    private ObservableList<Bus> busData = FXCollections.observableArrayList();
//
//    @Override
//    public void setScreenParent(ScreensController controller){
//        myController = controller;
//    }
//
//    public void goToScreen2(ActionEvent event){
//        myController.setScreen(App.screen2ID);
//    }
//
//    public ObservableList<Bus> getBusList() {
//        System.out.println("getBusList run ......");
//        //ObservableList<Bus> buses = FXCollections.observableArrayList();
//        MongoDatabase db = MongoDBConnection.GetDatabase();
//        MongoCollection<Bus> busCollection = db.getCollection("bus", Bus.class);
//        busCollection.createIndex(Indexes.ascending("routeNumber"));
//        busCollection.createIndex(Indexes.ascending("routeNumber", "numberPlate"));
//
//        //get only route name
//        busRouters = busCollection.aggregate(
//                FXCollections.observableArrayList(Aggregates.sort(
//                        Projections.fields(
////                                Projections.excludeId(),
//                                Projections.include("routeNumber")
//                                //Projections.computed(
//                                //"firstCategory",
//                                //new Document("$arrayElemAt", Arrays.asList("$categories", 0))
//                                //)
//                        )
//                ))
//        ).into(FXCollections.observableArrayList());
//
//
//        busData = busCollection.find().sort(Sorts.ascending("routeNumber", "numberPlate"))
//                .limit(20).into(FXCollections.observableArrayList());
//        //buses.forEach(System.out::println);
//        return busData;
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//        busTable.setEditable(true);
//
//        jobCode_col.setCellValueFactory(new PropertyValueFactory<>("jobCode"));
//        jobCode_col.setCellFactory(TextFieldTableCell.forTableColumn());
//        jobCode_col.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Bus, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Bus, String> t) {
//                        ((Bus) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setJobCode(t.getNewValue());
//                    }
//                }
//        );
//
//
////        jobCode_col.setCellValueFactory(new PropertyValueFactory<Bus, String>("jobCode"));
////        jobCode_col.setCellFactory(TextFieldTableCell.forTableColumn());
////        jobCode_col.setOnEditCommit(
////                new EventHandler<CellEditEvent<Bus, String>>() {
////                    @Override
////                    public void handle(CellEditEvent<Bus, String> t) {
////                        ((Bus) t.getTableView().getItems().get(
////                                t.getTablePosition().getRow())
////                        ).setJobCode(t.getNewValue());
////                    }
////                }
////        );
//
//        brand_col.setCellValueFactory(new PropertyValueFactory<>("brand"));
//        brand_col.setCellFactory(TextFieldTableCell.forTableColumn());
//        brand_col.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Bus, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Bus, String> t) {
//                        ((Bus) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setBrand(t.getNewValue());
//                    }
//                }
//        );
//
//
//        routeNumber_col.setCellValueFactory(new PropertyValueFactory<>("routeNumber"));
//        routeNumber_col.setCellFactory(TextFieldTableCell.forTableColumn());
//        routeNumber_col.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Bus, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Bus, String> t) {
//                        ((Bus) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setRouteNumber(t.getNewValue());
//                    }
//                }
//        );
//
//
//        routeName_col.setCellValueFactory(new PropertyValueFactory<>("routeName"));
//        routeName_col.setCellFactory(TextFieldTableCell.forTableColumn());
//        routeName_col.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Bus, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Bus, String> t) {
//                        ((Bus) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setRouteName(t.getNewValue());
//                    }
//                }
//        );
//
//
//        numberPlate_col.setCellValueFactory(new PropertyValueFactory<>("numberPlate"));
//        numberPlate_col.setCellFactory(TextFieldTableCell.forTableColumn());
//        numberPlate_col.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Bus, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Bus, String> t) {
//                        ((Bus) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setNumberPlate(t.getNewValue());
//                    }
//                }
//        );
//
////        contract_col.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isContract()));
//        contract_col.setCellValueFactory(new PropertyValueFactory<>("contract"));
//        contract_col.setCellFactory(tc -> new CheckBoxTableCell<>());
//
////        contract_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bus, CheckBox>, ObservableValue<CheckBox>>() {
////
////            @Override
////            public ObservableValue<CheckBox> call(
////                    TableColumn.CellDataFeatures<Bus, CheckBox> arg0) {
////
////                Bus bus = arg0.getValue();
////
////                CheckBox checkBox = new CheckBox();
////                checkBox.selectedProperty().setValue(bus.isContract());
////
////                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
////                    public void changed(ObservableValue<? extends Boolean> ov,
////                                        Boolean old_val, Boolean new_val) {
////                        System.out.println("new value: " + new_val);
////                        bus.setContract(new_val);
////
////                    }
////                });
////
////                return new SimpleObjectProperty<CheckBox>(checkBox);
////            }
////
////        });
//
//
////        contract_col.setCellValueFactory(new PropertyValueFactory<>("contract"));
////        contract_col.setCellFactory(c -> new CheckBoxTableCell<>());
////        contract_col.setOnEditCommit(
////                new EventHandler<CellEditEvent<Bus, Boolean>>() {
////                    @Override
////                    public void handle(CellEditEvent<Bus, Boolean> t) {
////                        ((Bus) t.getTableView().getItems().get(
////                                t.getTablePosition().getRow())
////                        ).setContract(t.getNewValue());
////                    }
////                }
////        );
//
//
//        Callback<TableColumn<Bus, Date>, TableCell<Bus, Date>> dateCellFactory
//                = (TableColumn<Bus, Date> param) -> new DateEditingCell();
//        startDay_col.setCellValueFactory(new PropertyValueFactory<Bus, Date>("startDay"));
//        startDay_col.setCellFactory(dateCellFactory);
//
//
////            startDay_col.setCellValueFactory(new PropertyValueFactory<>("startDay"));
////            startDay_col.setCellFactory(new DateCell());
//
////            startDay_col.setCellFactory(tc-> new DateCell(tc.getCellData()));
////
////        startDay_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bus, DatePicker>, ObservableValue<DatePicker>>() {
////
////            @Override
////            public ObservableValue<DatePicker> call(
////                    TableColumn.CellDataFeatures<Bus, DatePicker> arg0) {
////
////                Bus bus = arg0.getValue();
////
//////              CheckBox checkBox = new CheckBox();
////                DatePicker datePicker = new DatePicker();
////                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
////
//////                checkbox.selectedProperty().setValue(bus.isContract());
////
////
////                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
////                    public void changed(ObservableValue<? extends Boolean> ov,
////                                        Boolean old_val, Boolean new_val) {
////                        System.out.println("new value: " + new_val);
////                        bus.setContract(new_val);
////
////                    }
////                });
////
////                return new SimpleObjectProperty<DatePicker>(checkBox);
////            }
////
////        });
//
//
////        startDay_col.setCellValueFactory(new PropertyValueFactory<>("startDay"));
//////        startDay_col.setCellFactory(DateCell.forTableColumn());
////        startDay_col.setOnEditCommit(
////                new EventHandler<CellEditEvent<Bus, Date>>() {
////                    @Override
////                    public void handle(CellEditEvent<Bus, Date> t) {
////                        ((Bus) t.getTableView().getItems().get(
////                                t.getTablePosition().getRow())
////                        ).setStartDay(t.getNewValue());
////                    }
////                }
////        );
//
////        Callback<TableColumn<Bus, Date>, TableCell<Bus, Date>> dateCellFactory
////                = (TableColumn<Bus, Date> param) -> new DateEditingCell();
//        endDay_col.setCellValueFactory(new PropertyValueFactory<Bus, Date>("endDay"));
//        endDay_col.setCellFactory(dateCellFactory);
////        endDay_col.setCellValueFactory(new PropertyValueFactory<Bus, Date>("endDay"));
////        //routeName_col.setCellFactory(TextFieldTableCell.forTableColumn());
////        endDay_col.setOnEditCommit(
////                new EventHandler<CellEditEvent<Bus, Date>>() {
////                    @Override
////                    public void handle(CellEditEvent<Bus, Date> t) {
////                        ((Bus) t.getTableView().getItems().get(
////                                t.getTablePosition().getRow())
////                        ).setEndDay(t.getNewValue());
////                    }
////                }
////        );
//
//        note_col.setCellValueFactory(new PropertyValueFactory<>("note"));
//        note_col.setCellFactory(TextFieldTableCell.forTableColumn());
//        note_col.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Bus, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Bus, String> t) {
//                        ((Bus) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setNote(t.getNewValue());
//                    }
//                }
//        );
//
//
//        //populate data table
//        busTable.setItems(getBusList());
//
//
//        FilteredList<Bus> busFilteredList = new FilteredList<>(busData, b -> true);
//
//
//
//
//        SortedList<Bus> sortedList = new SortedList<>(busFilteredList);
//        sortedList.comparatorProperty().bind(busTable.comparatorProperty());
//
//        busTable.setItems(sortedList);
//    }
//
//
//    //search for route
//    @FXML
//    public ObservableList<Bus> searchByRoute() {
////        busRouterComboBox.setOnAction(e->{
//////            System.out.println(busRouterComboBox.getValue());
////            busData.clear();
////        });
//        return busData;
//    }
//
//
//    // format date cell
//    static class DateEditingCell extends TableCell<Bus, Date> {
//
//        private DatePicker datePicker;
//
//        private DateEditingCell() {
//        }
//
//        @Override
//        public void startEdit() {
//            if (!isEmpty()) {
//                super.startEdit();
//                createDatePicker();
//                setText(null);
//                setGraphic(datePicker);
//            }
//        }
//
//        @Override
//        public void cancelEdit() {
//            super.cancelEdit();
//
//            setText(getDate().toString());
//            setGraphic(null);
//        }
//
//        @Override
//        public void updateItem(Date item, boolean empty) {
//            super.updateItem(item, empty);
//
//            if (empty) {
//                setText(null);
//                setGraphic(null);
//            } else {
//                if (isEditing()) {
//                    if (datePicker != null) {
//                        datePicker.setValue(getDate());
//                    }
//                    setText(null);
//                    setGraphic(datePicker);
//                } else {
//                    setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
//                    setGraphic(null);
//                }
//            }
//        }
//
//        private void createDatePicker() {
//            datePicker = new DatePicker(getDate());
//            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
//            datePicker.setOnAction((e) -> {
//                System.out.println("Committed: " + datePicker.getValue().toString());
//                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//            });
////            datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
////                if (!newValue) {
////                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
////                }
////            });
//        }
//
//        private LocalDate getDate() {
//            return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        }
//    }
//
//
//    static class EditingCell extends TableCell<Bus, String> {
//
//        private TextField textField;
////        private ComboBox contractComboBox;
//
//        public EditingCell() {
//        }
//
//        @Override
//        public void startEdit() {
//            System.out.println("start edit");
//            if (!isEmpty()) {
//                super.startEdit();
//                createTextField();
//                setText(null);
//                setGraphic(textField);
//                textField.selectAll();
//            }
//        }
//
//        @Override
//        public void cancelEdit() {
//            super.cancelEdit();
//            System.out.println("cancel editing ....");
//            setText((String) getItem());
//            setGraphic(null);
//        }
//
//        @Override
//        public void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//            System.out.println("update item ****");
//            if (empty) {
//                setText(null);
//                setGraphic(null);
//            } else {
//                if (isEditing()) {
//                    if (textField != null) {
//                        textField.setText(getString());
//                    }
//                    setText(null);
//                    setGraphic(textField);
//                } else {
//                    setText(getString());
//                    setGraphic(null);
//                }
//            }
//        }
//
//        private void createTextField() {
//            textField = new TextField(getString());
//            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
//            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//                @Override
//                public void changed(ObservableValue<? extends Boolean> arg0,
//                                    Boolean arg1, Boolean arg2) {
//                    if (!arg2) {
//                        commitEdit(textField.getText());
//                    }
//                }
//            });
//        }
//
//        private String getString() {
//            return getItem() == null ? "" : getItem();
//        }
//    }
//
//}
