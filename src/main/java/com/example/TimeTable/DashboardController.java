package com.example.TimeTable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.io.IOException;

public class DashboardController {
    @FXML
    private Button SchedulesBtn;
    @FXML
    private Button ClassroomBtn;
    @FXML
    private Button ReportsBtn;
    @FXML
    private Button ResourcesBtn;
    @FXML
    private Button AddUserBtn;
    @FXML
    private Button SignOutBtn;


    @FXML
    public void ResourcesBtnAction(ActionEvent event){
        try {
            Parent root6 = FXMLLoader.load(getClass().getResource("Resources.fxml"));
            Stage dashboard4 = new Stage();
            dashboard4.setScene(new Scene(root6));
            dashboard4.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();
    }
    @FXML
    public void ClassroomBtnAction(ActionEvent event){
        try {
            Parent root5 = FXMLLoader.load(getClass().getResource("Classrooms.fxml"));
            Stage dashboard3 = new Stage();
            dashboard3.setScene(new Scene(root5));
            dashboard3.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();
    }

    @FXML
    public void SchedulesBtnAction(ActionEvent event) throws IOException {
        try {
            Parent root3 = FXMLLoader.load(getClass().getResource("Schedules.fxml"));
            Stage dashboard2 = new Stage();
            dashboard2.setScene(new Scene(root3));
            dashboard2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();
    }

    @FXML
    public void SignOutBtnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage dashboard = new Stage();
        dashboard.setScene(new Scene(root));
        dashboard.show();


        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();
    }

    @FXML
    public void AddUserBtnAction(ActionEvent event)throws IOException{
        Parent root1 = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
        Stage dashboard1 = new Stage();
        dashboard1.setScene(new Scene(root1));
        dashboard1.show();


        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();
    }

}
