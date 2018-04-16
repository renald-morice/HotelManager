package Controller;

import Manager.EmployeeManager;
import Util.Constants;
import Util.DataSet;
import Util.Hibernate;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * LoginFormApplication class. Handle login window.
 */
public class LoginFormApplication extends Application {

    private Stage window;

    /**
     * Starting point of the application
     * @param args string params
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Starting point of the window.
     * @param primaryStage javaFX generated stage
     * @throws Exception The exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception  {
        //Hibernate initialization
        Hibernate.init();
        //Load initial data at the first execution
        if ((new EmployeeManager().listAll()).size() == 0) DataSet.data();

        Parent root = FXMLLoader.load(getClass().getResource(Constants.LOGIN_FXML));
        window = primaryStage;
        JFXDecorator decorator = new JFXDecorator(window,root,false,false,false);
        decorator.setOnCloseButtonAction(() -> System.exit(0));
        decorator.setText(Constants.WINDOW_TITLE);
        window.setResizable(false);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(getClass().getResource("/Resources/CSS/main.css").toExternalForm());
        window.setScene(scene);
        window.show();

    }
}
