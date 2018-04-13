package Controller.Menu;

import Controller.Dialog.DialogController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public abstract class MenuController {

    protected StackPane contentStackPane;

    public void setContentStackPane(StackPane stackPane) {
        this.contentStackPane = stackPane;
    }

    protected void loadDialog(String fxmlFile){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            JFXDialogLayout dialogLayout = loader.load();

            JFXDialog dialog = new JFXDialog(contentStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            DialogController dialogController = loader.getController();
            dialogController.setDialog(dialog);
            dialogController.setMenuController(this);
            dialog.setOverlayClose(false);
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
