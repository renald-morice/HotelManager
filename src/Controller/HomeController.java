package Controller;

import Controller.Menu.MenuController;
import Util.Constants;
import com.jfoenix.controls.JFXButton;
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
    @FXML
    private JFXButton employeesButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.getInstance().getEmployee().getRole().getAccessLevel() < Constants.ACCESS_LEVEL_MIN) {
            employeesButton.setManaged(false);
        }
    }

    @FXML
    protected void handleRooms() { loadContent(Constants.ROOMS_FXML); }

    @FXML
    protected void handleReservations() { loadContent(Constants.RESERVATIONS_FXML); }

    @FXML
    protected void handleEmployees() { loadContent(Constants.EMPLOYEES_FXML); }

    @FXML
    protected void handleMyAccount() { loadContent(Constants.ACCOUNT_FXML); }

    @FXML
    protected void handleExit() { System.exit(0); }

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
