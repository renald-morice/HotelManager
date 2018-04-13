package Controller.Menu;

import Manager.EmployeeManager;
import Manager.RoleManager;
import Model.Employee;
import Model.Role;
import Util.MD5Hashing;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeesController extends MenuController implements Initializable {
    @FXML
    private JFXTreeTableView<Employee> employeesTreeTable;

    @FXML
    private JFXTextField searchInput;

    private EmployeeManager employeeManager;
    private ObservableList<Employee> employees;

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

        // GET DATA
        employeeManager = new EmployeeManager();
        employees = FXCollections.observableArrayList(employeeManager.listAll());

        final TreeItem<Employee> root = new RecursiveTreeItem<>(employees, RecursiveTreeObject::getChildren);
        employeesTreeTable.setRoot(root);
        employeesTreeTable.setShowRoot(false);
        employeesTreeTable.getColumns().setAll(idColumn,usernameColumn,firstNameColumn,lastNameColumn,salaryColumn);

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> employeesTreeTable.setPredicate(employeeTreeItem -> employeeTreeItem.getValue().getUsername().contains(newValue)
            || Integer.toString(employeeTreeItem.getValue().getId()).contains(newValue)
            || employeeTreeItem.getValue().getUsername().contains(newValue)
            || employeeTreeItem.getValue().getFirstName().contains(newValue)
            || employeeTreeItem.getValue().getLastName().contains(newValue)
            || Double.toString(employeeTreeItem.getValue().getSalary()).contains(newValue)
        ));
    }

    @FXML
    protected void addNewEmployee() {
        Dialog dialog = new Dialog();
        dialog.setTitle("Ajouter un nouvel employé");

//        JFXDialogLayout content = new JFXDialogLayout();
//        content.setHeading(new Text("Ajouter un nouvel employé"));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        JFXTextField username = new JFXTextField();
        username.setPromptText("Identifiant");
        JFXTextField firstName = new JFXTextField();
        firstName.setPromptText("Prénom");
        JFXTextField lastName = new JFXTextField();
        lastName.setPromptText("Nom");
        JFXTextField salary = new JFXTextField();
        salary.setPromptText("Salaire");

        RoleManager roleManager = new RoleManager();
        List<Role> roles = roleManager.listAll();
        JFXComboBox<Role> roleChoice = new JFXComboBox<>();
        roleChoice.getItems().addAll(roles);
        roleChoice.setPromptText("Choisir le role de l'employé");

//        JFXButton close = new JFXButton("Annuler");
//        close.setOnAction(event -> dialog.close());
//        JFXButton add = new JFXButton("Ajouter");
//        add.setOnAction(event -> {
//            Role role = roleChoice.getValue();
//            Employee employee = new Employee(username.getText(),MD5Hashing.hash("test"),firstName.getText(),lastName.getText(),Double.parseDouble(salary.getText()),role);
//            employeeManager.add(employee);
//            employeesTreeTable.refresh();
//            dialog.close();
//        });

        grid.add(username, 0, 0);
        grid.add(firstName, 0, 1);
        grid.add(lastName, 0, 2);
        grid.add(salary, 0, 3);
        grid.add(roleChoice, 0, 4);

        ButtonType addButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Role role = roleChoice.getValue();
                Employee employee = new Employee(username.getText(),MD5Hashing.hash("test"),firstName.getText(),lastName.getText(),Double.parseDouble(salary.getText()),role);
                employeeManager.add(employee);
                employees.add(employee);
                employeesTreeTable.refresh();
                dialog.close();
            }
            return null;
        });

        dialog.getDialogPane().setContent(grid);

        //content.setBody(grid);
        //content.setActions(close,add);

        dialog.show();
    }
}
