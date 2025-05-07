package org.example.Controllers.Paneles.Docente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CrearQuizController {

    @FXML
    private Button cancelarButton;

    @FXML
    private Label fechaFinLabel;

    @FXML
    private TextField fechaFinTextField;

    @FXML
    private Label fechaInicioLabel;

    @FXML
    private TextField fechaInicioTextField;

    @FXML
    private Label intentosLabel;

    @FXML
    private TextField intentosTextField;

    @FXML
    private ComboBox<?> materiaComboBox;

    @FXML
    private Label materiaLabel;

    @FXML
    private Label nombreQuizLabel;

    @FXML
    private TextField nombreQuizTextField;

    @FXML
    private Button siguienteButton;

    @FXML
    private ComboBox<?> temaComboBox;

    @FXML
    private Label temaLabel;

    @FXML
    private Label tiempoLabel;

    @FXML
    private TextField tiempoTextField;

    @FXML
    private Label tituloLabel;

    @FXML
    private ComboBox<?> unidadComboBox;

    @FXML
    private Label unidadLabel;

    @FXML
    void cancelarEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Ventanas/ventanaInicioDocente.fxml"));
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
    void siguienteEvent(ActionEvent event) {
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
