package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.UQuizzes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormatoEmparejamientoController {

    @FXML
    private VBox parejasContainer;

    @FXML
    private VBox emparejamientoPanel;

    @FXML
    private Label mensajeError;

    @FXML
    private Label lblEnunciado;

    @FXML
    private Button btnValidarRespuesta;

    @FXML
    private ScrollPane scrollPaneContenedor;


    private UQuizzes uQuizzes = UQuizzes.getInstance();

    private int idPregunta;


    // Lista para almacenar los pares de elementos
    private List<ParElementos> listaPares = new ArrayList<>();

    @FXML
    private void initialize() {
        agregarPar();
        agregarPar();agregarPar();
        agregarPar(); // Agrega un par inicial por defecto
    }


    public void agregarPar() {
        try {
            // Crear un nuevo par de elementos
            ParElementos parElementos = new ParElementos();

            // Agregar el par al contenedor y a la lista
            parejasContainer.getChildren().add(parElementos.getContenedor());
            listaPares.add(parElementos);

            // Limpiar cualquier mensaje de error previo
            mensajeError.setText("");
        } catch (Exception e) {
            // Mostrar mensaje de error si hay algún problema
            mensajeError.setText("Error al agregar par: " + e.getMessage());
        }
    }

    public void setEnunciado(String enunciado) {
        lblEnunciado.setText(enunciado);
    }

    public void setOpciones(List<OpcionMultipleRespuesta> opciones , int idPregunta) {
        // Limpiar vista previa

        this.idPregunta = idPregunta;
        parejasContainer.getChildren().clear();
        listaPares.clear();

        List<String> listaA = new ArrayList<>();
        List<String> listaB = new ArrayList<>();

        // Extraer lados A y B de cada opción
        for (OpcionMultipleRespuesta opcion : opciones) {
            String texto = opcion.getTexto();
            if (texto.contains(";")) {
                String[] partes = texto.split(";", 2);
                listaA.add(partes[0].trim());
                listaB.add(partes[1].trim());
            }
        }

        // Desordenar ambas listas de forma independiente
        Collections.shuffle(listaA);
        Collections.shuffle(listaB);

        int cantidad = Math.min(listaA.size(), listaB.size());

        for (int i = 0; i < cantidad; i++) {
            ParElementos par = new ParElementos();
            par.elementoA.setText(listaA.get(i));
            par.elementoB.setText(listaB.get(i));

            // Opcional: desactivar edición si es solo para responder
            par.elementoA.setEditable(false);
            par.elementoB.setEditable(false);

            parejasContainer.getChildren().add(par.getContenedor());
            listaPares.add(par);
        }
    }



    public void registrarRespuesta(ActionEvent actionEvent) {
        mensajeError.setText("");
        mensajeError.setVisible(false);

        List<String> respuestasUsuario = new ArrayList<>();

        for (ParElementos par : listaPares) {

            String letraSeleccionadaA = par.opcionesRespuestaColA.getValue();
            if (letraSeleccionadaA == null || letraSeleccionadaA.isEmpty()) {
                mensajeError.setText("Falta seleccionar una letra en un campo del lado A.");
                mensajeError.setVisible(true);
                return;
            }

            // Buscar en la lista de pares dónde está esa letra en las opcionesRespuestaColB
            ParElementos parBEncontrado = null;
            for (ParElementos posibleParB : listaPares) {
                String letraSeleccionadaB = posibleParB.opcionesRespuestaColB.getValue();
                if (letraSeleccionadaB != null && letraSeleccionadaB.equals(letraSeleccionadaA)) {
                    parBEncontrado = posibleParB;
                    break;
                }
            }

            if (parBEncontrado == null) {
                mensajeError.setText("No se encontró emparejamiento para la letra: " + letraSeleccionadaA);
                mensajeError.setVisible(true);
                return;
            }

            String ladoA = par.getElementoA();
            String ladoB = parBEncontrado.getElementoB();

            respuestasUsuario.add(ladoA + ";" + ladoB);

            if(uQuizzes.validarRespuestaEmparejar(respuestasUsuario, idPregunta)){
                mensajeError.setText("Respuesta correcta");
                mensajeError.setTextFill(Paint.valueOf("green"));
                mensajeError.setVisible(true);
                disableAndColorContainer(true);
            }
            else{
                mensajeError.setText("Respuesta incorrecta");
                mensajeError.setTextFill(Paint.valueOf("red"));
                mensajeError.setVisible(true);

                //para deshabilitar el botón y cambiar el color de fondo
                disableAndColorContainer(false);
            }
        }

        // Mostrar respuestas (solo para prueba)
        System.out.println("Respuestas emparejadas del usuario:");
        for (String r : respuestasUsuario) {
            System.out.println(r);
        }
    }


    // Clase interna para manejar cada par de elementos
    private class ParElementos {
        private HBox contenedor;
        private TextField elementoA;
        private TextField elementoB;
        private ComboBox<String> opcionesRespuestaColA;
        private ComboBox<String> opcionesRespuestaColB;
        private Label espaciador;


        public ParElementos() {
            // Crear contenedor horizontal para el par
            contenedor = new HBox(10);

            // Crear campos de texto para los elementos A y B
            elementoA = new TextField();
            elementoA.setPromptText("Elemento A");
            elementoA.setMaxWidth(300);

            elementoB = new TextField();
            elementoB.setPromptText("Elemento B");
            elementoB.setMaxWidth(300);

            // Crear comboBox columna a de opciones de respuesta
            opcionesRespuestaColA = new ComboBox<>();
            opcionesRespuestaColA.getItems().addAll("a", "b", "c", "d");
            opcionesRespuestaColA.setPromptText("a");

            // Crear comboBox columna b de opciones de respuesta
            opcionesRespuestaColB = new ComboBox<>();
            opcionesRespuestaColB.getItems().addAll("a", "b", "c", "d");
            opcionesRespuestaColB.setPromptText("a");


            //Crear label espaciador entre elementos para alinear en las columnas
            espaciador = new Label();
            espaciador.setPrefWidth(200);
            espaciador.setVisible(false);

            // Agregar elementos al contenedor
            contenedor.getChildren().addAll(opcionesRespuestaColA,elementoA,espaciador,  elementoB, opcionesRespuestaColB);
        }

        private void eliminarPar() {
            // Eliminar este par del contenedor y de la lista
            parejasContainer.getChildren().remove(contenedor);
            listaPares.remove(this);
        }

        public HBox getContenedor() {
            return contenedor;
        }

        public String getElementoA() {
            return elementoA.getText().trim();
        }

        public String getElementoB() {
            return elementoB.getText().trim();
        }
    }


    private boolean hayElementosDuplicados() {
        List<String> elementosA = new ArrayList<>();
        List<String> elementosB = new ArrayList<>();

        for (ParElementos par : listaPares) {
            String a = par.getElementoA();
            String b = par.getElementoB();

            if (elementosA.contains(a) || elementosB.contains(b)) {
                return true;
            }

            elementosA.add(a);
            elementosB.add(b);
        }

        return false;
    }


    private void disableAndColorContainer(boolean correcto) {


        double vPos = scrollPaneContenedor.getVvalue();
        double hPos = scrollPaneContenedor.getHvalue();

        // 1) Deshabilita el botón
        btnValidarRespuesta.setDisable(true);

        if(correcto){
            mensajeError.setText("Respuesta correcta");
        }
        else {
            mensajeError.setText("Respuesta incorrecta");
        }
        mensajeError.setVisible(true);
        // 2) Elige el color
        String color = correcto ? "#C8E6C9" : "#FFCDD2";

        // 3) Pinta panel, scrollPane, viewport y VBox contenedor
        emparejamientoPanel.setStyle("-fx-background-color: " + color + ";");
        scrollPaneContenedor.setStyle("-fx-background-color: " + color + ";");
        Node viewport = scrollPaneContenedor.lookup(".viewport");
        if (viewport != null) viewport.setStyle("-fx-background-color: " + color + ";");
        parejasContainer.setStyle("-fx-background-color: " + color + ";");

        // 4) Para cada par: pinta el HBox y deshabilita sus ComboBoxes
        for (ParElementos par : listaPares) {
            par.getContenedor().setStyle("-fx-background-color: " + color + ";");

            // deshabilita sólo los ComboBoxes
            par.opcionesRespuestaColA.setDisable(true);
            par.opcionesRespuestaColB.setDisable(true);
        }

        Platform.runLater(() -> {
            scrollPaneContenedor.setVvalue(vPos);
            scrollPaneContenedor.setHvalue(hPos);
        });
    }


}