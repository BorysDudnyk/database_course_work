package com.example.database.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.database.DatabaseHandler;
import com.example.database.User;
import com.example.database.animations.Shake;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSigInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {

        authSigInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if(!loginText.equals("") && !loginPassword.equals(""))
                    loginUser(loginText, loginPassword);
            else
                System.out.println("login and password is empty");
        });

        loginSignUpButton.setOnAction(event -> {
            openNewScene("/com/example/database/signUp.fxml");
        });
    }

    private void loginUser(String loginText, String loginPassword) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;

        while(true){
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }

        if(counter >= 1) {
            openNewScene("/com/example/database/app.fxml");
        } else {
                Shake userLoginAnim = new Shake(login_field);
                Shake userPassAnim = new Shake(password_field);
                userLoginAnim.playAnim();
                userPassAnim.playAnim();
        }

    }

    public void openNewScene(String window) {
        loginSignUpButton.getScene().getWindow().hide();

        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}
