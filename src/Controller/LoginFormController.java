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

/**
 * LoginFormController class. Handle LoginForm interactions
 */
public class LoginFormController {
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Label badIDsText;

    /**
     * Handle the click of the submit button.
     * Access to the Home part of the application if the employee successfully logged in.
     * @param event submit button click
     */
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        EmployeeManager employeeManager = new EmployeeManager();
        Employee employee = employeeManager.exists(usernameTextField.getText());

        if(employee == null || !employee.getPassword().equals(MD5Hashing.hash(passwordField.getText()))) badIDsText.setVisible(true);
        else {
            Session.getInstance().setEmployee(employee);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Constants.HOME_FXML));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            Parent parent = loader.getRoot();
            Stage newStage = new Stage();

            JFXDecorator decorator = new JFXDecorator(newStage,parent,false,false,true);
            decorator.setText(Constants.WINDOW_TITLE);
            decorator.setOnCloseButtonAction(() -> System.exit(0));

            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

            Scene scene = new Scene(decorator,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());
            scene.getStylesheets().add(getClass().getResource(Constants.MAIN_CSS).toExternalForm());
            newStage.setMinWidth(Constants.MIN_WIDTH);
            newStage.setMinHeight(Constants.MIN_HEIGHT);
            newStage.setScene(scene);

            currentStage.close();
            newStage.show();
        }

    }
}
