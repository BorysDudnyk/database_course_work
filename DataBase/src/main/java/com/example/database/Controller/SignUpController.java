package com.example.database.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

import com.example.database.DatabaseHandler;
import com.example.database.User;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField login_field;

    @FXML
    private TextField signUpCountry;

    @FXML
    private Button authSigInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> signUpNewUser());
        authSigInButton.setOnAction(event -> openSignInScene());
    }

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName = signUpName.getText();
        String lastName = signUpLastName.getText();
        String userName = login_field.getText();
        String password = "your_default_password";  // встановіть ваше значення за замовчуванням
        String location = signUpCountry.getText();
        String gender = signUpCheckBoxMale.isSelected() ? "Мужской" : "Женский";

        User user = new User(firstName, lastName, userName, password, location, gender);

        dbHandler.signUpUser(user);

        closeWindow();
        System.out.println("User registered successfully!");
    }

    private void openSignInScene() {
        closeWindow();
        openNewScene("/com/example/database/hello-view.fxml");
    }

    private void closeWindow() {
        Stage currentStage = (Stage) signUpButton.getScene().getWindow();
        currentStage.close();
    }

    private void openNewScene(String window) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(window))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
