package Controller;

import Controller.Menu.MenuController;
import Model.Employee;
import Util.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Employee employee;

    @FXML
    private StackPane contentStackPane;
    @FXML
    private ScrollPane contentScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @FXML
    protected void handleRooms() {
        loadUI(Constants.ROOMS_FXML);
    }

    @FXML
    protected void handleReservations() {
        loadUI(Constants.RESERVATIONS_FXML);
    }
    @FXML
    protected void handleEmployees() {
        String ui;
        if (employee.getRole().getAccessLevel() >= Constants.ACCESS_LEVEL_MIN) ui = Constants.EMPLOYEES_FXML;
        else ui = Constants.NO_ACCESS_FXML;
        loadUI(ui);
    }

    @FXML
    protected void handleExit() {
        System.exit(0);
    }

    private void loadUI(String ui) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ui));
            Pane newPane = fxmlLoader.load();
            MenuController menuController = fxmlLoader.getController();
            menuController.setContentStackPane(contentStackPane);
            contentScrollPane.setContent(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
