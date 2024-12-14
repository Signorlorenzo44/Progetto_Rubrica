package com.example.addressbook;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupView {
    private VBox root;
    private Button backButton;
    private Button addGroupButton;
    private Button removeMemberButton;
    private ListView<Group> groupListView;
    private ListView<Contact> memberListView;
    private Button deleteGroupButton;
    private Button editGroupButton;
    private List<Contact> allContacts;
    private EditGroupView editGroupView;

    public GroupView() {
        root = new VBox();
        root.setSpacing(10);
        backButton = new Button("Indietro");
        addGroupButton = new Button("Aggiungi Gruppo");
        addGroupButton.setOnAction(event -> addNewGroup());
        removeMemberButton = new Button("Rimuovi Membri");
        removeMemberButton.setOnAction(event -> removeSelectedMembers());
        HBox buttonBox = new HBox(10, backButton, addGroupButton, removeMemberButton);
        groupListView = new ListView<>();
        groupListView.setPrefHeight(200);
        groupListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        memberListView = new ListView<>();
        memberListView.setPrefHeight(200);
        memberListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        editGroupButton = new Button("Modifica Gruppo");
        editGroupButton.setOnAction(event -> openEditGroupView());
        deleteGroupButton = new Button("Elimina Gruppo");
        deleteGroupButton.setOnAction(event -> removeSelectedGroup());
        root.getChildren().addAll(
                buttonBox,
                new Label("Gruppi:"),
                groupListView,
                new Label("Membri del gruppo selezionato:"),
                memberListView,
                editGroupButton,
                deleteGroupButton
        );
        allContacts = new ArrayList<>();
        initializeExampleGroups();
        groupListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                memberListView.getItems().setAll(newSelection.getMembers());
            } else {
                memberListView.getItems().clear();
            }
        });
    }

    private void initializeExampleGroups() {
        Contact contact1 = new Contact("Mario Rossi");
        Contact contact2 = new Contact("Luigi Bianchi");
        Contact contact3 = new Contact("Carla Verdi");
        Contact contact4 = new Contact("Anna Neri");

        // Aggiungi numeri di telefono o email casuali
        contact1.setPhoneNumbers(generateRandomPhoneList(1));
        contact2.setEmails(generateRandomEmailList(2));
        contact3.setPhoneNumbers(generateRandomPhoneList(2));
        contact4.setEmails(generateRandomEmailList(1));

        allContacts.add(contact1);
        allContacts.add(contact2);
        allContacts.add(contact3);
        allContacts.add(contact4);

        Group group1 = new Group("Famiglia");
        group1.addContact(contact1);
        group1.addContact(contact2);

        Group group2 = new Group("Amici");
        group2.addContact(contact3);
        group2.addContact(contact4);

        Group group3 = new Group("Colleghi");
        group3.addContact(contact1);
        group3.addContact(contact4);

        groupListView.getItems().addAll(group1, group2, group3);
    }

    private List<String> generateRandomPhoneList(int count) {
        Random rand = new Random();
        List<String> phoneList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String phone = "+39 3" + (rand.nextInt(900) + 100) + " " + (rand.nextInt(9000000) + 1000000);
            phoneList.add(phone);
        }
        return phoneList;
    }

    private List<String> generateRandomEmailList(int count) {
        String[] domains = {"gmail.com", "yahoo.com", "outlook.com", "example.com"};
        Random rand = new Random();
        List<String> emailList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = "user" + (rand.nextInt(1000) + 1);
            String domain = domains[rand.nextInt(domains.length)];
            emailList.add(name + "@" + domain);
        }
        return emailList;
    }

    private void removeSelectedMembers() {
        Group selectedGroup = groupListView.getSelectionModel().getSelectedItem();

        if (selectedGroup == null) {
            showAlert("Errore", "Nessun gruppo selezionato.");
            return;
        }

        List<Contact> selectedMembers = new ArrayList<>(memberListView.getSelectionModel().getSelectedItems());

        if (selectedMembers.isEmpty()) {
            showAlert("Errore", "Nessun membro selezionato.");
            return;
        }

        for (Contact contact : selectedMembers) {
            selectedGroup.removeContact(contact);
        }

        memberListView.getItems().removeAll(selectedMembers);
        showAlert("Successo", "Membri rimossi con successo.");
    }

    private void addNewGroup() {
        if (editGroupView == null) {
            editGroupView = new EditGroupView();
        }

        editGroupView.setGroup(new Group("Nuovo Gruppo"));
        editGroupView.show();

        editGroupView.getSaveButton().setOnAction(e -> {
            String groupName = editGroupView.getGroupName();
            List<String> memberNames = editGroupView.getMembers();

            if (groupName == null || groupName.trim().isEmpty()) {
                showAlert("Errore", "Il nome del gruppo non può essere vuoto.");
                return;
            }

            Group newGroup = new Group(groupName);

            for (String memberName : memberNames) {
                Contact contact = findContactByName(memberName);
                if (contact == null) {
                    contact = new Contact(memberName);
                    allContacts.add(contact);
                }
                newGroup.addContact(contact);
            }

            groupListView.getItems().add(newGroup);
            editGroupView.hide();
            showAlert("Successo", "Il nuovo gruppo è stato aggiunto con successo.");
        });

        editGroupView.getBackButton().setOnAction(e -> editGroupView.hide());
    }

    private void openEditGroupView() {
        if (editGroupView == null) {
            editGroupView = new EditGroupView();
        }

        Group selectedGroup = groupListView.getSelectionModel().getSelectedItem();

        if (selectedGroup != null) {
            editGroupView.setGroup(selectedGroup);
            editGroupView.show();

            editGroupView.getSaveButton().setOnAction(e -> {
                String newGroupName = editGroupView.getGroupName();
                List<String> newMemberNames = editGroupView.getMembers();

                if (newGroupName == null || newGroupName.trim().isEmpty()) {
                    showAlert("Errore", "Il nome del gruppo non può essere vuoto.");
                    return;
                }
                selectedGroup.setGroupName(newGroupName);
                List<Contact> updatedMembers = new ArrayList<>();
                for (String memberName : newMemberNames) {
                    Contact contact = findContactByName(memberName);
                    if (contact == null) {
                        contact = new Contact(memberName);
                        allContacts.add(contact);
                    }
                    updatedMembers.add(contact);
                }
                selectedGroup.setMembers(updatedMembers);
                groupListView.refresh();
                memberListView.getItems().setAll(selectedGroup.getMembers());

                editGroupView.hide();
                showAlert("Successo", "Il gruppo è stato aggiornato con successo.");
            });

            editGroupView.getBackButton().setOnAction(e -> editGroupView.hide());
        } else {
            showAlert("Errore", "Seleziona un gruppo da modificare.");
        }
    }



    private void removeSelectedGroup() {
        Group selectedGroup = groupListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            groupListView.getItems().remove(selectedGroup);
            memberListView.getItems().clear();
            showAlert("Gruppo Eliminato", "Il gruppo selezionato è stato eliminato.");
        } else {
            showAlert("Errore", "Seleziona un gruppo da eliminare.");
        }
    }

    private Contact findContactByName(String fullName) {
        return allContacts.stream()
                .filter(contact -> contact.getFullName().equals(fullName))
                .findFirst()
                .orElse(null);
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getRoot() {
        return root;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getEditGroupButton() {
        return editGroupButton;
    }
    public Group getSelectedGroup() {
        return groupListView.getSelectionModel().getSelectedItem();
    }
    public void updateGroupList() {
        groupListView.refresh();
    }
    public ListView<Group> getGroupListView() {
        return groupListView;
    }
    public Button getDeleteGroupButton() {
        return deleteGroupButton;
    }
    public ListView<Contact> getMemberListView() {
        return memberListView;
    }

    public Button getAddGroupButton() {
        return addGroupButton;
    }
    public Button getRemoveMemberButton() {
        return removeMemberButton;
    }
}
