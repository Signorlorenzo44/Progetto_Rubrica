package com.example.addressbook;

import java.util.ArrayList;
import java.util.List;

/**
  Gestisce i dati per la rubrica.
  Questa classe è responsabile per la gestione e l'elaborazione dei contatti e dei gruppi nella rubrica.
  Permette di aggiungere, rimuovere contatti, trovare duplicati, applicare filtri e ottenere i gruppi.
 */
public class AddressBookModel {

    /**
     La lista dei contatti nella rubrica.
     */
    private List<Contact> contacts;

    /**
     La lista dei gruppi nella rubrica.
     */
    private List<Group> groups;

    /**
      Aggiunge un contatto alla rubrica.
      @param contact Il contatto da aggiungere.
     */
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    /**
      Rimuove un contatto dalla rubrica.
     @param contact Il contatto da rimuovere.
     */
    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    /**
      Trova i contatti duplicati nella rubrica.
      @return Una lista di contatti duplicati.
     */
    public List<Contact> findDuplicates() {
        // Implementazione qui
        return null;
    }

    /**
     Filtra i contatti in base ai criteri forniti.
     @param criteria I criteri di filtro (es. "phone", "email").
     @return Una lista di contatti che soddisfano i criteri di filtro.
     */
    public List<Contact> filterContacts(String criteria) {
        return new ArrayList<>();
    }

    /**
      Restituisce i gruppi nella rubrica.
      @return La lista dei gruppi.
     */
    public List<Group> getGroups() {
        return groups;
    }
}

