package org.openjfx.models;

import javafx.beans.property.*;
import org.bson.types.ObjectId;
import java.util.Objects;
import java.util.UUID;

import java.util.Date;

public class Bus {

    private ObjectId id;
    //uuid
    private StringProperty uuid = new SimpleStringProperty(UUID.randomUUID().toString());
    //xí nghiệp
    private StringProperty enterprise = new SimpleStringProperty("");
    //nơi đậu xe
    private StringProperty parkingLot = new SimpleStringProperty("");
    //tuyến
    private IntegerProperty routeNumber = new SimpleIntegerProperty(0);
    //chủng loại xe
    private StringProperty vehicleCategory = new SimpleStringProperty("");
    //hiện trạng
    private StringProperty status = new SimpleStringProperty("");
    //biển kiểm soát
    private StringProperty numberPlate = new SimpleStringProperty("");
    //ngày thi công
    private ObjectProperty<Date> dateOfConstruction = new SimpleObjectProperty<>(new Date());
    //mã hàng
    private StringProperty jobCode = new SimpleStringProperty("");
    //agency
    private StringProperty agency = new SimpleStringProperty("");
    //nhãn hàng
    private StringProperty brand = new SimpleStringProperty("");
    //slogan
    private StringProperty slogan = new SimpleStringProperty("");
    //thời hạn hợp đồng
    private IntegerProperty duration = new SimpleIntegerProperty(0);
    //đội thi công
    private StringProperty constructionTeam = new SimpleStringProperty("");
    //in - quy cách
    private StringProperty printAndSupplier = new SimpleStringProperty("");
    //mô tả
    private StringProperty description = new SimpleStringProperty("");
    //cs
    private StringProperty cs = new SimpleStringProperty("");
    //ngày kết thúc
    private ObjectProperty<Date> startDay = new SimpleObjectProperty<>(new Date());
    //ngày kết thúc
    private ObjectProperty<Date> endDay = new SimpleObjectProperty<>(new Date());
    //thời hạn
    private IntegerProperty remainingDay = new SimpleIntegerProperty(0);
    //hợp đồng
    private BooleanProperty contract = new SimpleBooleanProperty(true);
    //note
    private StringProperty note = new SimpleStringProperty("");

    public Bus() {
    }

    public Bus(ObjectId id, StringProperty uuid, StringProperty enterprise, StringProperty parkingLot, IntegerProperty routeNumber, StringProperty vehicleCategory, StringProperty status, StringProperty numberPlate, ObjectProperty<Date> dateOfConstruction, StringProperty jobCode, StringProperty agency, StringProperty brand, StringProperty slogan, IntegerProperty duration, StringProperty constructionTeam, StringProperty printAndSupplier, StringProperty description, StringProperty cs, ObjectProperty<Date> startDay, ObjectProperty<Date> endDay, IntegerProperty remainingDay, BooleanProperty contract, StringProperty note) {
        this.id = id;
        this.uuid = uuid;
        this.enterprise = enterprise;
        this.parkingLot = parkingLot;
        this.routeNumber = routeNumber;
        this.vehicleCategory = vehicleCategory;
        this.status = status;
        this.numberPlate = numberPlate;
        this.dateOfConstruction = dateOfConstruction;
        this.jobCode = jobCode;
        this.agency = agency;
        this.brand = brand;
        this.slogan = slogan;
        this.duration = duration;
        this.constructionTeam = constructionTeam;
        this.printAndSupplier = printAndSupplier;
        this.description = description;
        this.cs = cs;
        this.startDay = startDay;
        this.endDay = endDay;
        this.remainingDay = remainingDay;
        this.contract = contract;
        this.note = note;
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

    public String getEnterprise() {
        return enterprise.get();
    }

    public StringProperty enterpriseProperty() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise.set(enterprise);
    }

    public String getParkingLot() {
        return parkingLot.get();
    }

    public StringProperty parkingLotProperty() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot.set(parkingLot);
    }

    public Integer getRouteNumber() {
        return routeNumber.get();
    }

    public IntegerProperty routeNumberProperty() {
        return routeNumber;
    }

    public void setRouteNumber(Integer routeNumber) {
        this.routeNumber.set(routeNumber);
    }

    public String getVehicleCategory() {
        return vehicleCategory.get();
    }

    public StringProperty vehicleCategoryProperty() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory.set(vehicleCategory);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
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

    public Date getDateOfConstruction() {
        return dateOfConstruction.get();
    }

    public ObjectProperty<Date> dateOfConstructionProperty() {
        return dateOfConstruction;
    }

    public void setDateOfConstruction(Date dateOfConstruction) {
        this.dateOfConstruction.set(dateOfConstruction);
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

    public String getAgency() {
        return agency.get();
    }

    public StringProperty agencyProperty() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency.set(agency);
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

    public String getSlogan() {
        return slogan.get();
    }

    public StringProperty sloganProperty() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan.set(slogan);
    }

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public String getConstructionTeam() {
        return constructionTeam.get();
    }

    public StringProperty constructionTeamProperty() {
        return constructionTeam;
    }

    public void setConstructionTeam(String constructionTeam) {
        this.constructionTeam.set(constructionTeam);
    }

    public String getPrintAndSupplier() {
        return printAndSupplier.get();
    }

    public StringProperty printAndSupplierProperty() {
        return printAndSupplier;
    }

    public void setPrintAndSupplier(String printAndSupplier) {
        this.printAndSupplier.set(printAndSupplier);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCs() {
        return cs.get();
    }

    public StringProperty csProperty() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs.set(cs);
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

    public int getRemainingDay() {
        return remainingDay.get();
    }

    public IntegerProperty remainingDayProperty() {
        return remainingDay;
    }

    public void setRemainingDay(int remainingDay) {
        this.remainingDay.set(remainingDay);
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

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", enterprise=" + enterprise +
                ", parkingLot=" + parkingLot +
                ", routeNumber=" + routeNumber +
                ", vehicleCategory=" + vehicleCategory +
                ", status=" + status +
                ", numberPlate=" + numberPlate +
                ", dateOfConstruction=" + dateOfConstruction +
                ", jobCode=" + jobCode +
                ", agency=" + agency +
                ", brand=" + brand +
                ", slogan=" + slogan +
                ", duration=" + duration +
                ", constructionTeam=" + constructionTeam +
                ", printAndSupplier=" + printAndSupplier +
                ", description=" + description +
                ", cs=" + cs +
                ", startDay=" + startDay +
                ", endDay=" + endDay +
                ", remainingDay=" + remainingDay +
                ", contract=" + contract +
                ", note=" + note +
                '}';
    }
}
