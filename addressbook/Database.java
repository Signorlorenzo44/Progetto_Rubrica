package com.example.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;

    public Database() throws SQLException {
        String url = "jdbc:sqlite:addressbook.db";
        connection = DriverManager.getConnection(url);
        String createContactsTableSQL = """
                CREATE TABLE IF NOT EXISTS contacts (
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    note TEXT,
                    PRIMARY KEY (first_name, last_name)
                )
                """;

        String createPhoneNumbersTableSQL = """
                CREATE TABLE IF NOT EXISTS phone_numbers (
                    contact_first_name TEXT,
                    contact_last_name TEXT,
                    phone_number TEXT,
                    FOREIGN KEY(contact_first_name, contact_last_name) REFERENCES contacts(first_name, last_name)
                )
                """;

        String createEmailsTableSQL = """
                CREATE TABLE IF NOT EXISTS emails (
                    contact_first_name TEXT,
                    contact_last_name TEXT,
                    email TEXT,
                    FOREIGN KEY(contact_first_name, contact_last_name) REFERENCES contacts(first_name, last_name)
                )
                """;

        String createGroupsTableSQL = """
                CREATE TABLE IF NOT EXISTS groups (
                    group_name TEXT PRIMARY KEY
                )
                """;

        String createGroupMembersTableSQL = """
                CREATE TABLE IF NOT EXISTS group_members (
                    group_name TEXT,
                    contact_first_name TEXT,
                    contact_last_name TEXT,
                    FOREIGN KEY(group_name) REFERENCES groups(group_name),
                    FOREIGN KEY(contact_first_name, contact_last_name) REFERENCES contacts(first_name, last_name)
                )
                """;

        String createGroupChangesLogSQL = """
                CREATE TABLE IF NOT EXISTS group_changes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    group_name TEXT,
                    operation TEXT,
                    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
                )
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createContactsTableSQL);
            stmt.execute(createPhoneNumbersTableSQL);
            stmt.execute(createEmailsTableSQL);
            stmt.execute(createGroupsTableSQL);
            stmt.execute(createGroupMembersTableSQL);
            stmt.execute(createGroupChangesLogSQL);
        }
    }
    public void addGroup(String groupName, List<Contact> members) throws SQLException {
        String insertGroupSQL = "INSERT INTO groups (group_name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertGroupSQL)) {
            pstmt.setString(1, groupName);
            pstmt.executeUpdate();
        }
        addGroupMembers(groupName, members);
        logGroupChange(groupName, "ADD");
    }

    private void addGroupMembers(String groupName, List<Contact> members) throws SQLException {
        String insertMemberSQL = "INSERT INTO group_members (group_name, contact_first_name, contact_last_name) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertMemberSQL)) {
            for (Contact member : members) {
                pstmt.setString(1, groupName);
                pstmt.setString(2, member.getFirstName());
                pstmt.setString(3, member.getLastName());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public void updateGroup(String oldGroupName, String newGroupName, List<Contact> members) throws SQLException {
        deleteGroup(oldGroupName);
        addGroup(newGroupName, members);
        logGroupChange(oldGroupName, "UPDATE");
    }

    public void deleteGroup(String groupName) throws SQLException {
        String deleteMembersSQL = "DELETE FROM group_members WHERE group_name = ?";
        String deleteGroupSQL = "DELETE FROM groups WHERE group_name = ?";

        try (PreparedStatement pstmt1 = connection.prepareStatement(deleteMembersSQL);
             PreparedStatement pstmt2 = connection.prepareStatement(deleteGroupSQL)) {
            pstmt1.setString(1, groupName);
            pstmt1.executeUpdate();

            pstmt2.setString(1, groupName);
            pstmt2.executeUpdate();
        }
        logGroupChange(groupName, "DELETE");
    }
    private void logGroupChange(String groupName, String operation) throws SQLException {
        String logChangeSQL = "INSERT INTO group_changes (group_name, operation) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(logChangeSQL)) {
            pstmt.setString(1, groupName);
            pstmt.setString(2, operation);
            pstmt.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
