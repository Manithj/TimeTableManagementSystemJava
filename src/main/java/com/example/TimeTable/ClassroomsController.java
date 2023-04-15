package com.example.TimeTable;

import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class ClassroomsController {

    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private TableView<Classrooms> table;
    @FXML
    private TableColumn <Classrooms,String> tid;
    @FXML
    private TableColumn <Classrooms,String> tname;
    @FXML
    private TableColumn <Classrooms,String> tcapacity;
    @FXML
    private TableColumn <Classrooms,String> twhiteboards;
    @FXML
    private TableColumn <Classrooms,String> tprojectors;
    @FXML
    private TableColumn <Classrooms,String> tclass;

    @FXML
    private TextField classroomBox;
    @FXML
    private TextField capacityBox;
    @FXML
    private TextField whiteboardBox;
    @FXML
    private TextField projectorsBox;
    @FXML
    private TextField classBox;

    @FXML
    void initialize() {
        table();
        table.setRowFactory( tv -> {
            TableRow<Classrooms> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    int myIndex =  table.getSelectionModel().getSelectedIndex();
                    classroomBox.setText(table.getItems().get(myIndex).getName());
                    capacityBox.setText(table.getItems().get(myIndex).getCapacity());
                    whiteboardBox.setText(table.getItems().get(myIndex).getWhiteboards());
                    projectorsBox.setText(table.getItems().get(myIndex).getProjectors());
                    classBox.setText(table.getItems().get(myIndex).getAssignedClass());
                }
            });
            return myRow;
        });

    }

    @FXML
    void Add(ActionEvent event) {
        String sclassroomBox, scapacityBox, swhiteboardBox, sprojectorsBox, sclassBox;
        sclassroomBox = classroomBox.getText();
        scapacityBox = capacityBox.getText();
        swhiteboardBox = whiteboardBox.getText();
        sprojectorsBox = projectorsBox.getText();
        sclassBox = classBox.getText();

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst = connectDB.prepareStatement("INSERT INTO classrooms_table(name,capacity,whiteboards,projectors,assigned_class) VALUES (?,?,?,?,?)");
            pst.setString(1, sclassroomBox);
            pst.setString(2, scapacityBox);
            pst.setString(3, swhiteboardBox);
            pst.setString(4, sprojectorsBox);
            pst.setString(5, sclassBox);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Classroom Registration");
            alert.setHeaderText("Classroom Registration");
            alert.setContentText("Record added successfully!");
            alert.showAndWait();

            table();

            classroomBox.setText("");
            capacityBox.setText("");
            whiteboardBox.setText("");
            projectorsBox.setText("");
            classBox.setText("");

            classroomBox.requestFocus();
            table();
        }

        catch (SQLException ex) {
            Logger.getLogger(ClassroomsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void table() {
        tname.setCellValueFactory(data -> data.getValue().nameProperty());
        tcapacity.setCellValueFactory(data -> data.getValue().capacityProperty());
        twhiteboards.setCellValueFactory(data -> data.getValue().whiteboardsProperty());
        tprojectors.setCellValueFactory(data -> data.getValue().projectorsProperty());
        tclass.setCellValueFactory(data -> data.getValue().assignedClassProperty());

        ObservableList<Classrooms> observableList = FXCollections.observableArrayList();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM classrooms_table");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Classrooms classrooms = new Classrooms();
                classrooms.setId(String.valueOf(rs.getInt("id")));
                classrooms.setName(rs.getString("name"));
                classrooms.setCapacity(rs.getString("capacity"));
                classrooms.setWhiteboards(rs.getString("whiteboards"));
                classrooms.setProjectors(rs.getString("projectors"));
                classrooms.setAssignedClass(rs.getString("assigned_class"));
                observableList.add(classrooms);
            }

            table.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(ClassroomsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Update(ActionEvent event) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String name = classroomBox.getText();
            String capacity = capacityBox.getText();
            String whiteboards = whiteboardBox.getText();
            String projectors = projectorsBox.getText();
            String assignedClass = classBox.getText();

            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM classrooms_table");

            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id");
            }

            PreparedStatement pst = connectDB.prepareStatement("UPDATE classrooms_table SET name=?, capacity=?, whiteboards=?, projectors=?, assigned_class=? WHERE id=?");

            pst.setString(1, name);
            pst.setString(2, capacity);
            pst.setString(3, whiteboards);
            pst.setString(4, projectors);
            pst.setString(5, assignedClass);
            pst.setInt(6, id);

            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Classroom Update");
            alert.setHeaderText("Classroom Update");
            alert.setContentText("Record updated successfully!");
            alert.showAndWait();

            classroomBox.setText("");
            capacityBox.setText("");
            whiteboardBox.setText("");
            projectorsBox.setText("");
            classBox.setText("");

            classroomBox.requestFocus();

            table();
        } catch (SQLException ex) {
            Logger.getLogger(ClassroomsController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }


    @FXML
    void Delete(ActionEvent event) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String name = table.getSelectionModel().getSelectedItem().getName();

            PreparedStatement pst = connectDB.prepareStatement("DELETE FROM classrooms_table WHERE name=?");
            pst.setString(1, name);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Classroom Deletion");
            alert.setHeaderText("Classroom Deletion");
            alert.setContentText("Record deleted successfully!");
            alert.showAndWait();
            classroomBox.setText("");
            capacityBox.setText("");
            whiteboardBox.setText("");
            projectorsBox.setText("");
            classBox.setText("");

            classroomBox.requestFocus();

            table();
        } catch (SQLException ex) {
            Logger.getLogger(ClassroomsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage dashboard = new Stage();
        dashboard.setScene(new Scene(root));
        dashboard.show();

        // Close the current window
        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();
    }





}
