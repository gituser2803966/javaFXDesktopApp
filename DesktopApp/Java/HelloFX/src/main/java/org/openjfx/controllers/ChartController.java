package org.openjfx.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.openjfx.models.EMonth;
import org.openjfx.models.OnJobBus;
import org.openjfx.models.Statistic;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ChartController implements Initializable, ControlledScreen {

    ScreensController myController;


    ObservableList<Statistic> statisticData = FXCollections.observableArrayList(
            new Statistic(new SimpleIntegerProperty(2021),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(2020),
                    new SimpleIntegerProperty(2020),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10)),
            new Statistic(new SimpleIntegerProperty(2022),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(2020),
                    new SimpleIntegerProperty(2020),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10),
                    new SimpleIntegerProperty(10))

    );



    @FXML
    private TableView<Statistic> chartTable;

    @FXML
    private TableColumn<Statistic, Number> year_col;
    @FXML
    private TableColumn<Statistic, Number> jan_col;
    @FXML
    private TableColumn<Statistic, Number> feb_col;
    @FXML
    private TableColumn<Statistic, Number> mar_col;
    @FXML
    private TableColumn<Statistic, Number> apr_col;
    @FXML
    private TableColumn<Statistic, Number> may_col;
    @FXML
    private TableColumn<Statistic, Number> jun_col;
    @FXML
    private TableColumn<Statistic, Number> jul_col;
    @FXML
    private TableColumn<Statistic, Number> aug_col;
    @FXML
    private TableColumn<Statistic, Number> sep_col;
    @FXML
    private TableColumn<Statistic, Number> oct_col;
    @FXML
    private TableColumn<Statistic, Number> nov_col;
    @FXML
    private TableColumn<Statistic, Number> dec_col;

    private static ObservableList<Integer> comboBoxYearData = FXCollections.observableArrayList();

    @FXML
    private Button buttonRefresh;

    @FXML
    private BorderPane chartBorderPane;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private static ObservableList<OnJobBus> onJobBusList = FXCollections.observableArrayList();

    @FXML
    private BarChart<String, Number> yearBarChart;

    @FXML
    private LineChart<String, Number> monthLineChart;

    @FXML
    private ComboBox<Integer> yearsComboBox;

    @FXML
    private ComboBox<EMonth> monthsComboBox;


    private ObservableList<Integer> getComboBoxYearData(ObservableList<OnJobBus> oJBus){

        yearsComboBox.getItems().clear();
        comboBoxYearData.clear();

        ObservableList<OnJobBus> a = removeOnJobBusDuplicateByYear(oJBus);

        a.forEach(b->
                comboBoxYearData.add(Integer.valueOf(getYearFromDate(b.getOnJobDateOfConstruction())))
                );

        return comboBoxYearData;
    }

    private ObservableList<OnJobBus> removeOnJobBusDuplicateByYear(ObservableList<OnJobBus> list) {

        yearsComboBox.getItems().clear();
        comboBoxYearData.clear();

        Set<Integer> nameSet = new HashSet<>();

        ObservableList<OnJobBus> employeesDistinctByName = list.stream()
                .filter(e -> nameSet.add(getYearFromDate(e.getOnJobDateOfConstruction())))
                .collect(Collectors.collectingAndThen(toList(),FXCollections::observableArrayList));

        return employeesDistinctByName;
    }

    public void initData(ObservableList<OnJobBus> list) {
        onJobBusList = list;
        return;
    }

    //    private initD
    private void initDataTableForYear() {
        year_col.setCellValueFactory(cellData->cellData.getValue().yearProperty());
        feb_col.setCellValueFactory(cellData->cellData.getValue().numberOfFebProperty());
        jan_col.setCellValueFactory(cellData->cellData.getValue().numberOfJanProperty());
        mar_col.setCellValueFactory(cellData->cellData.getValue().numberOfMarProperty());
        apr_col.setCellValueFactory(cellData->cellData.getValue().numberOfAprProperty());
        may_col.setCellValueFactory(cellData->cellData.getValue().numberOfMayProperty());
        jun_col.setCellValueFactory(cellData->cellData.getValue().numberOfJunProperty());
        jul_col.setCellValueFactory(cellData->cellData.getValue().numberOfJulProperty());
        aug_col.setCellValueFactory(cellData->cellData.getValue().numberOfAugProperty());
        sep_col.setCellValueFactory(cellData->cellData.getValue().numberOfSepProperty());
        oct_col.setCellValueFactory(cellData->cellData.getValue().numberOfOctProperty());
        nov_col.setCellValueFactory(cellData->cellData.getValue().numberOfNovProperty());
        dec_col.setCellValueFactory(cellData->cellData.getValue().numberOfDecProperty());

        chartTable.setItems(statisticData);
    }

    private int getMonthFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return month;
    }

    private int getYearFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        return year;
    }

    public void setMonthLineChart() {

        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        monthLineChart.setTitle("THÁNG 7");
        series.getData().add(new XYChart.Data<String, Number>("1", 130));
        series.getData().add(new XYChart.Data<String, Number>("2", 120));
        series.getData().add(new XYChart.Data<String, Number>("3", 110));
        series.getData().add(new XYChart.Data<String, Number>("4", 115));
        series.getData().add(new XYChart.Data<String, Number>("5", 190));
        series.getData().add(new XYChart.Data<String, Number>("6", 170));
        series.getData().add(new XYChart.Data<String, Number>("7", 130));
        series.getData().add(new XYChart.Data<String, Number>("8", 122));
        series.getData().add(new XYChart.Data<String, Number>("9", 135));
        series.getData().add(new XYChart.Data<String, Number>("10", 165));
        series.getData().add(new XYChart.Data<String, Number>("11", 129));
        series.getData().add(new XYChart.Data<String, Number>("12", 144));
        series.getData().add(new XYChart.Data<String, Number>("13", 130));
        series.getData().add(new XYChart.Data<String, Number>("14", 120));
        series.getData().add(new XYChart.Data<String, Number>("15", 110));
        series.getData().add(new XYChart.Data<String, Number>("16", 115));
        series.getData().add(new XYChart.Data<String, Number>("17", 190));
        series.getData().add(new XYChart.Data<String, Number>("18", 145));
        series.getData().add(new XYChart.Data<String, Number>("19", 130));
        series.getData().add(new XYChart.Data<String, Number>("20", 139));
        series.getData().add(new XYChart.Data<String, Number>("21", 135));
        series.getData().add(new XYChart.Data<String, Number>("22", 124));
        series.getData().add(new XYChart.Data<String, Number>("23", 129));
        series.getData().add(new XYChart.Data<String, Number>("24", 144));
        series.getData().add(new XYChart.Data<String, Number>("25", 170));
        series.getData().add(new XYChart.Data<String, Number>("26", 175));
        series.getData().add(new XYChart.Data<String, Number>("27", 134));
        series.getData().add(new XYChart.Data<String, Number>("28", 135));
        series.getData().add(new XYChart.Data<String, Number>("29", 165));
        series.getData().add(new XYChart.Data<String, Number>("30", 112));
        series.getData().add(new XYChart.Data<String, Number>("31", 144));

        monthLineChart.getData().add(series);
    }

    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final DecimalFormat df = new DecimalFormat("$###,##0.00");
        final Text dataText = new Text(df.format(data.getYValue()) + "");
        node.parentProperty().addListener((ov, oldParent, parent) -> {
            if (null != parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener((ov, oldBounds, bounds) -> {
            dataText.setLayoutX(
                    Math.round(
                            bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                    )
            );
            dataText.setLayoutY(
                    Math.round(
                            bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                    )
            );
        });

//        button.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent paramT) {
//                bc.getData().clear();
//                XYChart.Series series1 = new XYChart.Series();
//
//                for (int i = 10; i < 20; i++) {
//
//                    final XYChart.Data<String, Number> data = new XYChart.Data("Value " + i, 1.0 + (Math.random()*(10-1+1)));
//                    data.nodeProperty().addListener(new ChangeListener<Node>() {
//                        @Override
//                        public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
//                            if (node != null) {
//                                setNodeStyle(data);
//                                displayLabelForData(data);
//                            }
//                        }
//                    });
//                    series1.getData().add(data);
//                }
//                bc.getData().add(series1);
//
//            }
//        });
    }

    private int countNumberByYear(){
        return 1;
    }


    public void setYearBarChart(ObservableList<OnJobBus> newOnJobBusList, int year) {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(String.valueOf(year));
        yearBarChart.setTitle("NĂM " + year);

        ObservableList<OnJobBus> newList =
                newOnJobBusList.stream()
                        .filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year)
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        XYChart.Data<String, Number> janData = new XYChart.Data<>("Jan", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 1).count());
//        XYChart.Data<String, Number> janData = new XYChart.Data<>("Jan",19);
        series.getData().add(janData);

        //total = newList.stream().filter(b -> getMonthFromDate(b.getDateOfConstruction()) == 2).count();
        XYChart.Data<String, Number> febData = new XYChart.Data<>("Feb", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 2).count());
        series.getData().add(febData);

        //total = newList.stream().filter(b -> getMonthFromDate(b.getDateOfConstruction()) == 3).count();
        XYChart.Data<String, Number> marData = new XYChart.Data<>("Mar", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 3).count());
        series.getData().add(marData);

        XYChart.Data<String, Number> aprData = new XYChart.Data<>("Apr", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 4).count());
        series.getData().add(aprData);

        XYChart.Data<String, Number> mayData = new XYChart.Data<>("May", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 5).count());
        series.getData().add(mayData);

        XYChart.Data<String, Number> junData = new XYChart.Data<>("Jun", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 6).count());
        series.getData().add(junData);

        XYChart.Data<String, Number> julData = new XYChart.Data<>("Jul", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 7).count());
        series.getData().add(julData);

        XYChart.Data<String, Number> augData = new XYChart.Data<>("Aug", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 8).count());
        series.getData().add(augData);

        XYChart.Data<String, Number> sepData = new XYChart.Data<>("Sep", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 9).count());
        series.getData().add(sepData);

        //total = newList.stream().filter(b -> getMonthFromDate(b.getDateOfConstruction()) == 10).count();
        XYChart.Data<String, Number> octData = new XYChart.Data<>("Oct", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 10).count());
        series.getData().add(octData);

        XYChart.Data<String, Number> novData = new XYChart.Data<>("Nov", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 11).count());
        series.getData().add(novData);

        XYChart.Data<String, Number> decData = new XYChart.Data<>("Dec", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 12).count());
        series.getData().add(decData);

        yearBarChart.getData().add(series);
    }

    @FXML
    public void backToNavigationScreen(Event event) {
        //BorderPane root = chartBorderPane.;
        BorderPane root = (BorderPane) chartBorderPane.getScene().getRoot();
        root.setCenter(myController.getScreen(DashBoardController.screenNavigationID));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("chart Controller");

        //khởi tạo dữ liệu theo năm hiện tại
        setYearBarChart(onJobBusList, getYearFromDate(new Date()));
        initDataTableForYear();

        yearsComboBox.getItems().addAll(getComboBoxYearData(onJobBusList));
        //khởi tạo data cho MONTH comboBox
//        monthsComboBox.setItems(FXCollections.observableArrayList(EMonth.values()));
//        monthsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
////          //
//        });
//        yearsComboBox.getItems().addAll(comboBoxYearData);
        yearsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                setYearBarChart(onJobBusList, newValue);
            }

        });

        //
        buttonRefresh.setOnAction(event -> {

            yearsComboBox.getSelectionModel().clearSelection();
            yearsComboBox.getItems().addAll(getComboBoxYearData(onJobBusList));
            yearsComboBox.getSelectionModel().select(Integer.valueOf(getYearFromDate(new Date())));
            yearBarChart.getData().clear();
            setYearBarChart(onJobBusList, getYearFromDate(new Date()));

        });
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
