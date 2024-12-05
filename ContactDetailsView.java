package com.example.addressbook;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
  La vista per visualizzare i dettagli di un contatto nella rubrica.

  Questa classe rappresenta l'interfaccia utente per mostrare le informazioni
  di un contatto e consente agli utenti di modificare o tornare indietro.
 */
public class ContactDetailsView {

    /**
      Il contenitore principale della vista.
     */
    private VBox root;

    /**
      Etichetta per visualizzare i dettagli del contatto.
     */
    private Label contactDetails;

    /**
      Pulsante per accedere alla modalità di modifica del contatto.
     */
    private Button editButton;

    /**
      Pulsante per tornare alla schermata precedente.
     */
    private Button backButton;

    /**
      Costruttore della classe.

      Inizializza tutti i componenti grafici della vista.
     */
    public ContactDetailsView() {
    }

    /**
      Restituisce il contenitore principale della vista.

      @return Il contenitore principale (`VBox`) della vista.
     */
    public VBox getRoot() {
        return root;
    }

    /**
      Restituisce l'etichetta che visualizza i dettagli del contatto.

      @return L'etichetta con i dettagli del contatto.
     */
    public Label getContactDetails() {
        return contactDetails;
    }

    /**
      Restituisce il pulsante per modificare il contatto.

      @return Il pulsante di modifica.
     */
    public Button getEditButton() {
        return editButton;
    }

    /**Restituisce il pulsante per tornare indietro.

      @return Il pulsante per tornare indietro.
     */
    public Button getBackButton() {
        return backButton;
    }
}
