package com.example.addressbook;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailsView {
    private VBox root;
    private TextField nameField;
    private TextField phoneNumbersField;
    private TextField emailsField;
    private TextArea notesField;
    private Button saveButton;
    private Button backButton;
    private Contact contact; // Variabile per memorizzare il contatto corrente

    public ContactDetailsView() {
        root = new VBox(15);
        root.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 20;");
        nameField = new TextField();
        nameField.setPromptText("Nome completo");
        phoneNumbersField = new TextField();
        phoneNumbersField.setPromptText("Numeri di telefono (separati da virgole)");
        emailsField = new TextField();
        emailsField.setPromptText("Email (separate da virgole)");
        notesField = new TextArea();
        notesField.setPromptText("Note");
        saveButton = new Button("Salva");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 10;");
        backButton = new Button("Indietro");
        backButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 10;");
        HBox buttonBar = new HBox(10, saveButton, backButton);
        buttonBar.setStyle("-fx-alignment: center;");
        root.getChildren().addAll(
                new Label("Nome:"),
                nameField,
                new Label("Numeri di telefono:"),
                phoneNumbersField,
                new Label("Email:"),
                emailsField,
                new Label("Note:"),
                notesField,
                buttonBar
        );
        saveButton.setOnAction(event -> {
            try {
                String updatedName = nameField.getText().trim();
                List<String> updatedPhoneNumbers = parseCommaSeparated(phoneNumbersField.getText());
                List<String> updatedEmails = parseCommaSeparated(emailsField.getText());
                String updatedNotes = notesField.getText().trim();

                String[] nameParts = updatedName.split(" ", 2);
                contact.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
                contact.setLastName(nameParts.length > 1 ? nameParts[1] : "");
                contact.setPhoneNumbers(updatedPhoneNumbers);
                contact.setEmails(updatedEmails);
                contact.setNote(updatedNotes);

                showInfo("Successo", "Contatto aggiornato con successo.");
            } catch (Exception e) {
                showError("Errore", "Errore durante l'aggiornamento del contatto: " + e.getMessage());
            }
        });
        backButton.setOnAction(event -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        });
    }

    public void updateContactDetails(Contact contact) {
        this.contact = contact;
        nameField.setText(contact.getFullName());
        phoneNumbersField.setText(String.join(", ", contact.getPhoneNumbers()));
        emailsField.setText(String.join(", ", contact.getEmails()));
        notesField.setText(contact.getNote());
    }

    public VBox getRoot() {
        return root;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<String> parseCommaSeparated(String input) {
        List<String> result = new ArrayList<>();
        if (input != null && !input.trim().isEmpty()) {
            String[] items = input.split(",");
            for (String item : items) {
                result.add(item.trim());
            }
        }
        return result;
    }

    public Contact getDisplayedContact() {
        String fullName = nameField.getText().trim();
        Contact contact = new Contact(fullName);

        // Recupera i dati attuali dalla vista
        String[] nameParts = fullName.split(" ", 2);
        contact.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
        contact.setLastName(nameParts.length > 1 ? nameParts[1] : "");

        List<String> phoneNumbers = parseCommaSeparated(phoneNumbersField.getText());
        contact.setPhoneNumbers(phoneNumbers);

        List<String> emails = parseCommaSeparated(emailsField.getText());
        contact.setEmails(emails);

        contact.setNote(notesField.getText().trim());

        return contact;
    }

    public void enableEditFields(boolean enable) {
        nameField.setEditable(enable);
        phoneNumbersField.setEditable(enable);
        emailsField.setEditable(enable);
        notesField.setEditable(enable);
    }

    public TextField getNameField() {
        return nameField;
    }
    public Button getEditButton() {
        Button editButton = null;
        return editButton;
    }
    public void setContact(String contact) {
    }



    public TextField getPhoneField() {
        return phoneNumbersField;
    }

    public TextField getEmailField() {
        return emailsField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}
