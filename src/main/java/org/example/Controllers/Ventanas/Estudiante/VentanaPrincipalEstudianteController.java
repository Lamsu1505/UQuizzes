package org.example.Controllers.Ventanas.Estudiante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.example.Model.UQuizzes;

import java.io.IOException;

public class VentanaPrincipalEstudianteController {

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnClases;

    @FXML
    private Button btnExamenes;

    @FXML
    private Button btnExamenesPendientes;

    @FXML
    private Button btnInicio;

    @FXML
    private StackPane contenedorCambiante;

    @FXML
    private HBox hboxArriba;

    @FXML
    private SVGPath imgBuscar;

    @FXML
    private Label lblNombreApellido;

    @FXML
    private Label lblUquizzes;

    @FXML
    private BorderPane paneIrAdelante;

    @FXML
    private BorderPane paneIrAtras;

    @FXML
    private BorderPane panelCentral;

    @FXML
    private TextField txtFIeldBuscar;

    @FXML
    void cerrarSesion(javafx.event.ActionEvent event) {
        try {
            UQuizzes uQuizzes = UQuizzes.getInstance();
            uQuizzes.setUsuarioEnSesion(null);

            // 1. Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Login/login.fxml"));
            Parent root = loader.load();

            // 2. Obtener el Stage actual desde el botón que activó el eventos
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Crear una nueva escena y asignarla al stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("UQuizzes - Iniciar Sesión");

            stage.setWidth(630);
            stage.setHeight(390);
            stage.setResizable(false);

            stage.centerOnScreen();
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irPanelClases(ActionEvent event) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/estadisticasExamenesPresentados.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irPanelExamenes(ActionEvent event) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/estadisticasExamenesPresentados.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irPanelInicio(ActionEvent event) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/estadisticasExamenesPresentados.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irPanesExamenesProgramados(ActionEvent event) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/estadisticasExamenesPresentados.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
