package com.example.addressbook;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddContactView extends Stage {

    private TextField firstNameField;
    private TextField lastNameField;
    private List<TextField> emailFields;
    private List<TextField> phoneFields;
    private TextArea noteField;
    private Button saveButton;
    private Button backButton;
    private Button clearButton;

    public AddContactView() {
        setTitle("Aggiungi Contatto");
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        VBox personalSection = createSection("Dati Personali");
        firstNameField = createRestrictedTextField("Nome (max 15 caratteri)", 15, 200);
        lastNameField = createRestrictedTextField("Cognome (max 15 caratteri)", 15, 200);
        personalSection.getChildren().addAll(
                createLabeledField("Nome:", firstNameField),
                createLabeledField("Cognome:", lastNameField)
        );
        VBox contactSection = createSection("Contatti"); // Correzione: dichiarazione errata di contactSection
        emailFields = createMultipleRestrictedTextFields(3, "Email", 200, 50);
        phoneFields = createMultipleRestrictedTextFields(3, "Telefono (10 cifre)", 200, 10);

        contactSection.getChildren().add(createLabeledField("Email:", emailFields));
        contactSection.getChildren().add(createLabeledField("Numeri di Telefono:", phoneFields));


        VBox noteSection = createSection("Note");
        noteField = new TextArea();
        noteField.setPromptText("Inserisci una nota (max 100 caratteri)");
        noteField.setMaxWidth(400);
        noteField.setWrapText(true);
        noteField.setPrefHeight(80);
        noteField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (noteField.getText().length() >= 100) e.consume();
        });
        noteSection.getChildren().add(new Label("Nota:"));
        noteSection.getChildren().add(noteField);
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        saveButton = new Button("Salva");
        backButton = new Button("Indietro");
        clearButton = new Button("Pulisci");
        clearButton.setOnAction(e -> clearFields());
        buttonBox.getChildren().addAll(saveButton, clearButton, backButton);
        root.getChildren().addAll(personalSection, contactSection, noteSection, buttonBox);
        Scene scene = new Scene(root, 500, 600);
        setScene(scene);
    }

    private TextField createRestrictedTextField(String prompt, int maxLength, int maxWidth) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setMaxWidth(maxWidth);
        textField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (textField.getText().length() >= maxLength) e.consume();
        });
        return textField;
    }

    private VBox createSection(String title) {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10, 0, 10, 0));
        Label sectionLabel = new Label(title);
        sectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        section.getChildren().add(sectionLabel);
        return section;
    }

    private HBox createLabeledField(String labelText, TextField field) {
        Label label = new Label(labelText);
        label.setMinWidth(120);
        HBox fieldBox = new HBox(10, label, field);
        return fieldBox;
    }

    private VBox createLabeledField(String labelText, List<TextField> fields) {
        Label label = new Label(labelText);
        label.setMinWidth(120);
        VBox vbox = new VBox(5);
        vbox.getChildren().add(label);
        fields.forEach(vbox.getChildren()::add);
        return vbox;
    }

    private List<TextField> createMultipleRestrictedTextFields(int count, String placeholder, int maxWidth, int maxLength) {
        List<TextField> fields = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            TextField field = createRestrictedTextField(placeholder + " " + (i + 1), maxLength, maxWidth);
            fields.add(field);
        }
        return fields;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public List<TextField> getEmailFields() {
        return emailFields;
    }

    public List<TextField> getPhoneFields() {
        return phoneFields;
    }

    public TextArea getNoteField() {
        return noteField;
    }

    public void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailFields.forEach(TextField::clear);
        phoneFields.forEach(TextField::clear);
        noteField.clear();
    }

    public boolean validateInput() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        if (firstName.isEmpty() && lastName.isEmpty()) {
            showAlert("Errore di Validazione", "Devi inserire almeno il nome o il cognome.");
            return false;
        }

        if (!validateEmails()) return false;
        if (!validatePhones()) return false;

        if (noteField.getText().length() > 100) {
            showAlert("Errore di Validazione", "La nota non può superare i 100 caratteri.");
            return false;
        }

        return true;
    }

    private boolean validateEmails() {
        for (TextField emailField : emailFields) {
            String email = emailField.getText().trim();
            if (!email.isEmpty() && !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                showAlert("Errore di Validazione", "Formato email non valido.");
                return false;
            }
        }
        return true;
    }

    private boolean validatePhones() {
        for (TextField phoneField : phoneFields) {
            String phone = phoneField.getText().trim();
            if (!phone.isEmpty() && !phone.matches("\\d{10}")) {
                showAlert("Errore di Validazione", "I numeri di telefono devono avere esattamente 10 cifre.");
                return false;
            }
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public List<String> getPhoneNumbers() {
        List<String> phoneNumbers = new ArrayList<>();
        for (TextField phoneField : phoneFields) {
            String phone = phoneField.getText().trim();
            if (!phone.isEmpty()) {
                phoneNumbers.add(phone);
            }
        }
        return phoneNumbers;
    }
    public List<String> getEmails() {
        List<String> emails = new ArrayList<>();
        for (TextField emailField : emailFields) {
            String email = emailField.getText().trim();
            if (!email.isEmpty()) {
                emails.add(email);
            }
        }
        return emails;
    }

}
