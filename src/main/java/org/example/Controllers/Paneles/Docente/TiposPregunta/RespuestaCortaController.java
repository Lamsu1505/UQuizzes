package org.example.Controllers.Paneles.Docente.TiposPregunta;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RespuestaCortaController {
    @FXML
    private VBox respuestasContainer;

    @FXML
    private TextField nuevaRespuestaTextField;

    @FXML
    private Button agregarRespuestaButton;

    @FXML
    private Label mensajeError;

    // Lista para almacenar las respuestas aceptadas
    private List<RespuestaCorta> listaRespuestas = new ArrayList<>();

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
     * Validar las respuestas cortas
     * @return true si las respuestas son válidas, false en caso contrario
     */
    public boolean validarRespuestas() {
        // Limpiar mensaje de error previo
        mensajeError.setText("");

        // Verificar que haya al menos una respuesta
        if (listaRespuestas.isEmpty()) {
            mensajeError.setText("Debe haber al menos una respuesta aceptada");
            return false;
        }

        return true;
    }

    /**
     * Obtener todas las respuestas
     * @return Lista de respuestas
     */
    public List<RespuestaCorta> obtenerRespuestas() {
        return new ArrayList<>(listaRespuestas);
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

    /**
     * Método para verificar si una respuesta dada coincide con alguna de las respuestas aceptadas
     * @param respuestaUsuario Respuesta a verificar
     * @return true si la respuesta coincide con alguna respuesta aceptada, false en caso contrario
     */
    public boolean verificarRespuesta(String respuestaUsuario) {
        if (respuestaUsuario == null || respuestaUsuario.trim().isEmpty()) {
            return false;
        }

        return listaRespuestas.stream()
                .anyMatch(r -> r.getTexto().trim().equalsIgnoreCase(respuestaUsuario.trim()));
    }

    /**
     * Método para agregar múltiples respuestas de una vez
     * @param respuestas Lista de respuestas a agregar
     */
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