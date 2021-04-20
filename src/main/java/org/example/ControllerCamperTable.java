package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControllerCamperTable implements Initializable {

    @FXML
    private TableView<ModelCamperTable> tvCampers;
    @FXML
    private TableColumn<ModelCamperTable, String> col_id;
    @FXML
    private TableColumn<ModelCamperTable, String> col_last_name;
    @FXML
    private TableColumn<ModelCamperTable, String> col_first_name;
    @FXML
    private TableColumn<ModelCamperTable, String> col_contact;
    @FXML
    private TableColumn<ModelCamperTable, String> col_comments;

    ObservableList<ModelCamperTable> oblist = FXCollections.observableArrayList();

    @FXML
    private void switchToPrescriptions() throws IOException {
        App.setRoot("prescription_table");
    }

    @FXML
    private void switchToAddCamper() throws IOException {
        App.setRoot("add_camper");
    }

    @FXML
    private void addCamper() throws IOException {

        try (Connection con = DBDriver.getConnection()) {

            // mysql statement
            String query = " insert into campers (last_name, first_name, contact, comments)"
                    + " values (?, ?, ?, ?)";

            // create the mysql preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, "Bboy");
            preparedStmt.setString (2, "Troy");
            preparedStmt.setString (3, "6666777766");
            preparedStmt.setString (4, "Sick");

            // execute the preparedstatement
            preparedStmt.execute();

            // update the campers table
            ResultSet rs = con.createStatement().executeQuery("select * from campers");
            while (rs.next()) {
                oblist.add(new ModelCamperTable(rs.getString("camper_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("contact"),
                        rs.getString("comments")));
            }
            System.out.println("Camper added successfully");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (Connection con = DBDriver.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from campers");
            while (rs.next()) {
                oblist.add(new ModelCamperTable(rs.getString("camper_id"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("contact"), rs.getString("comments")));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_comments.setCellValueFactory(new PropertyValueFactory<>("comments"));

        tvCampers.setItems(oblist);
    }

}