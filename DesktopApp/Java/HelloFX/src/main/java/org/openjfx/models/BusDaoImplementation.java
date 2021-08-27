package org.openjfx.models;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.conversions.Bson;
import org.openjfx.service.MongoDBConnection;

import static com.mongodb.client.model.Filters.eq;

public class BusDaoImplementation implements BusDao{

    MongoDatabase mgDB = MongoDBConnection.getConnection();
    MongoCollection<Bus> busCollection = mgDB.getCollection("busList", Bus.class);

    @Override
    public void add(Bus bus) {
        System.out.println("start insert new bus ********");
        busCollection.insertOne(bus);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Bus getBus(int id) {
        return null;
    }

    @Override
    public ObservableList<Bus> getBusList() {

        busCollection.createIndex(Indexes.ascending("routeNumber"));
        busCollection.createIndex(Indexes.ascending("routeNumber", "numberPlate"));

        return busCollection.find().sort(Sorts.ascending("routeNumber", "numberPlate"))
                .into(FXCollections.observableArrayList());

//        return busCollection.find()
//                .into(FXCollections.observableArrayList());

    }

    @Override
    public long update(Bson filter, Bson value) {
        UpdateResult result = busCollection.updateOne(filter, value);
        System.out.println("start update ********");
        return  result.getModifiedCount();
    }

    public DistinctIterable<String> getOnlyRoute(){
        DistinctIterable<String> iterable = (DistinctIterable<String>) mgDB.getCollection("bus", Bus.class).distinct("routeNumber", String.class);
        return iterable;
    }
}
