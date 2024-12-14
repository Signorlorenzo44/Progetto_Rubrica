package com.example.addressbook;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddContactControllerTest {

    private AddressBookModel model;
    private AddContactView view;
    private AddContactController controller;

    @BeforeAll
    void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await(); // Attende che il toolkit sia pronto
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        model = new AddressBookModel();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view = new AddContactView();
            controller = new AddContactController(model, view);
            latch.countDown();
        });
        latch.await();
        CountDownLatch inputLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getFirstNameField().setText("John");
            view.getLastNameField().setText("Doe");
            view.getNoteField().setText("Test note");
            view.getPhoneFields().get(0).setText("1234567890");
            view.getEmailFields().get(0).setText("john.doe@example.com");
            inputLatch.countDown();
        });
        inputLatch.await();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testHandleSaveContact() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.handleSaveContact();
            latch.countDown();
        });
        latch.await();

        assertEquals(1, model.getContacts().size());
        Contact savedContact = model.getContacts().get(0);
        assertEquals("John", savedContact.getFirstName());
        assertEquals("Doe", savedContact.getLastName());
        assertEquals("Test note", savedContact.getNote());
        assertTrue(savedContact.getPhoneNumbers().contains("1234567890"));
        assertTrue(savedContact.getEmails().contains("john.doe@example.com"));
    }

    @Test
    void testHandleSaveContactEmptyName() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getFirstNameField().clear();
            view.getLastNameField().clear();
            controller.handleSaveContact();
            latch.countDown();
        });
        latch.await();

        assertEquals(0, model.getContacts().size());
    }

    @Test
    void testHandleSaveContactTooManyPhoneNumbers() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getPhoneFields().get(0).setText("1234567890");
            view.getPhoneFields().get(1).setText("2345678901");
            view.getPhoneFields().get(2).setText("3456789012");
            view.getPhoneFields().get(3).setText("4567890123"); // Troppo
            controller.handleSaveContact();
            latch.countDown();
        });
        latch.await();

        assertEquals(0, model.getContacts().size());
    }

    @Test
    void testHandleSaveContactTooManyEmails() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getEmailFields().get(0).setText("email1@example.com");
            view.getEmailFields().get(1).setText("email2@example.com");
            view.getEmailFields().get(2).setText("email3@example.com");
            view.getEmailFields().get(3).setText("email4@example.com"); // Troppo
            controller.handleSaveContact();
            latch.countDown();
        });
        latch.await();

        assertEquals(0, model.getContacts().size());
    }

    @Test
    void testHandleSaveContactNoteTooLong() throws InterruptedException {
        String longNote = "a".repeat(101);
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getNoteField().setText(longNote);
            controller.handleSaveContact();
            latch.countDown();
        });
        latch.await();

        assertEquals(0, model.getContacts().size());
    }

    @Test
    void testClearFields() throws InterruptedException {
        // Salva il contatto
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.handleSaveContact();
            view.clearFields();
            latch.countDown();
        });
        latch.await();

        // Verifica che i campi siano vuoti
        assertEquals("", view.getFirstNameField().getText());
        assertEquals("", view.getLastNameField().getText());
        assertEquals("", view.getNoteField().getText());
        assertEquals("", view.getPhoneFields().get(0).getText());
        assertEquals("", view.getEmailFields().get(0).getText());
    }

    @Test
    void testHandleBack() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getBackButton().fire();
            latch.countDown();
        });
        latch.await();
    }
}
