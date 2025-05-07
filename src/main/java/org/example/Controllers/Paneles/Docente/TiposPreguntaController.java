package org.example.Controllers.Paneles.Docente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TiposPreguntaController {

    @FXML
    private ToggleButton CheckboxesButton;

    @FXML
    private Button ContinuarButton;

    @FXML
    private ToggleButton DocumentButton;

    @FXML
    private ToggleButton EssayButton;

    @FXML
    private ToggleButton FillblanksButton;

    @FXML
    private ToggleButton MatchingButton;

    @FXML
    private ToggleButton MultipleChoiceButton;

    @FXML
    private ToggleButton True_FalseButton;

    @FXML
    private ToggleButton Video_audioButton;

    @FXML
    private ToggleButton WritetextButton;

    @FXML
    private Button volverButton;

    @FXML
    void CheckboxesEvent(ActionEvent event) {

    }

    @FXML
    void ContinuarEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/seleccionar_preguntas.fxml"));
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
    void DocumentEvent(ActionEvent event) {

    }

    @FXML
    void EssayEvent(ActionEvent event) {

    }

    @FXML
    void Event(ActionEvent event) {

    }

    @FXML
    void FillblanksEvent(ActionEvent event) {

    }

    @FXML
    void MatchingEvent(ActionEvent event) {

    }

    @FXML
    void MultipleChoiceEvent(ActionEvent event) {

    }

    @FXML
    void True_FalseEvent(ActionEvent event) {

    }

    @FXML
    void WritetextEvent(ActionEvent event) {

    }

    @FXML
    void volverEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/panelCrearQuiz.fxml"));
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
