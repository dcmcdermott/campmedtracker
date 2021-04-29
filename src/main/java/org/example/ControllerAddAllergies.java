/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerAddAllergies implements Initializable {

    public Label lblCamperName;
    public TextField tfAllergyName;
    public TextField tfReaction;
    public Button btnAdd;
    public Button btnCancel;
    public int camperID = ControllerAddCamper.currentCamperID;

    // setup table view
    @FXML
    private TableView<Allergy> tvNewAllergies;
    @FXML
    private TableColumn<Allergy, String> col_allergy;
    @FXML
    private TableColumn<Allergy, String> col_reaction;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = "select first_name, last_name from campers where id = ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, camperID);

            ResultSet rs = preparedStmt.executeQuery();

            String lname = "";
            String fname = "";

            while (rs.next()) {
                lname = rs.getString("last_name");
                fname = rs.getString("first_name");
            }

            lblCamperName.setText(fname + " " + lname);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void addAllergy() throws IOException {

        ObservableList<Allergy> oblist = FXCollections.observableArrayList();

        try (Connection con = DBDriver.getConnection()) {

            // sql insert statement
            String query = " insert into allergies (name, reaction, camperid)"
                    + " values (?, ?, ?)";

            // create sql prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfAllergyName.getText());
            preparedStmt.setString (2, tfReaction.getText());
            preparedStmt.setInt (3, camperID);

            // execute the prepared statement
            preparedStmt.execute();

            // sql new user prescriptions statement
            String query2 = "select * from allergies where camperid = ?";

            // create sql prepared statement
            PreparedStatement preparedStmt2 = con.prepareStatement(query2);
            preparedStmt2.setInt (1, camperID);

            // execute the prepared statement
            ResultSet rs = preparedStmt2.executeQuery();

            // update the observable list
            while (rs.next()) {
                oblist.add(new Allergy(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("reaction"),
                        rs.getInt("camperid")));

                // update tableview and clear text fields
                tvNewAllergies.setItems(oblist);
                tfAllergyName.clear();
                tfReaction.clear();

                System.out.println("Allergies added successfully");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // update the tableview
        col_allergy.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_reaction.setCellValueFactory(new PropertyValueFactory<>("reaction"));
        tvNewAllergies.setItems(oblist);
    }

    @FXML
    private void switchToAddCamper() throws IOException {
        App.setRoot("add_camper");
    }

    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }
}
