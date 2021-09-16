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

public class BusListDaoImplementation implements BusListDao {

    MongoDatabase mgDB = MongoDBConnection.getConnection();
    MongoCollection<BusList> busCollection = mgDB.getCollection("busList", BusList.class);

    @Override
    public void add(BusList busList) {
        System.out.println("start insert new bus ********");
        busCollection.insertOne(busList);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public BusList getBus(int id) {
        return null;
    }

    @Override
    public ObservableList<BusList> getBusList() {

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

    @Override
    public long count() {
        return busCollection.countDocuments();
    }

    public DistinctIterable<String> getOnlyRoute(){
        DistinctIterable<String> iterable = (DistinctIterable<String>) mgDB.getCollection("bus", BusList.class).distinct("routeNumber", String.class);
        return iterable;
    }
}
