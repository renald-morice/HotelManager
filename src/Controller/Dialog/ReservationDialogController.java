package Controller.Dialog;

import Manager.ReservationManager;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReservationDialogController extends DialogController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private JFXButton actionButton;

    private ReservationManager reservationManager = new ReservationManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void handleActionButtonAction() {
        if(!checkInputs()) return;
        if(actionButton.getText().equals("Ajouter")) addReservation();
        else modifyReservation();

    }

    @FXML
    protected void handleCancelButtonAction() {
        dialog.close();
    }

    private boolean checkInputs(){
        return true;
    }

    private void addReservation(){

    }

    private void modifyReservation() {

    }

    @Override
    protected void initObjectToModify() {

        actionButton.setText("Modifier");
    }

    private void displayError(String error){
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
}