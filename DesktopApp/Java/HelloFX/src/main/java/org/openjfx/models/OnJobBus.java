package org.openjfx.models;

import javafx.beans.property.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class OnJobBus {

    private ObjectId id;
    //uuid
    private StringProperty onJobUuid = new SimpleStringProperty(UUID.randomUUID().toString());
    //mã hàng
    private StringProperty onJobJobCode = new SimpleStringProperty("");
    //nhãn hàng
    private StringProperty onJobBrand = new SimpleStringProperty("");
    //slogan
    private StringProperty onJobSlogan = new SimpleStringProperty("");
    //biển kiểm soát
    private StringProperty onJobNumberPlate = new SimpleStringProperty("");
    //tuyến
    private IntegerProperty onJobRouteNumber = new SimpleIntegerProperty(0);
    //nội dung thi công //Dán mới //thay layout //Phát sinh //sữa chữa// tháo dỡ
    private StringProperty onJobConstructionContent = new SimpleStringProperty("");
    //ngày thi công
    private ObjectProperty<Date> onJobDateOfConstruction = new SimpleObjectProperty<>(new Date());
    //ngày hết hạn
    private ObjectProperty<Date> onJobEndDay = new SimpleObjectProperty<>(new Date());
    //chủng loại xe
    private StringProperty onJobVehicleCategory = new SimpleStringProperty("");
    //agency
    private StringProperty onJobAgency = new SimpleStringProperty("");
    //xí nghiệp
    private StringProperty onJobEnterprise = new SimpleStringProperty("");
    //cs
    private StringProperty onJobCs = new SimpleStringProperty("");

    public OnJobBus() {
    }

    public OnJobBus(StringProperty onJobUuid, StringProperty onJobJobCode, StringProperty onJobBrand, StringProperty onJobSlogan, StringProperty onJobNumberPlate, IntegerProperty onJobRouteNumber, StringProperty onJobConstructionContent, ObjectProperty<Date> onJobDateOfConstruction, ObjectProperty<Date> onJobEndDay, StringProperty onJobVehicleCategory, StringProperty onJobAgency, StringProperty onJobEnterprise, StringProperty onJobCs) {
        this.onJobUuid = onJobUuid;
        this.onJobJobCode = onJobJobCode;
        this.onJobBrand = onJobBrand;
        this.onJobSlogan = onJobSlogan;
        this.onJobNumberPlate = onJobNumberPlate;
        this.onJobRouteNumber = onJobRouteNumber;
        this.onJobConstructionContent = onJobConstructionContent;
        this.onJobDateOfConstruction = onJobDateOfConstruction;
        this.onJobEndDay = onJobEndDay;
        this.onJobVehicleCategory = onJobVehicleCategory;
        this.onJobAgency = onJobAgency;
        this.onJobEnterprise = onJobEnterprise;
        this.onJobCs = onJobCs;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getOnJobUuid() {
        return onJobUuid.get();
    }

    public StringProperty onJobUuidProperty() {
        return onJobUuid;
    }

    public void setOnJobUuid(String onJobUuid) {
        this.onJobUuid.set(onJobUuid);
    }

    public String getOnJobJobCode() {
        return onJobJobCode.get();
    }

    public StringProperty onJobJobCodeProperty() {
        return onJobJobCode;
    }

    public void setOnJobJobCode(String onJobJobCode) {
        this.onJobJobCode.set(onJobJobCode);
    }

    public String getOnJobBrand() {
        return onJobBrand.get();
    }

    public StringProperty onJobBrandProperty() {
        return onJobBrand;
    }

    public void setOnJobBrand(String onJobBrand) {
        this.onJobBrand.set(onJobBrand);
    }

    public String getOnJobSlogan() {
        return onJobSlogan.get();
    }

    public StringProperty onJobSloganProperty() {
        return onJobSlogan;
    }

    public void setOnJobSlogan(String onJobSlogan) {
        this.onJobSlogan.set(onJobSlogan);
    }

    public String getOnJobNumberPlate() {
        return onJobNumberPlate.get();
    }

    public StringProperty onJobNumberPlateProperty() {
        return onJobNumberPlate;
    }

    public void setOnJobNumberPlate(String onJobNumberPlate) {
        this.onJobNumberPlate.set(onJobNumberPlate);
    }

    public int getOnJobRouteNumber() {
        return onJobRouteNumber.get();
    }

    public IntegerProperty onJobRouteNumberProperty() {
        return onJobRouteNumber;
    }

    public void setOnJobRouteNumber(int onJobRouteNumber) {
        this.onJobRouteNumber.set(onJobRouteNumber);
    }

    public String getOnJobConstructionContent() {
        return onJobConstructionContent.get();
    }

    public StringProperty onJobConstructionContentProperty() {
        return onJobConstructionContent;
    }

    public void setOnJobConstructionContent(String onJobConstructionContent) {
        this.onJobConstructionContent.set(onJobConstructionContent);
    }

    public Date getOnJobDateOfConstruction() {
        return onJobDateOfConstruction.get();
    }

    public ObjectProperty<Date> onJobDateOfConstructionProperty() {
        return onJobDateOfConstruction;
    }

    public void setOnJobDateOfConstruction(Date onJobDateOfConstruction) {
        this.onJobDateOfConstruction.set(onJobDateOfConstruction);
    }

    public Date getOnJobEndDay() {
        return onJobEndDay.get();
    }

    public ObjectProperty<Date> onJobEndDayProperty() {
        return onJobEndDay;
    }

    public void setOnJobEndDay(Date onJobEndDay) {
        this.onJobEndDay.set(onJobEndDay);
    }

    public String getOnJobVehicleCategory() {
        return onJobVehicleCategory.get();
    }

    public StringProperty onJobVehicleCategoryProperty() {
        return onJobVehicleCategory;
    }

    public void setOnJobVehicleCategory(String onJobVehicleCategory) {
        this.onJobVehicleCategory.set(onJobVehicleCategory);
    }

    public String getOnJobAgency() {
        return onJobAgency.get();
    }

    public StringProperty onJobAgencyProperty() {
        return onJobAgency;
    }

    public void setOnJobAgency(String onJobAgency) {
        this.onJobAgency.set(onJobAgency);
    }

    public String getOnJobEnterprise() {
        return onJobEnterprise.get();
    }

    public StringProperty onJobEnterpriseProperty() {
        return onJobEnterprise;
    }

    public void setOnJobEnterprise(String onJobEnterprise) {
        this.onJobEnterprise.set(onJobEnterprise);
    }

    public String getOnJobCs() {
        return onJobCs.get();
    }

    public StringProperty onJobCsProperty() {
        return onJobCs;
    }

    public void setOnJobCs(String onJobCs) {
        this.onJobCs.set(onJobCs);
    }

    @Override
    public String toString() {
        return "OnJobBus{" +
                "id=" + id +
                ", onJobUuid=" + onJobUuid +
                ", onJobJobCode=" + onJobJobCode +
                ", onJobBrand=" + onJobBrand +
                ", onJobSlogan=" + onJobSlogan +
                ", onJobNumberPlate=" + onJobNumberPlate +
                ", onJobRouteNumber=" + onJobRouteNumber +
                ", onJobConstructionContent=" + onJobConstructionContent +
                ", onJobDateOfConstruction=" + onJobDateOfConstruction +
                ", onJobEndDay=" + onJobEndDay +
                ", onJobVehicleCategory=" + onJobVehicleCategory +
                ", onJobAgency=" + onJobAgency +
                ", onJobEnterprise=" + onJobEnterprise +
                ", onJobCs=" + onJobCs +
                '}';
    }
}
