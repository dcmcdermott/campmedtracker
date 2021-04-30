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

// controller class for dashboard.fxml
public class ControllerDashboard implements Initializable {

    public Button btnNewCamper;
    public Button btnLookupCamper;
    public Button btnMedList;
    public Button btnViewCampers;
    public Button btnViewMeds;
    public ImageView imgLogo;

    // initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // get logo image
        Image image = new Image("https://cdn.iconscout.com/icon/free/png-512/drugs-26-129384.png");
        imgLogo.setImage(image);
    }


    // when the view campers button is clicked, navigate to campers table
    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }

    // when the view prescriptions button is clicked, navigate to prescriptions table
    @FXML
    private void switchToPrescriptions() throws IOException {
        App.setRoot("prescription_table");
    }

    // when the add camper button is clicked, navigate to add camper
    @FXML
    private void switchToAddCamper() throws IOException {
        App.setRoot("add_camper");
    }

    // when the about button is clicked, navigate to about
    @FXML
    private void switchToAbout() throws IOException {
        App.setRoot("about");
    }

    // when the search campers button is clicked, navigate to search campers
    @FXML
    private void switchToSearch() throws IOException {
        App.setRoot("search_camper");
    }

    // when the med list button is clicked, navigate to med list
    @FXML
    private void switchToMedList() throws IOException {
        App.setRoot("med_list");
    }
}
