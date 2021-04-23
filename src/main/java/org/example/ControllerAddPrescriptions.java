package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerAddPrescriptions {
    public Label lblCamperName;
    public TextField tfMedName;
    public TextField tfDose;
    public TextField tfAdminTime;
    public Button btnFinish;
    public Button btnCancel;

    //ControllerAddCamper controllerAddCamper = new ControllerAddCamper();
    public String camperID = ControllerAddCamper.camperID;

    @FXML
    private void switchToAddCamper() throws IOException {
        App.setRoot("add_camper");
    }

    @FXML
    private void addPrescriptions() throws IOException {

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = " insert into prescriptions (medname, dose, admintime, userid)"
                    + " values (?, ?, ?, ?)";

            // create the mysql preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfMedName.getText());
            preparedStmt.setString (2, tfDose.getText());
            preparedStmt.setString (3, tfAdminTime.getText());
            preparedStmt.setString (4, camperID);

            // execute the preparedstatement
            preparedStmt.execute();

            System.out.println("Prescriptions added successfully");
            App.setRoot("add_prescriptions");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
