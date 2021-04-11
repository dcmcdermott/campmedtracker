package org.example;

import java.sql.*;

public class DBDriver {

    private static String url = "jdbc:mysql://localhost:3306/medtrackertest";
    private static String user = "root";
    private static String password = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

}
