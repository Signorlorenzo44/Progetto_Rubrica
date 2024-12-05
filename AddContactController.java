package com.example.addressbook;

/**
  Controller per gestire l'aggiunta di un nuovo contatto alla rubrica.

  Questa classe è responsabile di coordinare l'interazione tra il modello
  della rubrica (`AddressBookModel`) e la vista per l'aggiunta di contatti (`AddContactView`).
 */
public class AddContactController {

    /**
      Il modello della rubrica contenente i dati dei contatti.
     */
    private AddressBookModel model;

    /**
      La vista per l'interfaccia utente utilizzata per aggiungere un nuovo contatto.
     */
    private AddContactView view;

    /**
      Inizializza il controller, configurando eventuali gestori di eventi
      e preparando la vista per l'uso.
     */
    public void initialize() {
    }

    /**
      Gestisce il salvataggio di un nuovo contatto.

      Questo metodo raccoglie i dati inseriti nella vista, li valida e
      li salva nel modello della rubrica.
     */
    public void handleSaveContact() {
    }
}
