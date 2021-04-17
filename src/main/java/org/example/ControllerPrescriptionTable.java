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


public class ControllerPrescriptionTable implements Initializable {

    @FXML
    private TableView<ModelCamperTable> tvPrescriptions;
    @FXML
    private TableColumn<ModelCamperTable, String> col_id;
    @FXML
    private TableColumn<ModelCamperTable, String> col_med_name;
    @FXML
    private TableColumn<ModelCamperTable, String> col_dose;
    @FXML
    private TableColumn<ModelCamperTable, String> col_admin_time;
    @FXML
    private TableColumn<ModelCamperTable, String> col_camper_id;

    ObservableList<ModelCamperTable> oblist = FXCollections.observableArrayList();

    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (Connection con = DBDriver.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from prescriptions");
            while (rs.next()) {
                oblist.add(new ModelCamperTable(rs.getString("camper_id"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("contact"), rs.getString("comments")));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_med_name.setCellValueFactory(new PropertyValueFactory<>("medname"));
        col_dose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        col_admin_time.setCellValueFactory(new PropertyValueFactory<>("admintime"));
        col_camper_id.setCellValueFactory(new PropertyValueFactory<>("userid"));

        tvPrescriptions.setItems(oblist);
    }

}