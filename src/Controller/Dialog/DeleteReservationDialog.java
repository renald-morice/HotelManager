package Controller.Dialog;

import Controller.Menu.ReservationsController;
import javafx.fxml.FXML;

public class DeleteReservationDialog extends DialogController {

    @FXML
    protected void handleDeleteButtonAction() {
        ((ReservationsController)menuController).deleteSelectedReservation();
        dialog.close();
    }

    @FXML
    protected void handleCancelButtonAction() { dialog.close(); }

    @Override
    protected void initObjectToModify() {
    }

}
