package Controller.Menu;

import Manager.RoomManager;
import Model.Reservation;
import Model.Room;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {

    @FXML
    private JFXTreeTableView<Room> roomsTreeTableView;

    @FXML
    private JFXTextField numRoomTextField;
    @FXML
    private JFXTextField minPriceTextField;
    @FXML
    private JFXTextField maxPriceTextField;
    @FXML
    private JFXTextField minNbGuestsTextField;
    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    @FXML
    private JFXButton filterButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JFXTreeTableColumn<Room, String> numberColum = new JFXTreeTableColumn<>("Numéro");
        numberColum.setPrefWidth(150);
        numberColum.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getNumber())));

        JFXTreeTableColumn<Room, String> priceColumn = new JFXTreeTableColumn<>("Prix");
        priceColumn.setPrefWidth(150);
        priceColumn.setCellValueFactory(param -> new SimpleStringProperty(Float.toString(param.getValue().getValue().getPrice())));

        JFXTreeTableColumn<Room, String> nbGuestColumn = new JFXTreeTableColumn<>("Places");
        nbGuestColumn.setPrefWidth(150);
        nbGuestColumn.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getNbGuest())));

        numberColum.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(3));
        priceColumn.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(3));
        nbGuestColumn.prefWidthProperty().bind(roomsTreeTableView.widthProperty().divide(3));


        // GET DATA
        RoomManager roomManager = new RoomManager();
        ObservableList<Room> rooms = FXCollections.observableArrayList(roomManager.listAll());

        final TreeItem<Room> root = new RecursiveTreeItem<>(rooms, RecursiveTreeObject::getChildren);
        roomsTreeTableView.setRoot(root);
        roomsTreeTableView.setShowRoot(false);
        roomsTreeTableView.getColumns().setAll(numberColum, priceColumn, nbGuestColumn);

    }

    @FXML
    protected void handleFilterButtonAction(ActionEvent event) {

        if(!checkInputs()) return;

        roomsTreeTableView.setPredicate(row ->
                (numRoomTextField.getText().equals("") || row.getValue().getNumber() == Integer.parseInt(numRoomTextField.getText()))
                && (minPriceTextField.getText().equals("") || row.getValue().getPrice() >= Float.parseFloat(minPriceTextField.getText()))
                && (maxPriceTextField.getText().equals("") || row.getValue().getPrice() <= Float.parseFloat(maxPriceTextField.getText()))
                && (minNbGuestsTextField.getText().equals("") || row.getValue().getNbGuest() >= Float.parseFloat(minNbGuestsTextField.getText()))
                && (startDatePicker.getValue() == null || row.getValue().isAvailable(
                        Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))),
                        Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())))
                    )
                )

        );
    }

    public boolean checkInputs(){

        numRoomTextField.setStyle("-fx-border-color: default;");
        minPriceTextField.setStyle("-fx-border-color: default;");
        maxPriceTextField.setStyle("-fx-border-color: default;");
        minNbGuestsTextField.setStyle("-fx-border-color: default;");

        if(!numRoomTextField.getText().equals("")){
            try{
                Integer.parseInt(numRoomTextField.getText());
            } catch (NumberFormatException e){
                numRoomTextField.setStyle("-fx-border-color: red;");
                return false;
            }
        }

        if(!minPriceTextField.getText().equals("")){
            try{
                Float.parseFloat(minPriceTextField.getText());
            } catch (NumberFormatException e){
                minPriceTextField.setStyle("-fx-border-color: red;");
                return false;
            }
        }

        if(!maxPriceTextField.getText().equals("")){
            try{
                Float.parseFloat(maxPriceTextField.getText());
            } catch (NumberFormatException e){
                maxPriceTextField.setStyle("-fx-border-color: red;");
                return false;
            }
        }

        if(!minPriceTextField.getText().equals("") && !maxPriceTextField.getText().equals("") &&
                Float.parseFloat(minPriceTextField.getText()) > Float.parseFloat(maxPriceTextField.getText())){
            minPriceTextField.setStyle("-fx-border-color: red;");
            maxPriceTextField.setStyle("-fx-border-color: red;");
            return false;
        }

        if(!minNbGuestsTextField.getText().equals("")) {
            try {
                Integer.parseInt(minNbGuestsTextField.getText());
            } catch (NumberFormatException e) {
                minNbGuestsTextField.setStyle("-fx-border-color: red;");
                return false;
            }
        }

        if(startDatePicker.getValue() == null && endDatePicker.getValue() != null) startDatePicker.setValue(endDatePicker.getValue());
        else if (startDatePicker.getValue() != null && endDatePicker.getValue() == null) endDatePicker.setValue(startDatePicker.getValue());

        return true;
    }


}
