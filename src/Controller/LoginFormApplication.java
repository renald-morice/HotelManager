package Controller;

import Util.Hibernate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFormApplication extends Application {

    private Stage window;

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
}
