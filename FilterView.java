package com.example.addressbook;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
  La vista per applicare i filtri sui contatti nella rubrica.

  Questa classe rappresenta l'interfaccia utente che consente agli utenti
  di selezionare criteri di filtro come numero di telefono o email.
 */
public class FilterView {

    /**
      Il contenitore principale della vista.
     */
    private VBox root;

    /**
      Checkbox per attivare il filtro per numero di telefono.
     */
    private CheckBox filterByPhone;

    /**
      Checkbox per attivare il filtro per email.
     */
    private CheckBox filterByEmail;

    /**
      Pulsante per applicare i filtri selezionati.
     */
    private Button applyFilterButton;

    /**
      Costruttore della classe.

      Inizializza tutti i componenti grafici della vista.
     */
    public FilterView() {
    }

    /**
      Restituisce il contenitore principale della vista.

      @return Il contenitore principale (`VBox`) della vista.
     */
    public VBox getRoot() {
        return root;
    }

    /**
      Restituisce il checkbox per filtrare per numero di telefono.

      @return Il checkbox per il filtro per numero di telefono.
     */
    public CheckBox getFilterByPhone() {
        return filterByPhone;
    }

    /**
      Restituisce il checkbox per filtrare per email.

      @return Il checkbox per il filtro per email.
     */
    public CheckBox getFilterByEmail() {
        return filterByEmail;
    }

    /**
     Restituisce il pulsante per applicare i filtri selezionati.

      @return Il pulsante per applicare i filtri.
     */
    public Button getApplyFilterButton() {
        return applyFilterButton;
    }
}
