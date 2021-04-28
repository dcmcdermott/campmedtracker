package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ControllerSearchCamper {

    public TextField tfFirstName;
    public TextField tfLastName;
    public TextField tfDOB;
    public Button btnSearch;
    public Label lblCamper;
    public Label lblDOB;
    public Label lblGuardian;
    public Label lblContact;


    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }





    @FXML
    private String getCamper() {

        String camperID = "";

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = "select * from campers where last_name = ? and first_name = ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, tfLastName.getText());
            preparedStmt.setString (2, tfFirstName.getText());
            //preparedStmt.setString (3, tfDOB.getText());
            preparedStmt.execute();

            ResultSet fart = preparedStmt.executeQuery();

            while (fart.next()) {
                lblCamper.setText(fart.getString("first_name") + " " + fart.getString("last_name"));
                lblDOB.setText(fart.getString("dob"));
                lblGuardian.setText(fart.getString("guardian"));
                lblContact.setText(fart.getString("contact"));
            }










            /*

            ResultSet rs = preparedStmt.executeQuery();
            camperID = rs.getString("id");
            lblCamper.setText(rs.getString("first_name") + rs.getString("last_name"));
            lblDOB.setText(rs.getString("dob"));
            lblGuardian.setText(rs.getString("guardian"));
            lblContact.setText(rs.getString("contact"));


             */
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return camperID;
    }
}
