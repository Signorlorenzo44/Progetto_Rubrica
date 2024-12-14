package com.example.addressbook;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

/**
 * Controller per gestire l'aggiunta di un nuovo contatto alla rubrica.
 *
 * Questa classe è responsabile di coordinare l'interazione tra il modello
 * della rubrica (AddressBookModel) e la vista per l'aggiunta di contatti (AddContactView).
 */
public class AddContactController {

    /**
     * Il modello della rubrica contenente i dati dei contatti.
     */
    private AddressBookModel model;

    /**
     * La vista per l'interfaccia utente utilizzata per aggiungere un nuovo contatto.
     */
    private AddContactView view;

    /**
     * Costruttore del controller, che collega la vista e il modello.
     *
     * @param model Il modello della rubrica.
     * @param view  La vista per l'aggiunta di un contatto.
     */
    public AddContactController(AddressBookModel model, AddContactView view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    /**
     * Inizializza il controller, configurando eventuali gestori di eventi
     * e preparando la vista per l'uso.
     */
    public void initialize() {
        // Collega il pulsante Salva
        view.getSaveButton().setOnAction(event -> handleSaveContact());

        // Collega il pulsante Indietro (opzionale)
        view.getBackButton().setOnAction(event -> handleBack());
    }

    /**
     * Gestore per il pulsante "Indietro".
     * Può essere personalizzato in base alla logica necessaria.
     */
    private void handleBack() {
        System.out.println("Pulsante Indietro premuto");
    }

    /**
     * Gestisce il salvataggio di un nuovo contatto.
     *
     * Questo metodo raccoglie i dati inseriti nella vista, li valida e
     * li salva nel modello della rubrica.
     */
    public void handleSaveContact() {
        String firstName = view.getFirstNameField().getText().trim();
        String lastName = view.getLastNameField().getText().trim();
        List<String> phoneNumbers = view.getPhoneNumbers();
        List<String> emails = view.getEmails();
        String note = view.getNoteField().getText().trim();

        if ((firstName == null || firstName.isEmpty()) && (lastName == null || lastName.isEmpty())) {
            showError("È necessario inserire almeno il nome o il cognome.");
            return;
        }
        if (phoneNumbers != null) {
            phoneNumbers = phoneNumbers.stream()
                    .filter(phone -> phone != null && !phone.isEmpty())
                    .toList();
            if (phoneNumbers.size() > 3) {
                showError("È possibile inserire al massimo 3 numeri di telefono.");
                return;
            }
        }
        if (emails != null) {
            emails = emails.stream()
                    .filter(email -> email != null && !email.isEmpty())
                    .toList();
            if (emails.size() > 3) {
                showError("È possibile inserire al massimo 3 email.");
                return;
            }
        }

        if (note.length() > 100) {
            showError("La nota non può superare i 100 caratteri.");
            return;
        }
        Contact newContact = new Contact(firstName, lastName);
        if (phoneNumbers != null) {
            phoneNumbers.forEach(newContact::addPhoneNumber);
        }
        if (emails != null) {
            emails.forEach(newContact::addEmail);
        }
        newContact.setNote(note);
        model.addContact(newContact);
        System.out.println("Contatto aggiunto con successo!");
        System.out.println(formatContactDetails(newContact));
        view.clearFields();
        showSuccess("Contatto aggiunto con successo!");
    }
    private String formatContactDetails(Contact contact) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dettagli del contatto aggiunto:\n");
        sb.append("- Nome: ").append(contact.getFirstName() == null ? "N/D" : contact.getFirstName()).append("\n");
        sb.append("- Cognome: ").append(contact.getLastName() == null ? "N/D" : contact.getLastName()).append("\n");
        sb.append("- Numeri di Telefono: ").append(contact.getPhoneNumbers().isEmpty() ? "Nessuno" : String.join(", ", contact.getPhoneNumbers())).append("\n");
        sb.append("- Email: ").append(contact.getEmails().isEmpty() ? "Nessuna" : String.join(", ", contact.getEmails())).append("\n");
        sb.append("- Nota: ").append(contact.getNote() == null || contact.getNote().isEmpty() ? "Nessuna" : contact.getNote()).append("\n");
        return sb.toString();
    }



    /**
     * Mostra un messaggio di errore all'utente.
     *
     * @param message Il messaggio da visualizzare.
     */
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Mostra un messaggio di successo all'utente.
     *
     * @param message Il messaggio da visualizzare.
     */
    private void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Setta il modello della rubrica.
     *
     * @param model Il modello da utilizzare.
     */
    public void setModel(AddressBookModel model) {
        this.model = model;
    }

    /**
     * Setta la vista della rubrica.
     *
     * @param view La vista da utilizzare.
     */
    public void setView(AddContactView view) {
        this.view = view;
    }
}
