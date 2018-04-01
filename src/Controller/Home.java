package Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Home extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Hotel Manager");
        GridPane grid2 = new GridPane();
        Scene scene2 = new Scene(grid2, 500,500);
        window.setScene(scene2);
        window.show();
    }
}
