package org.openjfx.models;

import javafx.collections.ObservableList;

public interface EmployeeDAO {
    public void add(Employee emp);
    public void delete(int id);
    public Employee getEmployee(String username);
    public boolean findEmployeeWithUsername(String username);
    public ObservableList<Employee> getEmployees();
    public void update(Employee emp);
}
