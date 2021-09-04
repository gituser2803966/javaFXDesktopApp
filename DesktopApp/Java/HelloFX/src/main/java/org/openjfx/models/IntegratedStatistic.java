package org.openjfx.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IntegratedStatistic {
    private StringProperty year = new SimpleStringProperty("");
    private StringProperty month = new SimpleStringProperty("");
    private IntegerProperty numberOfReNew = new SimpleIntegerProperty(0);
    private IntegerProperty  numberOfChangeLayout = new SimpleIntegerProperty(0);
    private IntegerProperty  numberOfIncurred = new SimpleIntegerProperty(0);
    private IntegerProperty  numberOfDismantling = new SimpleIntegerProperty(0);
    private IntegerProperty  numberOfRepair = new SimpleIntegerProperty(0);
    private IntegerProperty total = new SimpleIntegerProperty(0);

    public IntegratedStatistic() {
    }

    public IntegratedStatistic(StringProperty year, StringProperty month, IntegerProperty numberOfReNew, IntegerProperty numberOfChangeLayout, IntegerProperty numberOfIncurred, IntegerProperty numberOfDismantling, IntegerProperty numberOfRepair, IntegerProperty total) {
        this.year = year;
        this.month = month;
        this.numberOfReNew = numberOfReNew;
        this.numberOfChangeLayout = numberOfChangeLayout;
        this.numberOfIncurred = numberOfIncurred;
        this.numberOfDismantling = numberOfDismantling;
        this.numberOfRepair = numberOfRepair;
        this.total = total;
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getMonth() {
        return month.get();
    }

    public StringProperty monthProperty() {
        return month;
    }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public int getNumberOfReNew() {
        return numberOfReNew.get();
    }

    public IntegerProperty numberOfReNewProperty() {
        return numberOfReNew;
    }

    public void setNumberOfReNew(int numberOfReNew) {
        this.numberOfReNew.set(numberOfReNew);
    }

    public int getNumberOfChangeLayout() {
        return numberOfChangeLayout.get();
    }

    public IntegerProperty numberOfChangeLayoutProperty() {
        return numberOfChangeLayout;
    }

    public void setNumberOfChangeLayout(int numberOfChangeLayout) {
        this.numberOfChangeLayout.set(numberOfChangeLayout);
    }

    public int getNumberOfIncurred() {
        return numberOfIncurred.get();
    }

    public IntegerProperty numberOfIncurredProperty() {
        return numberOfIncurred;
    }

    public void setNumberOfIncurred(int numberOfIncurred) {
        this.numberOfIncurred.set(numberOfIncurred);
    }

    public int getNumberOfDismantling() {
        return numberOfDismantling.get();
    }

    public IntegerProperty numberOfDismantlingProperty() {
        return numberOfDismantling;
    }

    public void setNumberOfDismantling(int numberOfDismantling) {
        this.numberOfDismantling.set(numberOfDismantling);
    }

    public int getNumberOfRepair() {
        return numberOfRepair.get();
    }

    public IntegerProperty numberOfRepairProperty() {
        return numberOfRepair;
    }

    public void setNumberOfRepair(int numberOfRepair) {
        this.numberOfRepair.set(numberOfRepair);
    }

    public int getTotal() {
        return total.get();
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    public void setTotal(int total) {
        this.total.set(total);
    }
}
