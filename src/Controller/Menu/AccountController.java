package Controller.Menu;

import Controller.Session;
import Model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountController extends MenuController implements Initializable {
    @FXML
    private Label firstNameLabel,lastNameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Employee employee = Session.getInstance().getEmployee();

        firstNameLabel.setText(employee.getFirstName());
        lastNameLabel.setText(employee.getLastName());
    }
}
