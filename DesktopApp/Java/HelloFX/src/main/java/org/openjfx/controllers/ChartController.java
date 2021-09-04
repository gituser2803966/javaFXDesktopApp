package org.openjfx.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.openjfx.models.IntegratedStatistic;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ChartController implements Initializable, ControlledScreen {

    ScreensController myController;

    ObservableList<IntegratedStatistic> statisticDataTable = FXCollections.observableArrayList();

    @FXML
    private TableView<IntegratedStatistic> integratedStatisticTableView;

    @FXML
    private TableColumn<IntegratedStatistic, String> no_col;
    @FXML
    private TableColumn<IntegratedStatistic, String> year_col;
    @FXML
    private TableColumn<IntegratedStatistic, String> month_col;
    @FXML
    private TableColumn<IntegratedStatistic, Number> reNew_col;
    @FXML
    private TableColumn<IntegratedStatistic, Number> changeLayout_col;
    @FXML
    private TableColumn<IntegratedStatistic, Number> incurred_col;
    @FXML
    private TableColumn<IntegratedStatistic, Number> dismantling_col;
    @FXML
    private TableColumn<IntegratedStatistic, Number> repair_col;
    @FXML
    private TableColumn<IntegratedStatistic, Number> total_col;

    @FXML
    private ComboBox<Integer> yearsComboBox;
    private static ObservableList<Integer> comboBoxYearData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Integer> routeComboBox;
    private static ObservableList<Integer> comboBoxRouteData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> optionComboBox;
    private static ObservableList<String> comboBoxOptionData = FXCollections.observableArrayList("Dán mới", "Sữa chữa");

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
    private BarChart<String, Number> optionBarChart;

    @FXML
    private LineChart<String, Number> monthLineChart;

    final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @FXML
    private ComboBox<EMonth> monthsComboBox;

    private ObservableList<Integer> getComboBoxYearData(ObservableList<OnJobBus> oJBus) {

        comboBoxYearData.clear();
        ObservableList<OnJobBus> years = removeOnJobBusDuplicateByYear(oJBus);

        years.forEach(b ->
                comboBoxYearData.add(Integer.valueOf(getYearFromDate(b.getOnJobDateOfConstruction())))
        );

        return comboBoxYearData;
    }


    private ObservableList<Integer> getComboBoxRouteNumberData(ObservableList<OnJobBus> oJBus) {

        comboBoxRouteData.clear();
        ObservableList<OnJobBus> a = removeOnJobBusDuplicateByRouteNumber(oJBus);

        a.forEach(b ->
                comboBoxRouteData.add(b.getOnJobRouteNumber())
        );

        return comboBoxRouteData;
    }

    private ObservableList<OnJobBus> removeOnJobBusDuplicateByYear(ObservableList<OnJobBus> list) {

        Set<Integer> nameSet = new HashSet<>();

        ObservableList<OnJobBus> onJobBusDistinctByYear = list.stream()
                .filter(e -> nameSet.add(getYearFromDate(e.getOnJobDateOfConstruction())))

                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        onJobBusDistinctByYear.sort((b, a) -> Integer.compare(getYearFromDate(a.getOnJobDateOfConstruction()), getYearFromDate(b.getOnJobDateOfConstruction())));

        return onJobBusDistinctByYear;
    }

    private ObservableList<OnJobBus> removeOnJobBusDuplicateByRouteNumber(ObservableList<OnJobBus> list) {

        Set<Integer> nameSet = new HashSet<>();

        ObservableList<OnJobBus> onJobBusDistinctByRouteNumber = list.stream()
                .filter(e -> nameSet.add(e.getOnJobRouteNumber()))

                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        onJobBusDistinctByRouteNumber.sort((b, a) -> Integer.compare(a.getOnJobRouteNumber(), b.getOnJobRouteNumber()));

        return onJobBusDistinctByRouteNumber;
    }

    private ObservableList<OnJobBus> removeOnJobBusDuplicateByRoute(ObservableList<OnJobBus> list) {

        Set<Integer> nameSet = new HashSet<>();

        ObservableList<OnJobBus> onJobBusDistinctByRoute = list.stream()
                .filter(e -> nameSet.add(getYearFromDate(e.getOnJobDateOfConstruction())))

                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        onJobBusDistinctByRoute.sort((b, a) -> Integer.compare(getYearFromDate(a.getOnJobDateOfConstruction()), getYearFromDate(b.getOnJobDateOfConstruction())));

        return onJobBusDistinctByRoute;
    }

    public void initData(ObservableList<OnJobBus> list) {

        onJobBusList = list;
        return;
    }

    private int countBy(int year, int month, String value) {

        int result = Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == month &&
                b.getOnJobConstructionContent().equals(value)).count());

        return result;
    }


    public ObservableList<IntegratedStatistic> getTableDataForIntegrateStatistic(ObservableList<OnJobBus> ojBus) {

        ObservableList<OnJobBus> oj = removeOnJobBusDuplicateByYear(ojBus);

        int year = getYearFromDate(new Date());

        int i = 0;
        for (i = 1; i <= months.length; i++) {

            IntegratedStatistic integratedStatistic = new IntegratedStatistic();
            integratedStatistic.setYear(String.valueOf(year));
            integratedStatistic.setMonth(String.valueOf(months[i - 1]));
            integratedStatistic.setNumberOfReNew(countBy(year, i, "DanMoi"));
            integratedStatistic.setNumberOfChangeLayout(countBy(year, i, "ThayLayout"));
            integratedStatistic.setNumberOfIncurred(countBy(year, i, "PhatSinh"));
            integratedStatistic.setNumberOfDismantling(countBy(year, i, "ThaoDo"));
            integratedStatistic.setNumberOfRepair(countBy(year, i, "SuaChua"));
            integratedStatistic.setTotal(integratedStatistic.getNumberOfChangeLayout() +
                    integratedStatistic.getNumberOfReNew() +
                    integratedStatistic.getNumberOfIncurred() +
                    integratedStatistic.getNumberOfDismantling() +
                    integratedStatistic.getNumberOfRepair()
            );

            statisticDataTable.add(integratedStatistic);
        }

        return statisticDataTable;
    }

    //    private initD
    private void initIntegrateStatisticDataTable() {

        no_col.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(integratedStatisticTableView.getItems().indexOf(p.getValue()) + ""));
        no_col.setSortable(false);
        no_col.setStyle("-fx-alignment: CENTER;");
        year_col.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        year_col.setStyle("-fx-alignment: CENTER;");
        month_col.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        month_col.setStyle("-fx-alignment: CENTER;");
        reNew_col.setCellValueFactory(cellData -> cellData.getValue().numberOfReNewProperty());
        reNew_col.setStyle("-fx-alignment: CENTER;");
        changeLayout_col.setCellValueFactory(cellData -> cellData.getValue().numberOfChangeLayoutProperty());
        changeLayout_col.setStyle("-fx-alignment: CENTER;");
        incurred_col.setCellValueFactory(cellData -> cellData.getValue().numberOfIncurredProperty());
        incurred_col.setStyle("-fx-alignment: CENTER;");
        dismantling_col.setCellValueFactory(cellData -> cellData.getValue().numberOfDismantlingProperty());
        dismantling_col.setStyle("-fx-alignment: CENTER;");
        repair_col.setCellValueFactory(cellData -> cellData.getValue().numberOfRepairProperty());
        repair_col.setStyle("-fx-alignment: CENTER;");
        total_col.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        total_col.setStyle("-fx-alignment: CENTER;");

        integratedStatisticTableView.setItems(getTableDataForIntegrateStatistic(onJobBusList));
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

    public void initDataForIntegrateStatisticBarChart(ObservableList<OnJobBus> newOnJobBusList, int year) {

//        yearBarChart.setTitle("THỐNG KÊ SỐ LƯỢNG XE DÁN MỚI QUA TỪNG THÁNG THEO NĂM");

//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName(String.valueOf(year));
//
//        ObservableList<OnJobBus> newList =
//                newOnJobBusList.stream()
//                        .filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year)
//                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
//
//        XYChart.Data<String, Number> janData = new XYChart.Data<>("Jan", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 1).count());
////        XYChart.Data<String, Number> janData = new XYChart.Data<>("Jan",19);
//        series.getData().add(janData);
//
//        //total = newList.stream().filter(b -> getMonthFromDate(b.getDateOfConstruction()) == 2).count();
//        XYChart.Data<String, Number> febData = new XYChart.Data<>("Feb", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 2).count());
//        series.getData().add(febData);
//
//        //total = newList.stream().filter(b -> getMonthFromDate(b.getDateOfConstruction()) == 3).count();
//        XYChart.Data<String, Number> marData = new XYChart.Data<>("Mar", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 3).count());
//        series.getData().add(marData);
//
//        XYChart.Data<String, Number> aprData = new XYChart.Data<>("Apr", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 4).count());
//        series.getData().add(aprData);
//
//        XYChart.Data<String, Number> mayData = new XYChart.Data<>("May", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 5).count());
//        series.getData().add(mayData);
//
//        XYChart.Data<String, Number> junData = new XYChart.Data<>("Jun", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 6).count());
//        series.getData().add(junData);
//
//        XYChart.Data<String, Number> julData = new XYChart.Data<>("Jul", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 7).count());
//        series.getData().add(julData);
//
//        XYChart.Data<String, Number> augData = new XYChart.Data<>("Aug", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 8).count());
//        series.getData().add(augData);
//
//        XYChart.Data<String, Number> sepData = new XYChart.Data<>("Sep", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 9).count());
//        series.getData().add(sepData);
//
//        //total = newList.stream().filter(b -> getMonthFromDate(b.getDateOfConstruction()) == 10).count();
//        XYChart.Data<String, Number> octData = new XYChart.Data<>("Oct", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 10).count());
//        series.getData().add(octData);
//
//        XYChart.Data<String, Number> novData = new XYChart.Data<>("Nov", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 11).count());
//        series.getData().add(novData);
//
//        XYChart.Data<String, Number> decData = new XYChart.Data<>("Dec", newList.stream().filter(b -> getMonthFromDate(b.getOnJobDateOfConstruction()) == 12).count());
//        series.getData().add(decData);

//        optionBarChart.getData().add(series);

        optionBarChart.getData().addAll(
                makeSeries("DanMoi", year),
                makeSeries("ThayLayout", year),
                makeSeries("PhatSinh", year),
                makeSeries("ThaoDo", year),
                makeSeries("SuaChua", year)

        );
    }


    private XYChart.Series<String, Number> makeSeries(String name, int year) {

//        ObservableList<OnJobBus> newList =
//                newOnJobBusList.stream()
//                        .filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year)
//                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        return new XYChart.Series(name,
                FXCollections.observableArrayList(
                        new XYChart.Data("Jan",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 1 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Feb",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 2 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Mar",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 3 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Apr",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 4 &&
                                        b.getOnJobConstructionContent().equals(name)).count())),
                        new XYChart.Data("May",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 5 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Jun",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 6 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Jul",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 7 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Aug",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 8 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Sep",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 9 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Oct",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 10 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Nov",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 11 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        ),
                        new XYChart.Data("Dec",
                                Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 12 &&
                                        b.getOnJobConstructionContent().equals(name)).count())
                        )
                )

        );
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

        initDataForIntegrateStatisticBarChart(onJobBusList, getYearFromDate(new Date()));
        initIntegrateStatisticDataTable();

        yearsComboBox.getItems().addAll(getComboBoxYearData(onJobBusList));

        optionComboBox.getItems().addAll(comboBoxOptionData);
        optionComboBox.getSelectionModel().selectFirst();

        routeComboBox.getItems().addAll(getComboBoxRouteNumberData(onJobBusList));

        yearsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                initDataForIntegrateStatisticBarChart(onJobBusList, newValue);
                changeNewPasteDataByYear(onJobBusList, newValue);
            }
        });


        optionComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("index option: " + newValue);
            }
        });

        //
        buttonRefresh.setOnAction(event -> {
            refreshComboBoxYear();
            refreshComboBoxRouteNumber();
            optionBarChart.getData().clear();
            initDataForIntegrateStatisticBarChart(onJobBusList, getYearFromDate(new Date()));
            refreshPasteDataByYear();
            integratedStatisticTableView.getItems().clear();
            initIntegrateStatisticDataTable();
        });
    }

    private void refreshPasteDataByYear() {
        integratedStatisticTableView.getItems().clear();
        changeNewPasteDataByYear(onJobBusList, getYearFromDate(new Date()));
    }

    //thay đổi dữ liệu xe bus (xe dán mới) trong bảng theo năm
    private void changeNewPasteDataByYear(ObservableList<OnJobBus> onJobBusList, int year) {

//        IntegratedStatistic statistic = new IntegratedStatistic(
//                new SimpleIntegerProperty(year),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 1).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 2).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 3).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 4).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 5).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 6).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 7).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 8).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 9).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 10).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 11).count())),
//                new SimpleIntegerProperty(Integer.valueOf((int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == 12).count()))
//        );

//        chartTable.getItems().add(statistic);
    }

    private void refreshComboBoxYear() {
        yearsComboBox.getItems().clear();
        yearsComboBox.getItems().addAll(getComboBoxYearData(onJobBusList));
        yearsComboBox.getSelectionModel().select(Integer.valueOf(getYearFromDate(new Date())));
    }

    private void refreshComboBoxRouteNumber() {
        routeComboBox.getItems().clear();
        routeComboBox.getItems().addAll(getComboBoxRouteNumberData(onJobBusList));
//        routeComboBox.getSelectionModel().select(Integer.valueOf(getYearFromDate(new Date())));
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
