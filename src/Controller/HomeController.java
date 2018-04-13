package Controller;

import Controller.Menu.AccountController;
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
    @FXML
    private StackPane contentStackPane;
    @FXML
    private ScrollPane contentScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    @FXML
    protected void handleRooms() {
        loadContent(Constants.ROOMS_FXML);
    }

    @FXML
    protected void handleReservations() {
        loadContent(Constants.RESERVATIONS_FXML);
    }

    @FXML
    protected void handleEmployees() {
        String ui;
        if (Session.getInstance().getEmployee().getRole().getAccessLevel() >= Constants.ACCESS_LEVEL_MIN) ui = Constants.EMPLOYEES_FXML;
        else ui = Constants.NO_ACCESS_FXML;
        loadContent(ui);
    }

    @FXML
    protected void handleMyAccount() { loadContent(Constants.ACCOUNT_FXML); }

    @FXML
    protected void handleExit() {
        System.exit(0);
    }

    private void loadContent(String ui) {
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
