package org.example.Controllers.Paneles.Estudiante.FormatosRespuestas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormatoUnicaRespuestaController implements Initializable {

    @FXML
    private VBox opcionesContainer;

    @FXML
    private Button agregarOpcionButton;

    @FXML
    private Label mensajeError;

    @FXML
    private Label enunciadoLabel;

    @FXML
    private Label lblEnunciado;


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
        agregarOpcionConTexto("t7uyfguj");
    }

    private void agregarOpcionConTexto(String texto) {
        // Crear modelo
        OpcionMultipleRespuesta opcion = new OpcionMultipleRespuesta(texto);
        listaOpcionesModel.add(opcion);

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

    private void eliminarOpcion(HBox opcionHBox) {
        if (listaOpciones.size() == 2) {
            mostrarMensajeError("No se pueden dejar menos de dos opciones.");
            return;
        }

        // Eliminar del modelo
        OpcionMultipleRespuesta modelo = (OpcionMultipleRespuesta) opcionHBox.getUserData();
        listaOpcionesModel.remove(modelo);

        // Deseleccionar si es la opción marcada
        RadioButton rb = (RadioButton) opcionHBox.getChildren().get(0);
        if (rb.isSelected()) {
            toggleGroup.selectToggle(null);
        }

        // Eliminar visualmente
        opcionesContainer.getChildren().remove(opcionHBox);
        listaOpciones.remove(opcionHBox);
    }

    private void mostrarMensajeError(String mensaje) {
        mensajeError.setText(mensaje);
        mensajeError.setVisible(true);
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

    private void ponerOpcionCorrecta() {
        for (OpcionMultipleRespuesta op : listaOpcionesModel) {
            op.setEsCorrecta(false);
        }
        int opcionCorrecta = getOpcionCorrectaVista();
        if (opcionCorrecta >= 0 && opcionCorrecta < listaOpcionesModel.size()) {
            listaOpcionesModel.get(opcionCorrecta).setEsCorrecta(true);
        }
    }

    public List<OpcionMultipleRespuesta> getListaOpciones() {

        System.out.println(listaOpcionesModel.size() + " " + listaOpciones.size());
        ponerOpcionCorrecta();
        return listaOpcionesModel;
    }



    public void setOpciones(List<OpcionMultipleRespuesta> opciones) {
        opcionesContainer.getChildren().clear();
        listaOpciones.clear();
        listaOpcionesModel.clear();
        for (OpcionMultipleRespuesta opcion : opciones) {
            agregarOpcionConTexto(opcion.getTexto());
        }
    }

    public void setEnunciado(String texto) {
        lblEnunciado.setText(texto);
    }
}
