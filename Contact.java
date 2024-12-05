package com.example.addressbook;

/**
 * Rappresenta un contatto nella rubrica.
 *
 * Questa classe contiene le informazioni di base relative a un contatto,
 * come nome, cognome, numero di telefono, email e note aggiuntive.
 */
public class Contact {

    /**
     * Nome del contatto.
     */
    private String firstName;

    /**
     Cognome del contatto.
     */
    private String lastName;

    /**
      Numero di telefono del contatto.
     */
    private String phoneNumber;

    /**
     Indirizzo email del contatto.
     */
    private String email;

    /**
     Note aggiuntive sul contatto.
     */
    private String note;

    /**
      Restituisce il nome completo del contatto.
     Combina il nome e il cognome in una singola stringa.
     @return Una stringa formata dal nome e cognome del contatto separati da uno spazio.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
