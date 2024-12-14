package com.example.addressbook;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe per la vista di filtro con un'interfaccia più estetica e opzioni aggiuntive.
 */
public class FilterView {

    private Stage stage;
    private ComboBox<String> filterComboBox;
    private TextField emailField;
    private TextField phoneField;
    private Button applyButton;

    /**
     * Costruttore che inizializza la finestra di filtro.
     */
    public FilterView() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Filtra Contatti");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #dcdcdc; -fx-border-radius: 8; -fx-background-radius: 8;");
        Label titleLabel = new Label("Filtra i Contatti");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #333;");
        filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Tutti", "Con Email", "Con Numero di Telefono", "Senza Email", "Senza Numero di Telefono");
        filterComboBox.setValue("Tutti");
        emailField = new TextField();
        emailField.setPromptText("Cerca per Email...");
        emailField.setStyle("-fx-padding: 8; -fx-background-radius: 5;");
        phoneField = new TextField();
        phoneField.setPromptText("Cerca per Numero di Telefono...");
        phoneField.setStyle("-fx-padding: 8; -fx-background-radius: 5;");
        applyButton = new Button("Applica");
        applyButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14; " +
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        applyButton.setPrefWidth(100);
        HBox buttonLayout = new HBox(applyButton);
        buttonLayout.setStyle("-fx-alignment: center;");
        layout.getChildren().addAll(titleLabel, filterComboBox, emailField, phoneField, buttonLayout);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
    }

    /**
     * Mostra la finestra di filtro.
     */
    public void show() {
        stage.showAndWait();
    }

    /**
     * Restituisce il valore selezionato nel menu a tendina.
     *
     * @return Il filtro selezionato.
     */
    public String getSelectedFilter() {
        return filterComboBox.getValue();
    }

    /**
     * Restituisce il valore del campo Email.
     *
     * @return Il filtro Email.
     */
    public String getEmailFilter() {
        return emailField.getText().trim();
    }

    /**
     * Restituisce il valore del campo Numero di Telefono.
     *
     * @return Il filtro Numero di Telefono.
     */
    public String getPhoneFilter() {
        return phoneField.getText().trim();
    }

    public Button getApplyButton() {
        return applyButton;
    }

    public Stage getStage() {
        return stage;
    }
}
