package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbs.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO" + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_LOCATION + "," + Const.USERS_GENDER + ")" +
                "VALUES(?,?,?,?,?,?)";
        try {

            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getLocation());
            prSt.setString(6, user.getGender());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {

            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    /*private void getConnection() throws SQLException {

        try {
            Connection connection = dbConnection.getConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            ResultSet rs = statement.executeQuery("Select * from user");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }


        ResultSet rs = null;

        rs.last();
        System.out.println("Last Users is" + rs.getInt("USERS_FIRSTNAME") + "" + rs.getInt("USERS_LASTNAME") + "" +
                rs.getInt("USERS_LOCATION") + "" + rs.getDate("USERS_ID"));

        rs.first();
        System.out.println("First  Users is" + rs.getInt("USERS_FIRSTNAME") + "" + rs.getInt("USERS_LASTNAME") + "" +
                rs.getInt("USERS_LOCATION") + "" + rs.getDate("USERS_ID"));

        rs.absolute(3);
        System.out.println("Users is" + rs.getInt("USERS_FIRSTNAME") + "" + rs.getInt("USERS_LASTNAME") + "" +
                rs.getInt("USERS_LOCATION") + "" + rs.getDate("USERS_ID"));

        rs.next();
        System.out.println("Next  Users is" + rs.getInt("USERS_FIRSTNAME") + "" + rs.getInt("USERS_LASTNAME") + "" +
                rs.getInt("USERS_LOCATION") + "" + rs.getDate("USERS_ID"));

        rs.last();
        System.out.println("Last  Users is" + rs.getInt("USERS_FIRSTNAME") + "" + rs.getInt("USERS_LASTNAME") + "" +
                rs.getInt("USERS_LOCATION") + "" + rs.getDate("USERS_ID"));

    }*/
    
}
