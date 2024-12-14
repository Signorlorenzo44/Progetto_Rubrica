package com.example.addressbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 Classe di utilità per gestire operazioni di importazione ed esportazione dei contatti.

 Questa classe fornisce metodi per salvare i contatti su un file e per caricare contatti
 da un file, facilitando la persistenza dei dati della rubrica.
 */


public class FileHandler {
    public void exportToFile(String fileName, List<Contact> contacts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Contact contact : contacts) {
                String phones = String.join(";", contact.getPhoneNumbers());
                String emails = String.join(";", contact.getEmails());
                writer.write(contact.getFullName() + "," + phones + "," + emails);
                writer.newLine();
            }
        }
    }
    /**
     Importa una lista di contatti da un file specificato.

     @param fileName Il nome del file da cui importare i contatti.
     @return Una lista di contatti caricata dal file.
     */

    public List<Contact> importFromFile(String fileName) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Contact contact = new Contact(line, line);
                    String[] phones = parts[2].split(";");
                    for (String phone : phones) {
                        contact.addPhoneNumber(phone);
                    }
                    if (parts.length > 3) {
                        String[] emails = parts[3].split(";");
                        for (String email : emails) {
                            contact.addEmail(email);
                        }
                    }
                    contacts.add(contact);
                }
            }
        }
        return contacts;
    }

}