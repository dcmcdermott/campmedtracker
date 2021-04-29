package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ControllerMedList implements Initializable {

    @FXML
    private TableView<Prescription> tvPrescriptions;
    @FXML
    private TableColumn<Prescription, String> col_p_id;
    @FXML
    private TableColumn<Prescription, String> col_p_med_name;
    @FXML
    private TableColumn<Prescription, String> col_p_dose;
    @FXML
    private TableColumn<Prescription, String> col_p_dose_unit;
    @FXML
    private TableColumn<Prescription, String> col_p_admin_time;
    @FXML
    private TableColumn<Prescription, String> col_p_camper_id;

    ObservableList<Prescription> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (Connection con = DBDriver.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from prescriptions");
            while (rs.next()) {
                oblist.add(new Prescription(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("dose"),
                        rs.getString("dose_unit"),
                        rs.getString("time"),
                        rs.getString("camperid")));
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

        tvPrescriptions.setItems(oblist);
    }

    @FXML
    private void giveMed() {
    }

    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }
}
