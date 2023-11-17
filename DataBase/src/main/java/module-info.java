module com.example.database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql.rowset;

    opens com.example.database to javafx.fxml;
    exports com.example.database;
    exports com.example.database.Controller;
    opens com.example.database.Controller to javafx.fxml;
}