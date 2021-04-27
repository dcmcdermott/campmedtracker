/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDashboard implements Initializable {

    public Button btnNewCamper;
    public Button btnLookupCamper;
    public Button btnMedList;
    public Button btnViewCampers;
    public Button btnViewMeds;
    public ImageView imgLogo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("https://cdn.iconscout.com/icon/free/png-512/drugs-26-129384.png");
        imgLogo.setImage(image);
    }



    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
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
    private void switchToAbout() throws IOException {
        App.setRoot("about");
    }
}
