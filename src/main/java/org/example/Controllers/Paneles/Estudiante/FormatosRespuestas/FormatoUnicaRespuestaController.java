package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.UQuizzes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormatoUnicaRespuestaController implements Initializable {

    @FXML
    private VBox opcionesContainer;


    @FXML
    private Label mensajeError;

    @FXML
    private Label enunciadoLabel;

    @FXML
    private Label lblEnunciado;

    @FXML
    private VBox opcionMultiplePanel;

    @FXML
    private Button btnValidarRespuesta;

    @FXML
    private ScrollPane scrollContenedor;

    private UQuizzes uQuizzes = UQuizzes.getInstance();

    private int idPreguntaDetalle;


    private ToggleGroup toggleGroup = new ToggleGroup(); // para controlar selección única
    private List<HBox> listaOpciones = new ArrayList<>();
    private List<OpcionMultipleRespuesta> listaOpcionesModel = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mensajeError.setText("");
        mensajeError.setVisible(false);
        opcionesContainer.setStyle("-fx-background-color: #f8f9fa;");
        agregarOpcionConTexto("A. Se fundo en 1998");
        agregarOpcionConTexto("B. No se tiene informacion");

    }

    @FXML
    void agregarOpcion(ActionEvent event) {
        agregarOpcionConTexto("");
    }

    private void agregarOpcionConTexto(String texto) {
        // Crear modelo
        OpcionMultipleRespuesta opcion = new OpcionMultipleRespuesta(texto);

        // Crear contenedor visual
        HBox opcionHBox = new HBox(10);
        opcionHBox.setAlignment(Pos.CENTER_LEFT);
        opcionHBox.setPadding(new Insets(5, 10, 5, 10));
        opcionHBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 5;");

        // RadioButton con grupo
        RadioButton radioButton = new RadioButton();
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setStyle("-fx-padding: 0 5 0 0;");

        // Campo de texto
        TextField opcionTextField = new TextField(texto);
        opcionTextField.setEditable(false);
        opcionTextField.setFocusTraversable(false);
        opcionTextField.setPromptText("Ingrese el texto de la opción");
        opcionTextField.getStyleClass().add("text-field");
        opcionTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        HBox.setHgrow(opcionTextField, Priority.ALWAYS);


        // Sincroniza texto del modelo con el campo
        opcionTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            opcion.setTexto(newVal);
        });



        // Agregar componentes
        opcionHBox.getChildren().addAll(radioButton, opcionTextField);
        opcionHBox.setUserData(opcion); // vincular modelo con vista

        // Agregar a listas y vista
        listaOpciones.add(opcionHBox);
        opcionesContainer.getChildren().add(opcionHBox);

        // Efecto hover
        opcionHBox.setOnMouseEntered(e -> opcionHBox.setStyle("-fx-background-color: #e9ecef; -fx-border-radius: 5;"));
        opcionHBox.setOnMouseExited(e -> opcionHBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 5;"));
    }

    public List<String> getOpciones() {
        List<String> opciones = new ArrayList<>();
        for (HBox opcionHBox : listaOpciones) {
            TextField opcionTextField = (TextField) opcionHBox.getChildren().get(2);
            opciones.add(opcionTextField.getText().trim());
        }
        return opciones;
    }

    public int getOpcionCorrectaVista() {
        for (int i = 0; i < listaOpciones.size(); i++) {
            RadioButton rb = (RadioButton) listaOpciones.get(i).getChildren().get(0);
            if (rb.isSelected()) {
                return i;
            }
        }
        return -1;
    }


    public void setOpciones(List<OpcionMultipleRespuesta> opciones , int idPregunta) {
        System.out.println("laaaaaaaaaaaaaaaaaa pregunta es " + idPregunta);
        opcionesContainer.getChildren().clear();
        listaOpciones.clear();
        listaOpcionesModel.clear();
        for (OpcionMultipleRespuesta opcionMultipleRespuesta : opciones) {

            opcionMultipleRespuesta.setIdPregunta(idPregunta);
            listaOpcionesModel.add(opcionMultipleRespuesta);
            agregarOpcionConTexto(opcionMultipleRespuesta.getTexto());
        }
    }

    public void setEnunciado(String texto) {
        lblEnunciado.setText(texto);
    }



    public void registrarRespuesta(ActionEvent actionEvent) {
        System.out.println(listaOpcionesModel);
        System.out.println(listaOpciones);
        int indiceSeleccionado = getOpcionCorrectaVista();
        System.out.println("el indice seleccionado es " + indiceSeleccionado);

        if (indiceSeleccionado == -1) {
            mensajeError.setText("Debe seleccionar al menos una opción .");
            mensajeError.setVisible(true);
            return;
        }

        mensajeError.setVisible(false);

        // Limpia cualquier selección previa en el modelo
        for (OpcionMultipleRespuesta op : listaOpcionesModel) {
            op.setEsCorrecta(false);
        }

        // Marca la opción seleccionada como correcta
        listaOpcionesModel.get(indiceSeleccionado).setEsCorrecta(true); //setEsCorrecta es "seleccionada" para el caso de estudiante

        int idPregunta= listaOpcionesModel.get(indiceSeleccionado).getIdPregunta();
        String respuesta = listaOpcionesModel.get(indiceSeleccionado).getTexto();

        System.out.println("Respuesta seleccionada: " + respuesta);

        boolean respuestaIsCOrrecta = uQuizzes.validarRespuesta(idPregunta , respuesta);

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
        // 1) guardar scroll
        double vPos = scrollContenedor.getVvalue();
        double hPos = scrollContenedor.getHvalue();

        // 2) deshabilitar botones
        btnValidarRespuesta.setDisable(true);

        // 3) color de fondo
        String color = correcto ? "#C8E6C9" : "#FFCDD2";

        // 4) pintar panel principal
        opcionMultiplePanel.setStyle("-fx-background-color: " + color + ";");

        // 5) pintar ScrollPane + viewport
        scrollContenedor.setStyle("-fx-background-color: " + color + ";");
        Node viewport = scrollContenedor.lookup(".viewport");
        if (viewport != null) {
            viewport.setStyle("-fx-background-color: " + color + ";");
        }

        // 6) pintar VBox de opciones
        opcionesContainer.setStyle("-fx-background-color: " + color + ";");

        // 7) para cada HBox: pintar y deshabilitar
        for (HBox box : listaOpciones) {
            box.setStyle("-fx-background-color: " + color + ";");
            box.setDisable(true);
            box.setFocusTraversable(false);
        }

        // 8) restaurar scroll al próximo ciclo de render
        Platform.runLater(() -> {
            scrollContenedor.setVvalue(vPos);
            scrollContenedor.setHvalue(hPos);
        });
    }

    public void setIdPreguntaDetalle(int idPreguntaDetalle) {
        this.idPreguntaDetalle = idPreguntaDetalle;
    }
}
