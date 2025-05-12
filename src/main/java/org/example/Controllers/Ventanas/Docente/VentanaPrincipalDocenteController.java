package org.example.Controllers.Ventanas.Docente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VentanaPrincipalDocenteController implements Initializable {

    @FXML
    private StackPane contenedorCambiante;

    @FXML
    private Label lblNombreApellido;

    @FXML
    private Label lblStorify;

    @FXML
    private BorderPane paneIrAdelante;

    @FXML
    private HBox hboxArriba;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnInicio;

    @FXML
    private TextField txtFIeldBuscar;

    @FXML
    private BorderPane paneIrAtras;

    @FXML
    private BorderPane panelCentral;

    @FXML
    private SVGPath imgBuscar;

    private static VentanaPrincipalDocenteController ventanaPrincipalDocenteController = new VentanaPrincipalDocenteController();


    public static VentanaPrincipalDocenteController getInstance()  {
        if (ventanaPrincipalDocenteController == null){
            ventanaPrincipalDocenteController = new VentanaPrincipalDocenteController();
        }
        return ventanaPrincipalDocenteController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UQuizzes uQuizzes = UQuizzes.getInstance();
        Connection conn = new ConexionOracle().conectar();

        try {
            String sql = "select nombre , apellido from docente where iddocente = ?";
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
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/panelInicioDocente.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);

        }
        catch (Exception e) {
        }



    }

    public void cerrarSesion(javafx.event.ActionEvent actionEvent) {

        try {
            UQuizzes uQuizzes = UQuizzes.getInstance();
            uQuizzes.setUsuarioEnSesion(null);

            // 1. Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Login/login.fxml"));
            Parent root = loader.load();

            // 2. Obtener el Stage actual desde el botón que activó el eventos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

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

    public void irPanelCrearPregunta(ActionEvent actionEvent) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/panelCrearPregunta.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void irPanelInicio(ActionEvent actionEvent) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/panelInicioDocente.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);
        }
        catch (Exception e) {
        }
    }

    public void irPanelCrearExamen(ActionEvent actionEvent) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/panelCrearQuiz.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);
        }
        catch (Exception e) {
        }
    }


    @FXML
    void informesEvent(ActionEvent event) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/estadisticasExamenesPresentados.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarContenido(Parent nuevoPanel) {
        this.contenedorCambiante.getChildren().clear();
        this.contenedorCambiante.getChildren().add(nuevoPanel);
    }


}
