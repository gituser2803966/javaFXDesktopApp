package org.openjfx;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private BarChart<String, Number> yearLineChart;

    @FXML
    private LineChart<String, Number> monthLineChart;

    @FXML
    private ComboBox<String> yearsComboBox;

    @FXML
    private ComboBox<String> monthsComboBox;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void setMonthLineChart(){

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

    public void setYearLineChart(){
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        yearLineChart.setTitle("NĂM 2021");
        series.getData().add(new XYChart.Data<String, Number>("Jan", 130));
        series.getData().add(new XYChart.Data<String, Number>("Feb", 120));
        series.getData().add(new XYChart.Data<String, Number>("Mar", 110));
        series.getData().add(new XYChart.Data<String, Number>("Apr", 115));
        series.getData().add(new XYChart.Data<String, Number>("May", 190));
        series.getData().add(new XYChart.Data<String, Number>("Jun", 170));
        series.getData().add(new XYChart.Data<String, Number>("Jul", 130));
        series.getData().add(new XYChart.Data<String, Number>("Aug", 122));
        series.getData().add(new XYChart.Data<String, Number>("Sep", 135));
        series.getData().add(new XYChart.Data<String, Number>("Oct", 165));
        series.getData().add(new XYChart.Data<String, Number>("Nov", 129));
        series.getData().add(new XYChart.Data<String, Number>("Dec", 144));

        yearLineChart.getData().add(series);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> cbxYearsData = FXCollections.observableArrayList("2020","2021");
        yearsComboBox.getItems().addAll(cbxYearsData);

        setYearLineChart();
        setMonthLineChart();


    }
}
