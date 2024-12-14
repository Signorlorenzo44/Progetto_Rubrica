package com.example.addressbook;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private AddContactView addContactView;
    private GroupView groupView;
    private EditGroupView editGroupView;
    private TextField searchField;
    private ListView<String> contactListView;
    private ComboBox<String> sortComboBox;
    private ContactDetailsView contactDetailsView;
    private List<String> allContacts;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Address Book");
        VBox mainLayout = createMainView();

        mainScene = new Scene(mainLayout, 800, 600, Color.WHITE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private VBox createMainView() {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20;");

        searchField = new TextField();
        searchField.setPromptText("Cerca contatti...");
        searchField.setStyle("-fx-font-size: 14; -fx-padding: 10; -fx-background-radius: 5;");
        searchField.setMaxWidth(400);

        HBox buttonBar = new HBox(10);
        Button addContactButton = createStyledButton("Aggiungi contatto");
        Button filterButton = createStyledButton("Filtra");
        Button groupButton = createStyledButton("Gestisci gruppi");
        Button importButton = createStyledButton("Importa");
        Button exportButton = createStyledButton("Esporta");
        Button showDetailsButton = createStyledButton("Mostra dettagli");
        Button deleteContactButton = createStyledButton("Elimina contatto");
        deleteContactButton.setDisable(true);

        contactListView = new ListView<>();
        contactListView.setStyle("-fx-background-color: white; -fx-border-color: lightgray;");
        contactListView.setPrefHeight(400);


        allContacts = new ArrayList<>(Arrays.asList(
                "Marco Rossi",
                "Lorenzo Cioffi",
                "Alice Bianco",
                "Giovanni Bianchi"
        ));
        contactListView.getItems().addAll(allContacts);

        contactListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showDetailsButton.setDisable(newValue == null);
            deleteContactButton.setDisable(newValue == null);
        });


        showDetailsButton.setOnAction(e -> {
            String selectedContact = contactListView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                openContactDetailsView(selectedContact);
            }
        });

        deleteContactButton.setOnAction(e -> {
            String selectedContact = contactListView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                deleteContact(selectedContact);
            }
        });

        sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll("Ordina per Cognome", "Ordina per Nome");
        sortComboBox.setValue("Ordina per Cognome");
        sortComboBox.setOnAction(e -> sortContacts());


        buttonBar.getChildren().addAll(addContactButton, filterButton, groupButton, importButton, exportButton, showDetailsButton, deleteContactButton);


        addContactButton.setOnAction(e -> openAddContactView());
        filterButton.setOnAction(e -> openFilterView());
        groupButton.setOnAction(e -> openGroupView());
        importButton.setOnAction(e -> importContacts());
        exportButton.setOnAction(e -> exportContacts());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchContacts(newValue));

        root.getChildren().addAll(searchField, sortComboBox, buttonBar, contactListView);
        root.setPadding(new Insets(20));

        return root;
    }
    private void deleteContact(String contact) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Cancella contatto");
        confirmationAlert.setHeaderText("Sei sicuro di voler cancellare il contatto");
        confirmationAlert.setContentText("Contatto: " + contact);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                allContacts.remove(contact);
                contactListView.getItems().remove(contact);
                showAlert("Successo", "Il contatto è stato eliminato con successo.");
            }
        });
    }


    private void openGroupView() {
        if (groupView == null) {
            groupView = new GroupView();
        }


        groupView.getBackButton().setOnAction(e -> primaryStage.setScene(mainScene));

        // Mostra la scena dei gruppi
        Scene groupScene = new Scene(groupView.getRoot(), 800, 600);
        primaryStage.setScene(groupScene);

        // Associa il pulsante di modifica gruppo
        groupView.getEditGroupButton().setOnAction(e -> {
            Group selectedGroup = groupView.getSelectedGroup();
            if (selectedGroup != null) {
                openEditGroupView(selectedGroup);
            } else {
                showAlert("Errore", "Nessun gruppo selezionato.");
            }
        });
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        String buttonStyle = "-fx-background-color: #007bff; -fx-text-fill: white; " +
                "-fx-font-size: 14; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;";
        button.setStyle(buttonStyle);
        return button;
    }

    private Contact sortContacts() {
        String selectedSort = sortComboBox.getValue();
        List<String> sortedList = new ArrayList<>(contactListView.getItems());

        if ("Ordina per Cognome".equals(selectedSort)) {
            sortedList = sortedList.stream()
                    .sorted(Comparator.comparing(contact -> contact.split(" ")[1])) // Ordina per cognome
                    .collect(Collectors.toList());
        } else if ("Ordina per Nome".equals(selectedSort)) {
            sortedList = sortedList.stream()
                    .sorted(Comparator.comparing(contact -> contact.split(" ")[0])) // Ordina per nome
                    .collect(Collectors.toList());
        }

        contactListView.getItems().setAll(sortedList);
        return null;
    }
    private void openFilterView() {
        FilterView filterView = new FilterView();

        // Associa l'evento al pulsante "Applica"
        filterView.getApplyButton().setOnAction(e -> {
            applyFilter(filterView.getSelectedFilter()); // Applica il filtro
            filterView.getStage().close(); // Chiude la finestra di filtro dopo l'applicazione
        });

        filterView.show(); // Mostra la finestra modale
    }
    private void applyFilter(String selectedFilter) {
        contactListView.getItems().clear();


        if ("Tutti".equals(selectedFilter)) {
            contactListView.getItems().addAll(allContacts);
        } else if ("Con Email".equals(selectedFilter)) {
            contactListView.getItems().add("Marco Rossi");
        } else if ("Con Numero di Telefono".equals(selectedFilter)) {
            contactListView.getItems().add("Lorenzo Cioffi");
        }
    }

    private void openContactDetailsView(String contactName) {
        if (contactDetailsView == null) {
            contactDetailsView = new ContactDetailsView();
        }

        // Trova il contatto corrispondente per nome
        Contact contact = allContacts.stream()
                .map(name -> new Contact(name)) // Converti le stringhe in oggetti Contact
                .filter(c -> c.getFullName().equals(contactName))
                .findFirst()
                .orElse(null);

        if (contact == null) {
            showAlert("Errore", "Contatto non trovato.");
            return;
        }

        // Passa il contatto a ContactDetailsView
        contactDetailsView.updateContactDetails(contact);


        Scene detailsScene = new Scene(contactDetailsView.getRoot(), 600, 400, Color.WHITE);
        primaryStage.setScene(detailsScene);

        contactDetailsView.getBackButton().setOnAction(e -> primaryStage.setScene(mainScene));


        contactDetailsView.getSaveButton().setOnAction(e -> {
            Contact updatedContact = contactDetailsView.getDisplayedContact();

            // Aggiorna la lista principale
            allContacts = allContacts.stream()
                    .map(existingContact -> existingContact.equals(contactName) ? updatedContact.getFullName() : existingContact)
                    .collect(Collectors.toList());

            contactListView.getItems().setAll(allContacts); // Aggiorna la lista visibile
            showAlert("Successo", "Contatto aggiornato con successo.");
            primaryStage.setScene(mainScene); // Torna alla scena principale
        });
    }


    private void openAddContactView() {
        if (addContactView == null) {
            addContactView = new AddContactView();
        }

        addContactView.show();

        addContactView.getSaveButton().setOnAction(e -> {
            String firstName = addContactView.getFirstNameField().getText().trim();
            String lastName = addContactView.getLastNameField().getText().trim();

            // Verifica: almeno uno dei due campi deve essere compilato
            if (firstName.isEmpty() && lastName.isEmpty()) {
                showAlert("Errore", "Almeno uno tra nome e cognome deve essere compilato.");
                return;
            }

            // Creazione del contatto con il formato richiesto
            String newContact = (firstName.isEmpty() ? "" : firstName) +
                    (lastName.isEmpty() ? "" : " " + lastName).trim();


            allContacts.add(newContact);
            searchContacts(""); // Aggiorna la lista visualizzata
            addContactView.clearFields();
            addContactView.close();
        });
    }


    private void searchContacts(String query) {
        contactListView.getItems().clear();
        if (query == null || query.trim().isEmpty()) {
            contactListView.getItems().addAll(allContacts);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            contactListView.getItems().addAll(
                    allContacts.stream()
                            .filter(contact -> contact.toLowerCase().contains(lowerCaseQuery))
                            .collect(Collectors.toList())
            );
        }
    }
    private void importContacts() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importa Contatti");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try {
                FileHandler fileHandler = new FileHandler();
                List<Contact> contacts = fileHandler.importFromFile(file.getAbsolutePath());
                allContacts.clear();
                for (Contact contact : contacts) {
                    allContacts.add(contact.getFullName() + " - " + contact.getEmails());
                }
                contactListView.getItems().setAll(allContacts);
                sortContacts(); // Ordina automaticamente dopo l'importazione
                showAlert("Successo", "Contacts imported successfully.");
            } catch (IOException ex) {
                showAlert("Errore", "Failed to import contacts: " + ex.getMessage());
            }
        }
    }

    private void exportContacts() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Esporta Contatti");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
                FileHandler fileHandler = new FileHandler();
                List<Contact> contacts = new ArrayList<>();
                for (String name : contactListView.getItems()) {
                    String[] parts = name.split(" ");
                    String firstName = parts.length > 0 ? parts[0] : "";
                    String lastName = parts.length > 1 ? parts[1] : "";
                    contacts.add(new Contact(firstName, lastName));
                }
                fileHandler.exportToFile(file.getAbsolutePath(), contacts);
                showAlert("Successo", "Contacts exported successfully.");
            } catch (IOException ex) {
                showAlert("Errore", "Failed to export contacts: " + ex.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void openEditGroupView(Group selectedGroup) {
        if (editGroupView == null) {
            editGroupView = new EditGroupView();
        }

        editGroupView.setGroup(selectedGroup);
        editGroupView.show();


        editGroupView.getSaveButton().setOnAction(e -> {
            String newGroupName = editGroupView.getGroupName();
            List<String> newMemberNames = editGroupView.getMembers();

            // Verifica se il nome del gruppo è vuoto
            if (newGroupName == null || newGroupName.trim().isEmpty()) {
                showAlert("Errore", "Il nome del gruppo non può essere vuoto.");
                return;
            }

            selectedGroup.setGroupName();

            List<Contact> newMembers = new ArrayList<>();
            for (String memberName : newMemberNames) {
                Contact contact = sortContacts(); // Trova il contatto tramite il nome
                if (contact != null) {
                    newMembers.add(contact);
                }
            }

            selectedGroup.setMembers(newMembers);

            // Aggiorna la vista del gruppo
            groupView.updateGroupList();

            // Chiudi la finestra di modifica
            editGroupView.hide();
            showAlert("Successo", "Il gruppo è stato aggiornato con successo.");
        });

        // Gestione del pulsante indietro
        editGroupView.getBackButton().setOnAction(e -> editGroupView.hide());
    }


    public static void main(String[] args) {
        launch(args);
    }
}