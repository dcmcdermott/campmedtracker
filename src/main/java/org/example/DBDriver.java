/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import java.sql.*;

public class DBDriver {

    final static String URL = "jdbc:mysql://localhost:3306/medtracker";
    final static String USER = "root";
    final static String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
