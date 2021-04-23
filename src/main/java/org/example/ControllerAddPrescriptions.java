package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = " insert into prescriptions (medname, dose, admintime, userid)"
                    + " values (?, ?, ?, ?)";

            // create the mysql prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfMedName.getText());
            preparedStmt.setString (2, tfDose.getText());
            preparedStmt.setString (3, tfAdminTime.getText());
            preparedStmt.setString (4, camperID);

            // execute the prepared statement
            preparedStmt.execute();

            System.out.println("Prescriptions added successfully");
            App.setRoot("add_prescriptions");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
