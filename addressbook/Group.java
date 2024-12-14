package com.example.addressbook;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupName;
    private List<Contact> members;

    public Group(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
    }

    public boolean addContact(Contact contact) {
        if (!members.contains(contact)) {
            members.add(contact);
            return true;
        }
        return false;
    }
    public boolean removeContact(Contact contact) {
        return members.remove(contact);
    }

    public List<Contact> getMembers() {
        return new ArrayList<>(members);
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String newGroupName) {
        this.groupName = newGroupName;
    }

    public void setMembers(List<Contact> newMembers) {
        this.members = new ArrayList<>(newMembers);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gruppo: ").append(groupName).append("\nMembri:\n");
        if (members.isEmpty()) {
            sb.append("Nessun membro.");
        } else {
            for (Contact contact : members) {
                sb.append("- ").append(contact.getFullName()).append("\n");
            }
        }
        return sb.toString();
    }
    public void setGroupName() {
    }
}