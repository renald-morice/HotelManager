package Controller.Menu;

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

public class RoomsController extends MenuController implements Initializable {

    @FXML
    private JFXTreeTableView<Room> roomsTreeTableView;

    @FXML
    private JFXTextField numRoomTextField,minPriceTextField,maxPriceTextField,minNbGuestsTextField;

    @FXML
    private JFXButton modifyRoomButton;

    private ObservableList<Room> rooms;
    private RoomManager roomManager = new RoomManager();

    private Room selectedRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    @FXML
    protected void handleReinitializeButtonAction() {
        numRoomTextField.setText(null);
        minPriceTextField.setText(null);
        maxPriceTextField.setText(null);
        minNbGuestsTextField.setText(null);
        roomsTreeTableView.setPredicate(row -> true);
    }

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

    public void addRoomToTable(Room room) {
        rooms.add(room);
        refreshTable();
    }

    public void refreshTable() {
        roomsTreeTableView.refresh();
    }


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

//        if(startDatePicker.getValue() == null && endDatePicker.getValue() != null) startDatePicker.setValue(endDatePicker.getValue());
//        else if (startDatePicker.getValue() != null && endDatePicker.getValue() == null) endDatePicker.setValue(startDatePicker.getValue());

        return true;
    }


    @FXML
    protected void handleNewRoomButtonAction() {
        loadDialog(Constants.ROOM_DIALOG_FXML, null);
    }

    @FXML
    protected void handleModifyRoomButtonAction() {
        loadDialog(Constants.ROOM_DIALOG_FXML, selectedRoom);
    }


}
