package com.example.addressbook;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *Classe principale che avvia l'applicazione e gestisce la finestra principale.
 */
public class MainApp extends Application {

    /**
      La finestra principale dell'applicazione.
     */
    private Stage primaryStage;

    /**
      La scena principale che contiene l'interfaccia utente.
     */
    private Scene mainScene;

    /**
      Il punto di ingresso per l'applicazione JavaFX.
      Imposta il titolo della finestra e la scena da visualizzare.

      @param primaryStage La finestra principale dell'applicazione.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Imposta il titolo della finestra principale
        primaryStage.setTitle("Address Book");

        // Imposta la scena principale nell'applicazione
        primaryStage.setScene(mainScene);

        // Mostra la finestra principale
        primaryStage.show();
    }
}

