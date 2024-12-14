package com.example.addressbook;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * La vista principale dell'applicazione di gestione della rubrica.
 */
public class MainView {

    private VBox root;
    private TextField searchField;
    private Button addContactButton;
    private Button showDetailsButton;
    private Button deleteContactButton; // Nuovo pulsante per eliminare un contatto
    private ComboBox<String> filterComboBox;
    private ListView<Contact> contactListView;

    /**
     * Costruttore che inizializza i componenti dell'interfaccia utente.
     */
    public MainView() {
        root = new VBox(20);
        root.setPadding(new Insets(20));
        searchField = new TextField();
        searchField.setPromptText("Search contacts...");
        searchField.setFont(Font.font(14));
        searchField.setMaxWidth(400);
        addContactButton = new Button("Add Contact", createIcon("/icons/add.png"));
        addContactButton.setPrefWidth(150);
        showDetailsButton = new Button("Show Details");
        showDetailsButton.setPrefWidth(150);
        showDetailsButton.setDisable(true);
        deleteContactButton = new Button("Delete Contact");
        deleteContactButton.setPrefWidth(150);
        deleteContactButton.setDisable(true);
        filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Tutti", "Con Email", "Con Numero di Telefono");
        filterComboBox.setValue("Tutti");
        filterComboBox.setStyle("-fx-font-size: 14; -fx-padding: 5;");
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addContactButton, showDetailsButton, deleteContactButton, filterComboBox);
        contactListView = new ListView<>();
        contactListView.setPlaceholder(new Label("No contacts to display"));
        contactListView.setPrefHeight(400);
        contactListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showDetailsButton.setDisable(newValue == null);
            deleteContactButton.setDisable(newValue == null);
        });
        root.getChildren().addAll(searchField, buttonBox, contactListView);
        styleComponents();
        showDetailsButton.setOnAction(event -> {
            Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                showContactDetails(selectedContact);
            }
        });
        deleteContactButton.setOnAction(event -> {
            Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                deleteContact(selectedContact);
            }
        });
    }
    /**
     * Mostra la finestra dei dettagli del contatto selezionato.
     * @param contact Il contatto selezionato.
     */
    private void showContactDetails(Contact contact) {
        Stage detailsStage = new Stage();
        ContactDetailsView detailsView = new ContactDetailsView();
        detailsView.getBackButton().setOnAction(event -> detailsStage.close());
        Scene detailsScene = new Scene(detailsView.getRoot(), 400, 600);
        detailsStage.setScene(detailsScene);
        detailsStage.setTitle("Contact Details - " + contact.getFullName());
        detailsStage.show();
    }

    /**
     * Elimina un contatto dalla lista.
     * @param contact Il contatto da eliminare.
     */
    private void deleteContact(Contact contact) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Contact");
        confirmationAlert.setHeaderText("Are you sure you want to delete this contact?");
        confirmationAlert.setContentText("Contact: " + contact.getFullName());

        // Mostra il dialogo di conferma
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                contactListView.getItems().remove(contact);
                showAlert("Contact Deleted", "The contact has been successfully deleted.");
            }
        });
    }

    /**
     * Mostra un messaggio di avviso.
     * @param title Il titolo della finestra di avviso.
     * @param message Il messaggio da mostrare.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Crea un'icona per i pulsanti utilizzando un'immagine.
     * @param imagePath Percorso dell'immagine.
     * @return Un oggetto ImageView.
     */
    private ImageView createIcon(String imagePath) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        return icon;
    }

    /**
     * Applica uno stile ai componenti.
     */
    private void styleComponents() {
        root.setStyle("-fx-background-color: #f9f9f9;");

        searchField.setStyle(
                "-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;"
        );

        addContactButton.setStyle(
                "-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;"
        );

        showDetailsButton.setStyle(
                "-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;"
        );

        deleteContactButton.setStyle(
                "-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;"
        );

        contactListView.setStyle(
                "-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;"
        );
    }

    // Getter methods
    public VBox getRoot() {
        return root;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getAddContactButton() {
        return addContactButton;
    }

    public ComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

    public ListView<Contact> getContactListView() {
        return contactListView;
    }
}
