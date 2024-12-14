package com.example.addressbook;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditGroupView {
    private Stage stage;
    private TextField groupNameField;
    private ListView<String> membersListView;
    private TextField newMemberField;
    private Button addMemberButton;
    private Button saveButton;
    private Button cancelButton;
    private Button backButton;
    private Group group;
    private Runnable onSaveCallback;

    public EditGroupView() {
        stage = new Stage();
        VBox root = new VBox(10);

        // Campo per il nome del gruppo
        groupNameField = new TextField();
        groupNameField.setPromptText("Nome del Gruppo");

        // Lista dei membri
        membersListView = new ListView<>();
        membersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        newMemberField = new TextField();
        newMemberField.setPromptText("Aggiungi nuovo membro");
        addMemberButton = new Button("Aggiungi Membro");
        addMemberButton.setOnAction(e -> addNewMember());
        saveButton = new Button("Salva");
        saveButton.setOnAction(e -> saveChanges());
        cancelButton = new Button("Annulla");
        cancelButton.setOnAction(e -> hide());
        backButton = new Button("Indietro");
        backButton.setOnAction(e -> hide());

        root.getChildren().addAll(
                new Label("Nome del Gruppo:"),
                groupNameField,
                new Label("Membri:"),
                membersListView,
                newMemberField,
                addMemberButton,
                saveButton,
                cancelButton,
                backButton
        );

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Modifica Gruppo");
    }

    /**
     * Mostra la finestra.
     */
    public void show() {
        if (!stage.isShowing()) {
            stage.show();
        }
    }

    /**
     * Nasconde la finestra.
     */
    public void hide() {
        if (stage.isShowing()) {
            stage.close();
        }
    }

    /**
     * Aggiunge un nuovo membro alla lista dei membri.
     */
    private void addNewMember() {
        String newMember = newMemberField.getText().trim();
        if (!newMember.isEmpty() && !membersListView.getItems().contains(newMember)) {
            membersListView.getItems().add(newMember);
            newMemberField.clear();
        } else if (newMember.isEmpty()) {
            showAlert("Errore", "Il nome del membro non può essere vuoto.");
        } else {
            showAlert("Errore", "Il membro è già nella lista.");
        }
    }

    /**
     * Salva le modifiche al gruppo selezionato.
     */
    private void saveChanges() {
        if (group == null) {
            showAlert("Errore", "Nessun gruppo selezionato.");
            return;
        }

        String newGroupName = groupNameField.getText().trim();
        if (newGroupName.isEmpty()) {
            showAlert("Errore", "Il nome del gruppo non può essere vuoto.");
            return;
        }
        group.setGroupName(newGroupName);

        // Aggiorna i membri del gruppo
        List<Contact> updatedMembers = new ArrayList<>();
        for (String memberName : membersListView.getItems()) {
            Contact contact = findContactByName(memberName);
            if (contact == null) {
                contact = new Contact(memberName);
            }
            updatedMembers.add(contact);
        }
        group.setMembers(updatedMembers);


        hide();
        showAlert("Successo", "Modifiche salvate con successo.");

        // Notifica la callback se impostata
        if (onSaveCallback != null) {
            onSaveCallback.run();
        }
    }

    /**
     * Imposta il gruppo da modificare.
     *
     * @param selectedGroup Il gruppo selezionato.
     */
    public void setGroup(Group selectedGroup) {
        if (selectedGroup == null) {
            showAlert("Errore", "Nessun gruppo selezionato.");
            return;
        }
        this.group = selectedGroup;
        groupNameField.setText(group.getGroupName());


        List<String> memberNames = new ArrayList<>();
        for (Contact contact : group.getMembers()) {
            memberNames.add(contact.getFullName());
        }
        membersListView.getItems().setAll(memberNames);
    }

    /**
     * Imposta la callback da eseguire dopo il salvataggio.
     *
     * @param onSaveCallback La callback da eseguire.
     */
    public void setOnSaveCallback(Runnable onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
    }

    /**
     * Mostra un messaggio di avviso.
     *
     * @param title   Il titolo della finestra di avviso.
     * @param message Il messaggio da mostrare.
     */
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Metodo placeholder per trovare un contatto per nome.
     * Implementare secondo la logica dell'applicazione principale.
     */
    private Contact findContactByName(String fullName) {

        return null;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public String getGroupName() {
        return groupNameField.getText().trim();
    }
    public List<String> getMembers() {
        return new ArrayList<>(membersListView.getItems());
    }

}
