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

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("dashboard"), 1200, 900);
        stage.getIcons().add(new Image("https://cdn.iconscout.com/icon/free/png-512/drugs-26-129384.png"));
        stage.setTitle("Adventure Camp Med Tracker");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        checkDate();

        launch();
    }

    private static void checkDate() {

        String currentDate = java.time.LocalDate.now().toString();

        try (Connection con = DBDriver.getConnection()) {

            ObservableList<WorkingDate> oblist = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("select * from date");

            if (rs.next()) {
                oblist.add(new WorkingDate(rs.getString("working_date")));
            }

            String workingDate = oblist.get(0).working_date;

            if (!workingDate.equals(currentDate)) {

                String query = "update prescriptions set given_today = 0 where given_today = 1";

                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.execute();

                String query2 = "update date set working_date = ? where working_date = ?";

                PreparedStatement preparedStmt2 = con.prepareStatement(query2);
                preparedStmt2.setString (1, currentDate);
                preparedStmt2.setString (2, workingDate);

                preparedStmt2.execute();

                System.out.println("given today reset!");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}