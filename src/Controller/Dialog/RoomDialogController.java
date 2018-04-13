package Controller.Dialog;

import Controller.Menu.MenuController;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RoomDialogController extends DialogController {



    @FXML
    protected void handleAddRoomButtonAction(ActionEvent event) {

    }

    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        dialog.close();
    }

}
