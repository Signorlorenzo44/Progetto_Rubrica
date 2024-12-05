package com.example.addressbook;

import java.util.List;

/**
  Classe di utilità per gestire operazioni di importazione ed esportazione dei contatti.

 Questa classe fornisce metodi per salvare i contatti su un file e per caricare contatti
  da un file, facilitando la persistenza dei dati della rubrica.
 */
public class FileHandler {

    /**
      Esporta una lista di contatti in un file specificato.

     @param fileName Il nome del file dove esportare i contatti.
      @param contacts La lista di contatti da salvare.
     */
    public void exportToFile(String fileName, List<Contact> contacts) {

    }

    /**
      Importa una lista di contatti da un file specificato.

     @param fileName Il nome del file da cui importare i contatti.
     @return Una lista di contatti caricata dal file.
     */
    public List<Contact> importFromFile(String fileName) {
        return null;
    }
}
