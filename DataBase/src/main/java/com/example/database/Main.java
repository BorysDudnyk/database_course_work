package com.example.database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("app.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 500, 620);
        stage.setTitle("My DataBase project");
        stage.setScene(scene);
        stage.show();

        // Тестовий запит до бази даних
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.testQuery();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
