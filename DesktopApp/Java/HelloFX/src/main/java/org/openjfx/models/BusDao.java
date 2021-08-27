package org.openjfx.models;

import javafx.collections.ObservableList;
import org.bson.conversions.Bson;

public interface BusDao {
    public void add(Bus bus);
    public void delete(int id);
    public Bus getBus(int id);
    public ObservableList<Bus> getBusList();
    public long update(Bson filter, Bson value);
}
