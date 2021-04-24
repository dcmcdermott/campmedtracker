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
    public TextField tfAdminTime;
    public Button btnAdd;
    public Button btnCancel;
    public String camperID = ControllerAddCamper.currentCamperID;

    // setup table view
    @FXML
    private TableView<ModelPrescriptionTable> tvNewMeds;
    @FXML
    private TableColumn<ModelPrescriptionTable, String> col_med_name_new;
    @FXML
    private TableColumn<ModelPrescriptionTable, String> col_dose_new;
    @FXML
    private TableColumn<ModelPrescriptionTable, String> col_admin_time_new;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = "select first_name, last_name from campers where camper_id = ?";

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
            String query = " insert into prescriptions (medname, dose, admintime, userid)"
                    + " values (?, ?, ?, ?)";

            // create sql prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfMedName.getText());
            preparedStmt.setString (2, tfDose.getText());
            preparedStmt.setString (3, tfAdminTime.getText());
            preparedStmt.setString (4, camperID);

            // execute the prepared statement
            preparedStmt.execute();

            // sql new user prescriptions statement
            String query2 = "select * from prescriptions where userid = ?";

            // create sql prepared statement
            PreparedStatement preparedStmt2 = con.prepareStatement(query2);
            preparedStmt2.setString (1, camperID);

            // execute the prepared statement
            ResultSet rs = preparedStmt2.executeQuery();

            // update the observable list
            while (rs.next()) {
                oblist.add(new ModelPrescriptionTable(
                        rs.getString("id"),
                        rs.getString("medname"),
                        rs.getString("dose"),
                        rs.getString("admintime"),
                        rs.getString("userid")));

            // update tableview and clear text fields
            tvNewMeds.setItems(oblist);
            tfMedName.clear();
            tfDose.clear();
            tfAdminTime.clear();

            System.out.println("Prescriptions added successfully");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // update the tableview
        col_med_name_new.setCellValueFactory(new PropertyValueFactory<>("medname"));
        col_dose_new.setCellValueFactory(new PropertyValueFactory<>("dose"));
        col_admin_time_new.setCellValueFactory(new PropertyValueFactory<>("admintime"));
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
