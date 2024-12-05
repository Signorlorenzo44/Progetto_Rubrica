package com.example.addressbook;

public class ContactDetailsController {

    /**
      Il modello della rubrica, che gestisce i dati.
     */
    private AddressBookModel model;

    /**
      La vista che mostra i dettagli di un contatto.
     */
    private ContactDetailsView view;

    /**
      Costruttore del controller per i dettagli di un contatto.

     @param model Il modello della rubrica.
      @param view La vista che visualizza i dettagli del contatto.
     */
    public ContactDetailsController(AddressBookModel model, ContactDetailsView view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    /**
      Inizializza la vista e i relativi eventi.
      Imposta i listener per i pulsanti di modifica e ritorno.
     */
    private void initialize() {
        view.getEditButton().setOnAction(e -> handleEditContact());
        view.getBackButton().setOnAction(e -> handleBack());
    }

    /**
      Gestisce l'azione di modifica del contatto.
      Questo metodo dovrebbe permettere all'utente di modificare i dettagli di un contatto.
     */
    private void handleEditContact() {
        // Implementazione della modifica del contatto
    }

    /**
      Gestisce l'azione di ritorno alla vista precedente.
      Questo metodo consente di tornare alla vista principale o alla vista precedente.
     */
    private void handleBack() {
        // Implementazione per tornare alla vista precedente
    }
}
