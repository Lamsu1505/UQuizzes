package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.UQuizzes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormatoSeleccionMultipleController {
    @FXML
    private VBox opcionesContainer;

    @FXML
    private Label mensajeError;

    @FXML
    private Label lblEnunciado;


    @FXML
    private Button btnValidarRespuesta;

    @FXML
    private ScrollPane scrollContenedor;

    @FXML
    private VBox seleccionMultiplePanel;

    private int idPregunta;

    private UQuizzes uQuizzes = UQuizzes.getInstance();

    private int idPreguntaDetalle;


    // Lista para almacenar las opciones
    private List<OpcionMultiple> listaOpciones = new ArrayList<>();
    private List<OpcionMultipleRespuesta> listaOpcionesModel = new ArrayList<>();


    @FXML
    private void initialize() {
        // Agregar una opción por defecto
        agregarOpcion(null);
    }

    @FXML
    private void agregarOpcion(String opcion) {
        try {
            // Crear una nueva opción con texto editable
            OpcionMultiple nuevaOpcion = new OpcionMultiple();
            nuevaOpcion.setTexto(opcion);

            // Agregar al contenedor y a la lista de opciones
            opcionesContainer.getChildren().add(nuevaOpcion.getContenedor());
            listaOpciones.add(nuevaOpcion);

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

    public void setEnunciado(String enunciado) {
        lblEnunciado.setText(enunciado);
    }

    public void registrarRespuesta(ActionEvent actionEvent) {
        List<OpcionMultipleRespuesta> opcionesSeleccionadas = new ArrayList<>();

        for (OpcionMultiple opcion : obtenerOpcionesSeleccionadas()) {
            OpcionMultipleRespuesta opcionRespuesta = new OpcionMultipleRespuesta(opcion.getTexto());
            opcionRespuesta.setIdPregunta(idPregunta);
            opcionRespuesta.setTexto(opcion.getTexto());
            opcionesSeleccionadas.add(opcionRespuesta);
        }

        int indiceSeleccionado = opcionesSeleccionadas.size();

        if (indiceSeleccionado == -1) {
            mensajeError.setText("Debe seleccionar al menos una opción .");
            mensajeError.setVisible(true);
            return;
        }
        mensajeError.setVisible(false);


        boolean respuestaIsCOrrecta = uQuizzes.validarRespuestaMultiple(idPregunta , opcionesSeleccionadas);

        String respuesta="";
        for (OpcionMultipleRespuesta op : opcionesSeleccionadas) {
            respuesta += op.getTexto();
            respuesta += ";";
        }

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

    public void setOpciones(List<OpcionMultipleRespuesta> opciones , int idPregunta) {
        opcionesContainer.getChildren().clear();
        this.idPregunta = idPregunta;
        listaOpciones.clear();
        listaOpcionesModel.clear();
        for (OpcionMultipleRespuesta opcion : opciones) {
            agregarOpcion(opcion.getTexto());
        }
    }

    public void setIdPreguntaDetalle(int idPreguntaDetalle) {
        this.idPreguntaDetalle = idPreguntaDetalle;
    }


    public class OpcionMultiple {
        private HBox contenedor;
        private CheckBox checkBox;
        private TextField textoOpcion;
        private Button eliminarButton;

        public OpcionMultiple() {
            // Crear contenedor horizontal
            contenedor = new HBox(10);

            // Crear checkbox
            checkBox = new CheckBox();
            checkBox.getStyleClass().add("opcion-check");

            // Crear campo de texto
            textoOpcion = new TextField("");
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

        public void setTexto(String texto) {
            textoOpcion.setText(texto);
        }

        /**
         * Establecer el estado de selección de la opción
         * @param seleccionada Estado de selección
         */
        public void setSeleccionada(boolean seleccionada) {
            checkBox.setSelected(seleccionada);
        }
    }


    private void disableAndColorContainer(boolean correcto) {

        double vPos = scrollContenedor.getVvalue();
        double hPos = scrollContenedor.getHvalue();

        // 1) Deshabilita botón
        btnValidarRespuesta.setDisable(true);

        // 2) Elige color
        String color = correcto ? "#C8E6C9" : "#FFCDD2";

        // 3) Pinta panel principal
        seleccionMultiplePanel.setStyle("-fx-background-color: " + color + ";");

        // 4) Pinta ScrollPane + su viewport
        scrollContenedor.setStyle("-fx-background-color: " + color + ";");
        Node viewport = scrollContenedor.lookup(".viewport");
        if (viewport != null) viewport.setStyle("-fx-background-color: " + color + ";");

        // 5) Pinta VBox de opciones
        opcionesContainer.setStyle("-fx-background-color: " + color + ";");

        // 6) Para cada opción: pinta y bloquea su HBox entero
        for (OpcionMultiple opt : listaOpciones) {
            HBox box = opt.getContenedor();
            box.setStyle("-fx-background-color: " + color + ";");
            box.setDisable(true);
        }


        Platform.runLater(() -> {
            scrollContenedor.setVvalue(vPos);
            scrollContenedor.setHvalue(hPos);
        });
    }
}







