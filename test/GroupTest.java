package com.example.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    private Group group;
    private Contact contact1;
    private Contact contact2;

    @BeforeEach
    void setUp() {
        group = new Group("Family");
        contact1 = new Contact("John Doe");
        contact2 = new Contact("Jane Smith");
    }

    @Test
    void testAddContact() {
        assertTrue(group.addContact(contact1));
        assertTrue(group.getMembers().contains(contact1));
    }

    @Test
    void testAddContactDuplicate() {
        group.addContact(contact1);
        assertFalse(group.addContact(contact1));
    }

    @Test
    void testRemoveContact() {
        group.addContact(contact1);
        assertTrue(group.removeContact(contact1));
        assertFalse(group.getMembers().contains(contact1));
    }

    @Test
    void testRemoveNonExistentContact() {
        assertFalse(group.removeContact(contact1));
    }

    @Test
    void testGetMembers() {
        group.addContact(contact1);
        group.addContact(contact2);
        assertEquals(2, group.getMembers().size());
        assertTrue(group.getMembers().contains(contact1));
        assertTrue(group.getMembers().contains(contact2));
    }

    @Test
    void testSetGroupName() {
        group.setGroupName("Work");
        assertEquals("Work", group.getGroupName());
    }

    @Test
    void testToString() {
        group.addContact(contact1);
        group.addContact(contact2);
        String expected = "Gruppo: Family\nMembri:\n- John Doe\n- Jane Smith\n";
        assertEquals(expected, group.toString());
    }

    @Test
    void testToStringNoMembers() {
        String expected = "Gruppo: Family\nMembri:\nNessun membro.";
        assertEquals(expected, group.toString());
    }
}
