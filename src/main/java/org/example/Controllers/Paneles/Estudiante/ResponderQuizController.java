package org.example.Controllers.Paneles.Estudiante;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.Controllers.Paneles.Estudiante.FormatosRespuestas.FormatoSeleccionMultipleController;
import org.example.Controllers.Paneles.Estudiante.FormatosRespuestas.FormatoUnicaRespuestaController;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.PruebaPreguntas;

import java.io.IOException;
import java.util.List;

public class ResponderQuizController {

    @FXML
    private VBox contenedorPreguntas;


    public void verificarTipoPregunta (int tipoPregunta){
        // Tipo 1: multiple respuesta
        if(tipoPregunta == 1){
            try {
                String fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoMultipleRespuesta.fxml";
                System.out.println("Cargando FXML: " + fxmlRuta);

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
                Node preguntaPane = loader.load();

                contenedorPreguntas.getChildren().add(preguntaPane);
                System.out.println("Pregunta cargada y añadida");

            } catch (Exception e) {
                System.err.println("Error al cargar pregunta: " + e.getMessage());
                e.printStackTrace();
            }

            // Tipo 2: unica respuesta
        } else if (tipoPregunta ==2) {
            try {
                String fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoUnicaRespuesta.fxml";
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


        //Tipo 3: verdader/falso
        else if (tipoPregunta == 3 ) {
            try {
                String fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoVerdaderoFalso.fxml";
                System.out.println("Cargando FXML: " + fxmlRuta);

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
                Node preguntaPane = loader.load();

                contenedorPreguntas.getChildren().add(preguntaPane);
                System.out.println("Pregunta cargada y añadida");

            } catch (Exception e) {
                System.err.println("Error al cargar pregunta: " + e.getMessage());
                e.printStackTrace();
            }



            //Tipo 4: respuesta corta
        } else if (tipoPregunta ==4) {
            try {
                String fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoRespuestaCorta.fxml";
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


        //Tipo 5: emparejamiento
        else if (tipoPregunta == 5 ) {
            try {
                String fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoEmparejar.fxml";
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
    }


    public void agregarPregunta(PruebaPreguntas pregunta, List<OpcionMultipleRespuesta> opciones) {
        try {
            String fxmlRuta = "";
            FXMLLoader loader;

            switch (pregunta.getIdTipoPregunta()) {
                case 1:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoMultipleRespuesta.fxml";
                    break;
                case 2:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoUnicaRespuesta.fxml";
                    break;
                case 3:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoVerdaderoFalso.fxml";
                    break;
                case 4:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoRespuestaCorta.fxml";
                    break;
                case 5:
                    fxmlRuta = "/Interfaces/Paneles/Estudiante/s/FormatoRespuestasQuiz/FormatoEmparejar.fxml";
                    break;
                default:
                    System.err.println("Tipo de pregunta desconocido: " + pregunta.getIdTipoPregunta());
                    return;
            }

            loader = new FXMLLoader(getClass().getResource(fxmlRuta));
            Node preguntaPane = loader.load();

            if (pregunta.getIdTipoPregunta() == 1) {
                FormatoSeleccionMultipleController controller = loader.getController();
                controller.setOpciones(opciones);

            }

            else if (pregunta.getIdTipoPregunta() == 2) {
                FormatoUnicaRespuestaController controller = loader.getController();
                controller.setOpciones(opciones);
                controller.setEnunciado(pregunta.getEnunciado());
            }

            contenedorPreguntas.getChildren().add(preguntaPane);
        } catch (IOException e) {
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

    }
}
