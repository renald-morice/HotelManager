package Controller.Dialog;

import Controller.Session;
import Manager.EmployeeManager;
import Model.Employee;
import Util.MD5Hashing;
import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Password dialog controller class. Used when the user wants to change his password.
 */
public class PasswordDialogController extends DialogController implements Initializable {
    @FXML
    private Label oldPasswordLabel,newPasswordLabel,newPasswordLabel2,errorLabel;

    @FXML
    private JFXPasswordField oldPasswordField,newPasswordField,newPasswordField2;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    @Override
    protected void initObjectToModify() { }

    /**
     * Close the dialog when the user clicks the cancel button.
     */
    @FXML
    private void handleCancelAction() { dialog.close(); }

    /**
     * Updates the user password when the user confirms.
     */
    @FXML
    private void handleConfirmAction() {
        Employee employee = Session.getInstance().getEmployee();
        EmployeeManager employeeManager = new EmployeeManager();
        if(!checkInputs(employee)) return;

        employee.setPassword(MD5Hashing.hash(newPasswordField2.getText()));
        employeeManager.update(employee);
        dialog.close();
    }

    /**
     * Check if all the input TextFields are correctly filled.
     * @return True if the inputs are correct, otherwise false.
     */
    private boolean checkInputs(Employee employee){
        oldPasswordField.getStyleClass().remove("error-textfield");
        newPasswordField.getStyleClass().remove("error-textfield");
        newPasswordField2.getStyleClass().remove("error-textfield");

        if (!MD5Hashing.hash(oldPasswordField.getText()).equals(employee.getPassword())) {
            oldPasswordField.getStyleClass().add("error-textfield");
            displayError("Mot de passe incorrect !");
            return false;
        }

        if (!newPasswordField.getText().matches(".{4,}")) {
            newPasswordField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(newPasswordLabel.getText()).concat("\" doit contenir au moins 4 caractères !"));
            return false;
        }

        if (!newPasswordField2.getText().matches(".{4,}")) {
            newPasswordField2.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(newPasswordLabel2.getText()).concat("\" doit contenir au moins 4 caractères !"));
            return false;
        }

        if (!newPasswordField.getText().equals(newPasswordField2.getText())) {
            displayError("Les nouveaux mots de passe ne correspondent pas !");
            return false;
        }

        errorLabel.setVisible(false);
        return true;
    }

    /**
     * Displays error in a Label.
     * @param error The error.
     */
    private void displayError(String error){
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
}
