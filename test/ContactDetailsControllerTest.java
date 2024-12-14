package com.example.addressbook;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactDetailsControllerTest {

    private AddressBookModel model;
    private ContactDetailsView view;
    private ContactDetailsController controller;
    private Contact contact;

    @BeforeAll
    void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        contact = new Contact("John", "Doe");
        contact.addPhoneNumber("1234567890");
        contact.addEmail("john.doe@example.com");
        contact.setNote("Test note");
        model = new AddressBookModel();
        model.addContact(contact);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view = new ContactDetailsView();
            view.updateContactDetails(contact);
            controller = new ContactDetailsController(model, view);
            controller.initialize();
            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(view.getRoot(), 400, 400));
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
    void testEditAndSaveContact() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getEditButton().fire();
            assertTrue(view.getNameField().isEditable(), "I campi dovrebbero essere editabili dopo aver premuto Edit");
            assertTrue(view.getSaveButton().isVisible(), "Il bottone Save dovrebbe essere visibile dopo Edit");
            view.getNameField().setText("Jane Doe");
            view.getPhoneField().setText("9876543210");
            view.getEmailField().setText("jane.doe@example.com");
            view.getSaveButton().fire();
            Contact updated = model.getContacts().stream()
                    .filter(c -> c.getFullName().equals("Jane Doe"))
                    .findFirst().orElse(null);

            assertNotNull(updated, "Il contatto aggiornato non è stato trovato nel modello.");
            assertTrue(updated.getPhoneNumbers().contains("9876543210"));
            assertTrue(updated.getEmails().contains("jane.doe@example.com"));
            assertFalse(view.getNameField().isEditable(), "I campi non dovrebbero essere più editabili dopo il salvataggio");
            assertFalse(view.getSaveButton().isVisible(), "Il bottone Save dovrebbe essere nascosto dopo il salvataggio");
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testBackButton() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            view.getBackButton().fire();
            Stage stage = (Stage) view.getRoot().getScene().getWindow();
            assertFalse(stage.isShowing(), "La finestra dovrebbe essere chiusa dopo aver premuto Back");
            latch.countDown();
        });
        latch.await();
    }
}
