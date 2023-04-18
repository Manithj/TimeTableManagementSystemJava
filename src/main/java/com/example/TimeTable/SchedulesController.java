package com.example.TimeTable;

import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;


import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;


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
    @FXML
    private TableColumn <Schedules,String> tsubject;
    @FXML
    private TableColumn <Schedules,String> tdate;
    @FXML
    private TableColumn <Schedules,String> ttime;
    @FXML
    private TableColumn <Schedules,String> tlecturer;
    @FXML
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




        table();


    }

    @FXML
    private void table() {
        tsubject.setCellValueFactory(data -> data.getValue().subjectProperty());
        tdate.setCellValueFactory(data -> data.getValue().dateProperty());
        ttime.setCellValueFactory(data -> data.getValue().timeProperty());
        tlecturer.setCellValueFactory(data -> data.getValue().lecturerProperty());
        tstudents.setCellValueFactory(data -> data.getValue().noStudentsProperty());

        ObservableList<Schedules> observableList = FXCollections.observableArrayList();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM schedules_table");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Schedules schedule = new Schedules();
                schedule.setSubject(rs.getString("subject"));
                schedule.setDate(rs.getString("date"));
                schedule.setTime(rs.getString("time"));
                schedule.setLecturer(rs.getString("lecturer"));
                schedule.setNoStudents(rs.getString("no_students"));
                observableList.add(schedule);
            }
            table.setItems(observableList);

        }catch (SQLException ex) {
            Logger.getLogger(ClassroomsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Add(ActionEvent event) {
        String ssubjectBox, sdateBox, stimeBox, slectBox, sstudentsBox;
        ssubjectBox = sbjCodeBox.getText();
        sdateBox = dateBox.getValue();
        stimeBox = timeBox.getValue();
        slectBox = lecBox.getText();
        sstudentsBox = studentBox.getText();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst = connectDB.prepareStatement("INSERT INTO schedules_table(subject,date,time,lecturer,no_students) VALUES (?,?,?,?,?)");
            pst.setString(1, ssubjectBox);
            pst.setString(2, sdateBox);
            pst.setString(3, stimeBox);
            pst.setString(4, slectBox);
            pst.setString(5, sstudentsBox);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Schedule Registration");
            alert.setHeaderText("Schedule Registration");
            alert.setContentText("Record added successfully!");
            alert.showAndWait();

            table();

            sbjCodeBox.setText("");
            dateBox.setValue(null);
            timeBox.setValue(null);
            lecBox.setText("");
            studentBox.setText("");

            sbjCodeBox.requestFocus();
            table();
        }

        catch (SQLException ex) {
            Logger.getLogger(SchedulesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Update(ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

            String ssubjectBox, sdateBox, stimeBox, slectBox, sstudentsBox;
            ssubjectBox = sbjCodeBox.getText();
            sdateBox = dateBox.getValue();
            stimeBox = timeBox.getValue();
            slectBox = lecBox.getText();
            sstudentsBox = studentBox.getText();

            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM schedules_table");

            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id");
            }
            try {

                PreparedStatement pst = connectDB.prepareStatement("UPDATE schedules_table SET subject = ?, date = ?, time = ?, lecturer = ?, no_students = ? WHERE id = ?");
                pst.setString(1, ssubjectBox);
                pst.setString(2, sdateBox);
                pst.setString(3, stimeBox);
                pst.setString(4, slectBox);
                pst.setString(5, sstudentsBox);
                pst.setInt(6, id);
                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Schedule Update");
                alert.setHeaderText("Schedule Update");
                alert.setContentText("Record updated successfully!");
                alert.showAndWait();

                table();

                sbjCodeBox.setText("");
                dateBox.setValue(null);
                timeBox.setValue(null);
                lecBox.setText("");
                studentBox.setText("");

                sbjCodeBox.requestFocus();
                table();
            }

            catch (SQLException ex) {
                Logger.getLogger(SchedulesController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    @FXML
    void Delete(ActionEvent event) {

            try {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();

                String subject= table.getSelectionModel().getSelectedItem().getSubject();
                PreparedStatement pst = connectDB.prepareStatement("DELETE FROM schedules_table WHERE subject = ?");
                pst.setString(1, subject);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Schedule Deletion");
                alert.setHeaderText("Schedule Deletion");
                alert.setContentText("Record deleted successfully!");
                alert.showAndWait();

                table();
            }

            catch (SQLException ex) {
                Logger.getLogger(SchedulesController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    @FXML
    public void Exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage dashboard = new Stage();
        dashboard.setScene(new Scene(root));
        dashboard.setTitle("Dashboard");
        dashboard.getIcons().add(new Image("file:src/img/classroom.png"));
        dashboard.show();

        // Close the current window
        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();
    }


}


