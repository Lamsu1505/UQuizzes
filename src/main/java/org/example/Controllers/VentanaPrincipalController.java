package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void cerrarSesion(ActionEvent actionEvent) {
        try {
            // 1. Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Login/login.fxml"));
            Parent root = loader.load();

            // 2. Obtener el Stage actual desde el botón que activó el evento
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // 3. Crear una nueva escena y asignarla al stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("UQuizzes - Iniciar Sesión");


            stage.setWidth(612);
            stage.setHeight(500);
            stage.centerOnScreen();
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            }
    }
}