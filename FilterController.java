package com.example.addressbook;

/**
  Controller per gestire la funzionalità di filtraggio nella rubrica.

  Questa classe collega il modello della rubrica (`AddressBookModel`) alla vista del filtro
  (`FilterView`) e implementa la logica per applicare i criteri di filtraggio.
 */
public class FilterController {

    /**
      Il modello della rubrica che contiene i dati da filtrare.
     */
    private AddressBookModel model;

    /**
      La vista che fornisce l'interfaccia utente per applicare i filtri.
     */
    private FilterView view;

    /**
      Costruttore della classe.

      @param model Il modello della rubrica.
      @param view  La vista per il filtraggio.
     */
    public FilterController(AddressBookModel model, FilterView view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    /**
     Inizializza il controller configurando la vista e associando i gestori di eventi.
     */
    private void initialize() {
    }

    /**
     Applica i criteri di filtraggio selezionati dall'utente.

      Questo metodo raccoglie i criteri dalla vista e applica i filtri al modello.
     */
    private void applyFilters() {
    }
}
