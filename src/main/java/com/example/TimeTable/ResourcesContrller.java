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

public class ResourcesContrller {

    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private TableView<Resources> table;
    @FXML
    private TableColumn <Resources,String> tname;
    @FXML
    private TableColumn <Resources,String> twhiteboards;
    @FXML
    private TableColumn <Resources,String> tprojectors;

    private TextField classroomBox;
    private TextField whiteboardBox;
    private TextField projectorBox;

    public void initialize() {
        table();
        table.setRowFactory(tv -> {
            TableRow<Resources> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = table.getSelectionModel().getSelectedIndex();
                    Resources selectedResource = table.getItems().get(myIndex);
                    classroomBox.setText(selectedResource.getCname());
                    whiteboardBox.setText(selectedResource.getWhiteboards());
                    projectorBox.setText(selectedResource.getProjectors());
                }
            });
            return myRow;
        });
    }

    @FXML
    private void table() {
        tname.setCellValueFactory(data -> data.getValue().cnameProperty());
        twhiteboards.setCellValueFactory(data -> data.getValue().whiteboardsProperty());
        tprojectors.setCellValueFactory(data -> data.getValue().projectorsProperty());

        ObservableList<Resources> observableList = FXCollections.observableArrayList();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM resources_table");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Resources resource = new Resources();
                resource.setCname(rs.getString("cname"));
                resource.setWhiteboards(rs.getString("whiteboards"));
                resource.setProjectors(rs.getString("projectors"));
                observableList.add(resource);
            }
            table.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(ClassroomsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Add(ActionEvent event) {
        String cnameBox, whiteboardsBox, projectorsBox;
        cnameBox = classroomBox.getText();
        whiteboardsBox = whiteboardBox.getText();
        projectorsBox = projectorBox.getText();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst = connectDB.prepareStatement("INSERT INTO resources_table(cname,whiteboards,projectors) VALUES (?,?,?)");
            pst.setString(1, cnameBox);
            pst.setString(2, whiteboardsBox);
            pst.setString(3, projectorsBox);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resource Registration");
            alert.setHeaderText("Resource Registration");
            alert.setContentText("Record added successfully!");
            alert.showAndWait();

            table();

            classroomBox.setText("");
            whiteboardBox.setText("");
            projectorBox.setText("");

            classroomBox.requestFocus();
            table();
        }

        catch (SQLException ex) {
            Logger.getLogger(ResourcesContrller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Resources selectedResource = table.getSelectionModel().getSelectedItem();
        if (selectedResource != null) {
            String cnameBox = classroomBox.getText();
            String whiteboardsBox = whiteboardBox.getText();
            String projectorsBox = projectorBox.getText();
            try {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                PreparedStatement pst = connectDB.prepareStatement("UPDATE resources_table SET cname=?, whiteboards=?, projectors=? WHERE id=?");
                pst.setString(1, cnameBox);
                pst.setString(2, whiteboardsBox);
                pst.setString(3, projectorsBox);
                pst.setInt(4, selectedResource.getId());
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resource Update");
                alert.setHeaderText("Resource Update");
                alert.setContentText("Record updated successfully!");
                alert.showAndWait();

                table();

                classroomBox.setText("");
                whiteboardBox.setText("");
                projectorBox.setText("");

                classroomBox.requestFocus();
                table();
            } catch (SQLException ex) {
                Logger.getLogger(ResourcesContrller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No resource selected");
            alert.setHeaderText("No resource selected");
            alert.setContentText("Please select a resource to update.");
            alert.showAndWait();
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        Resources selectedResource = table.getSelectionModel().getSelectedItem();

        if (selectedResource != null) {
            try {

                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();

                PreparedStatement pst = connectDB.prepareStatement("DELETE FROM resources_table WHERE cname = ?");
                pst.setString(1, selectedResource.getCname());
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resource Deletion");
                alert.setHeaderText("Resource Deletion");
                alert.setContentText("Record deleted successfully!");
                alert.showAndWait();

                table();

                classroomBox.setText("");
                whiteboardBox.setText("");
                projectorBox.setText("");

                classroomBox.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(ResourcesContrller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No resource selected");
            alert.setContentText("Please select a resource to delete.");
            alert.showAndWait();
        }
    }


}






