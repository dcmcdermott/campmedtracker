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

public class ControllerAddPrescriptions implements Initializable {

    public Label lblCamperName;
    public TextField tfMedName;
    public TextField tfDose;
    public TextField tfDoseUnit;
    public TextField tfAdminTime;
    public Button btnAdd;
    public Button btnCancel;
    public String camperID = ControllerAddCamper.currentCamperID;
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
    private TableView<ModelPrescriptionTable> tvNewMeds;
    @FXML
    private TableColumn<ModelPrescriptionTable, String> col_med_name_new;
    @FXML
    private TableColumn<ModelPrescriptionTable, String> col_dose_new;
    @FXML
    private TableColumn<ModelPrescriptionTable, String> col_dose_unit_new;
    @FXML
    private TableColumn<ModelPrescriptionTable, String> col_admin_time_new;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // initialize dose units choice box
        cbDoseUnit.setItems(doseUnits);

        // initialize times choice box
        cbTimes.setItems(times);

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = "select first_name, last_name from campers where id = ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, camperID);

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
    private void addPrescriptions() throws IOException {

        ObservableList<ModelPrescriptionTable> oblist = FXCollections.observableArrayList();

        try (Connection con = DBDriver.getConnection()) {

            // sql insert statement
            String query = " insert into prescriptions (name, dose, dose_unit, time, camperid)"
                    + " values (?, ?, ?, ?, ?)";

            // create sql prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfMedName.getText());
            preparedStmt.setString (2, tfDose.getText());
            preparedStmt.setString (3, cbDoseUnit.getValue());
            preparedStmt.setString (4, cbTimes.getValue());
            preparedStmt.setString (5, camperID);

            // execute the prepared statement
            preparedStmt.execute();

            // sql new user prescriptions statement
            String query2 = "select * from prescriptions where camperid = ?";

            // create sql prepared statement
            PreparedStatement preparedStmt2 = con.prepareStatement(query2);
            preparedStmt2.setString (1, camperID);

            // execute the prepared statement
            ResultSet rs = preparedStmt2.executeQuery();

            // update the observable list
            while (rs.next()) {
                oblist.add(new ModelPrescriptionTable(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("dose"),
                        rs.getString("dose_unit"),
                        rs.getString("time"),
                        rs.getString("camperid")));

            // update tableview and clear text fields
            tvNewMeds.setItems(oblist);
            tfMedName.clear();
            tfDose.clear();

            System.out.println("Prescriptions added successfully");
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

    @FXML
    private void switchToAddCamper() throws IOException {
        App.setRoot("add_camper");
    }

    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }
}
