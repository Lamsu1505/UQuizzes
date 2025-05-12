package org.example.Controllers.Paneles.Docente.TiposPregunta;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class VerdaderoFalsoController {
    @FXML
    private RadioButton verdaderoRadioButton;

    @FXML
    private RadioButton falsoRadioButton;

    @FXML
    private ToggleGroup respuestaToggleGroup;

    /**
     * Método de inicialización que se llama automáticamente después de cargar el FXML
     */
    @FXML
    private void initialize() {
        // Configuraciones iniciales si son necesarias
        // Por defecto, ninguna opción está seleccionada
    }

    /**
     * Establecer la respuesta correcta
     * @param esVerdadero true si la respuesta correcta es Verdadero, false si es Falso
     */
    public void setRespuestaCorrecta(boolean esVerdadero) {
        if (esVerdadero) {
            verdaderoRadioButton.setSelected(true);
        } else {
            falsoRadioButton.setSelected(true);
        }
    }

    /**
     * Obtener la respuesta seleccionada
     * @return true si está seleccionado Verdadero, false si está seleccionado Falso
     * @throws IllegalStateException si no se ha seleccionado ninguna opción
     */
    public boolean getRespuestaSeleccionada() {
        Toggle selectedToggle = respuestaToggleGroup.getSelectedToggle();

        if (selectedToggle == null) {
            throw new IllegalStateException("No se ha seleccionado ninguna respuesta");
        }

        return selectedToggle == verdaderoRadioButton;
    }

    /**
     * Verificar si se ha seleccionado una respuesta
     * @return true si se ha seleccionado una opción, false en caso contrario
     */
    public boolean hayRespuestaSeleccionada() {
        return respuestaToggleGroup.getSelectedToggle() != null;
    }

    /**
     * Validar la selección de respuesta
     * @return true si se ha seleccionado una opción, false en caso contrario
     */
    public boolean validarRespuesta() {
        return hayRespuestaSeleccionada();
    }

    /**
     * Limpiar la selección
     */
    public void limpiarSeleccion() {
        respuestaToggleGroup.selectToggle(null);
    }

    /**
     * Verificar si la respuesta dada es correcta
     * @param respuesta true o false a verificar
     * @return true si la respuesta coincide con la selección actual, false en caso contrario
     * @throws IllegalStateException si no se ha seleccionado ninguna opción
     */
    public boolean verificarRespuesta(boolean respuesta) {
        return getRespuestaSeleccionada() == respuesta;
    }

    /**
     * Habilitar o deshabilitar la selección de respuestas
     * @param habilitado true para habilitar, false para deshabilitar
     */
    public void setRespuestasHabilitadas(boolean habilitado) {
        verdaderoRadioButton.setDisable(!habilitado);
        falsoRadioButton.setDisable(!habilitado);
    }
}