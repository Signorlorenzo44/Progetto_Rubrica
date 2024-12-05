package com.example.addressbook;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;

/**
  La vista principale dell'applicazione di gestione della rubrica.
  Questa classe rappresenta l'interfaccia utente principale che consente agli utenti
 di cercare, aggiungere contatti, applicare filtri e visualizzare la lista dei contatti.
 */
public class MainView {

    /**
      Il contenitore principale della vista.
     */
    private VBox root;

    /**
      Campo di testo per cercare i contatti.
     */
    private TextField searchField;

    /**
      Pulsante per aggiungere un nuovo contatto.
     */
    private Button addContactButton;

    /**
      Pulsante per applicare filtri sui contatti.
     */
    private Button filterButton;

    /**
      Lista per visualizzare i contatti esistenti.
     */
    private ListView<Contact> contactListView;

    /**
      Restituisce il contenitore principale della vista.
      @return Il contenitore principale (`VBox`) della vista.
     */
    public VBox getRoot() {
        return root;
    }
}
