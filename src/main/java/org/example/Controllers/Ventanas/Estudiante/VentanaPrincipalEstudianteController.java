package org.example.Controllers.Ventanas.Estudiante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VentanaPrincipalEstudianteController implements Initializable {

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

    UQuizzes uQuizzes = UQuizzes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = new ConexionOracle().conectar();

        try {
            String sql = "select nombre , apellido from estudiante where idEstudiante = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1 , uQuizzes.getUsuarioEnSesion());

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                lblNombreApellido.setText(nombre + " " + apellido);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Estudiante/s/panelInicioEstudiante.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);

        }
        catch (Exception e) {
        }
    }


    @FXML
    void cerrarSesion(javafx.event.ActionEvent event) {

        uQuizzes.setUsuarioEnSesion(null);

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
            /*Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Estudiante/s/panelInicioEstudiante.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);*/

            reiniciarColoresBotones();
            btnClases.setStyle("-fx-background-color: #004513; -fx-text-fill: white;");


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reiniciarColoresBotones() {
        btnClases.setStyle("");
        btnInicio.setStyle("");
        btnExamenes.setStyle("");
    }

    @FXML
    void irPanelExamenes(ActionEvent event) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Estudiante/s/examenEstudiantePrincipal.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);

            reiniciarColoresBotones();
            btnExamenes.setStyle("-fx-background-color: #004513; -fx-text-fill: white;");


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irPanelInicio(ActionEvent event) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Estudiante/s/panelInicioEstudiante.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);

            reiniciarColoresBotones();
            btnInicio.setStyle("-fx-background-color: #004513; -fx-text-fill: white;");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irPanesExamenesProgramados(ActionEvent event) {
        try {
            /*Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/estadisticasExamenesPresentados.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);

            reiniciarColoresBotones();
            btnExamenesPendientes.setStyle("-fx-background-color: #004513; -fx-text-fill: white;");*/


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
