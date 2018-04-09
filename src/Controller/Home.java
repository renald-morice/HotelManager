package Controller;

import Util.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Home extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML));
        window = primaryStage;
        window.setTitle(Constants.WINDOW_TITLE);
        window.setScene(new Scene(root));
        window.setMaximized(true);
        window.setMinWidth(Constants.WINDOW_MIN_WIDTH);
        window.setMinHeight(Constants.WINDOW_MIN_HEIGHT);
        window.show();
    }
}
