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

/**
 * HomeController class. Handle Home interactions
 */
public class HomeController implements Initializable {
    @FXML
    private StackPane contentStackPane;
    @FXML
    private ScrollPane contentScrollPane;
    @FXML
    private JFXButton roomsButton,reservationsButton,clientsButton,employeesButton,accountButton;

    private JFXButton currentButton;

    /**
     * Initialize all the data
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.getInstance().getEmployee().getRole().getAccessLevel() < Constants.ACCESS_LEVEL_MIN) {
            employeesButton.setManaged(false);
        }
        currentButton = roomsButton;
        handleRooms();
    }

    /**
     * Handle the room menu button
     * Load room UI
     */
    @FXML
    protected void handleRooms() { loadContent(Constants.ROOMS_FXML,roomsButton); }

    /**
     * Handle the reservation menu button
     * Load reservation UI
     */
    @FXML
    protected void handleReservations() { loadContent(Constants.RESERVATIONS_FXML,reservationsButton); }

    /**
     * Handle the employee menu button
     * Load employee UI
     */
    @FXML
    protected void handleEmployees() { loadContent(Constants.EMPLOYEES_FXML,employeesButton); }

    /**
     * Handle the my account menu button
     * Load my account UI
     */
    @FXML
    protected void handleMyAccount() { loadContent(Constants.ACCOUNT_FXML,accountButton); }

    /**
     * Handle the client menu button
     * Load client UI
     */
    @FXML
    protected void handleClients() { loadContent(Constants.CLIENTS_FXML,clientsButton); }

    /**
     * Handle the exit menu button
     * Exit application
     */
    @FXML
    protected void handleExit() { System.exit(0); }

    /**
     * load specific UI in the right part of the application and modify the menu button
     * @param ui
     * @param button
     */
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
