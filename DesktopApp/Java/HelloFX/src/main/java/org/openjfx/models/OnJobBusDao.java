package org.openjfx.models;

import javafx.collections.ObservableList;
import org.bson.conversions.Bson;

public interface OnJobBusDao {
    public void add(OnJobBus bus);
    public void delete(int id);
    public OnJobBusDao getBus(int id);
    public ObservableList<OnJobBus> getOnJobBusList();
    public long update(Bson filter, Bson value);
}
