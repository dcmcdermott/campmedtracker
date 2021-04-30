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

// controller class for add_prescriptions.fxml
public class ControllerAddPrescriptions implements Initializable {

    public Label lblCamperName;
    public TextField tfMedName;
    public TextField tfDose;
    public Button btnAdd;
    public Button btnCancel;
    public int camperID = ControllerAddCamper.currentCamperID;

    // setup choice boxes
    ObservableList<String> doseUnits = FXCollections.observableArrayList(
            "g",
                "mg",
                "mcg",
                "cc",
                "units",
                "oz"
    );
    ObservableList<String> times = FXCollections.observableArrayList(
            "0000",
            "0100",
            "0200",
            "0300",
            "0400",
            "0500",
            "0600",
            "0600",
            "0700",
            "0800",
            "0900",
            "1000",
            "1100",
            "1200",
            "1300",
            "1400",
            "1500",
            "1600",
            "1700",
            "1800",
            "1900",
            "2000",
            "2100",
            "2200",
            "2300"
    );

    @FXML
    private ChoiceBox<String> cbDoseUnit;

    @FXML
    private ChoiceBox<String> cbTimes;

    // setup table view
    @FXML
    private TableView<Prescription> tvNewMeds;
    @FXML
    private TableColumn<Prescription, String> col_med_name_new;
    @FXML
    private TableColumn<Prescription, Integer> col_dose_new;
    @FXML
    private TableColumn<Prescription, String> col_dose_unit_new;
    @FXML
    private TableColumn<Prescription, Integer> col_admin_time_new;

    // initialize
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // initialize dose units choice box
        cbDoseUnit.setItems(doseUnits);

        // initialize times choice box
        cbTimes.setItems(times);

        try (Connection con = DBDriver.getConnection()) {

            // sql query get current camper name
            String query = "select first_name, last_name from campers where id = ?";

            // create prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, camperID);

            // execute prepared statement and get result set
            ResultSet rs = preparedStmt.executeQuery();

            String lname = "";
            String fname = "";

            while (rs.next()) {
                lname = rs.getString("last_name");
                fname = rs.getString("first_name");
            }

            // show current camper name in label
            lblCamperName.setText(fname + " " + lname);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // add prescriptions for a new camper
    @FXML
    private void addPrescriptions() throws IOException {

        ObservableList<Prescription> oblist = FXCollections.observableArrayList();

        try (Connection con = DBDriver.getConnection()) {

            // Q1
            // sql insert statement to add a new prescription
            String query = " insert into prescriptions (name, dose, dose_unit, time, camperid, given_today)"
                    + " values (?, ?, ?, ?, ?, ?)";

            // create sql prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfMedName.getText());
            preparedStmt.setString (2, tfDose.getText());
            preparedStmt.setString (3, cbDoseUnit.getValue());
            preparedStmt.setString (4, cbTimes.getValue());
            preparedStmt.setInt (5, camperID);
            preparedStmt.setBoolean (6, false);

            // execute the prepared statement
            preparedStmt.execute();

            // Q2
            // sql get all prescriptions for current camper
            String query2 = "select * from prescriptions where camperid = ?";

            // create sql prepared statement
            PreparedStatement preparedStmt2 = con.prepareStatement(query2);
            preparedStmt2.setInt (1, camperID);

            // execute the prepared statement
            ResultSet rs = preparedStmt2.executeQuery();

            // update the observable list
            while (rs.next()) {
                oblist.add(new Prescription(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("dose"),
                        rs.getString("dose_unit"),
                        rs.getInt("time"),
                        rs.getInt("camperid"),
                        rs.getBoolean("given_today")));

            // update tableview and clear text fields
            tvNewMeds.setItems(oblist);
            tfMedName.clear();
            tfDose.clear();
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // update the tableview
        col_med_name_new.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_dose_new.setCellValueFactory(new PropertyValueFactory<>("dose"));
        col_dose_unit_new.setCellValueFactory(new PropertyValueFactory<>("dose_unit"));
        col_admin_time_new.setCellValueFactory(new PropertyValueFactory<>("time"));
        tvNewMeds.setItems(oblist);
    }

    // when the back button is clicked, navigate to add camper
    @FXML
    private void switchToAddCamper() throws IOException {
        App.setRoot("add_camper");
    }

    // when the allergies button is clicked, navigate to add allergies
    @FXML
    private void switchToAllergies() throws IOException {
        App.setRoot("add_allergies");
    }
}
