/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

// controller class for med_list.fxml
public class ControllerMedList implements Initializable {

    public Label checkmark;
    public Label lblGivenMeds;

    // prescriptions table definition
    @FXML
    private TableView<Prescription> tvPrescriptions;
    @FXML
    private TableColumn<Prescription, Integer> col_p_id;
    @FXML
    private TableColumn<Prescription, String> col_p_med_name;
    @FXML
    private TableColumn<Prescription, Integer> col_p_dose;
    @FXML
    private TableColumn<Prescription, String> col_p_dose_unit;
    @FXML
    private TableColumn<Prescription, Integer> col_p_admin_time;
    @FXML
    private TableColumn<Prescription, Integer> col_p_camper_id;

    ObservableList<Prescription> oblist = FXCollections.observableArrayList();

    // meds given today table definition
    @FXML
    private TableView<GivenMed> tvGivenMeds;
    @FXML
    private TableColumn<GivenMed, Integer> col_gm_id;
    @FXML
    private TableColumn<GivenMed, String> col_gm_med_name;
    @FXML
    private TableColumn<GivenMed, Integer> col_gm_dose;
    @FXML
    private TableColumn<GivenMed, String> col_gm_dose_unit;
    @FXML
    private TableColumn<GivenMed, String> col_gm_given_at;
    @FXML
    private TableColumn<GivenMed, Integer> col_gm_camper_id;

    ObservableList<GivenMed> oblist2 = FXCollections.observableArrayList();

    // initialize med list
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // hide the given meds table by default
        tvGivenMeds.setVisible(false);
        lblGivenMeds.setVisible(false);

        // get prescriptions that have not been given today from db and put in an oblist
        try (Connection con = DBDriver.getConnection()) {

            // query db and get result set
            ResultSet rs = con.createStatement().executeQuery("select * from prescriptions");

            // fill oblist with prescription objects from result set only if they have not already been given today
            while (rs.next()) {
                if (!rs.getBoolean("given_today")){
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
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // set cell value factories for today's med list (prescriptions table) columns
        col_p_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_p_med_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_p_dose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        col_p_dose_unit.setCellValueFactory(new PropertyValueFactory<>("dose_unit"));
        col_p_admin_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_p_camper_id.setCellValueFactory(new PropertyValueFactory<>("camperid"));

        // apply oblist data to today's med list (prescriptions table)
        tvPrescriptions.setItems(oblist);

        // get given meds data from db and fill given meds table
        try {
            requestGivenMeds();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // When give medication button is clicked
    @FXML
    private void giveMed() throws Exception {

        // get data from currently selected row and current time
        int c_medid = tvPrescriptions.getSelectionModel().getSelectedItem().id;
        int c_dose = tvPrescriptions.getSelectionModel().getSelectedItem().dose;
        int c_camperid = tvPrescriptions.getSelectionModel().getSelectedItem().camperid;
        String c_name = tvPrescriptions.getSelectionModel().getSelectedItem().name;
        String c_dose_unit = tvPrescriptions.getSelectionModel().getSelectedItem().dose_unit;
        String c_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // remove the given med from today's med list
        tvPrescriptions.getItems().removeAll(tvPrescriptions.getSelectionModel().getSelectedItem());

        try (Connection con = DBDriver.getConnection()) {

            // Q1 update given today to true for selected med
            // sql statement
            String query1 = "update prescriptions set given_today = 1 where id = ?";

            // create sql prepared statement
            PreparedStatement preparedStmt1 = con.prepareStatement(query1);
            preparedStmt1.setInt (1, c_medid);

            // execute the prepared statement
            preparedStmt1.execute();

            // Q2 add selected med to the given meds table
            // sql statement
            String query2 = " insert into given_meds (name, medid, dose, dose_unit, time_given, camperid)"
                    + " values (?, ?, ?, ?, ?, ?)";

            // create sql prepared statement
            PreparedStatement preparedStmt2 = con.prepareStatement(query2);
            preparedStmt2.setString (1, c_name);
            preparedStmt2.setInt (2, c_medid);
            preparedStmt2.setInt (3, c_dose);
            preparedStmt2.setString (4, c_dose_unit);
            preparedStmt2.setString (5, c_time);
            preparedStmt2.setInt (6, c_camperid);

            // execute the prepared statement
            preparedStmt2.execute();

        }
        catch (SQLException throwables) {
                throwables.printStackTrace();
        }

        // refresh given meds table
        requestGivenMeds();

        // show green confirmation checkmark for a sec when med is given
        checkmark.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> checkmark.setVisible(false));
        pause.play();
    }

    // gets given_meds table from db and show in given meds table but only if meds was given today
    @FXML
    private void requestGivenMeds() throws Exception{

        // clear the table of old data
        tvGivenMeds.getItems().clear();

        // get given meds from db and show in table if given today
        try (Connection con = DBDriver.getConnection()) {

            // query db and get result set
            ResultSet rs2 = con.createStatement().executeQuery("select * from given_meds");

            // fill oblist2 with GivenMed objects
            while (rs2.next()) {

                // get date given for each med and today's date
                String given_date = rs2.getString("time_given").split("\\s")[0];
                String current_date = LocalDate.now().toString();

                // if given date is today, add med to oblist2
                if (given_date.equals(current_date)) {
                    oblist2.add(new GivenMed(
                            rs2.getInt("id"),
                            rs2.getString("name"),
                            rs2.getInt("medid"),
                            rs2.getInt("dose"),
                            rs2.getString("dose_unit"),
                            rs2.getString("time_given"),
                            rs2.getInt("camperid")));
                }
            }

            // set cell factory values for given meds table
            col_gm_id.setCellValueFactory(new PropertyValueFactory<>("medid"));
            col_gm_med_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_gm_dose.setCellValueFactory(new PropertyValueFactory<>("dose"));
            col_gm_dose_unit.setCellValueFactory(new PropertyValueFactory<>("dose_unit"));
            col_gm_given_at.setCellValueFactory(new PropertyValueFactory<>("time_given"));
            col_gm_camper_id.setCellValueFactory(new PropertyValueFactory<>("camperid"));

            // apply oblist2 to given meds table
            tvGivenMeds.setItems(oblist2);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // When show given meds button is clicked toggle visiblity of given meds table and label
    @FXML
    private void showGivenMeds() {

        if (tvGivenMeds.isVisible()) {
            tvGivenMeds.setVisible(false);
            lblGivenMeds.setVisible(false);
        }
        else {
            tvGivenMeds.setVisible(true);
            lblGivenMeds.setVisible(true);
        }
    }

    // When home button is clicked navigate to dashboard
    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }

    // When campers button is clicked navigate to dashboard
    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }
}
