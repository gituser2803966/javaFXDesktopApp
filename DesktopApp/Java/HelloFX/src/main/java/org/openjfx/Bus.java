package org.openjfx;

import javafx.beans.property.*;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import java.util.Date;

public class Bus {

    private ObjectId id;

    private StringProperty uuid = new SimpleStringProperty(UUID.randomUUID().toString());
    //job code
    private StringProperty jobCode = new SimpleStringProperty("");
    //nhan hang
    private StringProperty brand = new SimpleStringProperty("");
    //ma so tuyen
    private StringProperty routeNumber = new SimpleStringProperty("");
    //ten tuyen
    private StringProperty routeName = new SimpleStringProperty("");
    //bien so xe
    private StringProperty numberPlate = new SimpleStringProperty("");
    //hop dong
    private BooleanProperty contract = new SimpleBooleanProperty(true);
    // ngay bat dau
    private ObjectProperty<Date> startDay = new SimpleObjectProperty<>(new Date());
    // ngay ket thuc
    private ObjectProperty<Date> endDay = new SimpleObjectProperty<>(new Date());

    private IntegerProperty remainingDay = new SimpleIntegerProperty(0);
    //ghi chu
    private StringProperty note = new SimpleStringProperty("");

    public Bus() {
    }

    public int getRemainingDay() {
        return remainingDay.get();
    }

    public IntegerProperty remainingDayProperty() {
        return remainingDay;
    }

    public void setRemainingDay(int remainingDay) {
        this.remainingDay.set(remainingDay);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid.get();
    }

    public StringProperty uuidProperty() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid.set(uuid);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return Objects.equals(id, bus.id) && Objects.equals(uuid, bus.uuid) && Objects.equals(jobCode, bus.jobCode) && Objects.equals(brand, bus.brand) && Objects.equals(routeNumber, bus.routeNumber) && Objects.equals(routeName, bus.routeName) && Objects.equals(numberPlate, bus.numberPlate) && Objects.equals(contract, bus.contract) && Objects.equals(startDay, bus.startDay) && Objects.equals(endDay, bus.endDay) && Objects.equals(remainingDay, bus.remainingDay) && Objects.equals(note, bus.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, jobCode, brand, routeNumber, routeName, numberPlate, contract, startDay, endDay, remainingDay, note);
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




    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public Bus(StringProperty uuid, StringProperty jobCode, StringProperty brand, StringProperty routeNumber, StringProperty routeName, StringProperty numberPlate, BooleanProperty contract, ObjectProperty<Date> startDay, ObjectProperty<Date> endDay, IntegerProperty remainingDay, StringProperty note) {
        this.uuid = uuid;
        this.jobCode = jobCode;
        this.brand = brand;
        this.routeNumber = routeNumber;
        this.routeName = routeName;
        this.numberPlate = numberPlate;
        this.contract = contract;
        this.startDay = startDay;
        this.endDay = endDay;
        this.remainingDay = remainingDay;
        this.note = note;
    }
}
