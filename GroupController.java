package com.example.addressbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;

public class GroupController {

    /**
      La vista che gestisce i gruppi.
     */
    private GroupView view;

    /**
      Il modello della rubrica che gestisce i dati.
     */
    private AddressBookModel model;

    /**
      La lista dei gruppi come lista osservabile.
     */
    private ObservableList<String> groupList;

    /**
      Costruttore del controller per la gestione dei gruppi.

      @param view La vista dei gruppi.
      @param model Il modello della rubrica.
     */
    public GroupController(GroupView view, AddressBookModel model) {
        this.view = view;
        this.model = model;
        this.groupList = FXCollections.observableArrayList();

        initialize();
    }

    /**
      Inizializza la vista e i relativi eventi.
      Imposta i listener per i pulsanti di aggiunta e rimozione dei gruppi.
     */
    private void initialize() {
        view.getGroupListView().setItems(groupList);

        // Imposta gli eventi per i pulsanti di aggiunta e rimozione gruppo
        view.getAddGroupButton().setOnAction(e -> handleAddGroup());
        view.getDeleteGroupButton().setOnAction(e -> handleDeleteGroup());
    }

    /**
      Gestisce l'azione di aggiunta di un nuovo gruppo.
      Mostra una finestra di dialogo per inserire il nome del gruppo e lo aggiunge alla lista.
     */
    private void handleAddGroup() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Gruppo");
        dialog.setHeaderText("Crea un nuovo gruppo");
        dialog.setContentText("Inserisci il nome del gruppo:");

        // Aggiungi il gruppo alla lista se il nome non è vuoto
        dialog.showAndWait().ifPresent(groupName -> {
            if (!groupName.trim().isEmpty()) {
                groupList.add(groupName);
                System.out.println("Gruppo aggiunto: " + groupName);
            } else {
                System.out.println("Errore: il nome del gruppo non può essere vuoto.");
            }
        });
    }

    /**
      Gestisce l'azione di rimozione di un gruppo selezionato dalla lista.
      Rimuove il gruppo selezionato dalla lista e stampa un messaggio.
     */
    private void handleDeleteGroup() {
        // Ottieni l'indice del gruppo selezionato
        int selectedIndex = view.getGroupListView().getSelectionModel().getSelectedIndex();

        // Rimuovi il gruppo dalla lista se selezionato
        if (selectedIndex >= 0) {
            String removedGroup = groupList.remove(selectedIndex);
            System.out.println("Gruppo rimosso: " + removedGroup);
        } else {
            System.out.println("Errore: nessun gruppo selezionato.");
        }
    }
}
