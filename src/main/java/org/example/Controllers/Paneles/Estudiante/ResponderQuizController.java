package org.example.Controllers.Paneles.Estudiante;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.ConexionDB.ConexionOracle;
import org.example.ConexionDB.DAO.PreguntaDAO;
import org.example.Controllers.Paneles.Estudiante.FormatosRespuestas.*;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.PruebaPreguntas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResponderQuizController {

    @FXML
    private VBox contenedorPreguntas;

    @FXML
    private int idExamen;

    @FXML
    private Button enviarButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label lblIdExamen;

    @FXML
    public void initialize() {

    }


    public void cargarPreguntasDelExamen(int idExamen , int cantidadPreguntasBanco , int cantidadPreguntasGeneral) {
        PreguntaDAO dao = new PreguntaDAO();
        Map<PruebaPreguntas, List<OpcionMultipleRespuesta>> preguntasMap = dao.obtenerPreguntasPorExamen(idExamen);

        // Obtener la lista de todas las preguntas disponibles
        List<Map.Entry<PruebaPreguntas, List<OpcionMultipleRespuesta>>> listaPreguntas = new ArrayList<>(preguntasMap.entrySet());

        // Mezclar aleatoriamente la lista
        Collections.shuffle(listaPreguntas);

        // Tomar solo las primeras 'cantidadPreguntasGeneral'
        int totalAElegir = Math.min(cantidadPreguntasGeneral, listaPreguntas.size());
        List<Map.Entry<PruebaPreguntas, List<OpcionMultipleRespuesta>>> seleccionadas = listaPreguntas.subList(0, totalAElegir);

        // Agregar las preguntas seleccionadas
        for (Map.Entry<PruebaPreguntas, List<OpcionMultipleRespuesta>> entry : seleccionadas) {
            agregarPregunta(entry.getKey(), entry.getValue());
        }
    }

    public void agregarPregunta(PruebaPreguntas pregunta, List<OpcionMultipleRespuesta> opciones) {
        try {
            String fxmlRuta = "";
            FXMLLoader loader;

            switch (pregunta.getIdTipoPregunta()) {
                case 1:
                    System.out.println("Pregunta de tipo unica respuesta: " + pregunta.getEnunciado());
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoUnicaRespuesta.fxml";
                    break;
                case 2:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoVerdaderoFalso.fxml";
                    break;
                case 3:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoRespuestaCorta.fxml";
                    break;
                case 4:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoEmparejar.fxml";
                    break;
                case 5:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoMultipleRespuesta.fxml";
                    break;
                default:
                    System.err.println("Tipo de pregunta desconocido: " + pregunta.getIdTipoPregunta());
                    return;
            }

            loader = new FXMLLoader(getClass().getResource(fxmlRuta));
            Node preguntaPane = loader.load();

            if (pregunta.getIdTipoPregunta() == 1) {

                FormatoUnicaRespuestaController controller = loader.getController();
                controller.setOpciones(opciones , pregunta.getIdPregunta() );
                controller.setEnunciado(pregunta.getEnunciado());

            }

            else if (pregunta.getIdTipoPregunta() == 2) {
                FormatoVerdaderoFalsoController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setOpciones(opciones , pregunta.getIdPregunta());
            }

            else if (pregunta.getIdTipoPregunta() == 3) {
                FormatoRespuestaCortaController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setIdPregunta(pregunta.getIdPregunta());
            }

            else if (pregunta.getIdTipoPregunta() == 4) {
                FormatoEmparejamientoController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setOpciones(opciones);
            }

            else if (pregunta.getIdTipoPregunta() == 5) {
                FormatoSeleccionMultipleController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setOpciones(opciones);

            }

            contenedorPreguntas.getChildren().add(preguntaPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
        lblIdExamen.setText("Examen " + idExamen);

        cargarPreguntas();
    }

    public void cargarPreguntas() {

        int cantidadPreguntasBanco = -1;
        int cantidadPreguntasGeneral = -1;
        ConexionOracle conexion = new ConexionOracle();

        String sql = "select cantidadPreguntasBanco , cantidadPreguntas " +
                "from examen where idExamen = '" + idExamen + "'";

        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cantidadPreguntasBanco = rs.getInt("cantidadPreguntasBanco");
                    cantidadPreguntasGeneral = rs.getInt("cantidadPreguntas");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cargarPreguntasDelExamen(idExamen , cantidadPreguntasBanco ,cantidadPreguntasGeneral );
    }
}
