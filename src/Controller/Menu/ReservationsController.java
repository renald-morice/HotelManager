package Controller.Menu;

import Manager.ClientManager;
import Manager.EmployeeManager;
import Manager.ReservationManager;
import Model.Client;
import Model.Employee;
import Model.Reservation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class ReservationsController extends MenuController implements Initializable{

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
        employeeComboBox.getItems().addAll(employeeManager.listAll());

    }

    public void addReservationToTable(Reservation reservation) {
    }

    public void refreshTable() {

    }

    @FXML
    protected void handleFilterButtonAction(ActionEvent event) {
    }

    private boolean checkInputs(){

        return true;
    }


    @FXML
    protected void handleNewReservationButtonAction() {

    }

    @FXML
    protected void handleModifyReservationButtonAction() {

    }

}
