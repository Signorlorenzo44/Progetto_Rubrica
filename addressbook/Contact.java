package com.example.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe che rappresenta un contatto in una rubrica.
 */
public class Contact {

    private String firstName;
    private String lastName;
    private List<String> phoneNumbers;
    private List<String> emails;
    private String note;

    /**
     * Costruttore che accetta un nome completo.
     * @param fullName il nome completo del contatto
     */
    public Contact(String fullName) {
        String[] nameParts = fullName.split(" ", 2);
        this.firstName = nameParts.length > 0 ? nameParts[0] : "";
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
        this.phoneNumbers = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.note = "";
    }

    /**
     * Costruttore che accetta nome e cognome separatamente.
     * @param firstName il nome del contatto
     * @param lastName il cognome del contatto
     */
    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumbers = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.note = "";
    }

    /**
     * Ottiene il nome del contatto.
     * @return il nome
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Imposta il nome del contatto.
     * @param firstName il nuovo nome
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Ottiene il cognome del contatto.
     * @return il cognome
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Imposta il cognome del contatto.
     * @param lastName il nuovo cognome
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Ottiene i numeri di telefono del contatto.
     * @return la lista dei numeri di telefono
     */
    public List<String> getPhoneNumbers() {
        return new ArrayList<>(phoneNumbers);
    }

    /**
     * Imposta i numeri di telefono del contatto.
     * @param phoneNumbers la nuova lista di numeri di telefono
     */
    public void setPhoneNumbers(List<String> phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.size() > 3) {
            throw new IllegalArgumentException("Un contatto può avere al massimo 3 numeri di telefono.");
        }
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
    }

    /**
     * Aggiunge un numero di telefono al contatto.
     * @param phoneNumber il numero di telefono da aggiungere
     */
    public void addPhoneNumber(String phoneNumber) {
        if (this.phoneNumbers.size() >= 3) {
            throw new IllegalStateException("Non è possibile aggiungere più di 3 numeri di telefono.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Il numero di telefono non può essere nullo o vuoto.");
        }
        this.phoneNumbers.add(phoneNumber);
    }

    /**
     * Ottiene gli indirizzi email del contatto.
     *
     * @return la lista degli indirizzi email
     */
    public ArrayList<String> getEmails() {
        return new ArrayList<>(emails);
    }

    /**
     * Aggiunge un indirizzo email al contatto.
     * @param email l'indirizzo email da aggiungere
     */
    public void addEmail(String email) {
        if (this.emails.size() >= 3) {
            throw new IllegalStateException("Non è possibile aggiungere più di 3 indirizzi email.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("L'indirizzo email non può essere nullo o vuoto.");
        }
        this.emails.add(email);
    }

    /**
     * Ottiene le note associate al contatto.
     * @return la nota
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta una nota per il contatto.
     * @param note la nuova nota
     */
    public void setNote(String note) {
        if (note == null) {
            throw new IllegalArgumentException("La nota non può essere nulla.");
        }
        this.note = note;
    }

    /**
     * Ottiene il nome completo del contatto.
     * @return il nome completo (nome + cognome)
     */
    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }

    /**
     * Imposta un nuovo nome completo per il contatto.
     * @param fullName il nuovo nome completo
     */
    public void setFullName(String fullName) {
        String[] nameParts = fullName.split(" ", 2);
        this.firstName = nameParts.length > 0 ? nameParts[0] : "";
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
    }

    /**
     * Aggiorna il nome del contatto.
     * @param updatedName il nuovo nome completo
     */
    public void setName(String updatedName) {
        if (updatedName == null || updatedName.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere nullo o vuoto.");
        }
        String[] nameParts = updatedName.split(" ", 2);
        this.firstName = nameParts.length > 0 ? nameParts[0] : "";
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
    }

    public void setPhone(String updatedPhone) {
        if (updatedPhone == null || updatedPhone.isBlank()) {
            throw new IllegalArgumentException("Il numero di telefono non può essere nullo o vuoto.");
        }
        if (this.phoneNumbers.isEmpty()) {
            this.phoneNumbers.add(updatedPhone);
        } else {
            this.phoneNumbers.set(0, updatedPhone); // Aggiorna il primo numero della lista
        }
    }

    public void setEmail(String updatedEmail) {
        if (updatedEmail == null || updatedEmail.isBlank()) {
            this.emails.clear(); // Rimuove le email se il valore aggiornato è vuoto
        } else {
            if (this.emails.isEmpty()) {
                this.emails.add(updatedEmail);
            } else {
                this.emails.set(0, updatedEmail); // Aggiorna la prima email della lista
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        String phoneList = String.join(", ", phoneNumbers);
        String emailList = String.join(", ", emails);
        return getFullName() + " - Phones: [" + phoneList + "] - Emails: [" + emailList + "]";
    }

    public void setEmails(List<String> emails) {
        if (emails == null) {
            this.emails.clear();
        } else if (emails.size() > 3) {
            throw new IllegalArgumentException("Un contatto può avere al massimo 3 indirizzi email.");
        } else {
            this.emails = new ArrayList<>(emails); // Copia la lista per evitare modifiche esterne
        }
    }

}