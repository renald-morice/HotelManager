package Controller.Dialog;

import Controller.Menu.ReservationsController;
import Controller.Session;
import Manager.ClientManager;
import Manager.ReservationManager;
import Manager.RoomManager;
import Model.Client;
import Model.Reservation;
import Model.Room;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeTableCell;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ReservationDialogController extends DialogController implements Initializable {

    @FXML
    private JFXTreeTableView<Room> roomsTreeTableView;
    @FXML
    private Label errorLabel;
    @FXML
    private JFXComboBox<Client> clientComboBox;
    @FXML
    private JFXDatePicker startDatePicker, endDatePicker;

    private ReservationManager reservationManager = new ReservationManager();
    private ClientManager clientManager = new ClientManager();
    private ObservableList<Room> rooms;
    private RoomManager roomManager = new RoomManager();

    private JFXTreeTableColumn<Room, Boolean> selectColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clientComboBox.getItems().addAll(clientManager.listAll());

        JFXTreeTableColumn<Room, String> numberColum = new JFXTreeTableColumn<>("Numéro");
        numberColum.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getNumber())));

        JFXTreeTableColumn<Room, String> priceColumn = new JFXTreeTableColumn<>("Prix");
        priceColumn.setCellValueFactory(param -> new SimpleStringProperty(Float.toString(param.getValue().getValue().getPrice())));

        JFXTreeTableColumn<Room, String> nbGuestColumn = new JFXTreeTableColumn<>("Places");
        nbGuestColumn.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getNbGuest())));

        selectColumn = new JFXTreeTableColumn<>("Selection");
        selectColumn.setCellValueFactory(param -> param.getValue().getValue().getSelectedProperty());
        selectColumn.setCellFactory(param -> new CheckBoxTreeTableCell<>());

        selectColumn.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(4));
        numberColum.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(4));
        priceColumn.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(4));
        nbGuestColumn.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(4.1));

        roomsTreeTableView.setEditable(true);

        roomsTreeTableView.getColumns().setAll(selectColumn, numberColum, priceColumn, nbGuestColumn);

        rooms = FXCollections.observableArrayList(roomManager.listAll());
        final TreeItem<Room> root = new RecursiveTreeItem<>(rooms, RecursiveTreeObject::getChildren);

        roomsTreeTableView.setPredicate(row -> false);

        roomsTreeTableView.setRoot(root);
        roomsTreeTableView.setShowRoot(false);

        startDatePicker.valueProperty().addListener((o,oldVal,newVal) -> {
            filterRooms();
        });

        endDatePicker.valueProperty().addListener((o,oldVal,newVal) -> {
            filterRooms();
        });

    }

    private void filterRooms(){

        startDatePicker.getStyleClass().remove("error-textfield");
        endDatePicker.getStyleClass().remove("error-textfield");
        errorLabel.setVisible(false);

        if(startDatePicker.getValue() != null && endDatePicker.getValue() != null){

            final Date startDate = Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
            final Date endDate = Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));

            if(startDate.compareTo(endDate) <= 0) {
                roomsTreeTableView.setPredicate(row -> row.getValue().isAvailable(startDate, endDate));
                return;
            }
            else{
                startDatePicker.getStyleClass().add("error-textfield");
                endDatePicker.getStyleClass().add("error-textfield");
                displayError("La date de début ne peut pas être supérieure à la date de fin !");
            }
        }

        roomsTreeTableView.setPredicate(row -> false);
    }


    @FXML
    protected void handleActionButtonAction() {
        if(!checkInputs()) return;
        addReservation();
    }

    @FXML
    protected void handleCancelButtonAction() {
        dialog.close();
    }

    private boolean checkInputs(){

        clientComboBox.getStyleClass().remove("error-textfield");
        startDatePicker.getStyleClass().remove("error-textfield");
        endDatePicker.getStyleClass().remove("error-textfield");


        if(clientComboBox.getValue() == null){
            clientComboBox.getStyleClass().add("error-textfield");
            displayError("Aucun client n'est sélectionné !");
            return false;
        }

        if(startDatePicker.getValue() == null){
            startDatePicker.getStyleClass().add("error-textfield");
            displayError("Aucun date de début n'est sélectionnée !");
            return false;
        }

        if(endDatePicker.getValue() == null){
            endDatePicker.getStyleClass().add("error-textfield");
            displayError("Aucun date de fin n'est sélectionnée !");
            return false;
        }


        boolean oneRoomAtLeast = false;

        for(int i = 0; i<roomsTreeTableView.getCurrentItemsCount(); i++){
            if(selectColumn.getCellObservableValue(roomsTreeTableView.getTreeItem(i)).getValue()){
                oneRoomAtLeast = true;
                break;
            }
        }

        if(!oneRoomAtLeast){
            displayError("Aucune chambre de sélectionnée !");
            return false;
        }

        errorLabel.setVisible(false);
        return true;
    }

    private void addReservation(){

        final Date startDate = Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        final Date endDate = Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));

        Set<Room> reservationRooms = new HashSet<>();

        for(int i = 0; i<roomsTreeTableView.getCurrentItemsCount(); i++){
            Room room = roomsTreeTableView.getTreeItem(i).getValue();
            if(room.getSelected()) reservationRooms.add(room);
        }

        Reservation reservation = new Reservation(
                new Date(),
                startDate,
                endDate,
                Session.getInstance().getEmployee(),
                clientComboBox.getValue(),
                reservationRooms
        );

        reservationManager.add(reservation);
        ((ReservationsController) menuController).addReservationToTable(reservation);
        dialog.close();
    }

    @Override
    protected void initObjectToModify() {
    }

    private void displayError(String error){
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
}