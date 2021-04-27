package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

public class ControllerAbout {
    @FXML
    private void switchToDashboard() throws IOException {
        App.setRoot("dashboard");
    }
}
