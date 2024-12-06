package com.example.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
  Classe che gestisce la connessione e le operazioni sul database.
 */
public class Database {
    private Connection connection;

    /**
      Costruttore che inizializza la connessione al database.
      @throws SQLException se la connessione al database fallisce.
     */
    public Database() throws SQLException {
        // Codice per inizializzare la connessione
    }

    /**
     Inserisce un nuovo contatto nel database.
      @param contact Il contatto da inserire.
      @throws SQLException se si verifica un errore durante l'inserimento.
     */
    public void insertContact(Contact contact) throws SQLException {
        // Codice per inserire un contatto
    }

    /**
      Recupera tutti i contatti dal database.
      @return Una lista di contatti recuperati.
      @throws SQLException se si verifica un errore durante il recupero.
     */
    public List<Contact> getAllContacts() throws SQLException {
        // Codice per recuperare tutti i contatti
        return null;
    }

    /**
      Aggiorna un contatto esistente nel database.
      @param contact Il contatto da aggiornare.
      @throws SQLException se si verifica un errore durante l'aggiornamento.
     */
    public void updateContact(Contact contact) throws SQLException {
        // Codice per aggiornare un contatto
    }

    /**
      Cancella un contatto dal database.
     @param contactId L'ID del contatto da cancellare.
      @throws SQLException se si verifica un errore durante la cancellazione.
     */
    public void deleteContact(int contactId) throws SQLException {
        // Codice per cancellare un contatto
    }

    /**
     Chiude la connessione al database.
      @throws SQLException se si verifica un errore durante la chiusura della connessione.
     */
    public void close() throws SQLException {
    }
}
