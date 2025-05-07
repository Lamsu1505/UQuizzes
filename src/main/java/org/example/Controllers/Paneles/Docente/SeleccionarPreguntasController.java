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

import java.io.IOException;

public class SeleccionarPreguntasController {

    @FXML
    private Button AgregarButton;

    @FXML
    private TableColumn<?, ?> ColumnPregunta;

    @FXML
    private TableColumn<?, ?> ColumnTipoPregunta;

    @FXML
    private Button DesagregarButton;

    @FXML
    private TableView<?> TableViewPregunta;

    @FXML
    private Button volverButton;

    @FXML
    void agregarButton(ActionEvent event) {

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

}
