package com.example.addressbook;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainControllerTest {

    private AddressBookModel model;
    private MainView view;
    private MainController controller;

    @BeforeAll
    void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        model = new AddressBookModel();
        Contact contact1 = new Contact("John", "Doe");
        contact1.addPhoneNumber("1234567890");
        contact1.addEmail("john.doe@example.com");

        Contact contact2 = new Contact("Jane", "Smith");
        contact2.addEmail("jane.smith@example.com");

        model.addContact(contact1);
        model.addContact(contact2);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view = new MainView();
            controller = new MainController(model, view);
            controller.initialize();
            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(view.getRoot(), 600, 600));
            stage.show();
            latch.countDown();
        });
        latch.await();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Stage stage = (Stage) view.getRoot().getScene().getWindow();
            stage.close();
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testInitialContactsDisplayed() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ListView<Contact> listView = view.getContactListView();
            assertEquals(2, listView.getItems().size());
            assertEquals("John Doe", listView.getItems().get(0).getFullName());
            assertEquals("Jane Smith", listView.getItems().get(1).getFullName());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testSearchFunction() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            TextField searchField = view.getSearchField();
            searchField.setText("john");
            ListView<Contact> listView = view.getContactListView();
            assertEquals(1, listView.getItems().size());
            assertEquals("John Doe", listView.getItems().get(0).getFullName());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testFilterWithEmail() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ComboBox<String> filterComboBox = view.getFilterComboBox();
            filterComboBox.setValue("Con Email");
            controller.handleFilter("Con Email");
            ListView<Contact> listView = view.getContactListView();
            assertEquals(2, listView.getItems().size());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testNavigateToAddContact() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Button addContactButton = view.getAddContactButton();
            addContactButton.fire();
            latch.countDown();
        });
        latch.await();
    }
}
