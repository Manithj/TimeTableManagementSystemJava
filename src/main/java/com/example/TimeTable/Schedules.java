package com.example.TimeTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Schedules {
    private final StringProperty id;
    private final StringProperty subject;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty lecturer;
    private final StringProperty no_students;

    public Schedules() {
        id = new SimpleStringProperty(this, "id");
        subject = new SimpleStringProperty(this, "subject");
        date = new SimpleStringProperty(this, "date");
        time = new SimpleStringProperty(this, "time");
        lecturer = new SimpleStringProperty(this, "lecturer");
        no_students = new SimpleStringProperty(this, "no_students");
    }


    public StringProperty idProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String newId) {
        id.set(newId);
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String newSubject) {
        subject.set(newSubject);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String newDate) {
        date.set(newDate);
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String newTime) {
        time.set(newTime);
    }

    public StringProperty lecturerProperty() {
        return lecturer;
    }

    public String getLecturer() {
        return lecturer.get();
    }

    public void setLecturer(String newLecturer) {
        lecturer.set(newLecturer);
    }

    public StringProperty noStudentsProperty() {
        return no_students;
    }

    public String getNoStudents() {
        return no_students.get();
    }

    public void setNoStudents(String newNoStudents) {
        no_students.set(newNoStudents);
    }
}
