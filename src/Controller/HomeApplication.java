package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApplication extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/Home.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        window.setTitle("Hotel Manager");
        window.setScene(new Scene(root));
        window.show();
    }
}
