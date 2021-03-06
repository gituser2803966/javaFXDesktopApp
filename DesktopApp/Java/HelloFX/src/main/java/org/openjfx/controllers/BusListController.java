package org.openjfx.controllers;

import com.mongodb.client.DistinctIterable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.bson.conversions.Bson;
import org.openjfx.App;
import org.openjfx.models.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static java.util.stream.Collectors.toList;

public class BusListController implements Initializable, ControlledScreen {

    ScreensController myController;
    BusListDaoImplementation busDaoImp = new BusListDaoImplementation();
    OnJobBusDaoImplement onJobBusDaoImp = new OnJobBusDaoImplement();

    @FXML
    private BorderPane homeBorderPanel;

    @FXML
    private Label busTotalLabel;

    @FXML
    private Label adsLabel;

    @FXML
    private Label noAdsLabel;

    @FXML
    private Label insideContractLabel;

    @FXML
    private Label outsideContractLabel;

    @FXML
    private Button showAllButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> routersComboBox;
    //on job bus
    @FXML
    private TableView<OnJobBus> onJobBusTable;
    //s??? th??? t???
    @FXML
    private TableColumn<OnJobBus, String> onJobNo_col;
    //x?? nghi???p
    @FXML
    private TableColumn<OnJobBus, String> onJobEnterprise_col;
    //tuy???n xe
    @FXML
    private TableColumn<OnJobBus, Integer> onJobRouteNumber_col;
    //ch???ng lo???i xe
    @FXML
    private TableColumn<OnJobBus, String> onJobVehicleCategory_col;
    //bi???n ki???m so??t
    @FXML
    private TableColumn<OnJobBus, String> onJobNumberPlate_col;
    //ng??y thi c??ng
    @FXML
    private TableColumn<OnJobBus, Date> onJobDateOfConstruction_col;
    //ng??y h???t h???n
    @FXML
    private TableColumn<OnJobBus, Date> onJobEndDay_col;
    //m?? h??ng
    @FXML
    private TableColumn<OnJobBus, String> onJobJobCode_col;
    //agency
    @FXML
    private TableColumn<OnJobBus, String> onJobAgency_col;
    //nh??n h??ng
    @FXML
    private TableColumn<OnJobBus, String> onJobBrand_col;
    //n???i dung thi c??ng
    @FXML
    private TableColumn<OnJobBus, String> onJobConstructionContent_col;
    //slogan
    @FXML
    private TableColumn<OnJobBus, String> onJobSlogan_col;
    //cs
    @FXML
    private TableColumn<OnJobBus, String> onJobCs_col;

    @FXML
    private ObservableList<OnJobBus> onJobBusData = FXCollections.observableArrayList();

    private static FilteredList<OnJobBus> onJobBusFilteredList = null;

    //bus list
    @FXML
    private TableView<BusList> busTable;

    //s??? th??? t???
    @FXML
    private TableColumn<BusList, String> no_col;
    //x?? nghi???p
    @FXML
    private TableColumn<BusList, String> enterprise_col;
    //b??i xe
    @FXML
    private TableColumn<BusList, String> parkingLot_col;
    //tuy???n xe
    @FXML
    private TableColumn<BusList, Integer> routeNumber_col;

    //ch???ng lo???i xe
    @FXML
    private TableColumn<BusList, String> vehicleCategory_col;
    //hi???n tr???ng
    @FXML
    private TableColumn<BusList, String> status_col;
    //bi???n ki???m so??t
    @FXML
    private TableColumn<BusList, String> numberPlate_col;
    //ng??y thi c??ng
    @FXML
    private TableColumn<BusList, Date> dateOfConstruction_col;
    //m?? h??ng
    @FXML
    private TableColumn<BusList, String> jobCode_col;
    //agency
    @FXML
    private TableColumn<BusList, String> agency_col;
    //nh??n h??ng
    @FXML
    private TableColumn<BusList, String> brand_col;
    //slogan
    @FXML
    private TableColumn<BusList, String> slogan_col;
    //th???i h???n h???p ?????ng ==> production
    @FXML
    private TableColumn<BusList, Integer> duration_col;
    //?????i thi c??ng
    @FXML
    private TableColumn<BusList, String> constructionTeam_col;
    //In - Quy c??ch
    @FXML
    private TableColumn<BusList, String> printAndSupplier_col;
    //M?? t???
    @FXML
    private TableColumn<BusList, String> description_col;
    //cs
    @FXML
    private TableColumn<BusList, String> cs_col;
    //ng??y b???t ?????u
    @FXML
    private TableColumn<BusList, Date> startDay_col;
    //ng??y k???t th??c
    @FXML
    private TableColumn<BusList, Date> endDay_col;
    //th???i h???n
    @FXML
    private TableColumn<BusList, Number> remainingDay_col;
    //
    @FXML
    private TableColumn<BusList, Boolean> contract_col;
    //note
    @FXML
    private TableColumn<BusList, String> note_col;

    @FXML
    private ObservableList<BusList> busListData = FXCollections.observableArrayList();

    private static FilteredList<BusList> busListFilteredList = null;

    //get all distinct route
    private void getAllRouteForComboBox() {
        DistinctIterable<String> routers = busDaoImp.getOnlyRoute();
        for (String s : routers) {
            routersComboBox.getItems().add(s);
        }
    }

    //get data for table
    public ObservableList<BusList> getBusList() {
        busListData = busDaoImp.getBusList();
        return busListData;
    }


    //get data for table
    public ObservableList<OnJobBus> getOnJobBusList() {
        onJobBusData = onJobBusDaoImp.getOnJobBusList();
        return onJobBusData;
    }

    public void setSearchTextFieldOnMousePressed() {
        searchTextField.setStyle("-fx-border-color: #00AFF0;");
    }

    public void setSearchTextFieldOnMouseExited() {
        searchTextField.setStyle("-fx-border-color: transparent;" + "-fx-background-color: #d6d6d6;");
    }

    private boolean checkEditPermissions() {
        EmployeeHolder employeeHolder = EmployeeHolder.getInstance();
        Employee employee = employeeHolder.getUser();

        if (employee.getDepartment().equals("PRODUCTION") || employee.getDepartment().equals("BOD")) {
            return true;
        } else
            return false;
    }

    //
    @FXML
    public void backToNavigationScreen(Event event) {
        BorderPane root = (BorderPane) homeBorderPanel.getScene().getRoot();
        root.setCenter(myController.getScreen(DashBoardController.screenNavigationID));

    }

    @FXML
    private void sendOnJobBusListDataToChartScreen(ObservableList<OnJobBus> b) {

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("ui/chart.fxml"));
            Stage stage = new Stage();
            stage.setScene(
                    new Scene(loader.load())
            );
            ChartController controller = loader.getController();
            controller.initDataForOnJobBusList(b);
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //Kh???i t???o d??? li???u cho danh s??ch xe bu??t
    public void initDataForBusList() {
        if (checkEditPermissions()) {
            System.out.println("you can edit on table");
            busTable.setEditable(true);
        } else {
            System.out.println("can't edit on table");
            busTable.setEditable(false);
        }

        busTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Callback<TableColumn<BusList, String>, TableCell<BusList, String>> textFieldCellFactory
                = (TableColumn<BusList, String> param) -> new EditingCell();

        Callback<TableColumn<BusList, Date>, TableCell<BusList, Date>> dateCellFactory
                = (TableColumn<BusList, Date> param) -> new DateEditingCell();

        Callback<TableColumn<BusList, Integer>, TableCell<BusList, Integer>> integerCellFactory
                = (TableColumn<BusList, Integer> param) -> new IntegerEditingCell();

        //no
        no_col.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(busTable.getItems().indexOf(p.getValue()) + ""));
        no_col.setSortable(false);

        //Doanh nghi???p
        enterprise_col.setCellValueFactory(cellData -> cellData.getValue().enterpriseProperty());
        enterprise_col.setCellFactory(TextFieldTableCell.forTableColumn());
        enterprise_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getEnterprise();
                    String newValue = t.getNewValue();
                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setEnterprise(t.getNewValue());
                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("enterprise", newValue);
                        updateInBackground(filters, updateValue);
                        System.out.println("=== Update Doanh nghi???p ===");
                    }

                });


        //B??i xe
        parkingLot_col.setCellValueFactory(cellData -> cellData.getValue().parkingLotProperty());
        parkingLot_col.setCellFactory(TextFieldTableCell.forTableColumn());
        //parkingLot_col.setCellFactory(textFieldCellFactory);
        parkingLot_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getParkingLot();
                    String newValue = t.getNewValue();
                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setParkingLot(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("parkingLot", newValue);

                        updateInBackground(filters, updateValue);
                    }
                });

        //tuy???n xe
        routeNumber_col.setCellValueFactory(cellData -> cellData.getValue().routeNumberProperty().asObject());
        routeNumber_col.setCellFactory(integerCellFactory);
        routeNumber_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, Integer> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    int oldValue = busList.getRouteNumber();
                    int newValue = t.getNewValue();

                    if (oldValue != newValue) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setRouteNumber(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("routeNumber", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //Lo???i xe
        vehicleCategory_col.setCellValueFactory(cellData -> cellData.getValue().vehicleCategoryProperty());
        vehicleCategory_col.setCellFactory(TextFieldTableCell.forTableColumn());
        vehicleCategory_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getVehicleCategory();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setVehicleCategory(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("vehicleCategory", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //Hi???n tr???ng ==> ??ang d??n ho???c ??ang tr???ng, c??n h???n hay h???t h???n
        status_col.setCellValueFactory(cellData -> Bindings.when(cellData.getValue().brandProperty().isEmpty()).then("").otherwise("??ang d??n"));
        status_col.setCellFactory(TextFieldTableCell.forTableColumn());
        status_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getStatus();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setStatus(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("status", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //Bi???n ki???m so??t
        numberPlate_col.setCellValueFactory(cellData -> cellData.getValue().numberPlateProperty());
        numberPlate_col.setCellFactory(TextFieldTableCell.forTableColumn());
        numberPlate_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getNumberPlate();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setNumberPlate(t.getNewValue());
                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("numberPlate", newValue);
                        updateInBackground(filters, updateValue);
                    }

                });

        //Ng??y thi c??ng
        dateOfConstruction_col.setCellValueFactory(cellData -> cellData.getValue().dateOfConstructionProperty());
        dateOfConstruction_col.setCellFactory(dateCellFactory);
        dateOfConstruction_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, Date> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    Date oldValue = busList.getDateOfConstruction();
                    Date newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setDateOfConstruction(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("dateOfConstruction", newValue);

                        updateInBackground(filters, updateValue);
                    }
                });

        //M?? h??ng
        jobCode_col.setCellValueFactory(cellData -> cellData.getValue().jobCodeProperty());
        //jobCode_col.setCellFactory(textFieldCellFactory);
        jobCode_col.setCellFactory(TextFieldTableCell.forTableColumn());
        jobCode_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();

                    String oldValue = busList.getJobCode();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setJobCode(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("jobCode", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //Agency
        agency_col.setCellValueFactory(cellData -> cellData.getValue().agencyProperty());
        //agency_col.setCellFactory(textFieldCellFactory);
        agency_col.setCellFactory(TextFieldTableCell.forTableColumn());
        agency_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getAgency();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setAgency(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("agency", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //Nh??n h??ng
        brand_col.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        // brand_col.setCellFactory(textFieldCellFactory);
        brand_col.setCellFactory(TextFieldTableCell.forTableColumn());
        brand_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();

                    String oldValue = busList.getBrand();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setBrand(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("brand", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //Slogan
        slogan_col.setCellValueFactory(cellData -> cellData.getValue().sloganProperty());
        //slogan_col.setCellFactory(textFieldCellFactory);
        slogan_col.setCellFactory(TextFieldTableCell.forTableColumn());
        slogan_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getSlogan();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setSlogan(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("slogan", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //Th???i h???n h???p ?????ng ==> Integer
        duration_col.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        duration_col.setCellFactory(integerCellFactory);
        duration_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, Integer> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    int oldValue = busList.getDuration();
                    int newValue = t.getNewValue();


                    if (oldValue != newValue) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setDuration(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("duration", newValue);

                        updateInBackground(filters, updateValue);

                    }

                });

        //?????i thi c??ng
        constructionTeam_col.setCellValueFactory(cellData -> cellData.getValue().constructionTeamProperty());
        constructionTeam_col.setCellFactory(TextFieldTableCell.forTableColumn());
        constructionTeam_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getConstructionTeam();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setConstructionTeam(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("constructionTeam", newValue);

                        updateInBackground(filters, updateValue);

                    }

                });

        //In - Quy c??ch
        printAndSupplier_col.setCellValueFactory(cellData -> cellData.getValue().printAndSupplierProperty());
        //printAndSupplier_col.setCellFactory(textFieldCellFactory);
        printAndSupplier_col.setCellFactory(TextFieldTableCell.forTableColumn());
        printAndSupplier_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getPrintAndSupplier();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setPrintAndSupplier(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("printAndSupplier", newValue);

                        updateInBackground(filters, updateValue);

                    }

                });

        //M?? t???
        description_col.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        //description_col.setCellFactory(textFieldCellFactory);
        description_col.setCellFactory(TextFieldTableCell.forTableColumn());
        description_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getDescription();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setDescription(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("description", newValue);

                        updateInBackground(filters, updateValue);

                    }

                });

        //cs
        cs_col.setCellValueFactory(cellData -> cellData.getValue().csProperty());
        cs_col.setCellFactory(TextFieldTableCell.forTableColumn());
        cs_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {
                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getCs();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setCs(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("cs", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //ng??y b???t ?????u
        startDay_col.setCellValueFactory(cellData -> cellData.getValue().startDayProperty());
        startDay_col.setCellFactory(dateCellFactory);
        startDay_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, Date> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    Date oldValue = busList.getStartDay();
                    Date newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setStartDay(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("startDay", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //ng??y k???t th??c
        endDay_col.setCellValueFactory(cellData -> cellData.getValue().endDayProperty());
        endDay_col.setCellFactory(dateCellFactory);
        endDay_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, Date> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    Date oldValue = busList.getEndDay();
                    Date newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setEndDay(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("endDay", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });
        //th???i h???n ==> Integer
        remainingDay_col.setCellValueFactory(cellData -> cellData.getValue().remainingDayProperty());

        //h???p ?????ng
        contract_col.setCellValueFactory(cellData -> cellData.getValue().contractProperty());
        contract_col.setCellFactory(p -> {
            CheckBoxTableCell<BusList, Boolean> ctCell = new CheckBoxTableCell<>();

            ctCell.setSelectedStateCallback(index -> busTable.getItems().get(index).contractProperty());
            //ki???m tra xem b???n c?? th??? click v??o checkBox ??? c???t h???p ?????ng hay kh??ng...
            if (checkEditPermissions()) {
                ctCell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

                    System.out.println(!ctCell.getItem());
                    BusList busList = ctCell.getTableRow().getItem();

                    //update checked state
                    busList.setContract(!ctCell.getItem());


                    Bson filter = eq("uuid", busList.getUuid());
                    Bson updateValue = set("contract", !ctCell.getItem());
                    updateInBackground(filter, updateValue);

                    //update for UI
                    updateTheNumberOfBusesOutsideContract(busListData);
                    updateTheNumberOfBusesInTheContract(busListData);

                    event.consume();

                });
            } else {
                ctCell.setDisable(true);
            }
            return ctCell;
        });
        //note
        note_col.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
//        note_col.setCellFactory(TextFieldTableCell.<Bus>forTableColumn());
        note_col.setCellFactory(TextFieldTableCell.forTableColumn());
        note_col.setOnEditCommit(
                (TableColumn.CellEditEvent<BusList, String> t) -> {

                    BusList busList = busTable.getSelectionModel().getSelectedItem();
                    String oldValue = busList.getNote();
                    String newValue = t.getNewValue();


                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        ((BusList) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setNote(t.getNewValue());

                        Bson filters = eq("uuid", busList.getUuid());
                        Bson updateValue = set("note", newValue);

                        updateInBackground(filters, updateValue);
                    }

                });

        //th??m menu khi click chu???t v??o 1 d??ng tr??n table
        if (checkEditPermissions()) {
            busTable.setRowFactory(
                    tableView -> {
                        final TableRow<BusList> row = new TableRow<>();
                        final ContextMenu rowContextMenu = new ContextMenu();
                        rowContextMenu.setStyle("-fx-background-radius:10;" + "-fx-border-radius:10;" + "-fx-background-color:#ebebeb;");
                        rowContextMenu.setPrefWidth(100.0);
                        MenuItem editItem = new MenuItem("Ch???nh s???a");
                        editItem.setOnAction(event -> {
                            //edit row
                            int rowIndex = busTable.getSelectionModel().getFocusedIndex();
                            busTable.edit(rowIndex, jobCode_col);
                        });
                        MenuItem deleteItem = new MenuItem("X??a");
                        deleteItem.setOnAction(event -> {

                            // needs multi row selection is set to true
                            ObservableList<BusList> readOnlyItems = busTable.getSelectionModel().getSelectedItems();

                            // removes all selected elements for the table (UI)
                            readOnlyItems.forEach(busListData::remove);
                            //delete in backend

                            updateTotalQuantity(busListData);
                            updateTheNumberOfBusesWithNoAds(busListData);
                            updateTheNumberOfBusesWithAds(busListData);
                            updateTheNumberOfBusesInTheContract(busListData);
                            updateTheNumberOfBusesOutsideContract(busListData);
                            // clear the selection
                            busTable.getSelectionModel().clearSelection();

                        });

                        MenuItem insertNewRowItem = new MenuItem("Th??m d??ng m???i");

                        insertNewRowItem.setOnAction(event -> {

                            refreshTable(busListFilteredList);

                            BusList busList = new BusList();

                            insertNewBusInBackground(busList);

                            int lastIndex = busTable.getItems().size();

                            busListData.add(lastIndex, busList);
                            busTable.scrollTo(busListData.size());
                            busTable.getSelectionModel().select(lastIndex);
                            busTable.edit(lastIndex, enterprise_col);

                            updateTotalQuantity(busListData);
                            updateTheNumberOfBusesWithNoAds(busListData);
                            updateTheNumberOfBusesWithAds(busListData);
                            updateTheNumberOfBusesInTheContract(busListData);
                            updateTheNumberOfBusesOutsideContract(busListData);

                        });

                        rowContextMenu.getItems().addAll(editItem, deleteItem, insertNewRowItem);

                        // only display context menu for non-empty rows:
                        row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(rowContextMenu));

                        return row;
                    });

            //th??m menu khi click chu???t ph???i v??o b???t k?? ????u tr??n table
            ContextMenu tableContextMenu = new ContextMenu();
            tableContextMenu.setStyle("-fx-background-radius:10;" + "-fx-border-radius:10;" + "-fx-background-color:#ebebeb;");
            MenuItem insertNewRowItem = new MenuItem("Th??m d??ng m???i");
            insertNewRowItem.setOnAction(event -> {

                refreshTable(busListFilteredList);

                BusList busList = new BusList();
//

                insertNewBusInBackground(busList);
                int lastIndex = busTable.getItems().size();
                System.out.println("last index: " + lastIndex);
                busListData.add(lastIndex, busList);
                busTable.getSelectionModel().select(lastIndex);
                busTable.edit(lastIndex, enterprise_col);

                updateTotalQuantity(busListData);
                updateTheNumberOfBusesWithNoAds(busListData);
                updateTheNumberOfBusesWithAds(busListData);
                updateTheNumberOfBusesInTheContract(busListData);
                updateTheNumberOfBusesOutsideContract(busListData);

            });
            tableContextMenu.getItems().add(insertNewRowItem);

            MenuItem refreshAllRowInTableITem = new MenuItem("L??m m???i d??? li???u");
            refreshAllRowInTableITem.setOnAction(event -> refreshTable(busListFilteredList));

            tableContextMenu.getItems().add(refreshAllRowInTableITem);

            busTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
                if (t.getButton() == MouseButton.SECONDARY) {
                    busTable.contextMenuProperty().bind(Bindings.when(busTable.disabledProperty()).then((ContextMenu) null).otherwise(tableContextMenu));
                }
            });
        }

        //populate data table
        busTable.setItems(getBusList());

        updateTotalQuantity(busListData);
        updateTheNumberOfBusesWithAds(busListData);
        updateTheNumberOfBusesWithNoAds(busListData);
        updateTheNumberOfBusesInTheContract(busListData);
        updateTheNumberOfBusesOutsideContract(busListData);
    }

    //kh???i t???o data cho on Job bus list
    private void initDataForOnJobBusList() {
        if (checkEditPermissions()) {
            System.out.println("you can edit on job table");
            onJobBusTable.setEditable(true);
        } else {
            System.out.println("can't edit on Job table");
            onJobBusTable.setEditable(false);
        }

        onJobBusTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Callback<TableColumn<OnJobBus, Date>, TableCell<OnJobBus, Date>> dateCellFactory
                = (TableColumn<OnJobBus, Date> param) -> new OnJobDateEditingCell();

        Callback<TableColumn<OnJobBus, Integer>, TableCell<OnJobBus, Integer>> integerCellFactory
                = (TableColumn<OnJobBus, Integer> param) -> new OnJobIntegerEditingCell();

        //On Job no
        onJobNo_col.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(onJobBusTable.getItems().indexOf(p.getValue()) + ""));
        onJobNo_col.setSortable(false);

        //On Job M?? h??ng
        onJobJobCode_col.setCellValueFactory(cellData -> cellData.getValue().onJobJobCodeProperty());
        onJobJobCode_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobJobCode_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobJobCode();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobJobCode(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobJobCode", newValue);
                        updateOnJobBus(filters, updateValue);

                        ObservableList<BusList> target =
                                busListData.stream()
                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
                                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));

                        if (target.size() > 0) {
                            target.get(0).setJobCode(newValue);
                            Bson busFilters = eq("uuid", target.get(0).getUuid());
                            Bson busUpdateValue = set("jobCode", newValue);
                            updateInBackground(busFilters, busUpdateValue);
                        }
                    }

                });

        //OJ Nh??n h??ng
        onJobBrand_col.setCellValueFactory(cellData -> cellData.getValue().onJobBrandProperty());
        onJobBrand_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobBrand_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobBrand();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobBrand(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobBrand", newValue);
                        updateOnJobBus(filters, updateValue);

                        ObservableList<BusList> target =
                                busListData.stream()
                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
                                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));

                        if (target.size() > 0) {
                            target.get(0).setBrand(newValue);
                            Bson busFilters = eq("uuid", target.get(0).getUuid());
                            Bson busUpdateValue = set("brand", newValue);
                            updateInBackground(busFilters, busUpdateValue);
                        }
                    }

                });

        //N???i dung thi c??ng
        onJobConstructionContent_col.setCellValueFactory(cellData -> cellData.getValue().onJobConstructionContentProperty());
        onJobConstructionContent_col.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("DanMoi","ThayLayout","SuaChua","ThaoDo","PhatSinh")));
        onJobConstructionContent_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();

                    String oldValue = ojBus.getOnJobConstructionContent();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobConstructionContent(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobConstructionContent", newValue);
                        updateOnJobBus(filters, updateValue);

//                        ObservableList<Bus> target =
//                                busData.stream()
//                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
//                                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
//
//                        if (target.size() > 0) {
//                            target.get(0).setBrand(newValue);
//                        }
                    }

                });

        //OJ Agency
        onJobAgency_col.setCellValueFactory(cellData -> cellData.getValue().onJobAgencyProperty());
        onJobAgency_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobAgency_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobAgency();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobAgency(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobAgency", newValue);
                        updateOnJobBus(filters, updateValue);

                        ObservableList<BusList> target =
                                busListData.stream()
                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
                                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));

                        if (target.size() > 0) {
                            target.get(0).setAgency(newValue);
                        }
                    }

                });

        //Ng??y thi c??ng
        onJobDateOfConstruction_col.setCellValueFactory(cellData -> cellData.getValue().onJobDateOfConstructionProperty());
        onJobDateOfConstruction_col.setCellFactory(dateCellFactory);
        onJobDateOfConstruction_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, Date> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    Date oldValue = ojBus.getOnJobDateOfConstruction();
                    Date newValue = t.getNewValue();


                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobDateOfConstruction(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobDateOfConstruction", newValue);
                        updateOnJobBus(filters, updateValue);
                        sendOnJobBusListDataToChartScreen(onJobBusData);
                        ObservableList<BusList> target =
                                busListData.stream()
                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
                                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
                        if (target.size() > 0) {
                            target.get(0).setDateOfConstruction(newValue);
                            Bson busFilters = eq("uuid", target.get(0).getUuid());
                            Bson busUpdateValue = set("dateOfConstruction", newValue);
                            updateInBackground(busFilters, busUpdateValue);
                        }
                    }

                });

        //OJ Slogan
        onJobSlogan_col.setCellValueFactory(cellData -> cellData.getValue().onJobSloganProperty());
        onJobSlogan_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobSlogan_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobSlogan();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobSlogan(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobSlogan", newValue);
                        updateOnJobBus(filters, updateValue);

                        ObservableList<BusList> target =
                                busListData.stream()
                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
                                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));

                        if (target.size() > 0) {
                            target.get(0).setSlogan(newValue);
                            Bson busFilters = eq("uuid", target.get(0).getUuid());
                            Bson busUpdateValue = set("slogan", newValue);
                            updateInBackground(busFilters, busUpdateValue);

                        }
                    }

                });

        //OJ Bi???n ki???m so??t
        onJobNumberPlate_col.setCellValueFactory(cellData -> cellData.getValue().onJobNumberPlateProperty());
        onJobNumberPlate_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobNumberPlate_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobNumberPlate();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        //update new job code
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobNumberPlate(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateSingleValue = set("onJobNumberPlate", newValue);

                        //c???p nh???t l???i ng??y thi c??ng cho b???ng bus list
                        ObservableList<BusList> target =
                                busListData.stream()
                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
                                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));


                        if (target.size() > 0) {
                            // c???p nh???t l???i ng??y thi c??ng cho b???ng bus list
                            //c???p nh???t l???i ng??y thi c??ng cho b???ng bus list theo bi???n s??? xe c???a bus on job
                            Date onJobCurrentDate = t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()).getOnJobDateOfConstruction();
                            target.get(0).setDateOfConstruction(onJobCurrentDate);

                            //t??? ?????ng c???p nh???t tuy???n xe n???u nh???p ????ng bi???n s??? xe
                            Integer busRoute = target.get(0).getRouteNumber();
                            ojBus.setOnJobRouteNumber(busRoute);

                            //t??? ?????ng c???p nh???t lo???i xe
                            String busVehicleCategory = target.get(0).getVehicleCategory();
                            ojBus.setOnJobVehicleCategory(busVehicleCategory);
                            //t??? ?????ng c???p nh???t doanh nghi???p
                            String busEnterprise = target.get(0).getEnterprise();
                            ojBus.setOnJobEnterprise(busEnterprise);

                            //c???p nh???t l???i s??? tuy???n, doanh nghi???p, lo???i xe
                            Bson updateMultipleValue = combine(set("onJobNumberPlate", newValue), set("onJobRouteNumber", busRoute),
                                    set("onJobEnterprise", busEnterprise),
                                    set("onJobVehicleCategory", busVehicleCategory));

                            System.out.println("update multiple");
                            updateOnJobBus(filters, updateMultipleValue);
                        } else {
                            System.out.println("update single");
                            updateOnJobBus(filters, updateSingleValue);
                        }
                    }
                });

        //OJ Tuy???n xe
        onJobRouteNumber_col.setCellValueFactory(cellData -> cellData.getValue().onJobRouteNumberProperty().asObject());
        onJobRouteNumber_col.setCellFactory(integerCellFactory);
        onJobRouteNumber_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, Integer> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    Integer oldValue = ojBus.getOnJobRouteNumber();
                    Integer newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobRouteNumber(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobRouteNumber", newValue);

                        updateOnJobBus(filters, updateValue);
                    }
                });

        //OJ Lo???i xe
        onJobVehicleCategory_col.setCellValueFactory(cellData -> cellData.getValue().onJobVehicleCategoryProperty());
        //vehicleCategory_col.setCellFactory(textFieldCellFactory);
        onJobVehicleCategory_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobVehicleCategory_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobVehicleCategory();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobVehicleCategory(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobVehicleCategory", newValue);
                        updateOnJobBus(filters, updateValue);

                    }
                });

        //OJ Doanh nghi???p
        onJobEnterprise_col.setCellValueFactory(cellData -> cellData.getValue().onJobEnterpriseProperty());
        onJobEnterprise_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobEnterprise_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobEnterprise();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobEnterprise(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobEnterprise", newValue);
                        updateOnJobBus(filters, updateValue);
                    }
                });

        //OJ cs
        onJobCs_col.setCellValueFactory(cellData -> cellData.getValue().onJobCsProperty());
        // cs_col.setCellFactory(textFieldCellFactory);
        onJobCs_col.setCellFactory(TextFieldTableCell.forTableColumn());
        onJobCs_col.setOnEditCommit(
                (TableColumn.CellEditEvent<OnJobBus, String> t) -> {

                    OnJobBus ojBus = onJobBusTable.getSelectionModel().getSelectedItem();
                    String oldValue = ojBus.getOnJobCs();
                    String newValue = t.getNewValue();

                    if (!oldValue.equals(newValue)) {
                        t.getTableView().getItems()
                                .get(t.getTablePosition().getRow())
                                .setOnJobCs(t.getNewValue());

                        Bson filters = eq("onJobUuid", ojBus.getOnJobUuid());
                        Bson updateValue = set("onJobCs", newValue);
                        updateOnJobBus(filters, updateValue);

                        ObservableList<BusList> target =
                                busListData.stream()
                                        .filter(b -> b.getNumberPlate().equals(ojBus.getOnJobNumberPlate()))
                                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));

                        if (target.size() > 0) {
                            target.get(0).setCs(newValue);
                            Bson busFilters = eq("uuid", target.get(0).getUuid());
                            Bson busUpdateValue = set("cs", newValue);
                            updateInBackground(busFilters, busUpdateValue);
                        }

                    }

                });


        //th??m menu khi click chu???t v??o 1 d??ng tr??n on job bus table
        if (checkEditPermissions()) {
            onJobBusTable.setRowFactory(
                    tableView -> {
                        final TableRow<OnJobBus> row = new TableRow<>();
                        final ContextMenu rowContextMenu = new ContextMenu();
                        rowContextMenu.setStyle("-fx-background-radius:10;" + "-fx-border-radius:10;" + "-fx-background-color:#ebebeb;");
                        rowContextMenu.setPrefWidth(100.0);
                        MenuItem editItem = new MenuItem("Ch???nh s???a");
                        editItem.setOnAction(event -> {
                            //edit row
                            int rowIndex = onJobBusTable.getSelectionModel().getFocusedIndex();
                            onJobBusTable.edit(rowIndex, onJobJobCode_col);
                        });
                        MenuItem deleteItem = new MenuItem("X??a");
                        deleteItem.setOnAction(event -> {

                            // needs multi row selection is set to true
                            ObservableList<OnJobBus> readOnlyItems = onJobBusTable.getSelectionModel().getSelectedItems();

                            // removes all selected elements for the table (UI)
                            readOnlyItems.forEach(onJobBusData::remove);
                            onJobBusTable.getSelectionModel().clearSelection();

                        });

                        MenuItem insertNewRowItem = new MenuItem("Th??m d??ng m???i");

                        insertNewRowItem.setOnAction(event -> {

                            //refreshTable(onJobBusFilteredList);

                            OnJobBus OJBus = new OnJobBus();
                            insertNewOnJobBus(OJBus);

                            int lastIndex = onJobBusTable.getItems().size();

                            onJobBusData.add(lastIndex, OJBus);
                            onJobBusTable.scrollTo(onJobBusData.size());
                            onJobBusTable.getSelectionModel().select(lastIndex);
                            onJobBusTable.edit(lastIndex, onJobNumberPlate_col);

                            sendOnJobBusListDataToChartScreen(onJobBusData);

                        });

                        rowContextMenu.getItems().addAll(editItem, deleteItem, insertNewRowItem);
                        row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(rowContextMenu));

                        return row;
                    });

            //th??m menu khi click chu???t ph???i v??o b???t k?? ????u tr??n table
            ContextMenu tableContextMenu = new ContextMenu();
            tableContextMenu.setStyle("-fx-background-radius:10;" + "-fx-border-radius:10;" + "-fx-background-color:#ebebeb;");
            MenuItem insertNewRowItem = new MenuItem("Th??m d??ng m???i");
            insertNewRowItem.setOnAction(event -> {

                //refreshTable(busFilteredList);

                OnJobBus OJBus = new OnJobBus();
                insertNewOnJobBus(OJBus);

                int lastIndex = onJobBusTable.getItems().size();

                onJobBusData.add(lastIndex, OJBus);
                onJobBusTable.scrollTo(onJobBusData.size());
                onJobBusTable.getSelectionModel().select(lastIndex);
                onJobBusTable.edit(lastIndex, onJobNumberPlate_col);

                sendOnJobBusListDataToChartScreen(onJobBusData);


            });
            tableContextMenu.getItems().add(insertNewRowItem);

            MenuItem refreshAllRowInTableITem = new MenuItem("L??m m???i");
            //refreshAllRowInTableITem.setOnAction(event -> refreshTable(busFilteredList));

            tableContextMenu.getItems().add(refreshAllRowInTableITem);

            onJobBusTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
                if (t.getButton() == MouseButton.SECONDARY) {
                    onJobBusTable.contextMenuProperty().bind(Bindings.when(onJobBusTable.disabledProperty()).then((ContextMenu) null).otherwise(tableContextMenu));
                }
            });
        }

        //populate data table
        onJobBusTable.setItems(getOnJobBusList());

        //g???i data qua m??n h??nh chart ????? th???ng k??
        if (onJobBusData.size() > 0) {
            sendOnJobBusListDataToChartScreen(onJobBusData);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Home controller start run ...");
        //getAllRouteForComboBox();
        initDataForBusList();
        initDataForOnJobBusList();


        busListFilteredList = new FilteredList<>(busListData, b -> true);

        // search text field with num plate and num route
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> busListFilteredList.setPredicate(BusList -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }

            String searchKeyWord = newValue.toLowerCase();

            if (BusList.getNumberPlate().toLowerCase().contains(searchKeyWord)) {
                return true;
            } else return BusList.getRouteNumber().toString().toLowerCase().contains(searchKeyWord);
//            return true;

        }));

        //select route comboBox
        routersComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(newValue);
//            busFilteredList.setPredicate(Bus -> {
//                if (newValue.isEmpty() || newValue.isBlank()) {
//                    return true;
//                }
//                String router = newValue.toLowerCase();
//
//                return Bus.getRouteNumber().toLowerCase().contains(router);
//            });

            return;

        });

        //show all button click
        showAllButton.setOnAction(event -> {
            searchTextField.setText("");
//            routersComboBox.getItems().add(0,"All");
            busListFilteredList.setPredicate(BusList -> true);
        });

        //
        SortedList<BusList> sortedList = new SortedList<>(busListFilteredList);
        sortedList.comparatorProperty().bind(busTable.comparatorProperty());
        busTable.setItems(sortedList);

    }

    private void updateTheNumberOfBusesWithAds(ObservableList<BusList> busListData) {

        int number = (int) busListData.stream().filter(busList -> !busList.getBrand().isBlank()).count();

        adsLabel.setText(String.valueOf(number));
    }

    private void updateTheNumberOfBusesWithNoAds(ObservableList<BusList> busListData) {

        int number = (int) busListData.stream().filter(busList -> busList.getBrand().isBlank()).count();

        noAdsLabel.setText(String.valueOf(number));
    }

    public void updateTheNumberOfBusesInTheContract(ObservableList<BusList> busListData) {
        int number = (int) busListData.stream().filter(BusList::isContract).count();

        insideContractLabel.setText(String.valueOf(number));
    }

    public void updateTheNumberOfBusesOutsideContract(ObservableList<BusList> busListData) {
        int number = (int) busListData.stream().filter(b -> !b.isContract()).count();
        outsideContractLabel.setText(String.valueOf(number));
    }

    private void updateTotalQuantity(ObservableList<BusList> b) {
        busTotalLabel.setText(String.valueOf(b.size()));
    }

    //fresh all row of table
    private void refreshTable(FilteredList<BusList> filteredList) {
        filteredList.setPredicate(BusList -> true);
    }

    //T??nh s??? ng??y ch??nh l???ch
    public static int betweenDates(Date firstDate, Date secondDate) {
        return Math.toIntExact(ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant()));
    }

    //th??m m???i d??? li???u xe bu??t
    public synchronized void insertNewBusInBackground(BusList busList) {

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {

                busDaoImp.add(busList);
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
                System.out.println("insert new bus fail...........");
                Alert alert = new Alert(Alert.AlertType.ERROR, getException().getMessage());
                alert.showAndWait();
            }
        };

        new Thread(task).start();

    }

    //th??m m???i d??? li???u on Job Bus
    public synchronized void insertNewOnJobBus(OnJobBus OJBus) {

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {

                onJobBusDaoImp.add(OJBus);
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
                System.out.println("insert new bus fail...........");
                Alert alert = new Alert(Alert.AlertType.ERROR, getException().getMessage());
                alert.showAndWait();
            }
        };

        new Thread(task).start();
    }

    //c???p nh???t d??? li???u on Job Bus
    public synchronized void updateOnJobBus(Bson filter, Bson updateValue) {

        Task<Long> task = new Task<>() {

            @Override
            protected Long call() {
                return onJobBusDaoImp.update(filter, updateValue);
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


    //generate uuid
    private String generateUUID() {
        String uniqueID = UUID.randomUUID().toString();
        System.out.println("uniqueID: " + uniqueID);
        return uniqueID;
    }

    //c???p nh???t (ch???y n???n)
    public synchronized void updateInBackground(Bson filter, Bson updateValue) {

        Task<Long> task = new Task<>() {

            @Override
            protected Long call() {
                return busDaoImp.update(filter, updateValue);
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

    //d??ng cho tr?????ng ng??y thi c??ng c???a On Job Bus List
    static class OnJobDateEditingCell extends TableCell<OnJobBus, Date> {

        private DatePicker datePicker;

        private OnJobDateEditingCell() {
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
                    setText(getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    //setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                    setGraphic(null);

                }
            }
        }

        private void createDatePicker() {
            datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                if (datePicker.getValue() != null) {
                    //commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.from())).toInstant());
                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } else {
                    //return last date selected ==> if date is empty
                    datePicker.setValue(getDate());
                }
            });
        }

        private LocalDate getDate() {
            //final LocalDate defaultLocalDate =LocalDate.of(2,2,2);
            return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    // format date cell
    static class DateEditingCell extends TableCell<BusList, Date> {

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
                    setText(getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    //setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                    setGraphic(null);

                }
            }
        }

        private void createDatePicker() {
            datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                if (datePicker.getValue() != null) {
                    //commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.from())).toInstant());
                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } else {
                    //return last date selected ==> if date is empty
                    datePicker.setValue(getDate());
                }
            });
        }

        private LocalDate getDate() {
            //final LocalDate defaultLocalDate =LocalDate.of(2,2,2);
            return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    //
    static class EditingCell extends TableCell<BusList, String> {

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
            System.out.println("item 1" + item);
            super.updateItem(item, empty);

            if (empty) {
                setText(item);
                setGraphic(null);
            } else {
                System.out.println("else 2");
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
            System.out.println("get string");
            return getItem() == null ? "" : getItem();
        }
    }

    //
    static class OnJobIntegerEditingCell extends TableCell<OnJobBus, Integer> {

        private TextField textField;
        //private final Pattern intPattern = Pattern.compile("-?\\d+");

        private OnJobIntegerEditingCell() {
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
            setText(getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {

            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getInteger().toString());
//                        setGraphic(null);
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getInteger().toString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getInteger().toString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnAction((e) -> commitEdit(Integer.valueOf(textField.getText())));
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue) {
                    //ki???m tra xem ng?????i d??ng c?? ph???i nh???p v??o s??? hay kh??ng.
//                    if (intPattern.matcher(text).matches()) {
//                        commitEdit(Integer.parseInt(text));
//                        System.out.println("Committing " + textField.getText());
                    commitEdit(Integer.valueOf(textField.getText()));
//                    } else {
//                        System.out.println("nguoi dung ko nhap so !!!!!!!");
//                        return;
//                        //commitEdit(oldNumber);
//                    }

                }
            });
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String text = change.getText();

                if (text.matches("[0-9]*")) {
                    return change;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Th??ng b??o");
                    alert.setContentText("B???n ch??? ???????c ph??p nh???p s???");
                    alert.show();
                }

                return null;
            };
            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
            textField.setTextFormatter(textFormatter);
        }

        private Integer getInteger() {
            return getItem() == null ? 0 : getItem();
        }
        //
    }

    //
    static class IntegerEditingCell extends TableCell<BusList, Integer> {

        private TextField textField;
        //private final Pattern intPattern = Pattern.compile("-?\\d+");

        private IntegerEditingCell() {
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
            setText(getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {

            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getInteger().toString());
//                        setGraphic(null);
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getInteger().toString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getInteger().toString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnAction((e) -> commitEdit(Integer.valueOf(textField.getText())));
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue) {
                    //ki???m tra xem ng?????i d??ng c?? ph???i nh???p v??o s??? hay kh??ng.
//                    if (intPattern.matcher(text).matches()) {
//                        commitEdit(Integer.parseInt(text));
//                        System.out.println("Committing " + textField.getText());
                    commitEdit(Integer.valueOf(textField.getText()));
//                    } else {
//                        System.out.println("nguoi dung ko nhap so !!!!!!!");
//                        return;
//                        //commitEdit(oldNumber);
//                    }

                }
            });
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String text = change.getText();

                if (text.matches("[0-9]*")) {
                    return change;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Th??ng b??o");
                    alert.setContentText("B???n ch??? ???????c ph??p nh???p s???");
                    alert.show();
                }

                return null;
            };
            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
            textField.setTextFormatter(textFormatter);
        }

        private Integer getInteger() {
            return getItem() == null ? 0 : getItem();
        }
        //
    }
}
