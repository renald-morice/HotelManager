package Controller;

import Util.Constants;
import Util.DataSet;
import Util.Hibernate;
import com.jfoenix.controls.JFXDecorator;
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
        //DataSet.data();
        Parent root = FXMLLoader.load(getClass().getResource(Constants.LOGIN_FXML));
        window = primaryStage;
        JFXDecorator decorator = new JFXDecorator(window,root,false,false,false);
        decorator.setOnCloseButtonAction(() -> System.exit(0));
        decorator.setText(Constants.WINDOW_TITLE);
        window.setResizable(false);
        window.setScene(new Scene(decorator));
        window.show();

    }
}
