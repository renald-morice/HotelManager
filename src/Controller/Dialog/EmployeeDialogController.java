package Controller.Dialog;

import Controller.Menu.EmployeesController;
import Manager.EmployeeManager;
import Manager.RoleManager;
import Model.Employee;
import Model.Role;
import Util.MD5Hashing;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Employee dialog controller class. Used when we create or modify an employee.
 */
public class EmployeeDialogController extends DialogController implements Initializable {
    @FXML
    private JFXTextField usernameTextField,firstNameTextField,lastNameTextField,salaryTextField;
    @FXML
    private JFXComboBox<Role> roleChoice;
    @FXML
    private Label usernameLabel,firstNameLabel,lastNameLabel,salaryLabel,errorLabel;
    @FXML
    private JFXButton actionButton;

    private EmployeeManager employeeManager = new EmployeeManager();

    /**
     * Initialize/populate the role combo box.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RoleManager roleManager = new RoleManager();
        roleChoice.getItems().addAll(roleManager.listAll());
    }

    /**
     * Add/modify the employee when the user clicks the add/modify button.
     */
    @FXML
    protected void handleAddEmployeeButtonAction() {
        if(!checkInputs()) return;
        if(actionButton.getText().equals("Ajouter")) addEmployee();
        else modifyEmployee();
    }

    /**
     * Add a new employee method.
     */
    private void addEmployee(){
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
            ((EmployeesController)menuController).addEmployeeToTable(employee);
            dialog.close();
        }
    }

    /**
     * Initialize the TextFields of the dialog when we modify a client.
     */
    @Override
    protected void initObjectToModify() {
        Employee employee = (Employee) objectToModify;

        usernameTextField.setText(String.valueOf(employee.getUsername()));
        firstNameTextField.setText(String.valueOf(employee.getFirstName()));
        lastNameTextField.setText(String.valueOf(employee.getLastName()));
        salaryTextField.setText(String.valueOf(employee.getSalary()));
        for (Role role:roleChoice.getItems() ) {
            if (role.equals(employee.getRole())) roleChoice.setValue(role);
        }

        actionButton.setText("Modifier");
    }

    /**
     * Modify an employee.
     */
    private void modifyEmployee() {
        Employee employeeToModify = (Employee) objectToModify;
        String usernameEmployee = usernameTextField.getText();

        if(!employeeToModify.getUsername().equals(usernameEmployee) && employeeManager.exists(usernameEmployee) != null){
            displayError("L'employé existe déjà !");
        }
        else{
            employeeToModify.setUsername(usernameEmployee);
            employeeToModify.setFirstName(firstNameTextField.getText());
            employeeToModify.setLastName(lastNameTextField.getText());
            employeeToModify.setSalary(Double.parseDouble(salaryTextField.getText()));
            employeeToModify.setRole(roleChoice.getValue());

            employeeManager.update(employeeToModify);
            ((EmployeesController)menuController).refreshTable();
            dialog.close();
        }
    }

    /**
     * Close the dialog when the user clicks the cancel button.
     */
    @FXML
    protected void handleCancelButtonAction() { dialog.close(); }

    /**
     * Check if all the input TextFields are correctly filled.
     * @return True if the inputs are correct, otherwise false.
     */
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

        if (!firstNameTextField.getText().matches("[a-zA-ZÀ-ÿ]{2,}")) {
            firstNameTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(firstNameLabel.getText()).concat("\" doit contenir au moins 2 caractères !"));
            return false;
        }

        if (!lastNameTextField.getText().matches("[a-zA-ZÀ-ÿ]{2,}")) {
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

    /**
     * Displays error in a Label.
     * @param error The error.
     */
    private void displayError(String error){
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
}
