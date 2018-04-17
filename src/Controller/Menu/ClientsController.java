package Controller.Menu;

import Manager.ClientManager;
import Model.Client;
import Util.Constants;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ClientsController class. Handle client page interactions.
 */
public class ClientsController extends MenuController implements Initializable {
    @FXML
    private JFXTreeTableView<Client> clientsTreeTable;

    @FXML
    private JFXTextField firstNameTextField,lastNameTextField,phoneNumberTextField,emailTextField;

    @FXML
    private JFXButton modifyClientButton;

    private ClientManager clientManager = new ClientManager();
    private ObservableList<Client> clients;
    private Client selectedClient;

    /**
     * Initialize all the data.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<Client, String> idColumn = new JFXTreeTableColumn<>("ID");
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getId())));

        JFXTreeTableColumn<Client, String> firstNameColumn = new JFXTreeTableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getFirstName()));

        JFXTreeTableColumn<Client, String> lastNameColumn = new JFXTreeTableColumn<>("Nom");
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getLastName()));

        JFXTreeTableColumn<Client, String> phoneNumberColumn = new JFXTreeTableColumn<>("Numéro de téléphone");
        phoneNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getPhoneNumber()));

        JFXTreeTableColumn<Client, String> emailColumn = new JFXTreeTableColumn<>("Email");
        emailColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getEmail()));

        int divider = 5;
        idColumn.prefWidthProperty().bind(clientsTreeTable.widthProperty().divide(divider));
        firstNameColumn.prefWidthProperty().bind(clientsTreeTable.widthProperty().divide(divider));
        lastNameColumn.prefWidthProperty().bind(clientsTreeTable.widthProperty().divide(divider));
        phoneNumberColumn.prefWidthProperty().bind(clientsTreeTable.widthProperty().divide(divider));
        emailColumn.prefWidthProperty().bind(clientsTreeTable.widthProperty().divide(divider+0.2));

        // GET DATA
        clients = FXCollections.observableArrayList(clientManager.listAll());

        final TreeItem<Client> root = new RecursiveTreeItem<>(clients, RecursiveTreeObject::getChildren);
        clientsTreeTable.setRoot(root);
        clientsTreeTable.setShowRoot(false);
        clientsTreeTable.getColumns().setAll(idColumn,firstNameColumn,lastNameColumn,phoneNumberColumn,emailColumn);

        clientsTreeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedClient = clientsTreeTable.getSelectionModel().getSelectedItem().getValue();
                modifyClientButton.setDisable(false);
            }
            else modifyClientButton.setDisable(true);
        });
    }

    /**
     * Handle reinitialize button.
     */
    @FXML
    protected void handleReinitializeButtonAction() {
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        phoneNumberTextField.setText("");
        emailTextField.setText("");
        clientsTreeTable.setPredicate(row -> true);
    }

    /**
     * Handle the filter button of the form.
     */
    @FXML
    protected void handleFilterButtonAction() {
        if(!checkInputs()) return;

        clientsTreeTable.setPredicate(row ->
                (firstNameTextField.getText().equals("") || row.getValue().getFirstName().toUpperCase().contains(firstNameTextField.getText().toUpperCase()))
                        && (lastNameTextField.getText().equals("") || row.getValue().getLastName().toUpperCase().contains(lastNameTextField.getText().toUpperCase()))
                        && (phoneNumberTextField.getText().equals("") || row.getValue().getPhoneNumber().toUpperCase().contains(phoneNumberTextField.getText().toUpperCase()))
                        && (emailTextField.getText().equals("") || row.getValue().getEmail().toUpperCase().contains(emailTextField.getText().toUpperCase()))
        );
    }

    /**
     * Check all form inputs.
     * @return true if all inputs are correct, else false
     */
    private boolean checkInputs(){
        firstNameTextField.getStyleClass().remove("error-textfield");
        lastNameTextField.getStyleClass().remove("error-textfield");
        phoneNumberTextField.getStyleClass().remove("error-textfield");
        emailTextField.getStyleClass().remove("error-textfield");

        return true;
    }

    /**
     * Handle the new client button.
     */
    @FXML
    protected void addNewClient() { loadDialog(Constants.CLIENT_DIALOG_FXML,null); }

    /**
     * Handle the modify client button.
     */
    @FXML
    protected void handleModifyClientButtonAction() {
        loadDialog(Constants.CLIENT_DIALOG_FXML, selectedClient);
    }

    /**
     * Add one client to the table.
     * @param client client
     */
    public void addClientToTable(Client client) {
        clients.add(client);
        refreshTable();
    }

    /**
     * Refresh table view
     */
    public void refreshTable() { clientsTreeTable.refresh(); }

}
