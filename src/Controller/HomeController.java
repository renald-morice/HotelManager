package Controller;

import Model.Employee;
import Util.Constants;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class HomeController implements Initializable {

    private Employee employee;

    @FXML
    private BorderPane borderPane;

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
        String ui = "";
        if (employee.getRole().getAccessLevel() > 7) {
            ui = Constants.EMPLOYEES_FXML;
        } else {
            ui = Constants.NO_ACCESS_FXML;
        }
        loadUI(ui);
    }

    @FXML
    protected void handleExit(ActionEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    private void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Constants.SIDEPANELINTERFACES_PATH+ui));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(root);
    }
}
