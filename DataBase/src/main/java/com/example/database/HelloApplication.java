package com.example.database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 620);
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
