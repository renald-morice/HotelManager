package Controller.Menu;

import Controller.Session;
import Manager.RoomManager;
import Model.Room;
import Util.Constants;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * RoomsController class. Handle room page interactions.
 */
public class RoomsController extends MenuController implements Initializable {

    @FXML
    private JFXTreeTableView<Room> roomsTreeTableView;

    @FXML
    private JFXTextField numRoomTextField,minPriceTextField,maxPriceTextField,minNbGuestsTextField;

    @FXML
    private JFXButton addRoomButton,modifyRoomButton;

    private ObservableList<Room> rooms;
    private RoomManager roomManager = new RoomManager();

    private Room selectedRoom;

    /**
     * Initialize all the data.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.getInstance().getEmployee().getRole().getAccessLevel() < Constants.ACCESS_LEVEL_MIN) {
            modifyRoomButton.setManaged(false);
            addRoomButton.setManaged(false);
        }

        JFXTreeTableColumn<Room, String> numberColum = new JFXTreeTableColumn<>("Numéro");
        numberColum.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getNumber())));

        JFXTreeTableColumn<Room, String> priceColumn = new JFXTreeTableColumn<>("Prix");
        priceColumn.setCellValueFactory(param -> new SimpleStringProperty(Float.toString(param.getValue().getValue().getPrice())));

        JFXTreeTableColumn<Room, String> nbGuestColumn = new JFXTreeTableColumn<>("Places");
        nbGuestColumn.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getNbGuest())));

        numberColum.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(3));
        priceColumn.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(3));
        nbGuestColumn.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(3.1));

        roomsTreeTableView.getColumns().setAll(numberColum, priceColumn, nbGuestColumn);

        rooms = FXCollections.observableArrayList(roomManager.listAll());
        final TreeItem<Room> root = new RecursiveTreeItem<>(rooms, RecursiveTreeObject::getChildren);

        roomsTreeTableView.setRoot(root);
        roomsTreeTableView.setShowRoot(false);

        roomsTreeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRoom = roomsTreeTableView.getSelectionModel().getSelectedItem().getValue();
                modifyRoomButton.setDisable(false);
            }
            else modifyRoomButton.setDisable(true);
        });

    }

    /**
     * Handle reinitialize button.
     */
    @FXML
    protected void handleReinitializeButtonAction() {
        numRoomTextField.setText(null);
        minPriceTextField.setText(null);
        maxPriceTextField.setText(null);
        minNbGuestsTextField.setText(null);
        roomsTreeTableView.setPredicate(row -> true);
    }

    /**
     * Handle the filter button of the form.
     * @param event filter button event
     */
    @FXML
    protected void handleFilterButtonAction(ActionEvent event) {
        if(!checkInputs()) return;

        roomsTreeTableView.setPredicate(row ->
                (numRoomTextField.getText().equals("") || row.getValue().getNumber() == Integer.parseInt(numRoomTextField.getText()))
                && (minPriceTextField.getText().equals("") || row.getValue().getPrice() >= Float.parseFloat(minPriceTextField.getText()))
                && (maxPriceTextField.getText().equals("") || row.getValue().getPrice() <= Float.parseFloat(maxPriceTextField.getText()))
                && (minNbGuestsTextField.getText().equals("") || row.getValue().getNbGuest() >= Float.parseFloat(minNbGuestsTextField.getText()))
        );
    }

    /**
     * Add one room to the table.
     * @param room new room
     */
    public void addRoomToTable(Room room) {
        rooms.add(room);
        refreshTable();
    }

    /**
     * Refresh table view.
     */
    public void refreshTable() {
        roomsTreeTableView.refresh();
    }

    /**
     * Check all form inputs.
     * @return true if all inputs are correct, else false
     */
    private boolean checkInputs(){

        numRoomTextField.getStyleClass().remove("error-textfield");
        minPriceTextField.getStyleClass().remove("error-textfield");
        maxPriceTextField.getStyleClass().remove("error-textfield");
        minNbGuestsTextField.getStyleClass().remove("error-textfield");

        if(!numRoomTextField.getText().equals("")){
            try{
                Integer.parseInt(numRoomTextField.getText());
            } catch (NumberFormatException e){
                numRoomTextField.getStyleClass().add("error-textfield");
                return false;
            }
        }

        if(!minPriceTextField.getText().equals("")){
            try{
                Float.parseFloat(minPriceTextField.getText());
            } catch (NumberFormatException e){
                minPriceTextField.getStyleClass().add("error-textfield");
                return false;
            }
        }

        if(!maxPriceTextField.getText().equals("")){
            try{
                Float.parseFloat(maxPriceTextField.getText());
            } catch (NumberFormatException e){
                maxPriceTextField.getStyleClass().add("error-textfield");
                return false;
            }
        }

        if(!minPriceTextField.getText().equals("") && !maxPriceTextField.getText().equals("") &&
                Float.parseFloat(minPriceTextField.getText()) > Float.parseFloat(maxPriceTextField.getText())){
            minPriceTextField.getStyleClass().add("error-textfield");
            maxPriceTextField.getStyleClass().add("error-textfield");
            return false;
        }

        if(!minNbGuestsTextField.getText().equals("")) {
            try {
                Integer.parseInt(minNbGuestsTextField.getText());
            } catch (NumberFormatException e) {
                minNbGuestsTextField.getStyleClass().add("error-textfield");
                return false;
            }
        }

        return true;
    }

    /**
     * Handle the new room button.
     */
    @FXML
    protected void handleNewRoomButtonAction() { loadDialog(Constants.ROOM_DIALOG_FXML, null); }

    /**
     * Handle the modify room button.
     */
    @FXML
    protected void handleModifyRoomButtonAction() {
        loadDialog(Constants.ROOM_DIALOG_FXML, selectedRoom);
    }


}
