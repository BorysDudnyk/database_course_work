package com.example.database.Controller;

import com.example.database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeController {

    @FXML
    private Button Inf_about_users;

    @FXML
    private Button the_greatest_length_of_the_sheet;

    @FXML
    private Button the_shortest_length_of_the_sheet;

    @FXML
    private Button the_user_who_sent_the_smallest_letter;

    @FXML
    private Button users_did_not_letters;

    @FXML
    private TextArea displayArea;

    @FXML
    void initialize() {
        Inf_about_users.setOnAction(event -> displayUsers());
        the_greatest_length_of_the_sheet.setOnAction(event -> displayLetters("the_greatest_length_of_the_sheet"));
        the_shortest_length_of_the_sheet.setOnAction(event -> displayLetters("the_shortest_length_of_the_sheet"));
        the_user_who_sent_the_smallest_letter.setOnAction(event -> displayLetters("the_user_who_sent_the_smallest_letter"));
        users_did_not_letters.setOnAction(event -> displayUsersWithoutLetters());
    }

    private void displayUsers() {
        try {
            DatabaseHandler databaseHandler = new DatabaseHandler();
            Connection connection = databaseHandler.getDbConnection();

            String sql = "SELECT firstname, lastname, username, location, gender, birthdate FROM new_schema.users";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder resultText = new StringBuilder();
                while (resultSet.next()) {
                    resultText.append("First Name: ").append(resultSet.getString("firstname")).append("\n")
                            .append("Last Name: ").append(resultSet.getString("lastname")).append("\n")
                            .append("Username: ").append(resultSet.getString("username")).append("\n")
                            .append("Location: ").append(resultSet.getString("location")).append("\n")
                            .append("Gender: ").append(resultSet.getString("gender")).append("\n")
                            .append("Birthdate: ").append(resultSet.getDate("birthdate")).append("\n")
                            .append("\n");
                }

                displayArea.setText(resultText.toString());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private void displayLetters(String buttonName) {
        try {
            DatabaseHandler databaseHandler = new DatabaseHandler();
            Connection connection = databaseHandler.getDbConnection();

            String sql = "";
            switch (buttonName) {
                case "the_greatest_length_of_the_sheet":
                    sql = "SELECT sender_username, message_text FROM new_schema.letters ORDER BY LENGTH(message_text) DESC LIMIT 1";
                    break;
                case "the_shortest_length_of_the_sheet":
                    sql = "SELECT sender_username, message_text FROM new_schema.letters ORDER BY LENGTH(message_text) ASC LIMIT 1";
                    break;
                case "the_user_who_sent_the_smallest_letter":
                    sql = "SELECT sender_username, message_text, LENGTH(message_text) as message_length FROM new_schema.letters ORDER BY message_length ASC LIMIT 1";
                    break;
                case "users_did_not_letters":
                    sql = "SELECT * FROM new_schema.users LEFT JOIN new_schema.letters ON users.username = letters.sender_username WHERE letters.sender_username IS NULL";
                    break;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder resultText = new StringBuilder();
                while (resultSet.next()) {
                    resultText.append("Sender: ").append(resultSet.getString("sender_username")).append("\n")
                            .append("Message: ").append(resultSet.getString("message_text"))
                            .append("\n");
                }

                displayArea.setText(resultText.toString());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private void displayUsersWithoutLetters() {
        try {
            DatabaseHandler databaseHandler = new DatabaseHandler();
            Connection connection = databaseHandler.getDbConnection();

            String sql = "SELECT * FROM new_schema.users WHERE username NOT IN (SELECT DISTINCT sender_username FROM new_schema.letters)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder resultText = new StringBuilder();
                if (!resultSet.next()) {
                    resultText.append("Всі користувачі отримали лист.");
                } else {
                    do {
                        resultText.append("Username: ").append(resultSet.getString("username")).append("\n");
                    } while (resultSet.next());
                }

                displayArea.setText(resultText.toString());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
