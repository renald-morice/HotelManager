package Controller.Menu;

import Manager.EmployeeManager;
import Manager.RoleManager;
import Model.Employee;
import Model.Role;
import Util.Constants;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * EmployeesController class. Handle employee page interactions.
 */
public class EmployeesController extends MenuController implements Initializable {
    @FXML
    private JFXTreeTableView<Employee> employeesTreeTable;

    @FXML
    private JFXTextField usernameTextField,firstNameTextField,lastNameTextField,salaryMinTextField,salaryMaxTextField;

    @FXML
    private JFXComboBox<Role> roleChoice;

    @FXML
    private JFXButton modifyEmployeeButton;

    private EmployeeManager employeeManager = new EmployeeManager();
    private ObservableList<Employee> employees;
    private Employee selectedEmployee;

    /**
     * Initialize all the data.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<Employee, String> idColumn = new JFXTreeTableColumn<>("ID");
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getId())));

        JFXTreeTableColumn<Employee, String> usernameColumn = new JFXTreeTableColumn<>("Identifiant");
        usernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getUsername()));

        JFXTreeTableColumn<Employee, String> firstNameColumn = new JFXTreeTableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getFirstName()));

        JFXTreeTableColumn<Employee, String> lastNameColumn = new JFXTreeTableColumn<>("Nom");
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getLastName()));

        JFXTreeTableColumn<Employee, String> salaryColumn = new JFXTreeTableColumn<>("Salaire");
        salaryColumn.setCellValueFactory(param -> new SimpleStringProperty(Double.toString(param.getValue().getValue().getSalary())));

        JFXTreeTableColumn<Employee, String> roleColumn = new JFXTreeTableColumn<>("Role");
        roleColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getRole().getRole()));

        idColumn.prefWidthProperty().bind(employeesTreeTable.widthProperty().divide(6));
        usernameColumn.prefWidthProperty().bind(employeesTreeTable.widthProperty().divide(6));
        firstNameColumn.prefWidthProperty().bind(employeesTreeTable.widthProperty().divide(6));
        lastNameColumn.prefWidthProperty().bind(employeesTreeTable.widthProperty().divide(6));
        salaryColumn.prefWidthProperty().bind(employeesTreeTable.widthProperty().divide(6));
        roleColumn.prefWidthProperty().bind(employeesTreeTable.widthProperty().divide(6.1));

        // GET DATA
        employees = FXCollections.observableArrayList(employeeManager.listAll());

        final TreeItem<Employee> root = new RecursiveTreeItem<>(employees, RecursiveTreeObject::getChildren);
        employeesTreeTable.setRoot(root);
        employeesTreeTable.setShowRoot(false);
        employeesTreeTable.getColumns().setAll(idColumn,usernameColumn,firstNameColumn,lastNameColumn,salaryColumn,roleColumn);

        employeesTreeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEmployee = employeesTreeTable.getSelectionModel().getSelectedItem().getValue();
                modifyEmployeeButton.setDisable(false);
            }
            else modifyEmployeeButton.setDisable(true);
        });

        // POPULATE FILTER COMBO BOX
        RoleManager roleManager = new RoleManager();
        roleChoice.getItems().add(null);
        roleChoice.getItems().addAll(roleManager.listAll());
    }

    /**
     * Handle reinitialize button.
     */
    @FXML
    protected void handleReinitializeButtonAction() {
        usernameTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        salaryMinTextField.setText("");
        salaryMaxTextField.setText("");
        roleChoice.setValue(null);
        employeesTreeTable.setPredicate(row -> true);
    }

    /**
     * Handle the filter button of the form.
     */
    @FXML
    protected void handleFilterButtonAction() {
        if(!checkInputs()) return;

        employeesTreeTable.setPredicate(row ->
            (usernameTextField.getText().equals("") || row.getValue().getUsername().toUpperCase().contains(usernameTextField.getText().toUpperCase()))
                && (firstNameTextField.getText().equals("") || row.getValue().getFirstName().toUpperCase().contains(firstNameTextField.getText().toUpperCase()))
                && (lastNameTextField.getText().equals("") || row.getValue().getLastName().toUpperCase().contains(lastNameTextField.getText().toUpperCase()))
                && (salaryMinTextField.getText().equals("") || row.getValue().getSalary() >= Double.parseDouble(salaryMinTextField.getText()))
                && (salaryMaxTextField.getText().equals("") || row.getValue().getSalary() <= Double.parseDouble(salaryMaxTextField.getText()))
                && (roleChoice.getValue() == null || row.getValue().getRole().equals(roleChoice.getValue()))
        );
    }

    /**
     * Check all form inputs.
     * @return true if all inputs are correct, else false
     */
    private boolean checkInputs(){

        usernameTextField.getStyleClass().remove("error-textfield");
        firstNameTextField.getStyleClass().remove("error-textfield");
        lastNameTextField.getStyleClass().remove("error-textfield");
        salaryMinTextField.getStyleClass().remove("error-textfield");
        salaryMaxTextField.getStyleClass().remove("error-textfield");

        if(!salaryMinTextField.getText().equals("")){
            try{
                Double.parseDouble(salaryMinTextField.getText());
            } catch (NumberFormatException e){
                salaryMinTextField.getStyleClass().add("error-textfield");
                return false;
            }
        }

        if(!salaryMaxTextField.getText().equals("")){
            try{
                Double.parseDouble(salaryMaxTextField.getText());
            } catch (NumberFormatException e){
                salaryMaxTextField.getStyleClass().add("error-textfield");
                return false;
            }
        }

        if(!salaryMinTextField.getText().equals("") && !salaryMaxTextField.getText().equals("") &&
                Double.parseDouble(salaryMinTextField.getText()) > Double.parseDouble(salaryMaxTextField.getText())){
            salaryMinTextField.getStyleClass().add("error-textfield");
            salaryMaxTextField.getStyleClass().add("error-textfield");
            return false;
        }

        return true;
    }

    /**
     * Handle the new employee button.
     */
    @FXML
    protected void addNewEmployee() { loadDialog(Constants.EMPLOYEE_DIALOG_FXML,null); }

    /**
     * Handle the modify employee button.
     */
    @FXML
    protected void handleModifyEmployeeButtonAction() {
        loadDialog(Constants.EMPLOYEE_DIALOG_FXML, selectedEmployee);
    }

    /**
     * Add one employee to the table.
     * @param employee employee
     */
    public void addEmployeeToTable(Employee employee) {
        employees.add(employee);
        refreshTable();
    }

    /**
     * Refresh table view
     */
    public void refreshTable() { employeesTreeTable.refresh(); }
}
