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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerCamperTable implements Initializable {

    @FXML
    private TableView<Camper> tvCampers;
    @FXML
    private TableColumn<Camper, String> col_id;
    @FXML
    private TableColumn<Camper, String> col_last_name;
    @FXML
    private TableColumn<Camper, String> col_first_name;
    @FXML
    private TableColumn<Camper, String> col_dob;
    @FXML
    private TableColumn<Camper, String> col_guardian;
    @FXML
    private TableColumn<Camper, String> col_contact;
    @FXML
    private TableColumn<Camper, String> col_note;

    ObservableList<Camper> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (Connection con = DBDriver.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from campers");
            while (rs.next()) {
                oblist.add(new Camper(rs.getInt("id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("dob"),
                        rs.getString("guardian"),
                        rs.getString("contact"),
                        rs.getString("note")));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        col_guardian.setCellValueFactory(new PropertyValueFactory<>("guardian"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_note.setCellValueFactory(new PropertyValueFactory<>("note"));

        tvCampers.setItems(oblist);
    }

    @FXML
    private void switchToPrescriptions() throws IOException {
        App.setRoot("prescription_table");
    }
    @FXML
    private void switchToAddCamper() throws IOException {
        App.setRoot("add_camper");
    }
    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }
}