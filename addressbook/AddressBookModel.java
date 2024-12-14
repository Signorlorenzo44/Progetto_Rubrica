package com.example.addressbook;

import com.example.addressbook.Contact;
import com.example.addressbook.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modello della rubrica che gestisce contatti e gruppi.
 */
public class AddressBookModel {

    private final List<Runnable> listeners;
    private final List<Contact> contacts;
    private final List<Group> groups;

    /**
     * Costruttore per inizializzare le liste di contatti e gruppi.
     *
     * @param listeners Una lista di listener per notificare i cambiamenti.
     */
    public AddressBookModel(List<Runnable> listeners) {
        this.listeners = listeners != null ? new ArrayList<>(listeners) : new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    /**
     * Costruttore di default.
     */
    public AddressBookModel() {
        this(new ArrayList<>());
    }
    /**
     * Aggiunge un nuovo contatto alla rubrica.
     *
     * @param contact Il contatto da aggiungere.
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Il contatto non può essere nullo.");
        }
        contacts.add(contact);
        notifyListeners();
    }

    /**
     * Rimuove un contatto dalla rubrica.
     *
     * @param contact Il contatto da rimuovere.
     */
    public void removeContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Il contatto non può essere nullo.");
        }
        if (!contacts.remove(contact)) {
            throw new IllegalArgumentException("Il contatto specificato non esiste nella rubrica.");
        }
        groups.forEach(group -> group.removeContact(contact));
        notifyListeners();
    }

    /**
     * Restituisce tutti i contatti nella rubrica.
     *
     * @return Una lista di contatti.
     */
    public List<Contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    /**
     * Aggiorna i dettagli di un contatto esistente.
     *
     * @param updatedContact Il contatto con i dettagli aggiornati.
     */
    public void updateContact(Contact updatedContact) {
        if (updatedContact == null) {
            throw new IllegalArgumentException("Il contatto aggiornato non può essere nullo.");
        }
        int index = contacts.indexOf(updatedContact);
        if (index != -1) {
            contacts.set(index, updatedContact);
            notifyListeners();
        } else {
            throw new IllegalArgumentException("Il contatto da aggiornare non esiste nella rubrica.");
        }
    }

    /**
     * Cerca i contatti in base a un termine di ricerca.
     *
     * @param searchTerm Il termine di ricerca (nome, numero di telefono o email).
     * @return Una lista di contatti che corrispondono al termine di ricerca.
     */
    public List<Contact> searchContacts(String searchTerm) {

        if (searchTerm == null || searchTerm.isBlank()) {

            throw new IllegalArgumentException("Specifica un termine di ricerca valido.");

        }
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        return contacts.stream()
                .filter(contact -> contact.getFullName().toLowerCase().contains(lowerCaseSearchTerm)
                        || contact.getPhoneNumbers().stream().anyMatch(phone -> phone.toLowerCase().contains(lowerCaseSearchTerm))
                        || contact.getEmails().stream().anyMatch(email -> email.toLowerCase().contains(lowerCaseSearchTerm)))
                .collect(Collectors.toList());
    }

    /**
     * Aggiunge un nuovo gruppo alla rubrica.
     *
     * @param group Il gruppo da aggiungere.
     */
    public void addGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Il gruppo non può essere nullo.");
        }
        groups.add(group);
        notifyListeners();
    }

    /**
     * Rimuove un gruppo dalla rubrica.
     *
     * @param group Il gruppo da rimuovere.
     */
    public void removeGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Il gruppo non può essere nullo.");
        }
        if (!groups.remove(group)) {
            throw new IllegalArgumentException("Il gruppo specificato non esiste nella rubrica.");
        }
        notifyListeners();
    }

    /**
     * Aggiorna i dettagli di un gruppo esistente.
     *
     * @param updatedGroup Il gruppo con i dettagli aggiornati.
     */
    public void updateGroup(Group updatedGroup) {
        if (updatedGroup == null) {
            throw new IllegalArgumentException("Il gruppo aggiornato non può essere nullo.");
        }
        int index = groups.indexOf(updatedGroup);
        if (index != -1) {
            groups.set(index, updatedGroup);
            notifyListeners();
        } else {
            throw new IllegalArgumentException("Il gruppo da aggiornare non esiste nella rubrica.");
        }
    }

    /**
     * Restituisce tutti i gruppi nella rubrica.
     *
     * @return Una lista di gruppi.
     */
    public List<Group> getGroups() {
        return new ArrayList<>(groups);
    }
    /**
     * Filtra i contatti che hanno almeno un numero di telefono o un'email.
     *
     * @return Una lista di contatti filtrati.
     */
    public List<Contact> filterContacts() {
        return contacts.stream()
                .filter(contact -> !contact.getPhoneNumbers().isEmpty() || !contact.getEmails().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Aggiunge un listener per i cambiamenti.
     *
     * @param listener Un listener da notificare quando ci sono cambiamenti.
     */
    public void addChangeListener(Runnable listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Notifica tutti i listener dei cambiamenti.
     */
    private void notifyListeners() {
        listeners.forEach(Runnable::run);
    }

    /**
     * Restituisce i gruppi a cui appartiene un contatto specifico.
     *
     * @param contact Il contatto da verificare.
     * @return Una lista di gruppi che contengono il contatto.
     */
    public List<Group> getGroupsForContact(Contact contact) {
        return groups.stream()
                .filter(group -> group.getMembers().contains(contact))
                .collect(Collectors.toList());
    }
}
