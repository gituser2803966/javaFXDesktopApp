package org.openjfx.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Statistic {
    private IntegerProperty year = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfJan = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfFeb = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfMar = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfApr = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfMay = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfJun = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfJul = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfAug = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfSep = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfOct = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfNov = new SimpleIntegerProperty(0);
    private IntegerProperty numberOfDec = new SimpleIntegerProperty(0);

    public Statistic() {
    }

    public Statistic(IntegerProperty year, IntegerProperty numberOfJan, IntegerProperty numberOfFeb, IntegerProperty numberOfMar, IntegerProperty numberOfApr, IntegerProperty numberOfMay, IntegerProperty numberOfJun, IntegerProperty numberOfJul, IntegerProperty numberOfAug, IntegerProperty numberOfSep, IntegerProperty numberOfOct, IntegerProperty numberOfNov, IntegerProperty numberOfDec) {
        this.year = year;
        this.numberOfJan = numberOfJan;
        this.numberOfFeb = numberOfFeb;
        this.numberOfMar = numberOfMar;
        this.numberOfApr = numberOfApr;
        this.numberOfMay = numberOfMay;
        this.numberOfJun = numberOfJun;
        this.numberOfJul = numberOfJul;
        this.numberOfAug = numberOfAug;
        this.numberOfSep = numberOfSep;
        this.numberOfOct = numberOfOct;
        this.numberOfNov = numberOfNov;
        this.numberOfDec = numberOfDec;
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public int getNumberOfJan() {
        return numberOfJan.get();
    }

    public IntegerProperty numberOfJanProperty() {
        return numberOfJan;
    }

    public void setNumberOfJan(int numberOfJan) {
        this.numberOfJan.set(numberOfJan);
    }

    public int getNumberOfFeb() {
        return numberOfFeb.get();
    }

    public IntegerProperty numberOfFebProperty() {
        return numberOfFeb;
    }

    public void setNumberOfFeb(int numberOfFeb) {
        this.numberOfFeb.set(numberOfFeb);
    }

    public int getNumberOfMar() {
        return numberOfMar.get();
    }

    public IntegerProperty numberOfMarProperty() {
        return numberOfMar;
    }

    public void setNumberOfMar(int numberOfMar) {
        this.numberOfMar.set(numberOfMar);
    }

    public int getNumberOfApr() {
        return numberOfApr.get();
    }

    public IntegerProperty numberOfAprProperty() {
        return numberOfApr;
    }

    public void setNumberOfApr(int numberOfApr) {
        this.numberOfApr.set(numberOfApr);
    }

    public int getNumberOfMay() {
        return numberOfMay.get();
    }

    public IntegerProperty numberOfMayProperty() {
        return numberOfMay;
    }

    public void setNumberOfMay(int numberOfMay) {
        this.numberOfMay.set(numberOfMay);
    }

    public int getNumberOfJun() {
        return numberOfJun.get();
    }

    public IntegerProperty numberOfJunProperty() {
        return numberOfJun;
    }

    public void setNumberOfJun(int numberOfJun) {
        this.numberOfJun.set(numberOfJun);
    }

    public int getNumberOfJul() {
        return numberOfJul.get();
    }

    public IntegerProperty numberOfJulProperty() {
        return numberOfJul;
    }

    public void setNumberOfJul(int numberOfJul) {
        this.numberOfJul.set(numberOfJul);
    }

    public int getNumberOfAug() {
        return numberOfAug.get();
    }

    public IntegerProperty numberOfAugProperty() {
        return numberOfAug;
    }

    public void setNumberOfAug(int numberOfAug) {
        this.numberOfAug.set(numberOfAug);
    }

    public int getNumberOfSep() {
        return numberOfSep.get();
    }

    public IntegerProperty numberOfSepProperty() {
        return numberOfSep;
    }

    public void setNumberOfSep(int numberOfSep) {
        this.numberOfSep.set(numberOfSep);
    }

    public int getNumberOfOct() {
        return numberOfOct.get();
    }

    public IntegerProperty numberOfOctProperty() {
        return numberOfOct;
    }

    public void setNumberOfOct(int numberOfOct) {
        this.numberOfOct.set(numberOfOct);
    }

    public int getNumberOfNov() {
        return numberOfNov.get();
    }

    public IntegerProperty numberOfNovProperty() {
        return numberOfNov;
    }

    public void setNumberOfNov(int numberOfNov) {
        this.numberOfNov.set(numberOfNov);
    }

    public int getNumberOfDec() {
        return numberOfDec.get();
    }

    public IntegerProperty numberOfDecProperty() {
        return numberOfDec;
    }

    public void setNumberOfDec(int numberOfDec) {
        this.numberOfDec.set(numberOfDec);
    }
}
