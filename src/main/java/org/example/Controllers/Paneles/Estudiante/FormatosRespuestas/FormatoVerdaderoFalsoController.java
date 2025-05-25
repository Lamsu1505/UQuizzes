package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    private VBox verdaderoFalsoPanel;

    @FXML
    private Label lblEnunciado;

    @FXML
    private Label mensajeError;

    @FXML private Button btnValidarRespuesta;

    private int idPreguntaDetalle;

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

        boolean respuestaIsCOrrecta = uQuizzes.validarRespuestaVerdaderoFalso(idPregunta , respuesta);

        System.out.println("Respuesta es correcta?: " + respuestaIsCOrrecta);

        if(respuestaIsCOrrecta){
            //guardar respuesta
            if(uQuizzes.guardarRespuesta(idPreguntaDetalle , respuesta , true)){
                System.out.println("Respuesta guardada correctamente");

                mensajeError.setText("Respuesta correcta");
                mensajeError.setTextFill(Paint.valueOf("green"));
                mensajeError.setVisible(true);
                disableAndColorContainer(true);
            }
            else {
                mensajeError.setText("Error al guardar la respuesta en la base de datos");
                mensajeError.setVisible(true);
            }
        }
        else {

            if(uQuizzes.guardarRespuesta(idPreguntaDetalle , respuesta , false)){
                System.out.println("Respuesta guardada correctamente");

                mensajeError.setText("Respuesta incorrecta");
                mensajeError.setTextFill(Paint.valueOf("red"));
                mensajeError.setVisible(true);
                disableAndColorContainer(false);
            }else {
                mensajeError.setText("Error al guardar la respuesta en la base de datos");
            }
        }
    }


    private void disableAndColorContainer(boolean correcto) {
        // 1) Deshabilitar controles
        btnValidarRespuesta.setDisable(true);
        verdaderoRadioButton.setDisable(true);
        falsoRadioButton.setDisable(true);

        // 2) Escoger color de fondo
        String color = correcto ? "#C8E6C9" : "#FFCDD2";

        // 3) Pintar panel completo
        verdaderoFalsoPanel.setStyle("-fx-background-color: " + color + ";");

        // 4) (Opcional) Quitar foco de los radios para evitar scroll o saltos
        verdaderoRadioButton.setFocusTraversable(false);
        falsoRadioButton.setFocusTraversable(false);

        // 5) Si hubiera algún relayout, en un runLater podrías restaurar scroll,
        //    pero como no hay ScrollPane aquí, no hace falta.
    }

    public void setIdPreguntaDetalle(int idPreguntaDetalle) {
        this.idPreguntaDetalle = idPreguntaDetalle;

    }
}
