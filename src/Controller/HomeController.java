package Controller;

import Util.Constants;
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

public class HomeController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void handleUI1(ActionEvent event) {
        loadUI(Constants.UI1_FXML);
    }

    @FXML
    protected void handleUI2(ActionEvent event) {
        loadUI(Constants.UI2_FXML);
    }
    @FXML
    protected void handleUI3(ActionEvent event) {
        loadUI(Constants.UI3_FXML);
    }
    @FXML
    protected void handleExit(ActionEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
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
