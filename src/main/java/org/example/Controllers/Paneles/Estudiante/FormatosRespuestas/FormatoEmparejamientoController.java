package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class FormatoEmparejamientoController {

    @FXML
    private VBox parejasContainer;

    @FXML
    private Label mensajeError;

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

    public boolean validarPares() {
        // Limpiar mensaje de error previo
        mensajeError.setText("");

        // Verificar que no haya pares vacíos
        for (ParElementos par : listaPares) {
            if (par.getElementoA().isEmpty() || par.getElementoB().isEmpty()) {
                mensajeError.setText("Todos los pares deben tener elementos definidos");
                return false;
            }
        }

        // Verificar que no haya elementos duplicados
        if (hayElementosDuplicados()) {
            mensajeError.setText("No pueden existir elementos duplicados");
            return false;
        }

        return true;
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

    public List<ParElementos> obtenerPares() {
        return new ArrayList<>(listaPares);
    }
}