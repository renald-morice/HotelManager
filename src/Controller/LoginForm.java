package Controller;

import Manager.EmployeeManager;
import Model.Employee;
import Util.Hibernate;
import Util.MD5Hashing;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginForm extends Application {

    private Stage window;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label badIDsText;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception  {

        //Hibernate initialization
        Hibernate.init();

        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginForm.fxml"));
        window = primaryStage;
        window.setTitle("Hotel Manager");
        window.setScene(new Scene(root));
        window.show();
    }

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
