package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;

import java.util.List;

public class FormatoVerdaderoFalsoController {
    @FXML
    private RadioButton verdaderoRadioButton;

    @FXML
    private RadioButton falsoRadioButton;

    @FXML
    private ToggleGroup respuestaToggleGroup;

    @FXML
    private Label lblEnunciado;

    @FXML
    private void initialize() {

    }

    public Boolean obtenerRespuestaSeleccionada() {
        Toggle selectedToggle = respuestaToggleGroup.getSelectedToggle();
        if (selectedToggle == null) return null;

        return selectedToggle == verdaderoRadioButton; // true si se seleccionó "Verdadero", false si "Falso"
    }


    public void setOpciones(List<OpcionMultipleRespuesta> opciones) {

    }

    public void setEnunciado(String enunciado) {
        lblEnunciado.setText(enunciado);
    }
}
