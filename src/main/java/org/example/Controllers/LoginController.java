package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void iniciarSesion(ActionEvent actionEvent) throws SQLException {
        String email = emailField.getText();
        String contrasenia = passwordField.getText();

        if (email.isEmpty() || contrasenia.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor llena todos los campos");
            alert.showAndWait();
            return;
        }

        ConexionOracle conexion = new ConexionOracle();
        try (Connection connection = conexion.conectar()) {
            // Intentar como docente
            String sqlDocente = "SELECT * FROM DOCENTE WHERE CORREO = ? AND CONTRASENIA = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sqlDocente)) {
                stmt.setString(1, email.trim());
                stmt.setString(2, contrasenia.trim());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        cargarVentana("/Interfaces/Principal/ventanaPrincipalDocente.fxml", actionEvent);
                        return;
                    }
                }
            }

            // Intentar como estudiante
            String sqlEstudiante = "SELECT * FROM ESTUDIANTE WHERE CORREO = ? AND CONTRASENIA = ?";
            try (PreparedStatement stmt2 = connection.prepareStatement(sqlEstudiante)) {
                stmt2.setString(1, email.trim());
                stmt2.setString(2, contrasenia.trim());
                try (ResultSet rs2 = stmt2.executeQuery()) {
                    if (rs2.next()) {
                        cargarVentana("/Interfaces/Principal/ventanaPrincipalEstudiante.fxml", actionEvent);
                        return;
                    }
                }
            }

            // Si no se encontró en ninguna tabla
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("No se encontró el usuario.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVentana(String rutaFXML, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("UQuizzes - Home");
        stage.centerOnScreen();
        stage.show();
    }


}
