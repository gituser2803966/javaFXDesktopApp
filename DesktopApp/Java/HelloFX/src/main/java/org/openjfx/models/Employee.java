package org.openjfx.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.bson.types.ObjectId;

import java.util.Objects;

public  class Employee {

    private ObjectId id;
    private StringProperty firstName = new SimpleStringProperty("");
    private StringProperty lastName = new SimpleStringProperty("");
    private StringProperty photoURL =  new SimpleStringProperty("");
    private StringProperty username =  new SimpleStringProperty("");
    private StringProperty password =  new SimpleStringProperty("");
    private StringProperty department =  new SimpleStringProperty("");
    private StringProperty rule =  new SimpleStringProperty(ERule.EMPLOYEE.getName());
    private BooleanProperty active = new SimpleBooleanProperty(true);

    public Employee(){}

    public Employee(StringProperty firstName, StringProperty lastName, StringProperty photoURL, StringProperty username, StringProperty password, StringProperty department, StringProperty rule, BooleanProperty active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoURL = photoURL;
        this.username = username;
        this.password = password;
        this.department = department;
        this.rule = rule;
        this.active = active;
    }


    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhotoURL() {
        return photoURL.get();
    }

    public StringProperty photoURLProperty() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL.set(photoURL);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(photoURL, employee.photoURL) && Objects.equals(username, employee.username) && Objects.equals(password, employee.password) && Objects.equals(department, employee.department) && Objects.equals(rule, employee.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, photoURL, username, password, department, rule);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getDepartment() {
        return department.get();
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getRule() {
        return rule.get();
    }

    public StringProperty ruleProperty() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule.set(rule);
    }

    public boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

}
