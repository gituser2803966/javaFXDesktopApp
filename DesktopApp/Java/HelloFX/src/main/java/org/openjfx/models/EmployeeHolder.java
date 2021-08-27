package org.openjfx.models;

public final class EmployeeHolder {

    private Employee employee;
    private final static EmployeeHolder INSTANCE = new EmployeeHolder();

    private EmployeeHolder() {}

    public static EmployeeHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(Employee e) {
        this.employee = e;
    }

    public Employee getUser() {
        return this.employee;
    }
}
