package com.example.addressbook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 Classe di utilità per rilevare contatti duplicati nella rubrica.
 Questa classe fornisce un metodo per analizzare una lista di contatti
 e identificare eventuali duplicati basandosi su criteri definiti, come
 nome, numero di telefono o indirizzo email.
 */

public class DuplicateChecker {
    public List<Contact> findDuplicates(List<Contact> contacts) {
        HashSet<String> seen = new HashSet<>();
        List<Contact> duplicates = new ArrayList<>();

        for (Contact contact : contacts) {
            String phoneNumber = contact.getPhoneNumbers().isEmpty() ? "" : contact.getPhoneNumbers().get(0);
            String identifier = contact.getFullName() + phoneNumber;

            if (!seen.add(identifier)) {
                duplicates.add(contact);
            }
        }

        return duplicates;
    }

}