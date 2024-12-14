package com.example.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateCheckerTest {

    private DuplicateChecker duplicateChecker;

    @BeforeEach
    void setUp() {
        duplicateChecker = new DuplicateChecker();
    }

    @Test
    void testNoDuplicates() {
        // Test: Nessun duplicato nella lista
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Marco", "Rossi"));
        contacts.add(new Contact("Luca", "Bianchi"));

        List<Contact> duplicates = duplicateChecker.findDuplicates(contacts);

        assertTrue(duplicates.isEmpty(), "Non dovrebbero esserci duplicati nella lista.");
    }

    @Test
    void testWithExactDuplicates() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Marco", "Rossi"));
        contacts.add(new Contact("Marco", "Rossi"));

        List<Contact> duplicates = duplicateChecker.findDuplicates(contacts);

        assertEquals(1, duplicates.size(), "Dovrebbe esserci un duplicato nella lista.");
        assertEquals("Marco Rossi", duplicates.get(0).getFullName(), "Il duplicato dovrebbe essere 'Marco Rossi'.");
    }

    @Test
    void testNoDuplicatesWithDifferentPhoneNumbers() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Marco", "Rossi"));
        contacts.add(new Contact("Marco", "Rossi"));
        List<Contact> duplicates = duplicateChecker.findDuplicates(contacts);

        assertTrue(duplicates.isEmpty(), "Contatti con numeri diversi non dovrebbero essere considerati duplicati.");
    }

    @Test
    void testDuplicatesBasedOnEmptyPhoneNumber() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Marco", "Rossi"));
        contacts.add(new Contact("Marco", "Rossi"));

        List<Contact> duplicates = duplicateChecker.findDuplicates(contacts);

        assertEquals(1, duplicates.size(), "Dovrebbe esserci un duplicato anche se il numero di telefono è vuoto.");
        assertEquals("Marco Rossi", duplicates.get(0).getFullName(), "Il duplicato dovrebbe essere 'Marco Rossi'.");
    }

    @Test
    void testDuplicatesWithMixedFields() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Marco"));
        contacts.add(new Contact("Marco", "Rossi"));

        List<Contact> duplicates = duplicateChecker.findDuplicates(contacts);

        assertEquals(1, duplicates.size(), "Contatti con lo stesso numero ma email diverse dovrebbero essere considerati duplicati.");
    }
}
