package Controller;

import Manager.EmployeeManager;
import Model.Employee;
import Util.MD5Hashing;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginFormController {
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Label badIDsText;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        EmployeeManager employeeManager = new EmployeeManager();
        Employee employee = employeeManager.exists(usernameTextField.getText());

        if(employee == null || !employee.getPassword().equals(MD5Hashing.hash(passwordField.getText()))) badIDsText.setVisible(true);
        else {
            try {
                new HomeApplication().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        }
    }
}
