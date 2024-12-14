package com.example.addressbook;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GroupControllerTest {

    private GroupController controller;
    private GroupView mockView;
    private AddressBookModel mockModel;

    @BeforeEach
    void setUp() throws Exception {
        if (Application.getUserAgentStylesheet() == null) {
            Application.launch(JavaFXTestApp.class);
        }
        mockView = new GroupView();
        mockModel = new AddressBookModel();
        controller = new GroupController(mockView, mockModel);
    }

    @Test
    void updateGroupList() {
        Group group1 = new Group("Family");
        Group group2 = new Group("Friends");
        mockModel.addGroup(group1);
        mockModel.addGroup(group2);
        controller.updateGroupList();
        assertTrue(mockView.getGroupListView().getItems().contains(group1));
        assertTrue(mockView.getGroupListView().getItems().contains(group2));
    }

    public static class JavaFXTestApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setScene(new Scene(new GroupView().getRoot()));
            primaryStage.show();
            primaryStage.close();
        }
    }
}
