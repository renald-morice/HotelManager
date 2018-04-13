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
import java.util.List;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<Employee, String> idColumn = new JFXTreeTableColumn<>("ID");
        idColumn.setPrefWidth(150);
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getId())));

        JFXTreeTableColumn<Employee, String> usernameColumn = new JFXTreeTableColumn<>("Identifiant");
        usernameColumn.setPrefWidth(150);
        usernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getUsername()));

        JFXTreeTableColumn<Employee, String> firstNameColumn = new JFXTreeTableColumn<>("Prénom");
        firstNameColumn.setPrefWidth(150);
        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getFirstName()));

        JFXTreeTableColumn<Employee, String> lastNameColumn = new JFXTreeTableColumn<>("Nom");
        lastNameColumn.setPrefWidth(150);
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getLastName()));

        JFXTreeTableColumn<Employee, String> salaryColumn = new JFXTreeTableColumn<>("Salaire");
        salaryColumn.setPrefWidth(150);
        salaryColumn.setCellValueFactory(param -> new SimpleStringProperty(Double.toString(param.getValue().getValue().getSalary())));

        JFXTreeTableColumn<Employee, String> roleColumn = new JFXTreeTableColumn<>("Role");
        roleColumn.setPrefWidth(150);
        roleColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getRole().getRole()));

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
        List<Role> roles = roleManager.listAll();
        roleChoice.getItems().add(null);
        roleChoice.getItems().addAll(roles);
    }

    @FXML
    protected void handleFilterButtonAction() {
        if(!checkInputs()) return;

        employeesTreeTable.setPredicate(row ->
            (usernameTextField.getText().equals("") || row.getValue().getUsername().contains(usernameTextField.getText()))
                && (firstNameTextField.getText().equals("") || row.getValue().getFirstName().contains(firstNameTextField.getText()))
                && (lastNameTextField.getText().equals("") || row.getValue().getLastName().contains(lastNameTextField.getText()))
                && (salaryMinTextField.getText().equals("") || row.getValue().getSalary() >= Double.parseDouble(salaryMinTextField.getText()))
                && (salaryMaxTextField.getText().equals("") || row.getValue().getSalary() <= Double.parseDouble(salaryMaxTextField.getText()))
                && (roleChoice.getValue() == null || row.getValue().getRole().equals(roleChoice.getValue()))
        );
    }

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

    @FXML
    protected void addNewEmployee() { loadDialog(Constants.NEW_EMPLOYEE_DIALOG_FXML,null); }

    @FXML
    protected void handleModifyEmployeeButtonAction() {
        loadDialog(Constants.NEW_EMPLOYEE_DIALOG_FXML, selectedEmployee);
    }

    public void addEmployeeToTable(Employee employee) {
        employees.add(employee);
        refreshTable();
    }

    public void refreshTable() { employeesTreeTable.refresh(); }
}
