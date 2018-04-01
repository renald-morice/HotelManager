package Controller;

import Manager.EmployeeManager;
import Model.Employee;
import Util.Hibernate;
import Util.MD5Hashing;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Application {

    private Stage window;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Hibernate initialization
        Hibernate.init();

        EmployeeManager employeeManager = new EmployeeManager();

        //employeeManager.add("lbotho","password","Loïc","Bothorel",2000);

        window = primaryStage;
        window.setTitle("Hotel Manager");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        Text connectionText = new Text("Connexion");
        connectionText.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
        grid.add(connectionText,0,0);

        Label userNameLabel = new Label("Nom d'utilisateur");
        grid.add(userNameLabel,0,1);

        TextField userNameTextField = new TextField();
        userNameTextField.setPromptText("username");
        grid.add(userNameTextField,1,1);

        Label passwordLabel = new Label("Mot de passe");
        grid.add(passwordLabel,0,2);

        PasswordField passwordField = new PasswordField();
        userNameTextField.setPromptText("password");
        grid.add(passwordField,1,2);

        Button connectionButton = new Button("Connexion");
        grid.add(connectionButton, 0,3);

        Text badIDsText = new Text("Identifiant(s) incorrect(s)");
        badIDsText.setFill(Color.RED);
        badIDsText.setVisible(false);
        grid.add(badIDsText,0,4);

        connectionButton.setOnAction(event -> {
            badIDsText.setVisible(false);
            Employee employee = employeeManager.login(userNameTextField.getText(), MD5Hashing.hash(passwordField.getText()));
            if(employee == null) badIDsText.setVisible(true);
            else{
                new Home().start(new Stage());
                window.close();
            }
        });

        Scene scene = new Scene(grid, 500,500);
        window.setScene(scene);
        window.show();

    }
}
