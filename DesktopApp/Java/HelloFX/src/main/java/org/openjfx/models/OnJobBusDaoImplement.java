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
        onJobBusCollection.createIndex(Indexes.ascending("routeNumber"));
        onJobBusCollection.createIndex(Indexes.ascending("routeNumber", "numberPlate"));

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
}
