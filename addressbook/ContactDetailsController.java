package com.example.addressbook;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContactDetailsController {

    private AddressBookModel model;
    private ContactDetailsView view;

    public ContactDetailsController(AddressBookModel model, ContactDetailsView view) {
        this.model = model;
        this.view = view;
    }

    public void initialize() {
        view.getEditButton().setOnAction(e -> handleEditContact());
        view.getBackButton().setOnAction(e -> handleBack());
    }

    private void handleEditContact() {
        Contact currentContact = view.getDisplayedContact();
        if (currentContact == null) {
            showAlert("Errore", "Nessun contatto selezionato per la modifica.", Alert.AlertType.ERROR);
            return;
        }
        view.enableEditFields(true);
        view.getSaveButton().setVisible(true);
        view.getSaveButton().setOnAction(e -> handleSaveContact(currentContact));
    }

    private void handleSaveContact(Contact currentContact) {
        try {
            String updatedName = view.getNameField().getText().trim();
            String updatedPhone = view.getPhoneField().getText().trim();
            String updatedEmail = view.getEmailField().getText().trim();

            if (updatedName.isEmpty() || updatedPhone.isEmpty()) {
                showAlert("Errore", "Nome e telefono sono obbligatori.", Alert.AlertType.ERROR);
                return;
            }
            List<String> emailList = updatedEmail.isEmpty()
                    ? new ArrayList<>()
                    : Arrays.stream(updatedEmail.split(","))
                    .map(String::trim)
                    .filter(email -> !email.isEmpty())
                    .collect(Collectors.toList());
            if (emailList.size() > 3) {
                showAlert("Errore", "Non puoi aggiungere più di 3 email.", Alert.AlertType.ERROR);
                return;
            }
            currentContact.setName(updatedName);
            currentContact.setPhone(updatedPhone);
            currentContact.setEmails(emailList);
            model.updateContact(currentContact);
            view.enableEditFields(false);
            view.getSaveButton().setVisible(false);
            view.updateContactDetails(currentContact);
            showAlert("Successo", "Il contatto è stato aggiornato con successo.", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            showAlert("Errore", "Si è verificato un errore durante l'aggiornamento del contatto: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void handleBack() {
        Stage currentStage = (Stage) view.getRoot().getScene().getWindow();
        currentStage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
