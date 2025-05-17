package org.example.Controllers.Paneles.Docente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.Controllers.Paneles.Estudiante.ResponderQuizController;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.PruebaPreguntas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeleccionarPreguntasController {

    @FXML
    private Button AgregarButton;

    @FXML
    private TableColumn<PruebaPreguntas, String> ColumnPregunta;

    @FXML
    private TableColumn<PruebaPreguntas, String> ColumnTipoPregunta;

    @FXML
    private Button DesagregarButton;

    @FXML
    private TableView<PruebaPreguntas> TableViewPregunta;

    @FXML
    private Button volverButton;

    @FXML
    void agregarButton(ActionEvent event) {
        PruebaPreguntas preguntaSeleccionada = TableViewPregunta.getSelectionModel().getSelectedItem();
        if (preguntaSeleccionada != null) {
            // Simular opciones quemadas para pruebas
            List<OpcionMultipleRespuesta> opciones = new ArrayList<>();
            opciones.add(new OpcionMultipleRespuesta("Opción A"));
            opciones.add(new OpcionMultipleRespuesta("Opción B"));
            opciones.add(new OpcionMultipleRespuesta("Opción C"));

            // Llamar a ResponderQuizController
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Estudiante/s/responderQuiz.fxml"));
                Node root = loader.load();
                ResponderQuizController controller = loader.getController();
                controller.agregarPregunta(preguntaSeleccionada, opciones);

                // Opcional: si quieres cambiar la escena completa
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene((Parent) root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void desagregarButton(ActionEvent event) {

    }

    @FXML
    void volverEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/panelTiposPregunta.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        ColumnTipoPregunta.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idTipoPregunta"));
        ColumnPregunta.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("enunciado"));

        javafx.collections.ObservableList<PruebaPreguntas> preguntas = javafx.collections.FXCollections.observableArrayList(
                new PruebaPreguntas(1, 101, 1, null, "¿Cuál es la capital de Francia?", "", "París", "Geografía básica"),
                new PruebaPreguntas(2, 102, 2, null, "El sol es una estrella.", "", "Verdadero", "Astronomía"),
                new PruebaPreguntas(3, 103, 3, null, "Define el concepto de algoritmo.", "", "Instrucciones lógicas para resolver un problema", "Informática")
        );

        TableViewPregunta.setItems(preguntas);
    }

}