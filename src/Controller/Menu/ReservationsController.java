package Controller.Menu;

import Manager.ClientManager;
import Manager.EmployeeManager;
import Manager.ReservationManager;
import Model.Client;
import Model.Employee;
import Model.Reservation;
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


public class ReservationsController extends MenuController implements Initializable{

    @FXML
    private JFXTreeTableView<Reservation> reservationsTreeTableView;

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
    private JFXButton modifyReservationButton;

    private ObservableList<Reservation> reservations;
    private ReservationManager reservationManager = new ReservationManager();
    private EmployeeManager employeeManager = new EmployeeManager();
    private ClientManager clientManager = new ClientManager();

    private Reservation selectedReservation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        employeeComboBox.getItems().add(null);
        employeeComboBox.getItems().addAll(employeeManager.listAll());
        clientComboBox.getItems().add(null);
        clientComboBox.getItems().addAll(clientManager.listAll());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


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
                modifyReservationButton.setDisable(false);
            }
            else modifyReservationButton.setDisable(true);
        });

    }

    @FXML
    protected void handleReinitializeButtonAction() {
        reservationDatePicker.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        employeeComboBox.setValue(null);
        clientComboBox.setValue(null);
        reservationsTreeTableView.setPredicate(row -> true);
    }

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
                /*&& startDatePicker.getValue() == null || row.getValue().isAvailable(
                        Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))),
                        Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())))
                )*/
        );
    }

    public void addReservationToTable(Reservation reservation) {
        reservations.add(reservation);
        refreshTable();
    }


    public void refreshTable() {
        reservationsTreeTableView.refresh();
    }


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


    @FXML
    protected void handleNewReservationButtonAction() {
        loadDialog(Constants.RESERVATION_DIALOG_FXML, null);
    }

    @FXML
    protected void handleModifyReservationButtonAction() {
        loadDialog(Constants.RESERVATION_DIALOG_FXML, selectedReservation);
    }

}
