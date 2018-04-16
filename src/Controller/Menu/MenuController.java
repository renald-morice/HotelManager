package Controller.Menu;

import Controller.Dialog.DialogController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * MenuController abstract class.
 */
public abstract class MenuController {

    protected StackPane contentStackPane;

    /**
     * Set the stackPane
     * @param stackPane content stackPane
     */
    public void setContentStackPane(StackPane stackPane) {
        this.contentStackPane = stackPane;
    }

    /**
     * Load dialog form to create or modify an object
     * @param fxmlFile FXML dialog UI file
     * @param objectToModify object to modify
     */
    protected void loadDialog(String fxmlFile, Object objectToModify){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            JFXDialogLayout dialogLayout = loader.load();

            JFXDialog dialog = new JFXDialog(contentStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            DialogController dialogController = loader.getController();
            dialogController.setDialog(dialog);
            dialogController.setMenuController(this);
            if(objectToModify != null) dialogController.setObjectToModify(objectToModify);
            dialog.setOverlayClose(false);
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
