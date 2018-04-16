package Controller.Dialog;

import Controller.Menu.ReservationsController;
import javafx.fxml.FXML;

/**
 * Delete reservation dialog controller class. Used when we delete a reservation.
 */
public class DeleteReservationDialog extends DialogController {

    /**
     * Delete the reservation when the user clicks the delete button.
     */
    @FXML
    protected void handleDeleteButtonAction() {
        ((ReservationsController)menuController).deleteSelectedReservation();
        dialog.close();
    }

    /**
     * Close the dialog when the user clicks the cancel button.
     */
    @FXML
    protected void handleCancelButtonAction() { dialog.close(); }

    @Override
    protected void initObjectToModify() { }

}
