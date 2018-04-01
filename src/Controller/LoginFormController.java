package Controller;

import Manager.EmployeeManager;
import Model.Employee;
import Util.MD5Hashing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFormController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label badIDsText;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        EmployeeManager employeeManager = new EmployeeManager();
        Employee employee = employeeManager.login(usernameTextField.getText(), MD5Hashing.hash(passwordField.getText()));
        if(employee == null) badIDsText.setVisible(true);
        else{
            new Home().start(new Stage());
            stage.close();
        }
    }
}
