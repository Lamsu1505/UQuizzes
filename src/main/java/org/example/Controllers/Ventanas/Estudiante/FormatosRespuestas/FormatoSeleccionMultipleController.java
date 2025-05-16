package org.example.Controllers.Ventanas.Estudiante.FormatosRespuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Controllers.Paneles.Docente.TiposPregunta.SeleccionMultipleController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormatoSeleccionMultipleController {
    @FXML
    private VBox opcionesContainer;

    @FXML
    private Label mensajeError;


    // Lista para almacenar las opciones
    private List<OpcionMultiple> listaOpciones = new ArrayList<>();

    private int contadorOpciones = 1;

    @FXML
    private void initialize() {
        // Agregar una opción por defecto
        agregarOpcion();
    }

    @FXML
    private void agregarOpcion() {
        try {
            // Crear una nueva opción con texto editable
            OpcionMultiple nuevaOpcion = new OpcionMultiple(contadorOpciones);

            // Agregar al contenedor y a la lista de opciones
            opcionesContainer.getChildren().add(nuevaOpcion.getContenedor());
            listaOpciones.add(nuevaOpcion);

            contadorOpciones++;

            // Limpiar cualquier mensaje de error previo
            mensajeError.setText("");
        } catch (Exception e) {
            // Mostrar mensaje de error si hay algún problema
            mensajeError.setText("Error al agregar opción: " + e.getMessage());
        }
    }

    /**
     * Validar las opciones de selección múltiple
     * @return true si las opciones son válidas, false en caso contrario
     */
    public boolean validarOpciones() {
        // Limpiar mensaje de error previo
        mensajeError.setText("");

        // Verificar que haya al menos una opción
        if (listaOpciones.isEmpty()) {
            mensajeError.setText("Debe haber al menos una opción");
            return false;
        }

        // Verificar que no haya opciones vacías
        for (OpcionMultiple opcion : listaOpciones) {
            if (opcion.getTexto().trim().isEmpty()) {
                mensajeError.setText("Todas las opciones deben tener un texto");
                return false;
            }
        }

        // Verificar que no haya opciones duplicadas
        if (hayOpcionesDuplicadas()) {
            mensajeError.setText("No pueden existir opciones duplicadas");
            return false;
        }

        return true;
    }

    /**
     * Verificar si hay opciones de texto duplicadas
     * @return true si hay duplicados, false en caso contrario
     */
    private boolean hayOpcionesDuplicadas() {
        List<String> textos = listaOpciones.stream()
                .map(opcion -> opcion.getTexto().trim().toLowerCase())
                .collect(Collectors.toList());

        // Verificar si el tamaño de la lista original es diferente
        // de la lista con elementos únicos
        return textos.size() != textos.stream().distinct().count();
    }

    /**
     * Obtener todas las opciones
     * @return Lista de opciones
     */
    public List<OpcionMultiple> obtenerOpciones() {
        return new ArrayList<>(listaOpciones);
    }

    /**
     * Obtener las opciones seleccionadas
     * @return Lista de opciones seleccionadas
     */
    public List<OpcionMultiple> obtenerOpcionesSeleccionadas() {
        return listaOpciones.stream()
                .filter(OpcionMultiple::isSeleccionada)
                .collect(Collectors.toList());
    }

    /**
     * Clase interna para manejar cada opción de selección múltiple
     */
    public class OpcionMultiple {
        private HBox contenedor;
        private CheckBox checkBox;
        private TextField textoOpcion;
        private Button eliminarButton;

        public OpcionMultiple(int numero) {
            // Crear contenedor horizontal
            contenedor = new HBox(10);

            // Crear checkbox
            checkBox = new CheckBox();
            checkBox.getStyleClass().add("opcion-check");

            // Crear campo de texto
            textoOpcion = new TextField("Opción " + numero);
            textoOpcion.setMaxWidth(300);

            // Agregar elementos al contenedor
            contenedor.getChildren().addAll(checkBox, textoOpcion);
        }


        /**
         * Obtener el contenedor de la opción
         * @return HBox que contiene la opción
         */
        public HBox getContenedor() {
            return contenedor;
        }

        /**
         * Obtener el texto de la opción
         * @return Texto de la opción
         */
        public String getTexto() {
            return textoOpcion.getText();
        }

        /**
         * Verificar si la opción está seleccionada
         * @return true si está seleccionada, false en caso contrario
         */
        public boolean isSeleccionada() {
            return checkBox.isSelected();
        }

        /**
         * Establecer el estado de selección de la opción
         * @param seleccionada Estado de selección
         */
        public void setSeleccionada(boolean seleccionada) {
            checkBox.setSelected(seleccionada);
        }
    }
}
