package Controller.Dialog;

import Controller.Menu.RoomsController;
import Manager.RoomManager;
import Model.Room;
import com.jfoenix.controls.JFXButton;
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
    @FXML
    private JFXButton actionButton;

    private RoomManager roomManager = new RoomManager();


    @FXML
    protected void handleActionButtonAction(ActionEvent event) {

        Room room;

        if(!checkInputs()) return;

        if(actionButton.getText() == "Ajouter") addRoom();
        else modifyRoom();

    }

    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        dialog.close();
    }

    private void addRoom(){

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

    private void modifyRoom(){

        Room roomToModify = (Room) objectToModify;
        int numRoom = Integer.parseInt(numRoomTextField.getText());

        if(roomToModify.getNumber() != numRoom && roomManager.exists(numRoom) != null){
            displayError("Le numéro de chambre existe déjà !");
        }
        else{
            roomToModify.setNumber(numRoom);
            roomToModify.setPrice(Float.parseFloat(priceTextField.getText()));
            roomToModify.setNbGuest(Integer.parseInt(nbGuestsTextField.getText()));

            roomManager.update(roomToModify);
            ((RoomsController)menuController).refreshTable();
            dialog.close();
        }
    }

    @Override
    protected void initObjectToModify() {

        Room room = (Room) objectToModify;

        numRoomTextField.setText(String.valueOf(room.getNumber()));
        priceTextField.setText(String.valueOf(room.getPrice()));
        nbGuestsTextField.setText(String.valueOf(room.getNbGuest()));

        actionButton.setText("Modifier");
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
