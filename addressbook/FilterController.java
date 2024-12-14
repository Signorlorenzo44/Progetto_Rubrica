package com.example.addressbook;

import java.util.List;

/**
 * Controller per gestire la funzionalità di filtraggio nella rubrica.
 */
public class FilterController {

    private AddressBookModel model;
    private FilterView view;

    /**
     * Costruttore della classe.
     *
     * @param model Il modello della rubrica.
     * @param view  La vista per il filtraggio.
     */
    public FilterController(AddressBookModel model, FilterView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Inizializza il controller configurando la vista e associando i gestori di eventi.
     */
    public void initialize() {
        view.getApplyButton().setOnAction(e -> applyFilters());
    }

    /**
     * Applica i criteri di filtraggio selezionati dall'utente.
     */
    private void applyFilters() {
        String selectedFilter = view.getSelectedFilter();
        List<Contact> filteredContacts;

        switch (selectedFilter) {
            case "Con Email":
                filteredContacts = model.filterContacts();
                break;
            case "Con Numero di Telefono":
                filteredContacts = model.filterContacts();
                break;
            default:
                filteredContacts = model.getContacts();
                break;
        }
        model.updateContact((Contact) filteredContacts);
    }
}
