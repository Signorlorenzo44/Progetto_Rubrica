package com.example.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    private Database database;
    private Contact contact1;
    private Contact contact2;

    @BeforeEach
    void setUp() throws SQLException {
        database = new Database();
        contact1 = new Contact("John", "Doe");
        contact2 = new Contact("Jane", "Smith");
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.close();
    }

    @Test
    void testAddGroup() throws SQLException {
        List<Contact> members = Arrays.asList(contact1, contact2);
        database.addGroup("Family", members);
        String query = "SELECT group_name FROM groups WHERE group_name = 'Family'";
        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            assertTrue(rs.next());
            assertEquals("Family", rs.getString("group_name"));
        }

        query = "SELECT contact_first_name, contact_last_name FROM group_members WHERE group_name = 'Family'";
        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            assertTrue(rs.next());
            assertEquals("John", rs.getString("contact_first_name"));
            assertEquals("Doe", rs.getString("contact_last_name"));

            assertTrue(rs.next());
            assertEquals("Jane", rs.getString("contact_first_name"));
            assertEquals("Smith", rs.getString("contact_last_name"));
        }
    }

    @Test
    void testUpdateGroup() throws SQLException {

        List<Contact> members = Arrays.asList(contact1);
        database.addGroup("Family", members);
        List<Contact> newMembers = Arrays.asList(contact1, contact2);
        database.updateGroup("Family", "Friends", newMembers);
        String query = "SELECT group_name FROM groups WHERE group_name = 'Friends'";
        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            assertTrue(rs.next());
            assertEquals("Friends", rs.getString("group_name"));
        }

        query = "SELECT contact_first_name, contact_last_name FROM group_members WHERE group_name = 'Friends'";
        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            assertTrue(rs.next());
            assertEquals("John", rs.getString("contact_first_name"));
            assertEquals("Doe", rs.getString("contact_last_name"));

            assertTrue(rs.next());
            assertEquals("Jane", rs.getString("contact_first_name"));
            assertEquals("Smith", rs.getString("contact_last_name"));
        }
    }

    @Test
    void testDeleteGroup() throws SQLException {

        List<Contact> members = Arrays.asList(contact1, contact2);
        database.addGroup("Family", members);
        database.deleteGroup("Family");
        String query = "SELECT group_name FROM groups WHERE group_name = 'Family'";
        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            assertFalse(rs.next());
        }
        query = "SELECT contact_first_name, contact_last_name FROM group_members WHERE group_name = 'Family'";
        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            assertFalse(rs.next());
        }
    }

    @Test
    void testLogGroupChange() throws SQLException {

        List<Contact> members = Arrays.asList(contact1);
        database.addGroup("Family", members);
        String query = "SELECT * FROM group_changes WHERE group_name = 'Family' AND operation = 'ADD'";
        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            assertTrue(rs.next());
            assertEquals("Family", rs.getString("group_name"));
            assertEquals("ADD", rs.getString("operation"));
        }
    }
}
