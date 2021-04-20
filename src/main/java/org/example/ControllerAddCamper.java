package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

public class ControllerAddCamper {

    @FXML
    private void switchToCampers() throws IOException {
        App.setRoot("camper_table");
    }


}
