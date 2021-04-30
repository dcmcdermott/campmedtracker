/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

// controller class for prescription_table.fxml
public class ControllerPrescriptionTable implements Initializable {

    // prescription table definition
    @FXML
    private TableView<Prescription> tvPrescriptions;
    @FXML
    private TableColumn<Prescription, Integer> col_id;
    @FXML
    private TableColumn<Prescription, String> col_med_name;
    @FXML
    private TableColumn<Prescription, Integer> col_dose;
    @FXML
    private TableColumn<Prescription, String> col_dose_unit;
    @FXML
    private TableColumn<Prescription, Integer> col_admin_time;
    @FXML
    private TableColumn<Prescription, Integer> col_camper_id;

    ObservableList<Prescription> oblist = FXCollections.observableArrayList();

    // initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // get prescriptions result set from db and put them in oblist
        try (Connection con = DBDriver.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from prescriptions");
            while (rs.next()) {
                oblist.add(new Prescription(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("dose"),
                        rs.getString("dose_unit"),
                        rs.getInt("time"),
                        rs.getInt("camperid"),
                        rs.getBoolean("given_today")));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // set cell value factories for prescriptions table columns
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_med_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_dose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        col_dose_unit.setCellValueFactory(new PropertyValueFactory<>("dose_unit"));
        col_admin_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_camper_id.setCellValueFactory(new PropertyValueFactory<>("camperid"));

        // apply oblist to prescriptions table
        tvPrescriptions.setItems(oblist);
    }

    // // when the campers button is clicked, navigate to campers table
    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }

    // when the home button is clicked, navigate to dashboard
    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }
}
