package Controller.Menu;

import Manager.EmployeeManager;
import Model.Employee;
import Util.Constants;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {

    @FXML
    private JFXTreeTableView<Employee> employeesTreeTable;

    @FXML
    private JFXTextField searchInput;

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
        EmployeeManager employeeManager = new EmployeeManager();
        ObservableList<Employee> employees = FXCollections.observableArrayList(employeeManager.listAll());

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

    }
}
