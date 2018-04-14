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
    private JFXButton roomsButton,reservationsButton,employeesButton,accountButton;

    private JFXButton currentButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.getInstance().getEmployee().getRole().getAccessLevel() < Constants.ACCESS_LEVEL_MIN) {
            employeesButton.setManaged(false);
        }
        currentButton = roomsButton;
        handleRooms();
    }

    @FXML
    protected void handleRooms() {

        loadContent(Constants.ROOMS_FXML,roomsButton);
    }

    @FXML
    protected void handleReservations() {
        loadContent(Constants.RESERVATIONS_FXML,reservationsButton);
    }

    @FXML
    protected void handleEmployees() {
        loadContent(Constants.EMPLOYEES_FXML,employeesButton);
    }

    @FXML
    protected void handleMyAccount() {
        loadContent(Constants.ACCOUNT_FXML,accountButton);
    }

    @FXML
    protected void handleExit() { System.exit(0); }

    private void loadContent(String ui,JFXButton button) {
        currentButton.setStyle("-fx-background-color: #30415d");
        currentButton = button;
        currentButton.setStyle("-fx-background-color: #1e293a");

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
