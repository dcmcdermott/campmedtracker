package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void checkDate() {
        // get current date
        String currentDate = java.time.LocalDate.now().toString();


        try (Connection con = DBDriver.getConnection()) {

            // get working date from db
            ObservableList<WorkingDate> oblist = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery("select * from date");

            if (rs.next()) {
                oblist.add(new WorkingDate(rs.getString("working_date")));
            }

            String workingDate = oblist.get(0).working_date;

            // if the date changed update prescription table given_today column values all to false for the new day
            if (!workingDate.equals(currentDate)) {

                // Q1
                // set all prescriptions given_today to false
                String query = "update prescriptions set given_today = 0 where given_today = 1";

                // create prepared statement
                PreparedStatement preparedStmt = con.prepareStatement(query);

                // execute prepared statement
                preparedStmt.execute();

                // Q2
                // update working date to today's date
                String query2 = "update date set working_date = ? where working_date = ?";

                // create prepared statement
                PreparedStatement preparedStmt2 = con.prepareStatement(query2);
                preparedStmt2.setString (1, currentDate);
                preparedStmt2.setString (2, workingDate);

                // execute prepared statement
                preparedStmt2.execute();
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(currentDate, java.time.LocalDate.now().toString());
    }

}