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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
            newStage.setScene(new Scene(parent));

            JFXDecorator decorator = new JFXDecorator(newStage,parent);
            decorator.setCustomMaximize(true);
            decorator.setMaximized(true);
            decorator.setText(Constants.WINDOW_TITLE);
            newStage.setScene(new Scene(decorator));
            newStage.setMinWidth(Constants.WINDOW_MIN_WIDTH);
            newStage.setMinHeight(Constants.WINDOW_MIN_HEIGHT);

            currentStage.close();
            newStage.show();
        }

    }
}
