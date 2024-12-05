package com.example.addressbook;

import java.util.List;

/**
 * La classe rappresenta un gruppo all'interno della rubrica.
 *
 * Un gruppo è composto da un nome e da una lista di contatti
 * associati. È possibile aggiungere o rimuovere contatti da un gruppo.
 */
public class Group {

    /**
     Il nome del gruppo.
     */
    private String groupName;

    /**
      La lista dei contatti che appartengono al gruppo.
     */
    private List<Contact> members;

    /**
     Aggiunge un contatto al gruppo.
     * @param contact Il contatto da aggiungere al gruppo.
     */
    public void addContact(Contact contact) {
        members.add(contact);
    }

    /**
      Rimuove un contatto dal gruppo.
     * @param contact Il contatto da rimuovere dal gruppo.
     */
    public void removeContact(Contact contact) {
        members.remove(contact);
    }

}
