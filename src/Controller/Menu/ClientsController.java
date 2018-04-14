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


    @FXML
    protected void handleFilterButtonAction() {
        if(!checkInputs()) return;

        clientsTreeTable.setPredicate(row ->
                (firstNameTextField.getText().equals("") || row.getValue().getFirstName().contains(firstNameTextField.getText()))
                        && (lastNameTextField.getText().equals("") || row.getValue().getLastName().contains(lastNameTextField.getText()))
                        && (phoneNumberTextField.getText().equals("") || row.getValue().getPhoneNumber().contains(phoneNumberTextField.getText()))
                        && (emailTextField.getText().equals("") || row.getValue().getEmail().contains(emailTextField.getText()))
        );
    }

    private boolean checkInputs(){
        firstNameTextField.getStyleClass().remove("error-textfield");
        lastNameTextField.getStyleClass().remove("error-textfield");
        phoneNumberTextField.getStyleClass().remove("error-textfield");
        emailTextField.getStyleClass().remove("error-textfield");

        return true;
    }

    @FXML
    protected void addNewClient() { loadDialog(Constants.CLIENT_DIALOG_FXML,null); }

    @FXML
    protected void handleModifyClientButtonAction() {
        loadDialog(Constants.CLIENT_DIALOG_FXML, selectedClient);
    }

    public void addClientToTable(Client client) {
        clients.add(client);
        refreshTable();
    }

    public void refreshTable() { clientsTreeTable.refresh(); }

}
