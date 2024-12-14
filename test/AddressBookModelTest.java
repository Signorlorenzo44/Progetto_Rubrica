package com.example.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookModelTest {

    private AddressBookModel model;
    private Contact contact1;
    private Contact contact2;
    private Group group1;
    private Group group2;
    private int changeCounter;

    @BeforeEach
    void setUp() {
        model = new AddressBookModel();
        contact1 = new Contact("John", "Doe");
        contact1.addEmail("john.doe@example.com");
        contact1.addPhoneNumber("1234567890");

        contact2 = new Contact("Jane", "Smith");
        contact2.addPhoneNumber("0987654321");

        group1 = new Group("Family");
        group2 = new Group("Work");

        changeCounter = 0;
        model.addChangeListener(() -> changeCounter++);
    }

    @Test
    void testAddContact() {
        model.addContact(contact1);
        assertEquals(1, model.getContacts().size());
        assertTrue(model.getContacts().contains(contact1));
        assertEquals(1, changeCounter);
    }

    @Test
    void testRemoveContact() {
        model.addContact(contact1);
        model.removeContact(contact1);
        assertEquals(0, model.getContacts().size());
        assertEquals(2, changeCounter); // Aggiunta + rimozione
    }

    @Test
    void testRemoveNonExistentContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> model.removeContact(contact1));
    }

    @Test
    void testUpdateContact() {
        model.addContact(contact1);
        Contact updated = new Contact("John", "Doe");
        updated.addEmail("john.new@example.com");
        model.updateContact(updated);
        assertEquals(1, model.getContacts().size());
        assertTrue(model.getContacts().contains(updated));
        assertFalse(model.getContacts().contains(contact1));
        assertEquals(2, changeCounter);
    }

    @Test
    void testUpdateNonExistentContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> model.updateContact(contact1));
    }

    @Test
    void testSearchContacts() {
        model.addContact(contact1);
        model.addContact(contact2);
        List<Contact> results = model.searchContacts("john");
        assertEquals(1, results.size());
        assertTrue(results.contains(contact1));
    }

    @Test
    void testSearchContactsThrowsOnInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> model.searchContacts(""));
        assertThrows(IllegalArgumentException.class, () -> model.searchContacts(null));
    }

    @Test
    void testAddGroup() {
        model.addGroup(group1);
        assertEquals(1, model.getGroups().size());
        assertTrue(model.getGroups().contains(group1));
        assertEquals(1, changeCounter);
    }

    @Test
    void testRemoveGroup() {
        model.addGroup(group1);
        model.removeGroup(group1);
        assertEquals(0, model.getGroups().size());
        assertEquals(2, changeCounter);
    }

    @Test
    void testRemoveNonExistentGroupThrows() {
        assertThrows(IllegalArgumentException.class, () -> model.removeGroup(group1));
    }

    @Test
    void testUpdateGroup() {
        model.addGroup(group1);
        Group updatedGroup = new Group("Friends");
        model.updateGroup(updatedGroup);
        assertTrue(model.getGroups().contains(updatedGroup));
        assertFalse(model.getGroups().contains(group1));
        assertEquals(2, changeCounter);
    }

    @Test
    void testUpdateNonExistentGroupThrows() {
        assertThrows(IllegalArgumentException.class, () -> model.updateGroup(group1));
    }

    @Test
    void testFilterContacts() {
        model.addContact(contact1);
        model.addContact(contact2);
        List<Contact> filtered = model.filterContacts();
        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(contact1));
        assertTrue(filtered.contains(contact2));
    }

    @Test
    void testFilterContactsEmpty() {
        assertTrue(model.filterContacts().isEmpty());
    }

    @Test
    void testGetGroupsForContact() {
        model.addContact(contact1);
        model.addGroup(group1);
        group1.addMember(contact1);
        List<Group> groupsForContact = model.getGroupsForContact(contact1);
        assertEquals(1, groupsForContact.size());
        assertTrue(groupsForContact.contains(group1));
    }

    @Test
    void testListeners() {
        model.addContact(contact1);
        assertEquals(1, changeCounter);
        model.addContact(contact2);
        assertEquals(2, changeCounter);
        model.removeContact(contact1);
        assertEquals(3, changeCounter);
    }
}
