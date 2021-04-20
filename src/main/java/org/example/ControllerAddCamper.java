/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerAddCamper {

    // camper table list model
    ObservableList<ModelCamperTable> oblist = FXCollections.observableArrayList();

    // text fields
    public TextField tfLastName;
    public TextField tfFirstName;
    public TextField tfContact;
    public TextField tfComments;

    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }

    @FXML
    private void addCamper() throws IOException {

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = " insert into campers (last_name, first_name, contact, comments)"
                    + " values (?, ?, ?, ?)";

            // create the mysql preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfLastName.getText());
            preparedStmt.setString (2, tfFirstName.getText());
            preparedStmt.setString (3, tfContact.getText());
            preparedStmt.setString (4, tfComments.getText());

            // execute the preparedstatement
            preparedStmt.execute();

            // update the campers table
            ResultSet rs = con.createStatement().executeQuery("select * from campers");
            while (rs.next()) {
                oblist.add(new ModelCamperTable(rs.getString("camper_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("contact"),
                        rs.getString("comments")));
            }
            System.out.println("Camper added successfully");
            App.setRoot("camper_table");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
