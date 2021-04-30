/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

// controller class for about.fxml
public class ControllerAbout {

    // when  home button is clicked navigate to dashboard
    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }
}
