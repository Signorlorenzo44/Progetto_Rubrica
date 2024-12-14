package com.example.addressbook;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class AddGroupView {
    private VBox root;
    private TextField groupNameField;
    private Button saveButton;
    private Button cancelButton;
    private Stage stage;

    private ListView<Contact> contactsListView;
    private ListView<Contact> selectedMembersListView;
    private Button addMemberButton;

    public AddGroupView(List<Contact> allContacts) {
        root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(20));


        groupNameField = new TextField();
        groupNameField.setPromptText("Nome del gruppo");
        saveButton = new Button("Salva");
        cancelButton = new Button("Annulla");
        cancelButton.setOnAction(e -> stage.close());
        contactsListView = new ListView<>();
        contactsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if (allContacts != null) {
            contactsListView.getItems().addAll(allContacts);
        } else {
            showAlert("Errore", "La lista dei contatti non è stata fornita.");
        }
        selectedMembersListView = new ListView<>();
        addMemberButton = new Button("Aggiungi Membri Selezionati");
        addMemberButton.setOnAction(e -> {
            List<Contact> selectedContacts = contactsListView.getSelectionModel().getSelectedItems();
            for (Contact contact : selectedContacts) {
                if (!selectedMembersListView.getItems().contains(contact)) {
                    selectedMembersListView.getItems().add(contact);
                }
            }
        });

        root.getChildren().addAll(
                groupNameField,
                new Label("Seleziona Membri:"),
                contactsListView,
                addMemberButton,
                new Label("Membri Selezionati:"),
                selectedMembersListView,
                saveButton,
                cancelButton
        );

        stage = new Stage();
        stage.setTitle("Aggiungi Gruppo");
        stage.setScene(new Scene(root, 400, 400));
    }

    // Mostra la finestra
    public void show() {
        stage.show();
    }

    public String getGroupName() {
        return groupNameField.getText().trim(); // Rimuove eventuali spazi superflui
    }

    // Metodo per ottenere il gruppo creato
    public Group getGroup() {
        String groupName = getGroupName();
        if (groupName.isEmpty()) {
            showAlert("Errore", "Il nome del gruppo non può essere vuoto.");
            return null;
        }

        Group group = new Group(groupName);

        // Aggiungi i membri selezionati al gruppo
        for (Contact contact : selectedMembersListView.getItems()) {
            group.addContact(contact);
        }
        return group;
    }


    public Button getSaveButton() {
        return saveButton;
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void close() {
        stage.close();
    }
}
