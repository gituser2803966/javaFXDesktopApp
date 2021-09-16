package org.openjfx.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.openjfx.models.OnJobBus;
import org.openjfx.models.IntegratedStatistic;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ChartController implements Initializable, ControlledScreen {

    ScreensController myController;

    ObservableList<IntegratedStatistic> statisticDataTable = FXCollections.observableArrayList();

    @FXML
    private VBox vBoxYear;

    @FXML
    private VBox vBoxConstructionContent;

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
    private ComboBox comboBoxQuarter;
//    private static ObservableList<String> comboBoxQuarterData = FXCollections.observableArrayList("Quý 1", "Quý 2", "Quý 3", "Quý 4", "Tất cả các quý", "Xem theo tháng");

    @FXML
    private ComboBox<String> comboBoxRoute;
    private ObservableList<String> comboBoxRouteData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> comboBoxConstructionContent;
    private ObservableList<String> constructionContentSelected = FXCollections.observableArrayList();
    private ObservableList<String> constructionContentData = FXCollections.observableArrayList("DanMoi", "ThayLayout", "SuaChua", "ThaoDo", "PhatSinh", "XemTatCa");

    private ObservableList<Integer> yearSelected = FXCollections.observableArrayList();

    @FXML
    private VBox vBoxQuarter;
    private static ObservableList<String> comboBoxQuarterData = FXCollections.observableArrayList("Quý 1", "Quý 2", "Quý 3", "Quý 4", "Tất cả các quý", "Xem theo tháng");
    private static ObservableList<String> quarterData = FXCollections.observableArrayList("Q1", "Q2", "Q3", "Q4", "All Quarter", "view By Month");
    private ObservableList<String> quarterSelected = FXCollections.observableArrayList();

    @FXML
    private VBox vBoxRoute;
    //    private static ObservableList<String> quarterData = FXCollections.observableArrayList("Q1", "Q2", "Q3", "Q4", "All Quarter", "view By Month");
    private ObservableList<String> routeSelected = FXCollections.observableArrayList();

    @FXML
    private Button buttonRefresh;

    @FXML
    private BorderPane chartBorderPane;

    private static ObservableList<OnJobBus> onJobBusList = FXCollections.observableArrayList();

    static String[] cellColors = new String[]{
            //tím
            "#8E44AD",
            //cam
            "#E74C3C",
            //xanh
            "#1ABC9C",
            //reddit
            "#FF5700",
            //yahoo
            "#410093",
            //skype
            "#00AFF0"
    };

    @FXML
    private PieChart pieChart;

    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Xe đang trống", 100),
            new PieChart.Data("QC còn hạn", 50),
            new PieChart.Data("QC hết hạn", 50)
    );

    @FXML
    private BarChart<String, Number> optionBarChart;

    final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

//    private ObservableList<Integer> getComboBoxYearData(ObservableList<OnJobBus> oJBus) {
//
//        ObservableList<OnJobBus> years = removeOnJobBusDuplicateByYear(oJBus);
//
//        years.forEach(b ->
//                comboBoxYearData.add(getYearFromDate(b.getOnJobDateOfConstruction()))
//        );
//
//        return comboBoxYearData;
//    }

    private ObservableList<String> getComboBoxRouteNumberData(ObservableList<OnJobBus> oJBus) {

        comboBoxRouteData.clear();
        ObservableList<OnJobBus> a = removeOnJobBusDuplicateByRouteNumber(oJBus);

        a.forEach(b ->
                comboBoxRouteData.add(String.valueOf(b.getOnJobRouteNumber()))
        );

        return comboBoxRouteData;
    }

    private ObservableList<OnJobBus> removeOnJobBusDuplicateByYear(ObservableList<OnJobBus> list) {

        Set<Integer> nameSet = new HashSet<>();

        ObservableList<OnJobBus> onJobBusDistinctByYear = list.stream()
                .filter(e -> nameSet.add(getYearFromDate(e.getOnJobDateOfConstruction())))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        //sắp xếp theo năm tăng dần
        onJobBusDistinctByYear.sort(Comparator.comparingInt(b -> getYearFromDate(b.getOnJobDateOfConstruction()))
        );

        return onJobBusDistinctByYear;
    }

    private void initDataForPieChart() {
        pieChart.setData(pieChartData);
    }

    private int randomNumber(int num) {
        Random rd = new Random();
        System.out.println("rd: " + rd.nextInt(num));
        return rd.nextInt(num);
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

    public void initDataForOnJobBusList(ObservableList<OnJobBus> list) {
        onJobBusList = list;
    }

    //count option 1
    private int countByConstructionContent(int year, int month, String constructionContent, String routeNumber) {
        return countByAllConstructionContent(year, month, constructionContent, routeNumber);
    }

    //count option 2
    private int countByConstructionContent(int year, int month, ObservableList<String> constructionContent, String routeNumber) {

        int sum = 0;
        for (int i = 0; i < constructionContent.size(); i++) {
            sum += countByAllConstructionContent(year, month, String.valueOf(constructionContent.get(i)), routeNumber);
        }
        return sum;
    }

    private int countByAllConstructionContent(int year, int month, String constructionContent, String numRoute) {

        if (constructionContent.equals("XemTatCa")) {

            System.out.println("Xem tất cả OKKKKK");

            return countByOnlyConstructionContent(year, month, "DanMoi") +
                    countByOnlyConstructionContent(year, month, "ThayLayout") +
                    countByOnlyConstructionContent(year, month, "PhatSinh") +
                    countByOnlyConstructionContent(year, month, "PhatSinh") +
                    countByOnlyConstructionContent(year, month, "SuaChua");
        } else {
            return countByOnlyConstructionContent(year, month, constructionContent);
        }

    }

    private int countByOnlyConstructionContent(int year, int month, String constructionContent) {
        return (int) onJobBusList.stream().filter(b -> getYearFromDate(b.getOnJobDateOfConstruction()) == year && getMonthFromDate(b.getOnJobDateOfConstruction()) == month &&
                b.getOnJobConstructionContent().equals(constructionContent)).count();
    }

    public ObservableList<IntegratedStatistic> getTableDataForIntegrateStatistic(int year) {

        int i = 0;
        for (i = 1; i <= months.length; i++) {

            IntegratedStatistic integratedStatistic = new IntegratedStatistic();
            integratedStatistic.setYear(String.valueOf(year));
            integratedStatistic.setMonth(String.valueOf(months[i - 1]));
            integratedStatistic.setNumberOfReNew(countByConstructionContent(year, i, "DanMoi", "route"));
            integratedStatistic.setNumberOfChangeLayout(countByConstructionContent(year, i, "ThayLayout", "route"));
            integratedStatistic.setNumberOfIncurred(countByConstructionContent(year, i, "PhatSinh", "route"));
            integratedStatistic.setNumberOfDismantling(countByConstructionContent(year, i, "ThaoDo", "route"));
            integratedStatistic.setNumberOfRepair(countByConstructionContent(year, i, "SuaChua", "route"));

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
    private void initIntegrateStatisticDataTable(int year) {

        no_col.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(integratedStatisticTableView.getItems().indexOf(p.getValue()) + ""));
        no_col.setSortable(false);
        no_col.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<IntegratedStatistic, String>, TableCell<IntegratedStatistic, String>> defaultCellFactory =
                TextFieldTableCell.forTableColumn();

        Callback<TableColumn<IntegratedStatistic, String>, TableCell<IntegratedStatistic, String>> cellFactory = col -> {
            TableCell<IntegratedStatistic, String> cell = defaultCellFactory.call(null);
            cell.itemProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue == null) {
                    cell.setStyle("cell-selection-color: -fx-selection-bar ;");
                } else {
                    Random rd = new Random();
                    cell.setStyle("-fx-text-fill: " + cellColors[rd.nextInt(cellColors.length)] + ";");
                }
            });
            return cell;
        };

        year_col.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        year_col.setStyle("-fx-alignment: CENTER;");
//        year_col.setCellFactory(cellFactory);

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

        //khởi tạo dữ liệu table với năm hiện hành
        integratedStatisticTableView.setItems(getTableDataForIntegrateStatistic(year));
    }

    private int getMonthFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue();
    }

    private int getYearFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear();
    }

    @FXML
    public void backToNavigationScreen(Event event) {
        //BorderPane root = chartBorderPane.;
        BorderPane root = (BorderPane) chartBorderPane.getScene().getRoot();
        root.setCenter(myController.getScreen(DashBoardController.screenNavigationID));
    }

    private void hideColumn(ObservableList<TableColumn<IntegratedStatistic, Number>> column) {
//        column.setVisible(true);
        column.forEach(col -> col.setVisible(false));
    }

    private void unHideColumn(ObservableList<TableColumn<IntegratedStatistic, Number>> column) {
//        column.setVisible(true);
        column.forEach(col -> col.setVisible(true));
    }

    private void initOptionBarChart() {
//        changeOptionBarChartBy(yearSelected, constructionContentSelected);
    }

    //biểu đồ thay đổi theo năm
    private void changeOptionBarChartByYearSelected(ObservableList<Integer> years) {

        System.out.println("change aaaaa");

        optionBarChart.getData().clear();
        optionBarChart.setAnimated(false);

        if (years.size() > 0) {
            years.forEach(y -> {

                int quarterIndex;
                boolean isComboBoxQuarterChoose = comboBoxQuarter.getSelectionModel().isEmpty();
                if (isComboBoxQuarterChoose) {
                    //nếu không chọn quý ==> mặc định hiển thị biểu đồ theo tháng
                    int lastIndex = comboBoxQuarter.getItems().size() - 1;
                    quarterIndex = lastIndex;
                } else {
                    quarterIndex = comboBoxQuarter.getSelectionModel().getSelectedIndex();
                }

//                ObservableList<String> constructionContent

                setOptionBarChartChange(y, quarterIndex, constructionContentData, "1");

            });
        } else {
            optionBarChart.getData().clear();
        }

    }

    //biểu đồ thay đổi theo nội dung thi công
    private void changeOptionBarChartByConstructionContentSelected(ObservableList<String> constructionContent) {

        System.out.println("change aaaaa");

        optionBarChart.getData().clear();
        optionBarChart.setAnimated(false);

        if (yearSelected.size() > 0) {
            yearSelected.forEach(y -> {

                int quarterIndex;
                boolean isComboBoxQuarterChoose = comboBoxQuarter.getSelectionModel().isEmpty();
                if (isComboBoxQuarterChoose) {
                    //nếu không chọn quý ==> mặc định hiển thị biểu đồ theo tháng
                    int lastIndex = comboBoxQuarter.getItems().size() - 1;
                    quarterIndex = lastIndex;
                } else {
                    quarterIndex = comboBoxQuarter.getSelectionModel().getSelectedIndex();
                }

                setOptionBarChartChange(y, quarterIndex, constructionContent, "1");

            });
        } else {
            optionBarChart.getData().clear();
        }

    }

    //biểu đồ thay đổi theo tuyến
    private void changeOptionBarChartByRouteSelected(String route) {

        System.out.println("change by route");

        optionBarChart.getData().clear();
        optionBarChart.setAnimated(false);

        if (yearSelected.size() > 0) {
            yearSelected.forEach(y -> {

                int quarterIndex;
                boolean isComboBoxQuarterChoose = comboBoxQuarter.getSelectionModel().isEmpty();
                if (isComboBoxQuarterChoose) {
                    //nếu không chọn quý ==> mặc định hiển thị biểu đồ theo tháng
                    int lastIndex = comboBoxQuarter.getItems().size() - 1;
                    quarterIndex = lastIndex;
                } else {
                    quarterIndex = comboBoxQuarter.getSelectionModel().getSelectedIndex();
                }

                setOptionBarChartChange(y, quarterIndex, constructionContentSelected, route);

            });
        } else {
            optionBarChart.getData().clear();
        }

    }

    //thay đổi biểu đồ theo năm
//    private void changeOptionBarChartByYearSelected(ObservableList<Integer> years) {
//
//        if (constructionContentSelected.size() > 0) {
//            constructionContentSelected.forEach(e -> System.out.println(e));
//        } else {
//            System.out.println("ko có chọn nội dung");
//        }
//
//        optionBarChart.getData().clear();
//        optionBarChart.setAnimated(false);
//
//        if (years.size() > 0) {
//            years.forEach(y -> {
//
//                int quarterIndex;
//                boolean isComboBoxQuarterChoose = comboBoxQuarter.getSelectionModel().isEmpty();
//                if (isComboBoxQuarterChoose) {
//                    //nếu không chọn quý ==> mặc định hiển thị biểu đồ theo tháng
//                    int lastIndex = comboBoxQuarter.getItems().size() - 1;
//                    quarterIndex = lastIndex;
//                } else {
//                    quarterIndex = comboBoxQuarter.getSelectionModel().getSelectedIndex();
//                }
//
//                String constructionContent;
//                boolean isComboBoxConstructionContentChoose = comboBoxConstructionContent.getSelectionModel().isEmpty();
//                if (isComboBoxConstructionContentChoose) {
//                    //nếu nội dung thi công ko được chọn ==> xem tất cả nội dung thi công
//                    int lastIndex = comboBoxConstructionContent.getItems().size() - 1;
//                    String lastItemSelected = comboBoxConstructionContent.getItems().get(Integer.valueOf(lastIndex));
//                    constructionContent = lastItemSelected;
//
//                } else {
//                    constructionContent = comboBoxConstructionContent.getValue();
//                }
//
//                setOptionBarChartChange(y, quarterIndex, constructionContent, "1");
//
//            });
//        } else {
//            optionBarChart.getData().clear();
//        }
//
//    }

    //theo đổi option barChart theo quý
    private void changeOptionBarChartByQuarterSelected(int quarterIndex) {

        optionBarChart.getData().clear();
        optionBarChart.setAnimated(false);

        if (yearSelected.size() > 0) {
            yearSelected.forEach(y -> {
//                String constructionContent;
//                boolean isComboBoxConstructionContentChoose = comboBoxConstructionContent.getSelectionModel().isEmpty();
//                if (isComboBoxConstructionContentChoose) {
//                    //nếu nội dung thi công ko được chọn ==> xem tất cả nội dung thi công
//                    System.out.println("nội dung thi công không được chọn");
//                    int lastIndex = comboBoxConstructionContent.getItems().size() - 1;
//                    String lastItemSelected = comboBoxConstructionContent.getItems().get(Integer.valueOf(lastIndex));
//                    constructionContent = lastItemSelected;
//                    System.out.println("nội dung tc: " + constructionContent);
//                } else {
//                    constructionContent = comboBoxConstructionContent.getValue();
//                }

                optionBarChart.layout();
                setOptionBarChartChange(y, quarterIndex, constructionContentSelected, "1");

            });
        } else {
            optionBarChart.getData().clear();
            optionBarChart.layout();
        }

    }

    //option1
    private void setOptionBarChartChange(int year, int quarterIndex, String constructionContent, String routeNumber) {

        XYChart.Series<String, Number> quarterSeries = new XYChart.Series<>();
        quarterSeries.setName(String.valueOf(year));

        switch (quarterIndex) {
            case 0:
                //quý 1
                optionBarChart.layout();
                XYChart.Data<String, Number> jan = new XYChart.Data<>("Jan",
                        countByConstructionContent(year, 1, constructionContent, routeNumber));
                quarterSeries.getData().add(jan);

                XYChart.Data<String, Number> feb = new XYChart.Data<>("Feb",
                        countByConstructionContent(year, 2, constructionContent, routeNumber));
                quarterSeries.getData().add(feb);

                XYChart.Data<String, Number> mar = new XYChart.Data<>("Mar",
                        countByConstructionContent(year, 3, constructionContent, routeNumber));
                quarterSeries.getData().add(mar);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 1:
                optionBarChart.layout();
                XYChart.Data<String, Number> apr = new XYChart.Data<>("Apr",
                        countByConstructionContent(year, 4, constructionContent, routeNumber));
                quarterSeries.getData().add(apr);

                XYChart.Data<String, Number> may = new XYChart.Data<>("May",
                        countByConstructionContent(year, 5, constructionContent, routeNumber));
                quarterSeries.getData().add(may);

                XYChart.Data<String, Number> jun = new XYChart.Data<>("Jun",
                        countByConstructionContent(year, 6, constructionContent, routeNumber));
                quarterSeries.getData().add(jun);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 2:
                optionBarChart.layout();
                XYChart.Data<String, Number> jul = new XYChart.Data<>("Jul",
                        countByConstructionContent(year, 7, constructionContent, routeNumber));
                quarterSeries.getData().add(jul);

                XYChart.Data<String, Number> aug = new XYChart.Data<>("Aug",
                        countByConstructionContent(year, 8, constructionContent, routeNumber));
                quarterSeries.getData().add(aug);

                XYChart.Data<String, Number> sep = new XYChart.Data<>("Sep",
                        countByConstructionContent(year, 9, constructionContent, routeNumber));
                quarterSeries.getData().add(sep);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 3:
                optionBarChart.layout();
                XYChart.Data<String, Number> oct = new XYChart.Data<>("Oct",
                        countByConstructionContent(year, 10, constructionContent, routeNumber));
                quarterSeries.getData().add(oct);

                XYChart.Data<String, Number> nov = new XYChart.Data<>("Nov",
                        countByConstructionContent(year, 11, constructionContent, routeNumber));
                quarterSeries.getData().add(nov);

                XYChart.Data<String, Number> dec = new XYChart.Data<>("Dec",
                        countByConstructionContent(year, 12, constructionContent, routeNumber));
                quarterSeries.getData().add(dec);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 4:
                optionBarChart.layout();
                XYChart.Data<String, Number> q1 = new XYChart.Data<>("Q1",
                        countByConstructionContent(year, 1, constructionContent, routeNumber) +
                                countByConstructionContent(year, 2, constructionContent, routeNumber) +
                                countByConstructionContent(year, 3, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q1);

                XYChart.Data<String, Number> q2 = new XYChart.Data<>("Q2",
                        countByConstructionContent(year, 4, constructionContent, routeNumber) +
                                countByConstructionContent(year, 5, constructionContent, routeNumber) +
                                countByConstructionContent(year, 6, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q2);

                XYChart.Data<String, Number> q3 = new XYChart.Data<>("Q3",
                        countByConstructionContent(year, 7, constructionContent, routeNumber) +
                                countByConstructionContent(year, 8, constructionContent, routeNumber) +
                                countByConstructionContent(year, 9, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q3);

                XYChart.Data<String, Number> q4 = new XYChart.Data<>("Q4",
                        countByConstructionContent(year, 10, constructionContent, routeNumber) +
                                countByConstructionContent(year, 11, constructionContent, routeNumber) +
                                countByConstructionContent(year, 12, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q4);

                optionBarChart.getData().add(quarterSeries);
                break;
            default:
                optionBarChart.layout();
                XYChart.Data<String, Number> allQuarterJan = new XYChart.Data<>("Jan",
                        countByConstructionContent(year, 1, constructionContent, routeNumber));
                quarterSeries.getData().add(allQuarterJan);

                XYChart.Data<String, Number> allQuarterFeb = new XYChart.Data<>("Feb",
                        countByConstructionContent(year, 2, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterFeb);

                XYChart.Data<String, Number> allQuarterMar = new XYChart.Data<>("Mar",
                        countByConstructionContent(year, 3, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterMar);

                XYChart.Data<String, Number> allQuarterApr = new XYChart.Data<>("Apr",
                        countByConstructionContent(year, 4, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterApr);

                XYChart.Data<String, Number> allQuarterMay = new XYChart.Data<>("May",
                        countByConstructionContent(year, 5, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterMay);

                XYChart.Data<String, Number> allQuarterJun = new XYChart.Data<>("Jun",
                        countByConstructionContent(year, 6, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterJun);

                XYChart.Data<String, Number> allQuarterJul = new XYChart.Data<>("Jul",
                        countByConstructionContent(year, 7, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterJul);

                XYChart.Data<String, Number> allQuarterAug = new XYChart.Data<>("Aug",
                        countByConstructionContent(year, 8, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterAug);

                XYChart.Data<String, Number> allQuarterSep = new XYChart.Data<>("Sep",
                        countByConstructionContent(year, 9, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterSep);

                XYChart.Data<String, Number> allQuarterOct = new XYChart.Data<>("Oct",
                        countByConstructionContent(year, 10, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterOct);

                XYChart.Data<String, Number> allQuarterNov = new XYChart.Data<>("Nov",
                        countByConstructionContent(year, 11, constructionContent, routeNumber));
                quarterSeries.getData().add(allQuarterNov);

                XYChart.Data<String, Number> allQuarterDec = new XYChart.Data<>("Dec",
                        countByConstructionContent(year, 12, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterDec);

                optionBarChart.getData().add(quarterSeries);
        }
    }

    //option2
    private void setOptionBarChartChange(int year, int quarterIndex, ObservableList<String> constructionContent, String routeNumber) {

        XYChart.Series<String, Number> quarterSeries = new XYChart.Series<>();
        quarterSeries.setName(String.valueOf(year));

        switch (quarterIndex) {
            case 0:
                //quý 1
                optionBarChart.layout();
                XYChart.Data<String, Number> jan = new XYChart.Data<>("Jan",
                        countByConstructionContent(year, 1, constructionContent, routeNumber));
                quarterSeries.getData().add(jan);

                XYChart.Data<String, Number> feb = new XYChart.Data<>("Feb",
                        countByConstructionContent(year, 2, constructionContent, routeNumber));
                quarterSeries.getData().add(feb);

                XYChart.Data<String, Number> mar = new XYChart.Data<>("Mar",
                        countByConstructionContent(year, 3, constructionContent, routeNumber));
                quarterSeries.getData().add(mar);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 1:
                optionBarChart.layout();
                XYChart.Data<String, Number> apr = new XYChart.Data<>("Apr",
                        countByConstructionContent(year, 4, constructionContent, routeNumber));
                quarterSeries.getData().add(apr);

                XYChart.Data<String, Number> may = new XYChart.Data<>("May",
                        countByConstructionContent(year, 5, constructionContent, routeNumber));
                quarterSeries.getData().add(may);

                XYChart.Data<String, Number> jun = new XYChart.Data<>("Jun",
                        countByConstructionContent(year, 6, constructionContent, routeNumber));
                quarterSeries.getData().add(jun);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 2:
                optionBarChart.layout();
                XYChart.Data<String, Number> jul = new XYChart.Data<>("Jul",
                        countByConstructionContent(year, 7, constructionContent, routeNumber));
                quarterSeries.getData().add(jul);

                XYChart.Data<String, Number> aug = new XYChart.Data<>("Aug",
                        countByConstructionContent(year, 8, constructionContent, routeNumber));
                quarterSeries.getData().add(aug);

                XYChart.Data<String, Number> sep = new XYChart.Data<>("Sep",
                        countByConstructionContent(year, 9, constructionContent, routeNumber));
                quarterSeries.getData().add(sep);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 3:
                optionBarChart.layout();
                XYChart.Data<String, Number> oct = new XYChart.Data<>("Oct",
                        countByConstructionContent(year, 10, constructionContent, routeNumber));
                quarterSeries.getData().add(oct);

                XYChart.Data<String, Number> nov = new XYChart.Data<>("Nov",
                        countByConstructionContent(year, 11, constructionContent, routeNumber));
                quarterSeries.getData().add(nov);

                XYChart.Data<String, Number> dec = new XYChart.Data<>("Dec",
                        countByConstructionContent(year, 12, constructionContent, routeNumber));
                quarterSeries.getData().add(dec);

                optionBarChart.getData().add(quarterSeries);
                break;

            case 4:
                optionBarChart.layout();
                XYChart.Data<String, Number> q1 = new XYChart.Data<>("Q1",
                        countByConstructionContent(year, 1, constructionContent, routeNumber) +
                                countByConstructionContent(year, 2, constructionContent, routeNumber) +
                                countByConstructionContent(year, 3, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q1);

                XYChart.Data<String, Number> q2 = new XYChart.Data<>("Q2",
                        countByConstructionContent(year, 4, constructionContent, routeNumber) +
                                countByConstructionContent(year, 5, constructionContent, routeNumber) +
                                countByConstructionContent(year, 6, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q2);

                XYChart.Data<String, Number> q3 = new XYChart.Data<>("Q3",
                        countByConstructionContent(year, 7, constructionContent, routeNumber) +
                                countByConstructionContent(year, 8, constructionContent, routeNumber) +
                                countByConstructionContent(year, 9, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q3);

                XYChart.Data<String, Number> q4 = new XYChart.Data<>("Q4",
                        countByConstructionContent(year, 10, constructionContent, routeNumber) +
                                countByConstructionContent(year, 11, constructionContent, routeNumber) +
                                countByConstructionContent(year, 12, constructionContent, routeNumber)

                );
                quarterSeries.getData().add(q4);

                optionBarChart.getData().add(quarterSeries);
                break;
            default:
                optionBarChart.layout();
                XYChart.Data<String, Number> allQuarterJan = new XYChart.Data<>("Jan",
                        countByConstructionContent(year, 1, constructionContent, routeNumber));
                quarterSeries.getData().add(allQuarterJan);

                XYChart.Data<String, Number> allQuarterFeb = new XYChart.Data<>("Feb",
                        countByConstructionContent(year, 2, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterFeb);

                XYChart.Data<String, Number> allQuarterMar = new XYChart.Data<>("Mar",
                        countByConstructionContent(year, 3, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterMar);

                XYChart.Data<String, Number> allQuarterApr = new XYChart.Data<>("Apr",
                        countByConstructionContent(year, 4, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterApr);

                XYChart.Data<String, Number> allQuarterMay = new XYChart.Data<>("May",
                        countByConstructionContent(year, 5, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterMay);

                XYChart.Data<String, Number> allQuarterJun = new XYChart.Data<>("Jun",
                        countByConstructionContent(year, 6, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterJun);

                XYChart.Data<String, Number> allQuarterJul = new XYChart.Data<>("Jul",
                        countByConstructionContent(year, 7, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterJul);

                XYChart.Data<String, Number> allQuarterAug = new XYChart.Data<>("Aug",
                        countByConstructionContent(year, 8, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterAug);

                XYChart.Data<String, Number> allQuarterSep = new XYChart.Data<>("Sep",
                        countByConstructionContent(year, 9, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterSep);

                XYChart.Data<String, Number> allQuarterOct = new XYChart.Data<>("Oct",
                        countByConstructionContent(year, 10, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterOct);

                XYChart.Data<String, Number> allQuarterNov = new XYChart.Data<>("Nov",
                        countByConstructionContent(year, 11, constructionContent, routeNumber));
                quarterSeries.getData().add(allQuarterNov);

                XYChart.Data<String, Number> allQuarterDec = new XYChart.Data<>("Dec",
                        countByConstructionContent(year, 12, constructionContent, routeNumber)
                );
                quarterSeries.getData().add(allQuarterDec);

                optionBarChart.getData().add(quarterSeries);
        }
    }

    //
    private void addYearToListAndSetActiveButtonByCurrentYear(Button button) {

        int buttonYear = Integer.valueOf(button.getText());
        int index = yearSelected.indexOf(buttonYear);

        if (yearSelected.contains(buttonYear)) {
            setStyleDeSelectButton(button);
            yearSelected.remove(index);
        } else {
            setStyleOnClickButton(button);
            yearSelected.add(Integer.valueOf(button.getText()));
        }

        changeOptionBarChartByYearSelected(yearSelected);
    }

    private void setActiveButtonForCurrentYear(int currentYear) {
        vBoxYear.getChildren().forEach(n -> {
            if (n.getAccessibleText().equals(String.valueOf(currentYear))) {
                Button btn = (Button) n;
                addYearToListAndSetActiveButtonByCurrentYear(btn);
            }
        });
    }

    private void setActiveButtonForDefaultConstructionContent() {

        vBoxConstructionContent.getChildren().forEach(n -> {
            if (n.getAccessibleText().equals("XemTatCa")) {
                Button btn = (Button) n;
                addConstructionContentToListAndSetActiveButton(btn);
            }
        });
    }

    private void setActiveButtonForDefaultQuarter() {

        vBoxQuarter.getChildren().forEach(n -> {
            if (n.getAccessibleText().equals("view By Month")) {
                Button btn = (Button) n;
                addQuarterToListAndSetActiveButton(btn);
            }
        });
    }

    //
    private void addQuarterToListAndSetActiveButton(Button button) {

        int result = (int) quarterSelected.stream().filter(el -> el.equals(button.getText())).count();

        if (result > 0) {
            setStyleDeSelectButton(button);
            quarterSelected.remove(button.getText());

        } else {

            if (!button.getText().equals("view By Month")) {
                int count = (int) quarterSelected.stream().filter(el -> el.equals("view By Month")).count();

                if (count > 0) {
                    quarterSelected.remove("view By Month");
                    //set lại style cho button xem tất car
                    vBoxQuarter.getChildren().forEach(n -> {
                        if (n.getAccessibleText().equals("view By Month")) {
                            Button btn = (Button) n;
                            setStyleDeSelectButton(btn);
                        }
                    });
                }

                setStyleOnClickButton(button);
                quarterSelected.add(button.getText());

            } else {
                //nếu chọn xem tất cả nội dung thi công thì bỏ chọn các button khác
                quarterSelected.add(button.getText());
                setStyleOnClickButton(button);

                //bỏ chọn các button khác
                vBoxQuarter.getChildren().forEach(c -> {
                    if (!c.getAccessibleText().equals("view By Month")) {
                        Button btn = (Button) c;
                        setStyleDeSelectButton(btn);
                        quarterSelected.remove(c.getAccessibleText());
                    }
                });
            }

        }
//
//        changeOptionBarChartBy(yearSelected,constructionContentSelected);
    }

    //mặc định chọn tất cả cho nội dung thi công khi khởi chạy
    private void addConstructionContentToListAndSetActiveButton(Button button) {

        int result = (int) constructionContentSelected.stream().filter(el -> el.equals(button.getText())).count();

        if (result > 0) {
            setStyleDeSelectButton(button);
            constructionContentSelected.remove(button.getText());
        } else {

            if (!button.getText().equals("XemTatCa")) {
                int count = (int) constructionContentSelected.stream().filter(el -> el.equals("XemTatCa")).count();

                if (count > 0) {
                    constructionContentSelected.remove("XemTatCa");
                    //set lại style cho button xem tất car
                    vBoxConstructionContent.getChildren().forEach(n -> {
                        if (n.getAccessibleText().equals("XemTatCa")) {
                            Button btn = (Button) n;
                            setStyleDeSelectButton(btn);
                        }
                    });
                }

                setStyleOnClickButton(button);
                constructionContentSelected.add(button.getText());

            } else {
                //nếu chọn xem tất cả nội dung thi công thì bỏ chọn các button khác
                constructionContentSelected.add(button.getText());
                setStyleOnClickButton(button);

                //bỏ chọn các button khác
                vBoxConstructionContent.getChildren().forEach(c -> {
                    if (!c.getAccessibleText().equals("XemTatCa")) {
                        Button btn = (Button) c;
                        setStyleDeSelectButton(btn);
                        constructionContentSelected.remove(c.getAccessibleText());
                    }
                });
            }

        }

        changeOptionBarChartByConstructionContentSelected(constructionContentSelected);
    }

    //
    private void addRouteToListAndSetActiveButton(Button button) {

        int result = (int) routeSelected.stream().filter(el -> el.equals(button.getText())).count();

        if (result > 0) {
            setStyleDeSelectButton(button);
            routeSelected.remove(button.getText());
        } else {

            if (!button.getText().equals("All Route")) {
                int count = (int) routeSelected.stream().filter(el -> el.equals("All Route")).count();

                if (count > 0) {
                    routeSelected.remove("All Route");
                    //set lại style cho button xem tất car
                    vBoxRoute.getChildren().forEach(n -> {
                        if (n.getAccessibleText().equals("All Route")) {
                            Button btn = (Button) n;
                            setStyleDeSelectButton(btn);
                        }
                    });
                }

                setStyleOnClickButton(button);
                routeSelected.add(button.getText());

            } else {
                //nếu chọn xem tất cả nội dung thi công thì bỏ chọn các button khác
                routeSelected.add(button.getText());
                setStyleOnClickButton(button);

                //bỏ chọn các button khác
                vBoxRoute.getChildren().forEach(c -> {
                    if (!c.getAccessibleText().equals("All Route")) {
                        Button btn = (Button) c;
                        setStyleDeSelectButton(btn);
                        routeSelected.remove(c.getAccessibleText());
                    }
                });
            }

        }

//        changeOptionBarChartByConstructionContentSelected(constructionContentSelected);
    }

    private void setButtonWith(Button button, double w) {
        button.setPrefWidth(w);
    }

    private Button createConstructionContentButton(String text) {
        Button button = new Button(text);
        setButtonWith(button, 100);
        button.setWrapText(true);
        button.setAccessibleText(text);
        setStyleOnInitButton(button);
        button.setOnAction(e -> addConstructionContentToListAndSetActiveButton(button));
        return button;
    }

    private Button createQuarterButton(String text) {
        Button button = new Button(text);
        setButtonWith(button, 130);
        button.setWrapText(true);
        button.setAccessibleText(text);
        setStyleOnInitButton(button);
        button.setOnAction(e -> addQuarterToListAndSetActiveButton(button));
        return button;
    }

    private Button createYearNumberButton(int number) {
        Button button = new Button(Integer.toString(number));
        setButtonWith(button, 100);
        setStyleOnInitButton(button);
        button.setAccessibleText(String.valueOf(number));
        button.setOnAction(e -> addYearToListAndSetActiveButtonByCurrentYear(button));
        return button;
    }

    private Button createRouteButton(String route) {

        Button button = new Button(route);
        setButtonWith(button, 100);
        setStyleOnInitButton(button);
        button.setAccessibleText(route);
        button.setOnAction(e -> addRouteToListAndSetActiveButton(button));
        return button;
    }

    private void setStyleOnInitButton(Button button) {
        button.setStyle("-fx-background-color: #e5e5e5;-fx-text-fill:#0c0c0c");
    }

    private void setStyleDeSelectButton(Button button) {
        button.setStyle("-fx-background-color: #e5e5e5;-fx-text-fill:#0c0c0c");
    }

    private void setStyleOnClickButton(Button button) {
        button.setStyle("-fx-background-color: #3686ff;-fx-text-fill:#fff");
    }

    private void renderYearAndRouteInVBox(ObservableList<OnJobBus> ojBus) {

        ObservableList<OnJobBus> years = removeOnJobBusDuplicateByYear(ojBus);
        vBoxYear.setSpacing(5);
        years.forEach(ojb -> vBoxYear.getChildren().addAll(
                createYearNumberButton(getYearFromDate(ojb.getOnJobDateOfConstruction()))
        ));

        renderRoute(ojBus);

    }

    private void renderRoute(ObservableList<OnJobBus> ojBus) {

//        ObservableList<OnJobBus> routers = removeOnJobBusDuplicateByYear(ojBus);

        ObservableList<String> allRoute = getComboBoxRouteNumberData(ojBus);
        //thêm vào dòng cuối cùng => thêm tùy chọn tất cả tuyến
        allRoute.add("All Route");

        vBoxRoute.setSpacing(5);
        allRoute.forEach(ojb -> vBoxRoute.getChildren().addAll(
                createRouteButton(ojb)
        ));

    }

    private void renderQuarter() {

        vBoxQuarter.setSpacing(5);
        quarterData.forEach(q -> vBoxQuarter.getChildren().addAll(
                createQuarterButton(q)
        ));
    }

    private void renderConstructionContentInVBox() {

        vBoxConstructionContent.setSpacing(5);
        constructionContentData.forEach(item -> vBoxConstructionContent.getChildren().addAll(
                createConstructionContentButton(item)
        ));
    }

    private void reFreshDataForYear(ObservableList<OnJobBus> ojBus) {

        vBoxYear.getChildren().clear();

        ObservableList<OnJobBus> years = removeOnJobBusDuplicateByYear(ojBus);
        vBoxYear.setSpacing(5);
        years.forEach(ojb -> vBoxYear.getChildren().addAll(
                createYearNumberButton(getYearFromDate(ojb.getOnJobDateOfConstruction()))
        ));
    }

    private void refreshConstructionContent() {
        setActiveButtonForDefaultConstructionContent();
    }

    private void initDataForComboBoxQuarter() {
        comboBoxQuarter.getItems().addAll(comboBoxQuarterData);
        //comboBoxQuarter.getSelectionModel().selectLast();
    }

    private void refreshDataIntegrateStatisticBarChart() {
        optionBarChart.getData().clear();
        setActiveButtonForCurrentYear(getYearFromDate(new Date()));
//        initDataForIntegrateStatisticBarChart(getYearFromDate(new Date()));
    }

    private void refreshComboBoxQuarter() {

        comboBoxQuarter.getSelectionModel().clearSelection();
        comboBoxQuarter.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    //nếu comboBox không có dữ liệu
                    setText("select quarter");
                } else {
                    setText(item);
                }
            }
        });

    }

    private void refreshComboBoxOption() {

        comboBoxConstructionContent.getSelectionModel().clearSelection();
        comboBoxConstructionContent.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    //nếu comboBox không có dữ liệu
                    setText("select construction content");
                } else {
                    setText(item);
                }
            }
        });
    }

    private void refreshComboBoxRouteNumber() {
        comboBoxRoute.getItems().clear();
        comboBoxRoute.getItems().addAll(getComboBoxRouteNumberData(onJobBusList));
    }

    private void initDataForConstructionContentComboBox() {
        comboBoxConstructionContent.getItems().addAll(constructionContentData);
    }

    private void initDataForRouteNumberComboBox() {
        comboBoxRoute.getItems().addAll(getComboBoxRouteNumberData(onJobBusList));
        comboBoxRoute.getItems().add("All Routes");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initDataForComboBoxQuarter();

        initDataForRouteNumberComboBox();

        renderYearAndRouteInVBox(onJobBusList);

//        renderQuarter();

        renderConstructionContentInVBox();

        setActiveButtonForCurrentYear(getYearFromDate(new Date()));

//        setActiveButtonForDefaultQuarter();

        setActiveButtonForDefaultConstructionContent();

//        initOptionBarChart();

        initDataForPieChart();

        comboBoxQuarter.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int index = (int) newValue;
                changeOptionBarChartByQuarterSelected(index);
            }
        });

//        comboBoxConstructionContent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                changeOptionBarChartByConstructionContentSelected(newValue);
//            }
//        });

        comboBoxRoute.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

            }
        });

//        String item = comboBoxRoute.getItems().get(1);
//        System.out.println("item: "+item);

        //làm mới dữ liệu khi có sự thay đổi được nhận từ
        buttonRefresh.setOnAction(event -> {

            reFreshDataForYear(onJobBusList);
            yearSelected.clear();
            setActiveButtonForCurrentYear(getYearFromDate(new Date()));

            refreshConstructionContent();

            refreshComboBoxQuarter();
            refreshComboBoxRouteNumber();

        });
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
