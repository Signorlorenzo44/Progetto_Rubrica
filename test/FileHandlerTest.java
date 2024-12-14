package com.example.addressbook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private FileHandler fileHandler;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileHandler = new FileHandler();
        tempFile = File.createTempFile("test_contacts", ".csv");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void exportToFile() throws IOException {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Marco", "Rossi"));
        contacts.add(new Contact("Alice", "Bianchi"));
        fileHandler.exportToFile(tempFile.getAbsolutePath(), contacts);
        assertTrue(tempFile.exists(), "Il file dovrebbe essere stato creato.");
        assertTrue(tempFile.length() > 0, "Il file non dovrebbe essere vuoto.");
    }

    @Test
    void importFromFile() throws IOException {
        String fileContent = """
        Marco,Rossi,123456789,marco@example.com
        Alice,Bianchi,987654321,alice@example.com
        """;
        java.nio.file.Files.writeString(tempFile.toPath(), fileContent);
        List<Contact> importedContacts = fileHandler.importFromFile(tempFile.getAbsolutePath());
        System.out.println("File content:\n" + fileContent);
        System.out.println("Imported contacts: " + importedContacts);
        assertNotNull(importedContacts, "La lista dei contatti non dovrebbe essere null.");
        assertEquals(2, importedContacts.size(), "Dovrebbero essere importati due contatti.");
    }
    }