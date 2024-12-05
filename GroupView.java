package com.example.addressbook;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;

public class GroupView {

    /**
     La root della vista, che contiene gli altri componenti.
     */
    private VBox root;

    /**
      La lista che mostra i gruppi.
     */
    private ListView<String> groupListView;

    /**
      Il pulsante per aggiungere un nuovo gruppo.
     */
    private Button addGroupButton;

    /**
      Il pulsante per rimuovere un gruppo esistente.
     */
    private Button deleteGroupButton;

    /**
      Costruttore della vista dei gruppi.
      Inizializza la root e gli elementi dell'interfaccia utente.
     */
    public GroupView() {
        root = new VBox();

        // Inizializza la lista dei gruppi e imposta l'altezza preferita
        groupListView = new ListView<>();
        groupListView.setPrefHeight(400);

        // Inizializza i pulsanti per aggiungere e rimuovere gruppi
        addGroupButton = new Button("Aggiungi Gruppo");
        deleteGroupButton = new Button("Rimuovi Gruppo");

        // Aggiungi gli elementi alla root
        root.getChildren().addAll(groupListView, addGroupButton, deleteGroupButton);
    }

    /**
      Restituisce la root della vista.

      @return La root della vista
     */
    public VBox getRoot() {
        return root;
    }

    /**
     Restituisce la ListView dei gruppi.

      @return La ListView dei gruppi
     */
    public ListView<String> getGroupListView() {
        return groupListView;
    }

    /**
      Restituisce il pulsante per aggiungere un nuovo gruppo.

      @return Il pulsante per aggiungere un gruppo
     */
    public Button getAddGroupButton() {
        return addGroupButton;
    }

    /**
      Restituisce il pulsante per rimuovere un gruppo esistente.

      @return Il pulsante per rimuovere un gruppo
     */
    public Button getDeleteGroupButton() {
        return deleteGroupButton;
    }
}
