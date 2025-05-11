package org.example.Controllers.Paneles.Docente.TiposPregunta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OpcionMultipleController implements Initializable {

    @FXML
    private VBox opcionesContainer;

    @FXML
    private Button agregarOpcionButton;

    @FXML
    private Label mensajeError;

    private ToggleGroup toggleGroup = new ToggleGroup(); // para controlar selección única
    private List<HBox> listaOpciones = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mensajeError.setText("");
        mensajeError.setVisible(false);

        opcionesContainer.setStyle("-fx-background-color: #f8f9fa;");


        agregarOpcionConTexto("");
        agregarOpcionConTexto("");

    }

    @FXML
    void agregarOpcion(ActionEvent event) {
        agregarOpcionConTexto("");
    }

    private void agregarOpcionConTexto(String texto) {
        HBox opcionHBox = new HBox(10);
        opcionHBox.setAlignment(Pos.CENTER_LEFT);
        opcionHBox.setPadding(new Insets(5, 10, 5, 10));
        opcionHBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 5;");

        // Usar RadioButton con el ToggleGroup para selección única
        RadioButton radioButton = new RadioButton();
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setStyle("-fx-padding: 0 5 0 0;");

        // Botón eliminar
        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498db; -fx-font-size: 14px; -fx-cursor: hand;");
        eliminarButton.setPadding(new Insets(0, 2, 0, 2));
        eliminarButton.setOnAction(e -> eliminarOpcion(opcionHBox));

        // Campo de texto
        TextField opcionTextField = new TextField(texto);
        opcionTextField.setPromptText("Ingrese el texto de la opción");
        opcionTextField.getStyleClass().add("text-field");
        opcionTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        HBox.setHgrow(opcionTextField, Priority.ALWAYS);

        // Armar HBox
        opcionHBox.getChildren().addAll(radioButton, eliminarButton, opcionTextField);
        listaOpciones.add(opcionHBox);
        opcionesContainer.getChildren().add(opcionHBox);

        // Efecto hover
        opcionHBox.setOnMouseEntered(e -> opcionHBox.setStyle("-fx-background-color: #e9ecef; -fx-border-radius: 5;"));
        opcionHBox.setOnMouseExited(e -> opcionHBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 5;"));
    }

    private void eliminarOpcion(HBox opcionHBox) {
        if (listaOpciones.size() <= 2) {
            mostrarMensajeError("No se pueden dejar menos de dos opciones.");
            return;
        }

        // Si se elimina la seleccionada, deselecciona del grupo
        RadioButton rb = (RadioButton) opcionHBox.getChildren().get(0);
        if (rb.isSelected()) {
            toggleGroup.selectToggle(null);
        }

        opcionesContainer.getChildren().remove(opcionHBox);
        listaOpciones.remove(opcionHBox);
    }

    private void mostrarMensajeError(String mensaje) {
        mensajeError.setText(mensaje);
        mensajeError.setVisible(true);
    }

    public boolean validarDatos() {
        mensajeError.setVisible(false);

        for (HBox opcionHBox : listaOpciones) {
            TextField opcionTextField = (TextField) opcionHBox.getChildren().get(2);
            if (opcionTextField.getText().trim().isEmpty()) {
                mostrarMensajeError("Todas las opciones deben tener texto");
                return false;
            }
        }

        if (toggleGroup.getSelectedToggle() == null) {
            mostrarMensajeError("Debe seleccionar una opción como correcta");
            return false;
        }

        return true;
    }

    public List<String> getOpciones() {
        List<String> opciones = new ArrayList<>();
        for (HBox opcionHBox : listaOpciones) {
            TextField opcionTextField = (TextField) opcionHBox.getChildren().get(2);
            opciones.add(opcionTextField.getText().trim());
        }
        return opciones;
    }

    public int getOpcionCorrecta() {
        for (int i = 0; i < listaOpciones.size(); i++) {
            RadioButton rb = (RadioButton) listaOpciones.get(i).getChildren().get(0);
            if (rb.isSelected()) {
                return i;
            }
        }
        return -1; // si no hay selección
    }
}
