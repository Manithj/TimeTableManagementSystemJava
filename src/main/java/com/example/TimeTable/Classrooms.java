package com.example.TimeTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Classrooms {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty capacity;
    private final StringProperty whiteboards;
    private final StringProperty projectors;
    private final StringProperty assigned_class;
    private final StringProperty course;

    public Classrooms(){
        id = new SimpleStringProperty(this,"id");
        name = new SimpleStringProperty(this,"name");
        capacity = new SimpleStringProperty(this,"capacity");
        whiteboards = new SimpleStringProperty(this,"whiteboards");
        projectors = new SimpleStringProperty(this,"projectors");
        assigned_class = new SimpleStringProperty(this,"assigned_class");
        course = new SimpleStringProperty(this,"course");
    }

    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }

    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String newName) { name.set(newName); }

    public StringProperty capacityProperty() { return capacity; }
    public String getCapacity() { return capacity.get(); }
    public void setCapacity(String newCapacity) { capacity.set(newCapacity); }

    public StringProperty whiteboardsProperty() { return whiteboards; }
    public String getWhiteboards() { return whiteboards.get(); }
    public void setWhiteboards(String newWhiteboards) { whiteboards.set(newWhiteboards); }

    public StringProperty projectorsProperty() { return projectors; }
    public String getProjectors() { return projectors.get(); }
    public void setProjectors(String newProjectors) { projectors.set(newProjectors); }

    public StringProperty assignedClassProperty() { return assigned_class; }
    public String getAssignedClass() { return assigned_class.get(); }
    public void setAssignedClass(String newAssignedClass) { assigned_class.set(newAssignedClass); }

    public StringProperty courseProperty() { return course; }
    public String getCourse() { return course.get(); }
    public void setCourse(String newCourse) { course.set(newCourse); }
}
