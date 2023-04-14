package com.example.TimeTable;

import javafx.collections.FXCollections;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;


import javafx.scene.control.*;


public class SchedulesController {

    @FXML
    private TextField sbjCodeBox;
    @FXML
    private ComboBox<String> dateBox;
    @FXML
    private ComboBox<String> timeBox;
    @FXML
    private TextField lecBox;
    @FXML
    private TextField studentBox;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private TableView<Schedules> table;
    private TableColumn <Schedules,String> tid;
    private TableColumn <Schedules,String> tsubject;
    private TableColumn <Schedules,String> tdate;
    private TableColumn <Schedules,String> ttime;
    private TableColumn <Schedules,String> tlecturer;
    private TableColumn <Schedules,String> tstudents;


    public void initialize() {
        ObservableList<String> options1 =
                FXCollections.observableArrayList(
                        "Monday",
                        "Tuesday",
                        "Wednsday",
                        "Thursday",
                        "Friday"
                );
        dateBox.setItems(options1);

        ObservableList<String> options2 =
                FXCollections.observableArrayList(
                        "8.00AM - 11.00AM",
                        "11.15AM - 02.15AM"
                );
        timeBox.setItems(options2);

        table();


    }

    @FXML
    public void Add(ActionEvent event) {
        String subject, date, time, lecturer, students;
        subject = sbjCodeBox.getText();
        date = dateBox.getValue();
        time = timeBox.getValue();
        lecturer = lecBox.getText();
        students = studentBox.getText();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();
            String insertQuery = "INSERT INTO schedules_table (subject, date, time, lecturer, no_students) VALUES ('" +
                    subject + "', '" + date + "', '" + time + "', '" + lecturer + "', '" + students + "')";
            statement.executeUpdate(insertQuery);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Schedule Added");
            alert.setHeaderText(null);
            alert.setContentText("Schedule has been added successfully!");
            alert.showAndWait();

            table();

            sbjCodeBox.setText("");
            studentBox.setText("");
            lecBox.setText("");
        }
     catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while adding schedule! Please try again.");
        alert.showAndWait();
        e.printStackTrace();
    }
}
    @FXML
    public void table() {

        ObservableList<Schedules> schedules = FXCollections.observableArrayList();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String query = "SELECT * FROM schedules_table";
            PreparedStatement statement = connectDB.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Schedules schedule = new Schedules();
                schedule.setId(rs.getString("id"));
                schedule.setSubject(rs.getString("subject"));
                schedule.setDate(rs.getString("date"));
                schedule.setTime(rs.getString("time"));
                schedule.setLecturer(rs.getString("lecturer"));
                schedule.setNoStudents(rs.getString("no_students"));
                schedules.add(schedule);
            }
            table.setItems(schedules);
            tid.setCellValueFactory(f -> f.getValue().idProperty());
            tsubject.setCellValueFactory(f -> f.getValue().subjectProperty());
            tdate.setCellValueFactory(f -> f.getValue().dateProperty());
            ttime.setCellValueFactory(f -> f.getValue().timeProperty());
            tlecturer.setCellValueFactory(f -> f.getValue().lecturerProperty());
            tstudents.setCellValueFactory(f -> f.getValue().noStudentsProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setRowFactory(tv -> {
            TableRow<Schedules> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = table.getSelectionModel().getSelectedIndex();
                    sbjCodeBox.setText(table.getItems().get(myIndex).getSubject());
                    dateBox.setValue(table.getItems().get(myIndex).getDate());
                    timeBox.setValue(table.getItems().get(myIndex).getTime());
                    lecBox.setText(table.getItems().get(myIndex).getLecturer());
                    studentBox.setText(table.getItems().get(myIndex).getNoStudents());
                }
            });
            return myRow;
        });
    }


    @FXML
    void Delete(ActionEvent event) {
        int myIndex = table.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst = connectDB.prepareStatement("delete from schedules_table where id = ?");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Student Registration");
            alert.setHeaderText("Student Registration");
            alert.setContentText("Deleted!");
            alert.showAndWait();
            table();
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Update(ActionEvent event) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            int myIndex = table.getSelectionModel().getSelectedIndex();
            int id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
            String subject = sbjCodeBox.getText();
            String date = dateBox.getValue();
            String time = timeBox.getValue();
            String lecturer = lecBox.getText();
            String students = studentBox.getText();

            String query = "UPDATE schedules_table SET subject = ?, date = ?, time = ?, lecturer = ?, no_students = ? WHERE id = ?";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, subject);
            statement.setString(2, date);
            statement.setString(3, time);
            statement.setString(4, lecturer);
            statement.setString(5, students);
            statement.setInt(6, id);
            statement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Schedule Updated");
            alert.setHeaderText(null);
            alert.setContentText("Schedule has been updated successfully!");
            alert.showAndWait();

            table();
            sbjCodeBox.setText("");
            studentBox.setText("");
            lecBox.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating schedule! Please try again.");
            alert.showAndWait();
        }
    }



}
