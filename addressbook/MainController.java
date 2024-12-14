package com.example.addressbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Controller principale che gestisce la logica tra modello e vista.
 */
public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    private AddressBookModel model;
    private MainView view;
    private ObservableList<Contact> displayedContacts;
    /**
     * Costruttore per inizializzare il controller con il modello e la vista.
     * @param model Modello della rubrica.
     * @param view Vista principale.
     */
    public MainController(AddressBookModel model, MainView view) {
        this.model = model;
        this.view = view;
        this.displayedContacts = FXCollections.observableArrayList();
        model.addChangeListener(this::updateDisplayedContacts);
    }

    /**
     * Inizializza il controller e configura i listener per gli eventi.
     */
    public void initialize() {
        displayedContacts.addAll(model.getContacts());
        view.getContactListView().setItems(displayedContacts);
        view.getAddContactButton().setOnAction(e -> navigateToAddContact());
        view.getFilterComboBox().setOnAction(e -> handleFilter(view.getFilterComboBox().getValue()));
        view.getSearchField().textProperty().addListener((obs, oldText, newText) -> handleSearch(newText));
    }

    /**
     * Gestisce la ricerca dei contatti in base alla query.
     * @param query Testo inserito dall'utente per la ricerca.
     */
    public void handleSearch(String query) {
        LOGGER.info("Searching for: " + query);
        displayedContacts.clear();
        if (query == null || query.isEmpty()) {
            displayedContacts.addAll(model.getContacts());
            LOGGER.info("Query is empty, displaying all contacts.");
        } else {
            for (Contact contact : model.getContacts()) {
                if (matchesQuery(contact, query)) {
                    displayedContacts.add(contact);
                }
            }
        }
    }

    /**
     * Controlla se un contatto corrisponde alla query di ricerca.
     * @param contact Il contatto da verificare.
     * @param query La query di ricerca.
     * @return true se il contatto corrisponde, false altrimenti.
     */
    private boolean matchesQuery(Contact contact, String query) {
        return contact.getFullName().toLowerCase().contains(query.toLowerCase())
                || contact.getPhoneNumbers().stream().anyMatch(phone -> phone.contains(query))
                || contact.getEmails().stream().anyMatch(email -> email.contains(query));
    }

    /**
     * Gestisce il filtro dei contatti in base al criterio selezionato nel menu a tendina.
     * @param filter Il criterio di filtro selezionato.
     */
    public void handleFilter(String filter) {
        LOGGER.info("Applying filter: " + filter);
        displayedContacts.clear();
        switch (filter) {
            case "Tutti":
                displayedContacts.addAll(model.getContacts());
                break;

            case "Con Email":
                for (Contact contact : model.getContacts()) {
                    if (!contact.getEmails().isEmpty()) {
                        displayedContacts.add(contact);
                    }
                }
                break;

            case "Con Numero di Telefono":
                for (Contact contact : model.getContacts()) {
                    if (!contact.getPhoneNumbers().isEmpty()) {
                        displayedContacts.add(contact);
                    }
                }
                break;

            default:
                LOGGER.warning("Unknown filter: " + filter);
                displayedContacts.addAll(model.getContacts());
                break;
        }
    }

    /**
     * Aggiorna i contatti mostrati nella lista quando il modello cambia.
     */
    private void updateDisplayedContacts() {
        LOGGER.info("Updating displayed contacts.");
        displayedContacts.setAll(model.getContacts());
    }

    /**
     * Naviga alla schermata per aggiungere un nuovo contatto.
     */
    public void navigateToAddContact() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/AddContactView.fxml"));
            Parent addContactRoot = loader.load();

            AddContactController addContactController = loader.getController();
            addContactController.setModel(model);

            // Get the Stage from the root node of the scene
            Stage stage = (Stage) view.getRoot().getScene().getWindow();
            Scene addContactScene = new Scene(addContactRoot);
            stage.setScene(addContactScene);
            stage.show();
        } catch (IOException e) {
            LOGGER.severe("Error loading AddContactView: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
