package com.example.TimeTable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField usernameBox;

    @FXML
    private TextField passwordBox;
    @FXML
    private Label loginLbl;

    @FXML
    private Button loginBtn;

    @FXML
    public void loginBtnAction(ActionEvent event) {
        if (usernameBox.getText().isBlank() == false && passwordBox.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginLbl.setText("Please enter username and password");
        }
    }

    public void initialize() {
        passwordBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginBtnAction(new ActionEvent());
            }
        });
    }


    @FXML
    private Button exitBtn;

    public void exitBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String username = usernameBox.getText();
        String password = passwordBox.getText();

        String verifyLogin = "SELECT * FROM user_account WHERE username = '" + username + "' AND password = '" + password + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            if (queryResult.next()) {
                //loginLbl.setText("Congratulations!");
                exitBtnOnAction(new ActionEvent());
                Dashboard();
            } else {
                loginLbl.setText("Invalid login. Please try again.");
            }

            queryResult.close();
            statement.close();
            connectDB.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Dashboard(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Stage dashboard = new Stage();
            dashboard.setScene(new Scene(root));
            dashboard.setResizable(false);
            dashboard.setTitle("Dashboard");
            dashboard.getIcons().add(new Image("file:src/img/classroom.png"));
            dashboard.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}