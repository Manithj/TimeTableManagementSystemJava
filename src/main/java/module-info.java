module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    //requires mysql.connector.java;


    opens com.example.TimeTable to javafx.fxml;
    exports com.example.TimeTable;
}