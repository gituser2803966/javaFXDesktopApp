package org.openjfx.models;

import javafx.collections.ObservableList;
import org.bson.conversions.Bson;

public interface BusListDao {
    public void add(BusList busList);
    public void delete(int id);
    public BusList getBus(int id);
    public ObservableList<BusList> getBusList();
    public long update(Bson filter, Bson value);
    public long count();
}
