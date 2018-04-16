package Controller.Dialog;

import Controller.Menu.ClientsController;
import Manager.ClientManager;
import Model.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Client dialog controller class. Used when we create or modify a client.
 */
public class ClientDialogController extends DialogController implements Initializable {
    @FXML
    private JFXTextField firstNameTextField,lastNameTextField,phoneNumberTextField,emailTextField;

    @FXML
    private Label firstNameLabel,lastNameLabel,errorLabel;

    @FXML
    private JFXButton actionButton;

    private ClientManager clientManager = new ClientManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    /**
     * Initialize the TextFields of the dialog when we modify a client.
     */
    @Override
    protected void initObjectToModify() {
        Client client = (Client) objectToModify;

        firstNameTextField.setText(String.valueOf(client.getFirstName()));
        lastNameTextField.setText(String.valueOf(client.getLastName()));
        phoneNumberTextField.setText(String.valueOf(client.getPhoneNumber()));
        emailTextField.setText(String.valueOf(client.getEmail()));

        actionButton.setText("Modifier");
    }

    /**
     * Add/modify the client when the user clicks the add/modify button.
     */
    @FXML
    protected void handleAddClientButtonAction() {
        if(!checkInputs()) return;
        if(actionButton.getText().equals("Ajouter")) addClient();
        else modifyClient();
    }

    /**
     * Add a new client method.
     */
    private void addClient(){
        Client client = new Client(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                phoneNumberTextField.getText(),
                emailTextField.getText()
        );

        if(clientManager.exists(client.getFirstName(),client.getLastName(),client.getPhoneNumber()) != null) {
            displayError("Ces informations sont déjà associées à un client !");
        }
        else {
            clientManager.add(client);
            ((ClientsController)menuController).addClientToTable(client);
            dialog.close();
        }
    }

    /**
     * Close the dialog when the user clicks the cancel button.
     */
    @FXML
    protected void handleCancelButtonAction() { dialog.close(); }

    /**
     * Modify a client.
     */
    private void modifyClient() {
        Client clientToModify = (Client) objectToModify;
        String firstNameClient = firstNameTextField.getText();
        String lastNameClient = lastNameTextField.getText();
        String phoneNumberClient = phoneNumberTextField.getText();

        if(!clientToModify.getFirstName().equals(firstNameClient)
                && !clientToModify.getLastName().equals(lastNameClient)
                && !clientToModify.getPhoneNumber().equals(phoneNumberClient)
                && clientManager.exists(firstNameClient,lastNameClient,phoneNumberClient) != null) {
            displayError("Le client existe déjà !");
        }
        else{
            clientToModify.setFirstName(firstNameTextField.getText());
            clientToModify.setLastName(lastNameTextField.getText());
            clientToModify.setPhoneNumber(phoneNumberTextField.getText());
            clientToModify.setEmail(emailTextField.getText());

            clientManager.update(clientToModify);
            ((ClientsController)menuController).refreshTable();
            dialog.close();
        }
    }

    /**
     * Check if all the input TextFields are correctly filled.
     * @return True if the inputs are correct, otherwise false.
     */
    private boolean checkInputs(){
        firstNameTextField.getStyleClass().remove("error-textfield");
        lastNameTextField.getStyleClass().remove("error-textfield");
        phoneNumberTextField.getStyleClass().remove("error-textfield");
        emailTextField.getStyleClass().remove("error-textfield");

        if (!firstNameTextField.getText().matches("[a-zA-ZÀ-ÿ]{2,}")) {
            firstNameTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(firstNameLabel.getText()).concat("\" doit contenir au moins 2 caractères  !"));
            return false;
        }

        if (!lastNameTextField.getText().matches("[a-zA-ZÀ-ÿ]{2,}")) {
            lastNameTextField.getStyleClass().add("error-textfield");
            displayError("Le champ \"".concat(lastNameLabel.getText()).concat("\" doit contenir au moins 2 caractères !"));
            return false;
        }

        if (!phoneNumberTextField.getText().matches("(?:(\\+?\\d{1,3}) )?(?:([\\(]?\\d+[\\)]?)[ -])?(\\d{1,5}[\\- ]?\\d{1,5})")) {
            phoneNumberTextField.getStyleClass().add("error-textfield");
            displayError("Le format du numéro de téléphone n'est pas bon ! Exemple : 581-890-1880");
            return false;
        }

        if (!emailTextField.getText().matches("(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b")) {
            emailTextField.getStyleClass().add("error-textfield");
            displayError("Le format de l'adresse mail n'est pas bon ! Exemple : adresse@mail.com");
            return false;
        }

        errorLabel.setVisible(false);
        return true;
    }

    /**
     * Displays error in a Label.
     * @param error The error.
     */
    private void displayError(String error){
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
}
