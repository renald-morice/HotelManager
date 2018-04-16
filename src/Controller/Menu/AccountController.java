package Controller.Menu;

import Controller.Session;
import Model.Employee;
import Util.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * AccountController class. Handle account page interactions.
 */
public class AccountController extends MenuController implements Initializable {
    @FXML
    private Label firstNameLabel,lastNameLabel,roleLabel,usernameLabel;

    /**
     * Initialize all the data.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Employee employee = Session.getInstance().getEmployee();

        firstNameLabel.setText(firstNameLabel.getText()+employee.getFirstName());
        lastNameLabel.setText(lastNameLabel.getText()+employee.getLastName());
        roleLabel.setText(roleLabel.getText()+employee.getRole().toString());
        usernameLabel.setText(usernameLabel.getText()+employee.getUsername());
    }

    /**
     * Handle the modify password button.
     */
    @FXML
    private void handleModifyPassword() { loadDialog(Constants.PASSWORD_DIALOG_FXML, null); }
}
