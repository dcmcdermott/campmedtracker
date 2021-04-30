/**
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    // setup stage
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("dashboard"), 1200, 900);
        stage.getIcons().add(new Image("https://cdn.iconscout.com/icon/free/png-512/drugs-26-129384.png"));
        stage.setTitle("Adventure Camp Med Tracker");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    // set root function
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // load FXML function
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Main
    public static void main(String[] args) {

        // check the date and update db if date changed since last use
        checkDate();

        // launch app
        launch();
    }

    // Compare current date with working date in db and update prescriptions given status to false if new date
    private static void checkDate() {

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
    }
}