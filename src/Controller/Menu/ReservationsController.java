package Controller.Menu;

import Manager.ClientManager;
import Manager.EmployeeManager;
import Manager.ReservationManager;
import Model.Client;
import Model.Employee;
import Model.Reservation;
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
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * ReservationsController class. Handle reservation page interactions.
 */
public class ReservationsController extends MenuController implements Initializable{

    @FXML
    private JFXTreeTableView<Reservation> reservationsTreeTableView;
    @FXML
    private JFXTreeTableView<Room> roomsTreeTableView;

    @FXML
    private JFXDatePicker reservationDatePicker;
    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    @FXML
    private JFXComboBox<Employee> employeeComboBox;
    @FXML
    private JFXComboBox<Client> clientComboBox;

    @FXML
    private JFXButton deleteReservationButton;

    private ObservableList<Reservation> reservations;
    private ReservationManager reservationManager = new ReservationManager();
    private EmployeeManager employeeManager = new EmployeeManager();
    private ClientManager clientManager = new ClientManager();

    private Reservation selectedReservation;

    /**
     * Initialize all the data.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        employeeComboBox.getItems().add(null);
        employeeComboBox.getItems().addAll(employeeManager.listAll());
        clientComboBox.getItems().add(null);
        clientComboBox.getItems().addAll(clientManager.listAll());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        /*------------------------*/
        /* Reservation Table View */
        /*------------------------*/

        JFXTreeTableColumn<Reservation, String> startDateColumColum = new JFXTreeTableColumn<>("Date de début");
        startDateColumColum.setCellValueFactory(param -> new SimpleStringProperty(df.format(param.getValue().getValue().getStartDate())));

        JFXTreeTableColumn<Reservation, String> endDateColumColum = new JFXTreeTableColumn<>("Date de fin");
        endDateColumColum.setCellValueFactory(param -> new SimpleStringProperty(df.format(param.getValue().getValue().getEndDate())));

        JFXTreeTableColumn<Reservation, String> clientColumColum = new JFXTreeTableColumn<>("Client");
        clientColumColum.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getClient().toString()));

        JFXTreeTableColumn<Reservation, String> reservationDateColumColum = new JFXTreeTableColumn<>("Date de réservation");
        reservationDateColumColum.setCellValueFactory(param -> new SimpleStringProperty(df.format(param.getValue().getValue().getReservationDate())));

        JFXTreeTableColumn<Reservation, String> employeeColumColum = new JFXTreeTableColumn<>("Employé");
        employeeColumColum.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getEmployee().toString()));

        startDateColumColum.prefWidthProperty().bind(reservationsTreeTableView.widthProperty().divide(5));
        endDateColumColum.prefWidthProperty().bind(reservationsTreeTableView.widthProperty().divide(5));
        clientColumColum.prefWidthProperty().bind(reservationsTreeTableView.widthProperty().divide(5));
        reservationDateColumColum.prefWidthProperty().bind(reservationsTreeTableView.widthProperty().divide(5));
        employeeColumColum.prefWidthProperty().bind(reservationsTreeTableView.widthProperty().divide(5.1));

        reservationsTreeTableView.getColumns().setAll(
                startDateColumColum,
                endDateColumColum,
                clientColumColum,
                reservationDateColumColum,
                employeeColumColum
        );

        reservations = FXCollections.observableArrayList(reservationManager.listAll());
        final TreeItem<Reservation> root = new RecursiveTreeItem<>(reservations, RecursiveTreeObject::getChildren);

        reservationsTreeTableView.setRoot(root);
        reservationsTreeTableView.setShowRoot(false);

        reservationsTreeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedReservation = reservationsTreeTableView.getSelectionModel().getSelectedItem().getValue();
                deleteReservationButton.setDisable(false);
                displayRoomsOfReservation();
            }
            else{
                selectedReservation = null;
                deleteReservationButton.setDisable(true);
                roomsTreeTableView.setRoot(null);
            }
        });

        /*---------------------------------*/
        /* Rooms of reservation Table View */
        /*---------------------------------*/

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

    }

    /**
     * Display all rooms of the selected reservation in the rooms table view
     */
    private void displayRoomsOfReservation(){
        ObservableList<Room> rooms = FXCollections.observableArrayList(selectedReservation.getRooms());
        final TreeItem<Room> root = new RecursiveTreeItem<>(rooms, RecursiveTreeObject::getChildren);
        roomsTreeTableView.setRoot(root);
        roomsTreeTableView.setShowRoot(false);
    }

    /**
     * Handle reinitialize button.
     */
    @FXML
    protected void handleReinitializeButtonAction() {
        reservationDatePicker.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        employeeComboBox.setValue(null);
        clientComboBox.setValue(null);
        reservationsTreeTableView.setPredicate(row -> true);
    }

    /**
     * Handle the filter button of the form.
     * @param event filter button event
     */
    @FXML
    protected void handleFilterButtonAction(ActionEvent event) {
        if(!checkInputs()) return;

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

        final Date filterReservationDate = reservationDatePicker.getValue() != null ? Date.from(Instant.from(reservationDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))) : null;
        final Date filterStartDate = startDatePicker.getValue() != null ? Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))) : null;
        final Date filterEndDate = endDatePicker.getValue() != null ? Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))) : null;

        reservationsTreeTableView.setPredicate(row ->
                (filterReservationDate == null || df.format(filterReservationDate).equals(df.format(row.getValue().getReservationDate())))
                && (filterStartDate == null || row.getValue().getStartDate().compareTo(filterStartDate) >= 0)
                && (filterEndDate == null || row.getValue().getEndDate().compareTo(filterEndDate) <= 0)
                && (employeeComboBox.getValue() == null || row.getValue().getEmployee().equals(employeeComboBox.getValue()))
                && (clientComboBox.getValue() == null || row.getValue().getClient().equals(clientComboBox.getValue()))
        );
    }

    /**
     * Add one reservation to the table.
     * @param reservation new reservation
     */
    public void addReservationToTable(Reservation reservation) {
        reservations.add(reservation);
        refreshTable();
    }

    /**
     * Refresh table view
     */
    public void refreshTable() {
        reservationsTreeTableView.refresh();
    }

    /**
     * Check all form inputs.
     * @return true if all inputs are correct, else false
     */
    private boolean checkInputs(){

        startDatePicker.getStyleClass().remove("error-textfield");
        endDatePicker.getStyleClass().remove("error-textfield");

        if(startDatePicker.getValue() != null && endDatePicker.getValue() != null){
            Date startDate = Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
            Date endDate = Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));

            if(startDate.compareTo(endDate) > 0){
                startDatePicker.getStyleClass().add("error-textfield");
                endDatePicker.getStyleClass().add("error-textfield");
                return false;
            }
        }

        return true;
    }

    /**
     * Handle the new reservation button.
     */
    @FXML
    protected void handleNewReservationButtonAction() {
        loadDialog(Constants.RESERVATION_DIALOG_FXML, null);
    }

    /**
     * Handle the delete room button.
     */
    @FXML
    protected void handleDeleteReservationButtonAction() {
        loadDialog(Constants.DELETE_RESERVATION_DIALOG_FXML, null);
    }

    /**
     * Delete selected reservation of the table view.
     */
    public void deleteSelectedReservation(){

        reservationManager.delete(selectedReservation);
        reservations.remove(selectedReservation);
        refreshTable();
    }

}
