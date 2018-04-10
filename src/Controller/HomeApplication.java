package Controller;

import Util.Constants;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeApplication extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML));
        window = primaryStage;
        JFXDecorator decorator = new JFXDecorator(window,root);
        decorator.setCustomMaximize(true);
        decorator.setMaximized(true);
        decorator.setText(Constants.WINDOW_TITLE);
        window.setScene(new Scene(decorator));
        window.setMinWidth(Constants.WINDOW_MIN_WIDTH);
        window.setMinHeight(Constants.WINDOW_MIN_HEIGHT);
        window.show();
    }
}
