package com.example.addressbook;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    void getFirstName() {
        Contact contact = new Contact("Marco", "Rossi");
        assertEquals("Marco", contact.getFirstName(), "Il nome dovrebbe essere 'Marco'.");
    }

    @Test
    void setFirstName() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setFirstName("Luca");
        assertEquals("Luca", contact.getFirstName(), "Il nome dovrebbe essere aggiornato a 'Luca'.");
    }

    @Test
    void getLastName() {
        Contact contact = new Contact("Marco", "Rossi");
        assertEquals("Rossi", contact.getLastName(), "Il cognome dovrebbe essere 'Rossi'.");
    }

    @Test
    void setLastName() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setLastName("Bianchi");
        assertEquals("Bianchi", contact.getLastName(), "Il cognome dovrebbe essere aggiornato a 'Bianchi'.");
    }

    @Test
    void getPhoneNumbers() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setPhoneNumbers(List.of("1234567890", "0987654321"));
        assertEquals(2, contact.getPhoneNumbers().size(), "Dovrebbero esserci 2 numeri di telefono.");
    }

    @Test
    void setPhoneNumbers() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setPhoneNumbers(List.of("1234567890", "0987654321"));
        assertEquals("1234567890", contact.getPhoneNumbers().get(0), "Il primo numero di telefono dovrebbe essere '1234567890'.");
    }

    @Test
    void addPhoneNumber() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.addPhoneNumber("1234567890");
        contact.addPhoneNumber("0987654321");
        assertEquals(2, contact.getPhoneNumbers().size(), "Dovrebbero esserci 2 numeri di telefono.");
    }

    @Test
    void getEmails() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setEmails(List.of("marco@example.com", "rossi@example.com"));
        assertEquals(2, contact.getEmails().size(), "Dovrebbero esserci 2 indirizzi email.");
    }

    @Test
    void addEmail() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.addEmail("marco@example.com");
        contact.addEmail("rossi@example.com");
        assertEquals(2, contact.getEmails().size(), "Dovrebbero esserci 2 indirizzi email.");
    }

    @Test
    void getNote() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setNote("Questo è un contatto importante.");
        assertEquals("Questo è un contatto importante.", contact.getNote(), "La nota dovrebbe corrispondere al valore impostato.");
    }

    @Test
    void setNote() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setNote("Nuova nota.");
        assertEquals("Nuova nota.", contact.getNote(), "La nota dovrebbe essere aggiornata.");
    }

    @Test
    void getFullName() {
        Contact contact = new Contact("Marco", "Rossi");
        assertEquals("Marco Rossi", contact.getFullName(), "Il nome completo dovrebbe essere 'Marco Rossi'.");
    }

    @Test
    void setFullName() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setFullName("Luca Bianchi");
        assertEquals("Luca Bianchi", contact.getFullName(), "Il nome completo dovrebbe essere 'Luca Bianchi'.");
    }

    @Test
    void setName() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setName("Luca Bianchi");
        assertEquals("Luca", contact.getFirstName(), "Il nome dovrebbe essere 'Luca'.");
        assertEquals("Bianchi", contact.getLastName(), "Il cognome dovrebbe essere 'Bianchi'.");
    }

    @Test
    void setPhone() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setPhone("1234567890");
        assertEquals("1234567890", contact.getPhoneNumbers().get(0), "Il numero di telefono dovrebbe essere '1234567890'.");
    }

    @Test
    void setEmail() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setEmail("marco@example.com");
        assertEquals("marco@example.com", contact.getEmails().get(0), "L'indirizzo email dovrebbe essere 'marco@example.com'.");
    }

    @Test
    void testEquals() {
        Contact contact1 = new Contact("Marco", "Rossi");
        Contact contact2 = new Contact("Marco", "Rossi");
        assertEquals(contact1, contact2, "I contatti dovrebbero essere uguali.");
    }

    @Test
    void testHashCode() {
        Contact contact1 = new Contact("Marco", "Rossi");
        Contact contact2 = new Contact("Marco", "Rossi");
        assertEquals(contact1.hashCode(), contact2.hashCode(), "I codici hash dovrebbero essere uguali.");
    }

    @Test
    void testToString() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.addPhoneNumber("1234567890");
        contact.addEmail("marco@example.com");
        String expected = "Marco Rossi - Phones: [1234567890] - Emails: [marco@example.com]";
        assertEquals(expected, contact.toString(), "La rappresentazione in stringa non corrisponde.");
    }

    @Test
    void setEmails() {
        Contact contact = new Contact("Marco", "Rossi");
        contact.setEmails(List.of("marco@example.com", "rossi@example.com"));
        assertEquals(2, contact.getEmails().size(), "Dovrebbero esserci 2 indirizzi email.");
    }
}
