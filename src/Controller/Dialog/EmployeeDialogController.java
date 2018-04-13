package Controller.Dialog;

import Controller.Menu.EmployeesController;
import Manager.EmployeeManager;
import Manager.RoleManager;
import Model.Employee;
import Model.Role;
import Util.MD5Hashing;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeDialogController extends DialogController implements Initializable {
    @FXML
    private JFXTextField usernameTextField,firstNameTextField,lastNameTextField,salaryTextField;
    @FXML
    private JFXComboBox<Role> roleChoice;
    @FXML
    private Label usernameLabel,firstNameLabel,lastNameLabel,salaryLabel,errorLabel;

    private EmployeeManager employeeManager = new EmployeeManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RoleManager roleManager = new RoleManager();
        List<Role> roles = roleManager.listAll();
        roleChoice.getItems().addAll(roles);
        roleChoice.setPromptText("Choisir le role de l'employé");
    }

    @FXML
    protected void handleAddEmployeeButtonAction() {
        if(!checkInputs()) return;

        Employee employee = new Employee(
                usernameTextField.getText(),
                MD5Hashing.hash("test"),
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                Double.parseDouble(salaryTextField.getText()),
                roleChoice.getValue()
        );

        if(employeeManager.exists(employee.getUsername()) != null) displayError("Cet identifiant est déjà associé à un employé !");
        else {
            employeeManager.add(employee);
            ((EmployeesController)menuController).refreshTable(employee);
            dialog.close();
        }
    }

    @FXML
    protected void handleCancelButtonAction() {
        dialog.close();
    }

    private boolean checkInputs(){
        usernameTextField.getStyleClass().remove("error-textfield");
        firstNameTextField.getStyleClass().remove("error-textfield");
        lastNameTextField.getStyleClass().remove("error-textfield");
        salaryTextField.getStyleClass().remove("error-textfield");

        if (!usernameTextField.getText().matches("[a-zA-Z0-9]{2,}")) {
            usernameTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(usernameLabel.getText()).concat("\" doit contenir au moins 2 caractères  !"));
            return false;
        }

        if (!firstNameTextField.getText().matches("[a-zA-Z]{2,}")) {
            firstNameTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(firstNameLabel.getText()).concat("\" doit contenir au moins 2 caractères !"));
            return false;
        }

        if (!lastNameTextField.getText().matches("[a-zA-Z]{2,}")) {
            lastNameTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(lastNameLabel.getText()).concat("\" doit contenir au moins 2 caractères !"));
            return false;
        }

        try {
            Double.parseDouble(salaryTextField.getText());
        } catch (NumberFormatException e) {
            salaryTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(salaryLabel.getText()).concat("\" doit contenir un chiffre !"));
            return false;
        }

        if (roleChoice.getValue() == null) {
            roleChoice.getStyleClass().add("error-textfield");
            displayError("Vous devez sélectionner un rôle !");
            return false;
        }

        errorLabel.setVisible(false);
        return true;
    }

    private void displayError(String error){
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }

    @Override
    protected void initObjectToModify() {

    }
}
