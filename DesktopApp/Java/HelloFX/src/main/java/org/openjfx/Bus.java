package org.openjfx;

import javafx.beans.property.*;
import org.bson.types.ObjectId;

import java.util.Date;

public class Bus {

    private ObjectId id;
    //job code
    private StringProperty jobCode = new SimpleStringProperty("jobCode");
    //nhan hang
    private StringProperty brand = new SimpleStringProperty("brand");
    //ma so tuyen
    private StringProperty routeNumber = new SimpleStringProperty("route number");
    //ten tuyen
    private StringProperty routeName = new SimpleStringProperty("route name");
    //bien so xe
    private StringProperty numberPlate = new SimpleStringProperty("number plate");
    //hop dong
    private BooleanProperty contract = new SimpleBooleanProperty(true);
    // ngay bat dau
    private ObjectProperty<Date> startDay = new SimpleObjectProperty<>(new Date());
    // ngay ket thuc
    private ObjectProperty<Date> endDay = new SimpleObjectProperty<>(new Date());
    //ghi chu
    private StringProperty note = new SimpleStringProperty("note");


    public Bus() {
    }

    public Bus(ObjectId id, StringProperty jobCode, StringProperty brand, StringProperty routeNumber, StringProperty routeName, StringProperty numberPlate, BooleanProperty contract, ObjectProperty<Date> startDay, ObjectProperty<Date> endDay, StringProperty note) {
        this.id = id;
        this.jobCode = jobCode;
        this.brand = brand;
        this.routeNumber = routeNumber;
        this.routeName = routeName;
        this.numberPlate = numberPlate;
        this.contract = contract;
        this.startDay = startDay;
        this.endDay = endDay;
        this.note = note;
    }

    public Date getStartDay() {
        return startDay.get();
    }

    public ObjectProperty<Date> startDayProperty() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay.set(startDay);
    }

    public Date getEndDay() {
        return endDay.get();
    }

    public ObjectProperty<Date> endDayProperty() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay.set(endDay);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getJobCode() {
        return jobCode.get();
    }

    public StringProperty jobCodeProperty() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode.set(jobCode);
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public String getRouteNumber() {
        return routeNumber.get();
    }

    public StringProperty routeNumberProperty() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber.set(routeNumber);
    }

    public String getRouteName() {
        return routeName.get();
    }

    public StringProperty routeNameProperty() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName.set(routeName);
    }

    public String getNumberPlate() {
        return numberPlate.get();
    }

    public StringProperty numberPlateProperty() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate.set(numberPlate);
    }

    public boolean isContract() {
        return contract.get();
    }

    public BooleanProperty contractProperty() {
        return contract;
    }

    public void setContract(boolean contract) {
        this.contract.set(contract);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }
}
