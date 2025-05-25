package org.example.Controllers.Paneles.Estudiante;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.ConexionDB.ConexionOracle;
import org.example.ConexionDB.DAO.PreguntaDAO;
import org.example.Controllers.Paneles.Estudiante.FormatosRespuestas.*;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.PruebaPreguntas;
import org.example.Model.UQuizzes;

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


    private UQuizzes uQuizzes = UQuizzes.getInstance();

    @FXML
    public void initialize() {

    }


    public void cargarPreguntasDelExamen(int idExamen , int cantidadPreguntasBanco , int cantidadPreguntasGeneral) {

        Map<PruebaPreguntas, List<OpcionMultipleRespuesta>> preguntasMap = uQuizzes.obtenerPreguntasPorExamen(idExamen);
        System.out.println(preguntasMap);

        // Obtener la lista de todas las preguntas disponibles
        List<Map.Entry<PruebaPreguntas, List<OpcionMultipleRespuesta>>> listaPreguntas = new ArrayList<>(preguntasMap.entrySet());

        // Mezclar aleatoriamente la lista
        Collections.shuffle(listaPreguntas);

        // Tomar solo las primeras 'cantidadPreguntasGeneral'
        int totalAElegir = Math.min(cantidadPreguntasGeneral, listaPreguntas.size());
        List<Map.Entry<PruebaPreguntas, List<OpcionMultipleRespuesta>>> seleccionadas = listaPreguntas.subList(0, totalAElegir);

        // Agregar las preguntas seleccionadas
        for (Map.Entry<PruebaPreguntas, List<OpcionMultipleRespuesta>> entry : seleccionadas) {

            //añadir pregunta a detallePregunta con idSolucion
            int idPregunta = entry.getKey().getIdPregunta();
            int idUsuario = Integer.parseInt(uQuizzes.getUsuarioEnSesion());

            int idPreguntaDetalle = 2 + uQuizzes.registrarPreguntaDetalleEnSolucion(idPregunta, idUsuario , idExamen );
            System.out.println("ID de pregunta detalle registrado: " + idPreguntaDetalle);

            if(idPreguntaDetalle == -1){
                System.out.println("Error al registrar la pregunta en detallePregunta");
                return;
            }
            else {
                System.out.println("Pregunta registrada en detallePregunta con ID: " + idPreguntaDetalle);
                agregarPregunta(entry.getKey(), entry.getValue() , idPreguntaDetalle);
            }


        }
    }

    public void agregarPregunta(PruebaPreguntas pregunta, List<OpcionMultipleRespuesta> opciones , int idPreguntaDetalle) {
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
                controller.setIdPreguntaDetalle(idPreguntaDetalle);

            }

            else if (pregunta.getIdTipoPregunta() == 2) {
                FormatoVerdaderoFalsoController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setOpciones(opciones , pregunta.getIdPregunta());
                controller.setIdPreguntaDetalle(idPreguntaDetalle);

            }

            else if (pregunta.getIdTipoPregunta() == 3) {
                FormatoRespuestaCortaController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setIdPregunta(pregunta.getIdPregunta());
                controller.setIdPreguntaDetalle(idPreguntaDetalle);
            }

            else if (pregunta.getIdTipoPregunta() == 4) {
                FormatoEmparejamientoController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setOpciones(opciones , pregunta.getIdPregunta());
                controller.setIdPreguntaDetalle(idPreguntaDetalle);
            }

            else if (pregunta.getIdTipoPregunta() == 5) {
                FormatoSeleccionMultipleController controller = loader.getController();
                controller.setEnunciado(pregunta.getEnunciado());
                controller.setOpciones(opciones , pregunta.getIdPregunta());
                controller.setIdPreguntaDetalle(idPreguntaDetalle);

            }

            contenedorPreguntas.getChildren().add(preguntaPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
        lblIdExamen.setText("Examen " + idExamen);

        empezarExamenBaseDatos();

    }

    private void empezarExamenBaseDatos() {

        int idUsuario = Integer.parseInt( uQuizzes.getUsuarioEnSesion());
        System.out.println("ID de usuario: " + idUsuario);
        System.out.println("ID de examen: " + idExamen);
        try{
            boolean empezado = uQuizzes.empezarExamenBaseDatos(idUsuario, idExamen);

            if (empezado) {
                cargarPreguntas();
            } else {
                System.out.println("Error al iniciar el examen en la base de datos.");
            }


        }catch (Exception e) {

            mostrarAlerta("Error al crear el examen", e.getMessage());
            return;  //
        }


    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
