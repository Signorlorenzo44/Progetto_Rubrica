package com.example.addressbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;

public class GroupController {

    /**
     * La vista che gestisce i gruppi.
     */
    private GroupView view;

    /**
     * Il modello della rubrica che gestisce i dati.
     */
    private AddressBookModel model;

    /**
     * La lista dei gruppi come lista osservabile.
     */
    private ObservableList<Group> groupList;

    /**
     * Costruttore del controller per la gestione dei gruppi.
     *
     * @param view La vista dei gruppi.
     * @param model Il modello della rubrica.
     */
    public GroupController(GroupView view, AddressBookModel model) {
        this.view = view;
        this.model = model;
        this.groupList = FXCollections.observableArrayList();

        initialize();
    }

    /**
     * Inizializza la vista e i relativi eventi.
     * Imposta i listener per i pulsanti di aggiunta, modifica e rimozione dei gruppi.
     */
    private void initialize() {
        view.getGroupListView().setItems(groupList);
        view.getAddGroupButton().setOnAction(e -> handleAddGroup());
        view.getDeleteGroupButton().setOnAction(e -> handleDeleteGroup());
        view.getEditGroupButton().setOnAction(e -> handleEditGroup());
        view.getRemoveMemberButton().setOnAction(e -> handleRemoveMember());
        groupList.addAll(model.getGroups());
    }

    /**
     * Gestisce l'azione di aggiunta di un nuovo gruppo.
     */
    private void handleAddGroup() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Gruppo");
        dialog.setHeaderText("Crea un nuovo gruppo");
        dialog.setContentText("Inserisci il nome del gruppo:");

        dialog.showAndWait().ifPresent(groupName -> {
            if (!groupName.trim().isEmpty()) {
                Group newGroup = new Group(groupName);
                groupList.add(newGroup);
                model.addGroup(newGroup);
                System.out.println("Gruppo aggiunto: " + groupName);
            } else {
                System.out.println("Errore: il nome del gruppo non può essere vuoto.");
            }
        });
    }

    /**
     * Gestisce l'azione di modifica di un gruppo selezionato.
     */
    private void handleEditGroup() {
        Group selectedGroup = view.getGroupListView().getSelectionModel().getSelectedItem();
        System.out.println("Gruppo selezionato: " + selectedGroup);

        if (selectedGroup != null) {
            TextInputDialog dialog = new TextInputDialog(selectedGroup.getGroupName());
            dialog.setTitle("Modifica Gruppo");
            dialog.setHeaderText("Modifica il nome del gruppo");
            dialog.setContentText("Inserisci il nuovo nome del gruppo:");

            dialog.showAndWait().ifPresent(newGroupName -> {
                if (!newGroupName.trim().isEmpty()) {
                    selectedGroup.setGroupName(newGroupName);
                    model.updateGroup(selectedGroup);
                    view.getGroupListView().refresh();
                    System.out.println("Gruppo modificato: " + newGroupName);
                } else {
                    System.out.println("Errore: il nome del gruppo non può essere vuoto.");
                }
            });
        } else {
            System.out.println("Errore: nessun gruppo selezionato per la modifica.");
        }
    }


    /**
     * Gestisce l'azione di rimozione di un gruppo selezionato dalla lista.
     */
    private void handleDeleteGroup() {
        Group selectedGroup = view.getGroupListView().getSelectionModel().getSelectedItem();

        if (selectedGroup != null) {
            groupList.remove(selectedGroup);
            model.removeGroup(selectedGroup);
            System.out.println("Gruppo rimosso: " + selectedGroup.getGroupName());
        } else {
            System.out.println("Errore: nessun gruppo selezionato.");
        }
    }

    /**
     * Gestisce l'azione di rimozione di un membro da un gruppo selezionato.
     */
    private void handleRemoveMember() {
        Group selectedGroup = view.getGroupListView().getSelectionModel().getSelectedItem();
        Contact selectedContact = view.getMemberListView().getSelectionModel().getSelectedItem();

        if (selectedGroup != null && selectedContact != null) {
            boolean removed = selectedGroup.removeContact(selectedContact);
            if (removed) {
                model.updateGroup(selectedGroup);
                view.getMemberListView().setItems(FXCollections.observableArrayList(selectedGroup.getMembers()));
                view.getMemberListView().refresh();
                System.out.println("Membro rimosso: " + selectedContact.getFullName());
            } else {
                System.out.println("Errore: il membro non è stato trovato nel gruppo.");
            }
        } else {
            System.out.println("Errore: nessun gruppo o membro selezionato.");
        }
    }
    /**
     * Aggiorna la lista dei gruppi visualizzata nella vista.
     */
    public void updateGroupList() {
        if (view == null || model == null) {
            System.err.println("Errore: view o model non è inizializzato.");
            return;
        }
        groupList.setAll(model.getGroups());
        view.getGroupListView().refresh();
    }
}