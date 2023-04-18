package com.example.TimeTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Resources {
    private final StringProperty cname;
    private final StringProperty whiteboards;
    private final StringProperty projectors;

    public Resources() {
        cname = new SimpleStringProperty(this, "cname");
        whiteboards = new SimpleStringProperty(this, "whiteboards");
        projectors = new SimpleStringProperty(this, "projectors");
    }

    public StringProperty cnameProperty() {
        return cname;
    }

    public String getCname() {
        return cname.get();
    }

    public void setCname(String newCname) {
        cname.set(newCname);
    }

    public StringProperty whiteboardsProperty() {
        return whiteboards;
    }

    public String getWhiteboards() {
        return whiteboards.get();
    }

    public void setWhiteboards(String newWhiteboards) {
        whiteboards.set(newWhiteboards);
    }

    public StringProperty projectorsProperty() {
        return projectors;
    }

    public String getProjectors() {
        return projectors.get();
    }

    public void setProjectors(String newProjectors) {
        projectors.set(newProjectors);
    }
}
