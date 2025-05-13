package org.example.Controllers.Ventanas.Estudiante;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ResponderQuizController {

    @FXML
    private VBox contenedorPreguntas;
    public void cargarPreguntaSeleccionada() {
        try {
            String fxmlRuta = "/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelUnicaRespuesta.fxml";
            System.out.println("Cargando FXML: " + fxmlRuta);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
            Node preguntaPane = loader.load();

            contenedorPreguntas.getChildren().add(preguntaPane);
            System.out.println("Pregunta cargada y añadida");

        } catch (Exception e) {
            System.err.println("Error al cargar pregunta: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private Button enviarButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        // Aquí llamas a tu método para cargar la interfaz de la pregunta
        for (int i = 0; i < 3; i++) {
            cargarPreguntaSeleccionada();
        }
    }
}
