package com.example.addressbook;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;

/**
  Vista per l'aggiunta di un nuovo contatto.
  Questa classe rappresenta l'interfaccia utente per l'inserimento dei dettagli di un nuovo contatto,
  inclusi nome, cognome, numero di telefono, email, note, e un pulsante per salvare il contatto.
 */
public class AddContactView {

    /**
     Il contenitore principale della vista.
     */
    private VBox root;

    /**
      Campo di testo per inserire il nome del contatto.
     */
    private TextField nameField;

    /**
      Campo di testo per inserire il cognome del contatto.
     */
    private TextField surnameField;

    /**
      Campo di testo per inserire il numero di telefono del contatto.
     */
    private TextField phoneField;

    /**
      Campo di testo per inserire l'indirizzo email del contatto.
     */
    private TextField emailField;

    /**
     Area di testo per inserire le note aggiuntive sul contatto.
     */
    private TextArea noteField;

    /**
      Pulsante per salvare il nuovo contatto.
     */
    private Button saveButton;

    /**
     Restituisce il contenitore principale della vista.
     @return Il contenitore principale (`VBox`) della vista.
     */
    public VBox getRoot() {
        return root;
    }
}

