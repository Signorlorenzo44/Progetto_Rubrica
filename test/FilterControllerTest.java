package com.example.addressbook;

import javafx.application.Platform;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class FilterControllerTest {

    private static boolean toolkitInitialized = false;

    private AddressBookModel mockModel;
    private FilterView mockView;
    private FilterController controller;

    @BeforeAll
    static void initToolkit() throws InterruptedException {
        if (!toolkitInitialized) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            latch.await();
            toolkitInitialized = true;
        }
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        mockModel = new MockAddressBookModel();

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            mockView = new MockFilterView();
            controller = new FilterController(mockModel, mockView);
            controller.initialize();
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testApplyFiltersWithConEmail() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ((MockFilterView) mockView).setSelectedFilter("Con Email");
            mockView.getApplyButton().fire();
            latch.countDown();
        });
        latch.await();
    }

    // Mock del modello
    class MockAddressBookModel extends AddressBookModel {
        @Override
        public List<Contact> filterContacts() {
            Contact c = new Contact("Mario", "Rossi");
            c.addEmail("mario.rossi@example.com");
            return Collections.singletonList(c);
        }

        @Override
        public List<Contact> getContacts() {
            Contact c1 = new Contact("John", "Doe");
            c1.addEmail("john.doe@example.com");
            Contact c2 = new Contact("Jane", "Smith");
            c2.addPhoneNumber("1234567890");
            return List.of(c1, c2);
        }

        @Override
        public void updateContact(Contact updatedContact) {
            System.out.println("updateContact chiamato con: " + updatedContact);
        }
    }

    class MockFilterView extends FilterView {

        private Button applyButton;
        private String selectedFilter = "Tutti";

        public MockFilterView() {
            applyButton = new Button("Applica");

        }

        @Override
        public Button getApplyButton() {
            return applyButton;
        }

        @Override
        public String getSelectedFilter() {
            return selectedFilter;
        }

        public void setSelectedFilter(String filter) {
            this.selectedFilter = filter;
        }
    }
}
