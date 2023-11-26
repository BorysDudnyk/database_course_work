package com.example.database.Controller;

import com.example.database.DatabaseHandler;
import com.example.database.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private Button authSigInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    public void initialize() {
        authSigInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if (!loginText.isEmpty() && !loginPassword.isEmpty()) {
                loginUser(loginText, loginPassword);
            } else {
                System.out.println("Логін та пароль не можуть бути порожніми");
            }
        });

        loginSignUpButton.setOnAction(event -> {
            Platform.runLater(() -> openNewScene("/com/example/database/signUp.fxml", "Реєстрація"));
        });
    }

    private void loginUser(String loginText, String loginPassword) {
        try (DatabaseHandler dbHandler = new DatabaseHandler()) {
            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPassword);
            try (ResultSet result = dbHandler.getUser(user)) {
                if (result.next()) {
                    // Обробка результатів тут, якщо потрібно
                    int counter = 1;

                    if (counter >= 1) {
                        Platform.runLater(() -> openNewScene("/com/example/database/app.fxml", "Моя програма"));
                    } else {
                        System.out.println("Вхід не вдалось");
                    }
                } else {
                    System.out.println("Користувача з наданими обліковими даними не знайдено");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void openNewScene(String window, String title) {
        Stage currentStage = (Stage) authSigInButton.getScene().getWindow();
        currentStage.hide();

        try {
            Parent root = FXMLLoader.load(getClass().getResource(window));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
