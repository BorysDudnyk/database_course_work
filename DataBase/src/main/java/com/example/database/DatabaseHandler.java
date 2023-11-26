package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseHandler extends Configs implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(DatabaseHandler.class.getName());
    private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        LOGGER.info("Connected to the database successfully!");

        return dbConnection;
    }

    public void testQuery() {
        String selectQuery = "SELECT 1";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(selectQuery)) {
            ResultSet resultSet = prSt.executeQuery();

            LOGGER.info("Test query executed successfully!");

            if (resultSet.next()) {
                int result = resultSet.getInt(1);
                LOGGER.info("Result of the test query: " + result);
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error executing test query: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "idusers INT AUTO_INCREMENT PRIMARY KEY," +
                "firstname VARCHAR(45)," +
                "lastname VARCHAR(45)," +
                "username VARCHAR(45) UNIQUE," +
                "password VARCHAR(45)," +
                "location VARCHAR(45)," +
                "gender VARCHAR(10)" +
                ")";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(createTableQuery)) {
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error creating users table: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_LOCATION + "," + Const.USERS_GENDER + ")" +
                " VALUES(?,?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getLocation());
            prSt.setString(6, user.getGender());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error signing up user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resSet;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error getting user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return resSet;
    }

    @Override
    public void close() throws SQLException {
        if (dbConnection != null && !dbConnection.isClosed()) {
            dbConnection.close();
        }
    }
}
