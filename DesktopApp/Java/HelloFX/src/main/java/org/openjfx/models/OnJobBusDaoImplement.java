package org.openjfx.models;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.conversions.Bson;
import org.openjfx.service.MongoDBConnection;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class OnJobBusDaoImplement implements OnJobBusDao {

    MongoDatabase mgDB = MongoDBConnection.getConnection();
    MongoCollection<OnJobBus> onJobBusCollection = mgDB.getCollection("onJobBusList", OnJobBus.class);

    @Override
    public void add(OnJobBus bus) {
        System.out.println("start insert new on job bus");
        onJobBusCollection.insertOne(bus);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public OnJobBusDao getBus(int id) {
        return null;
    }

    @Override
    public ObservableList<OnJobBus> getOnJobBusList() {

        onJobBusCollection.createIndex(Indexes.ascending("onJobRouteNumber"));
        onJobBusCollection.createIndex(Indexes.ascending("onJobRouteNumber", "onJobNumberPlate"));

//        return onJobBusCollection.find().sort(Sorts.ascending("routeNumber", "numberPlate"))
//                .into(FXCollections.observableArrayList());

        return onJobBusCollection.find()
                .into(FXCollections.observableArrayList());
    }

    @Override
    public long update(Bson filter, Bson value) {
        UpdateResult result = onJobBusCollection.updateOne(filter, value);
        System.out.println("start update ********");
        return result.getModifiedCount();
    }

    @Override
    public void addNewFieldToDocument(String fieldName) {

        onJobBusCollection.find().forEach(el->{
//            System.out.println(el.getOnJobRouteNumber());
            onJobBusCollection.updateOne(eq("_id",el.getId()),set(fieldName,new Date()));

        });

        System.out.println("update new field to all document successfully");

//        UpdateResult result = onJobBusCollection.updateOne(filter, value);
//        System.out.println("UpdateResult: "+result);
//        System.out.println("add new field successfully");
        return;
    }
}
