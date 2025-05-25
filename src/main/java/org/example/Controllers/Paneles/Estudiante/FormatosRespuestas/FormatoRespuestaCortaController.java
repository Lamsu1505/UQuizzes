package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.UQuizzes;

import java.util.ArrayList;
import java.util.List;

public class FormatoRespuestaCortaController {
    @FXML
    private VBox respuestasContainer;

    @FXML
    private TextField nuevaRespuestaTextField;

    @FXML
    private Button agregarRespuestaButton;

    @FXML private Button btnValidarRespuesta;

    @FXML
    private Label lblEnunciado;

    @FXML
    private Label mensajeError;

    @FXML
    private ScrollPane scrollContenedor;

    @FXML
    private VBox respuestaCortaPanel;

    private int idPregunta;
    private UQuizzes uQuizzes = UQuizzes.getInstance();

    // Lista para almacenar las respuestas aceptadas
    private List<RespuestaCorta> listaRespuestas = new ArrayList<>();
    private int idPreguntaDetalle;

    @FXML
    private void initialize() {
        // Configurar validación mientras se escribe
        nuevaRespuestaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Limpiar espacios al inicio y al final
            nuevaRespuestaTextField.setText(newValue.trim());
        });
    }

    @FXML
    private void agregarRespuesta() {
        try {
            String textoRespuesta = nuevaRespuestaTextField.getText().trim();

            // Validar que la respuesta no esté vacía
            if (textoRespuesta.isEmpty()) {
                mensajeError.setText("La respuesta no puede estar vacía");
                return;
            }

            // Verificar que no exista una respuesta duplicada
            if (existeRespuesta(textoRespuesta)) {
                mensajeError.setText("Esta respuesta ya existe");
                return;
            }

            // Crear nueva respuesta
            RespuestaCorta nuevaRespuesta = new RespuestaCorta(textoRespuesta);

            // Agregar al contenedor y a la lista de respuestas
            respuestasContainer.getChildren().add(nuevaRespuesta.getContenedor());
            listaRespuestas.add(nuevaRespuesta);

            // Limpiar campo de texto y mensaje de error
            nuevaRespuestaTextField.clear();
            mensajeError.setText("");
        } catch (Exception e) {
            // Mostrar mensaje de error si hay algún problema
            mensajeError.setText("Error al agregar respuesta: " + e.getMessage());
        }
    }

    /**
     * Verificar si ya existe una respuesta idéntica
     * @param texto Texto de la respuesta a verificar
     * @return true si la respuesta ya existe, false en caso contrario
     */
    private boolean existeRespuesta(String texto) {
        return listaRespuestas.stream()
                .anyMatch(r -> r.getTexto().equalsIgnoreCase(texto.trim()));
    }

    /**
     * Obtener todas las respuestas
     * @return Lista de respuestas
     */
    public List<String> getListaOpciones() {
        ArrayList<String> respuestas = new ArrayList<>();

        for (RespuestaCorta respuesta : listaRespuestas) {
            respuestas.add(respuesta.getTexto());
        }
        return respuestas;
    }

    public void setEnunciado(String enunciado) {
        lblEnunciado.setText(enunciado);
    }

    public void registrarRespuesta(ActionEvent actionEvent) {
        int idPregunta = getIdPregunta();

        String respuesta = "";
        for (String respuestaS : getListaOpciones()) {
            respuesta += respuestaS;
            respuesta += ";";
        }

        boolean respuestaIsCOrrecta = uQuizzes.validarRespuestaCorta(idPregunta , getListaOpciones());

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
        // 1) Guardar posición actual
        double vPos = scrollContenedor.getVvalue();
        double hPos = scrollContenedor.getHvalue();

        // 2) Deshabilitar botones y campo de texto
        btnValidarRespuesta.setDisable(true);
        agregarRespuestaButton.setDisable(true);
        nuevaRespuestaTextField.setDisable(true);

        // 3) Escoger color de fondo
        String color = correcto ? "#C8E6C9" : "#FFCDD2";

        // 4) Pintar panel principal
        respuestaCortaPanel.setStyle("-fx-background-color: " + color + ";");

        // 5) Pintar ScrollPane y su viewport
        scrollContenedor.setStyle("-fx-background-color: " + color + ";");
        Node viewport = scrollContenedor.lookup(".viewport");
        if (viewport != null) viewport.setStyle("-fx-background-color: " + color + ";");

        // 6) Pintar contenedor de respuestas
        respuestasContainer.setStyle("-fx-background-color: " + color + ";");

        // 7) Pintar y bloquear cada HBox de respuesta
        for (RespuestaCorta rc : listaRespuestas) {
            HBox box = rc.getContenedor();
            box.setStyle("-fx-background-color: " + color + ";");
            box.setDisable(true);
            box.setFocusTraversable(false);
        }

        // 8) Restaurar scroll tras el relayout
        Platform.runLater(() -> {
            scrollContenedor.setVvalue(vPos);
            scrollContenedor.setHvalue(hPos);
        });
    }




    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public void setIdPreguntaDetalle(int idPreguntaDetalle) {
        this.idPreguntaDetalle = idPreguntaDetalle;
    }

    /**
     * Clase interna para manejar cada respuesta corta
     */
    public class RespuestaCorta {
        private HBox contenedor;
        private Label textoRespuesta;
        private Button eliminarButton;

        public RespuestaCorta(String texto) {
            // Crear contenedor horizontal
            contenedor = new HBox(10);

            // Crear label para mostrar la respuesta
            textoRespuesta = new Label(texto);
            textoRespuesta.setMaxWidth(Double.MAX_VALUE);

            // Crear botón de eliminación
            eliminarButton = new Button("X");
            eliminarButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            eliminarButton.setOnAction(e -> eliminarRespuesta());

            // Agregar elementos al contenedor
            contenedor.getChildren().addAll(textoRespuesta, eliminarButton);
        }

        /**
         * Eliminar esta respuesta del contenedor y de la lista
         */
        private void eliminarRespuesta() {
            respuestasContainer.getChildren().remove(contenedor);
            listaRespuestas.remove(this);
        }

        /**
         * Obtener el contenedor de la respuesta
         * @return HBox que contiene la respuesta
         */
        public HBox getContenedor() {
            return contenedor;
        }

        /**
         * Obtener el texto de la respuesta
         * @return Texto de la respuesta
         */
        public String getTexto() {
            return textoRespuesta.getText();
        }
    }


    public boolean verificarRespuesta(String respuestaUsuario) {
        if (respuestaUsuario == null || respuestaUsuario.trim().isEmpty()) {
            return false;
        }

        return listaRespuestas.stream()
                .anyMatch(r -> r.getTexto().trim().equalsIgnoreCase(respuestaUsuario.trim()));
    }

    public void agregarRespuestas(List<String> respuestas) {
        respuestas.forEach(respuesta -> {
            if (!existeRespuesta(respuesta)) {
                RespuestaCorta nuevaRespuesta = new RespuestaCorta(respuesta.trim());
                respuestasContainer.getChildren().add(nuevaRespuesta.getContenedor());
                listaRespuestas.add(nuevaRespuesta);
            }
        });
    }
}
