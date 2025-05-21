package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.UQuizzes;

import java.util.ArrayList;
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
    private Label mensajeError;

    private UQuizzes uQuizzes = UQuizzes.getInstance();
    private List<OpcionMultipleRespuesta> opciones = new ArrayList<>();
    private int idPregunta;


    @FXML
    private void initialize() {

    }

    public Boolean obtenerRespuestaSeleccionada() {
        Toggle selectedToggle = respuestaToggleGroup.getSelectedToggle();
        if (selectedToggle == null) return null;

        return selectedToggle == verdaderoRadioButton; // true = "Verdadero", false = "Falso"
    }

    public void setOpciones(List<OpcionMultipleRespuesta> opciones , int idPregunta) {
        this.opciones = opciones;


            System.out.println("se va a setear el id de la pregunta a " + idPregunta);
            this.idPregunta = idPregunta; // suponemos que ambas opciones comparten el mismo id
    }

    public void setEnunciado(String enunciado) {
        lblEnunciado.setText(enunciado);

    }

    public void registrarRespuesta(ActionEvent actionEvent) {
        Boolean seleccion = obtenerRespuestaSeleccionada();

        if (seleccion == null) {
            mensajeError.setText("Debe seleccionar Verdadero o Falso.");
            mensajeError.setTextFill(Paint.valueOf("red"));
            mensajeError.setVisible(true);
            return;
        }

        // Convertimos la selección en texto compatible con la base de datos
        String respuesta = seleccion ? "Verdadero" : "Falso";

        System.out.println("Respuesta seleccionada: " + respuesta);

        if (uQuizzes.validarRespuestaVerdaderoFalso(idPregunta, respuesta)) {
            mensajeError.setText("Respuesta correcta");
            mensajeError.setTextFill(Paint.valueOf("green"));
        } else {
            mensajeError.setText("Respuesta incorrecta");
            mensajeError.setTextFill(Paint.valueOf("red"));
        }

        mensajeError.setVisible(true);
    }
}
