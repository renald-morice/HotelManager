package Controller.Dialog;

import Controller.Menu.RoomsController;
import Manager.RoomManager;
import Model.Room;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RoomDialogController extends DialogController {

    @FXML
    private Label numRoomLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label nbGuestsLabel;
    @FXML
    private JFXTextField numRoomTextField;
    @FXML
    private JFXTextField priceTextField;
    @FXML
    private JFXTextField nbGuestsTextField;
    @FXML
    private Label errorLabel;

    private RoomManager roomManager = new RoomManager();


    @FXML
    protected void handleAddRoomButtonAction(ActionEvent event) {

        if(!checkInputs()) return;

        Room room = new Room(
                Integer.parseInt(numRoomTextField.getText()),
                Float.parseFloat(priceTextField.getText()),
                Integer.parseInt(nbGuestsTextField.getText())
        );

        if(roomManager.exists(room.getNumber()) != null){
            displayError("Le numéro de chambre existe déjà !");
        }
        else{
            roomManager.add(room);
            ((RoomsController)menuController).addRoomToTable(room);
            dialog.close();
        }

    }

    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        dialog.close();
    }

    private boolean checkInputs(){

        numRoomTextField.getStyleClass().remove("error-textfield");
        priceTextField.getStyleClass().remove("error-textfield");
        nbGuestsTextField.getStyleClass().remove("error-textfield");

        try{
            Integer.parseInt(numRoomTextField.getText());
        } catch (NumberFormatException e){
            numRoomTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(numRoomLabel.getText()).concat("\" doit contenir un entier !"));
            return false;
        }

        try{
            Float.parseFloat(priceTextField.getText());
        } catch (NumberFormatException e){
            priceTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(priceLabel.getText()).concat("\" doit contenir un flottant !"));
            return false;
        }

        try{
            Integer.parseInt(nbGuestsTextField.getText());
        } catch (NumberFormatException e){
            nbGuestsTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(nbGuestsLabel.getText()).concat("\" doit contenir un entier !"));
            return false;
        }

        errorLabel.setVisible(false);
        return true;
    }

    private void displayError(String error){
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }

}
