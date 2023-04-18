package com.example.TimeTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class AddUserController {
    @FXML
    private ComboBox<String> accType;
    @FXML
    private TextField fnameBox;
    @FXML
    private TextField lnameBox;
    @FXML
    private TextField usernameBox;
    @FXML
    private TextField passwordBox;
    @FXML
    private TextField cpasswordBox;
    @FXML
    private Button addBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Label label;

    public void initialize() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Admin",
                        "User"
                );
        accType.setItems(options);


    }

    public void addBtnAction(ActionEvent event){
        if (passwordBox.getText().equals(cpasswordBox.getText())){
            addUser();

        }else{
            label.setText("Password does not match");
        }
    }

    public void exitBtnAction(ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage dashboard = new Stage();
        dashboard.setScene(new Scene(root2));
        dashboard.show();


        Stage currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentWindow.close();

    }

    public void addUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String firstname = fnameBox.getText();
        String lastname = lnameBox.getText();
        String username = usernameBox.getText();
        String password = passwordBox.getText();
        String accTypes = accType.getValue();


        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty() || accTypes.isEmpty()) {
            label.setText("Please fill in all fields");
            return;
        }


        String query = "INSERT INTO user_account(first_name, lastname, username, password, Acc_Type) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, accTypes);
            statement.executeUpdate();

            label.setText("User Added Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
            label.setText("Error: Failed to add user");
        }

    }
}
