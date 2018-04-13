package Controller;

import Manager.EmployeeManager;
import Model.Employee;
import Util.Constants;
import Util.MD5Hashing;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Label badIDsText;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        EmployeeManager employeeManager = new EmployeeManager();
        Employee employee = employeeManager.exists(usernameTextField.getText());

        if(employee == null || !employee.getPassword().equals(MD5Hashing.hash(passwordField.getText()))) badIDsText.setVisible(true);
        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Constants.HOME_FXML));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            HomeController homeController = loader.getController();
            homeController.setEmployee(employee);

            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            Parent parent = loader.getRoot();
            Stage newStage = new Stage();

            JFXDecorator decorator = new JFXDecorator(newStage,parent,false,false,true);
            decorator.setText(Constants.WINDOW_TITLE);
            decorator.setOnCloseButtonAction(() -> System.exit(0));

            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

            Scene scene = new Scene(decorator,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());
            scene.getStylesheets().add(getClass().getResource("/Resources/CSS/main.css").toExternalForm());
            newStage.setScene(scene);

            currentStage.close();
            newStage.show();
        }

    }
}
