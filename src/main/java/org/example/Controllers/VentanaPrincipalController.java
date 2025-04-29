package org.example.Controllers;

import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipalController implements Initializable {

    @FXML private Button btnCrearQuiz;
    @FXML private Button btnCrearPregunta;
    @FXML private Button btnInformes;
    @FXML private Button btnMisClases;
    @FXML private Button btnCerrarSesion;

    // Labels para exámenes (deberías tener un conjunto para cada examen)
    @FXML private Label lblNombreExamen1;
    @FXML private Label lblDetallesExamen1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
