package Controller;

import Manager.EmployeeManager;
import Model.Employee;
import Util.Hibernate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormApplication extends Application {

    private Stage window;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Hibernate initialization
        Hibernate.init();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/LoginForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Hotel Manager");
        window.setScene(new Scene(root));
        window.show();
    }
}
