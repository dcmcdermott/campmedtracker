package org.example;

import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerMedList implements Initializable {


    public Label checkmark;
    public Label lblGivenMeds;

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
    @FXML
    private TableColumn<Prescription, Boolean> col_p_given_today;

    ObservableList<Prescription> oblist = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvGivenMeds.setVisible(false);
        lblGivenMeds.setVisible(false);

        try (Connection con = DBDriver.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from prescriptions");
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

        col_p_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_p_med_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_p_dose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        col_p_dose_unit.setCellValueFactory(new PropertyValueFactory<>("dose_unit"));
        col_p_admin_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_p_camper_id.setCellValueFactory(new PropertyValueFactory<>("camperid"));
        col_p_given_today.setCellValueFactory(new PropertyValueFactory<>("given_today"));

        tvPrescriptions.setItems(oblist);

        requestGivenMeds();
    }

    @FXML
    private void giveMed() {

        // get data from currently selected row
        int c_medid = tvPrescriptions.getSelectionModel().getSelectedItem().id;
        int c_dose = tvPrescriptions.getSelectionModel().getSelectedItem().dose;
        int c_camperid = tvPrescriptions.getSelectionModel().getSelectedItem().camperid;
        String c_name = tvPrescriptions.getSelectionModel().getSelectedItem().name;
        String c_dose_unit = tvPrescriptions.getSelectionModel().getSelectedItem().dose_unit;
        String c_time = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());

        tvPrescriptions.getItems().removeAll(tvPrescriptions.getSelectionModel().getSelectedItem());
        System.out.println(tvPrescriptions.getSelectionModel().getSelectedItem().name);

        try (Connection con = DBDriver.getConnection()) {


            // Q1
            // mysql statement
            String query1 = "update prescriptions set given_today = 1 where id = ?";

            // create sql prepared statement
            PreparedStatement preparedStmt1 = con.prepareStatement(query1);
            preparedStmt1.setInt (1, c_medid);

            // execute the prepared statement
            preparedStmt1.execute();

            // Q2
            // mysql statement
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
        requestGivenMeds();

        checkmark.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> checkmark.setVisible(false));
        pause.play();
    }

    @FXML
    private void requestGivenMeds() {

        tvGivenMeds.getItems().clear();

        try (Connection con = DBDriver.getConnection()) {

            ResultSet rs2 = con.createStatement().executeQuery("select * from given_meds");
            while (rs2.next()) {
                oblist2.add(new GivenMed(
                        rs2.getInt("id"),
                        rs2.getString("name"),
                        rs2.getInt("medid"),
                        rs2.getInt("dose"),
                        rs2.getString("dose_unit"),
                        rs2.getString("time_given"),
                        rs2.getInt("camperid")));
            }

            col_gm_id.setCellValueFactory(new PropertyValueFactory<>("medid"));
            col_gm_med_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_gm_dose.setCellValueFactory(new PropertyValueFactory<>("dose"));
            col_gm_dose_unit.setCellValueFactory(new PropertyValueFactory<>("dose_unit"));
            col_gm_given_at.setCellValueFactory(new PropertyValueFactory<>("time_given"));
            col_gm_camper_id.setCellValueFactory(new PropertyValueFactory<>("camperid"));

            tvGivenMeds.setItems(oblist2);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

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

    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }
}
