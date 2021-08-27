package org.openjfx.models;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.service.MongoDBConnection;

import static com.mongodb.client.model.Filters.eq;

public class EmployeeDaoImplementation implements EmployeeDAO {

    static MongoDatabase mgDB = MongoDBConnection.getConnection();
    MongoCollection<Employee> empCollection = mgDB.getCollection("employees", Employee.class);

    @Override
    public void add(Employee emp) {
        empCollection.insertOne(emp);
    }

    @Override
    public void delete(int id) {
//        DeleteResult deleteResult = collection.deleteMany(eq("address.city", "London"));
//        System.out.println(deleteResult.getDeletedCount());
    }

    @Override
    public Employee getEmployee(String username) {
        return null;
    }

    @Override
    public boolean findEmployeeWithUsername(String username) {
        FindIterable<Employee> result = empCollection.find(eq("username", username));
        if (result.cursor().hasNext()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ObservableList<Employee> getEmployees() {

        empCollection.createIndex(Indexes.ascending("username"));
        empCollection.createIndex(Indexes.ascending("firstName", "lastName"));

        return empCollection.find().sort(Sorts.ascending("username"))
                .into(FXCollections.observableArrayList());

    }

    @Override
    public void update(Employee emp) {

    }
}
