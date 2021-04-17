package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class ControllerPrescriptionTable {

    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }
}