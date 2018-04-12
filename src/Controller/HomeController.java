package Controller;

import Model.Employee;
import Util.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    private Employee employee;

    @FXML
    private ScrollPane contentScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @FXML
    protected void handleRooms(ActionEvent event) {
        loadUI(Constants.ROOMS_FXML);
    }

    @FXML
    protected void handleReservations(ActionEvent event) {
        loadUI(Constants.RESERVATIONS_FXML);
    }
    @FXML
    protected void handleEmployees(ActionEvent event) {
        String ui;
        if (employee.getRole().getAccessLevel() >= Constants.ACCESS_LEVEL_MIN) {
            ui = Constants.EMPLOYEES_FXML;
        } else {
            ui = Constants.NO_ACCESS_FXML;
        }
        loadUI(ui);
    }

    @FXML
    protected void handleExit(ActionEvent event) {
        System.exit(0);
    }

    private void loadUI(String ui) {

        try {
            Pane newPane = FXMLLoader.load(getClass().getResource(Constants.SIDEPANELINTERFACES_PATH+ui));
            contentScrollPane.setContent(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
