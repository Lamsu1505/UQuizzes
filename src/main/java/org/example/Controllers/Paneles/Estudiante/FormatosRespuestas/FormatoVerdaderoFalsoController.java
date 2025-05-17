package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class FormatoVerdaderoFalsoController {
    @FXML
    private RadioButton verdaderoRadioButton;

    @FXML
    private RadioButton falsoRadioButton;

    @FXML
    private ToggleGroup respuestaToggleGroup;

    @FXML
    private void initialize() {

    }

    public Boolean obtenerRespuestaSeleccionada() {
        Toggle selectedToggle = respuestaToggleGroup.getSelectedToggle();
        if (selectedToggle == null) return null;

        return selectedToggle == verdaderoRadioButton; // true si se seleccionó "Verdadero", false si "Falso"
    }
}
