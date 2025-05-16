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


    public void verificarTipoPregunta (int tipoPregunta){
        // Tipo 1: multiple respuesta
        if(tipoPregunta == 1){
            try {
                String fxmlRuta = "/Interfaces/Paneles.Estudiante/FormatoRespuestasQuiz/FormatoMultipleRespuesta.fxml";
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
                String fxmlRuta = "/Interfaces/Paneles.Estudiante/FormatoRespuestasQuiz/FormatoUnicaRespuesta.fxml";
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
                String fxmlRuta = "/Interfaces/Paneles.Estudiante/FormatoRespuestasQuiz/FormatoVerdaderoFalso.fxml";
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
                String fxmlRuta = "/Interfaces/Paneles.Estudiante/FormatoRespuestasQuiz/FormatoRespuestaCorta.fxml";
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


    @FXML
    private Button enviarButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        // Aquí llamas a tu método para cargar la interfaz de la pregunta
       verificarTipoPregunta(1);
       verificarTipoPregunta(2);
       verificarTipoPregunta(3);
       verificarTipoPregunta(4);
    }
}
