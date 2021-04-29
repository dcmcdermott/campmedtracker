package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class ControllerSearchCamper implements Initializable {

    public TextField tfFirstName;
    public TextField tfLastName;
    public Button btnSearch;
    public Label lblCamper;
    public Label lblDOB;
    public Label lblGuardian;
    public Label lblContact;
    public VBox vbResults;

    // prescriptions table
    @FXML
    private TableView<Prescription> tvMeds;
    @FXML
    private TableColumn<Prescription, String> col_med_name;
    @FXML
    private TableColumn<Prescription, Integer> col_dose;
    @FXML
    private TableColumn<Prescription, String> col_dose_unit;
    @FXML
    private TableColumn<Prescription, Integer> col_admin_time;

    ObservableList<Prescription> oblist = FXCollections.observableArrayList();

    // allergy table
    @FXML
    private TableView<Allergy> tvAllergies;
    @FXML
    private TableColumn<Allergy, String> col_allergy;
    @FXML
    private TableColumn<Allergy, String> col_reaction;

    ObservableList<Allergy> oblist2 = FXCollections.observableArrayList();

    // hide results box until search happens
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vbResults.setVisible(false);
    }

    // search for the provided camper and display their info
    @FXML
    private void searchCamper() {

        // clear tableviews
        tvMeds.getItems().clear();
        tvAllergies.getItems().clear();

        // get camper id and info
        String camperID = getCamper();

        try (Connection con = DBDriver.getConnection()) {

            // prescriptions sql statement
            String query = "select * from prescriptions where camperid=?";

            // create sql prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, camperID);

            // execute the prepared statement
            preparedStmt.execute();

            // get result from db and put it in oblist
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                oblist.add(new Prescription(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("dose"),
                        rs.getString("dose_unit"),
                        rs.getInt("time"),
                        rs.getInt("camperid")));
            }

            // allergies sql statement
            String query2 = "select * from allergies where camperid=?";

            // create sql prepared statement
            PreparedStatement preparedStmt2 = con.prepareStatement(query2);
            preparedStmt2.setString (1, camperID);

            // execute the prepared statement
            preparedStmt2.execute();

            // get result from db and put it in oblist2
            ResultSet rs2 = preparedStmt2.executeQuery();
            while (rs2.next()) {
                oblist2.add(new Allergy(
                        rs2.getInt("id"),
                        rs2.getString("name"),
                        rs2.getString("reaction"),
                        rs2.getInt("camperid")));
            }

            // clear text fields
            tfFirstName.clear();
            tfLastName.clear();

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // set column value factories

            // prescription table
        col_med_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_dose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        col_dose_unit.setCellValueFactory(new PropertyValueFactory<>("dose_unit"));
        col_admin_time.setCellValueFactory(new PropertyValueFactory<>("time"));

            // allergies table
        col_allergy.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_reaction.setCellValueFactory(new PropertyValueFactory<>("reaction"));

        // populate tableviews with oblist data
        tvMeds.setItems(oblist);
        tvAllergies.setItems(oblist2);

        // show results box
        vbResults.setVisible(true);
    }

    // get camper data and return camperid to searchCamper() in order to populate
    // tables with specific camper info
    @FXML
    private String getCamper() {

        String camperID = "";

        try (Connection con = DBDriver.getConnection()) {

            // get camper sql statement
            String query = "select * from campers where last_name = ? and first_name = ?";

            // create sql prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfLastName.getText());
            preparedStmt.setString (2, tfFirstName.getText());

            // execute the prepared statement
            preparedStmt.execute();

            // get result from db and populate labels. store camper id
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                camperID = rs.getString("id");
                lblCamper.setText(rs.getString("first_name") + " " + rs.getString("last_name"));
                lblDOB.setText(rs.getString("dob"));
                lblGuardian.setText(rs.getString("guardian"));
                lblContact.setText(rs.getString("contact"));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return camperID;
    }

    // switch to dashboard view
    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }
}
