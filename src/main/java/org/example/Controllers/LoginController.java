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
import java.sql.Statement;
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

    public void iniciarSesion(ActionEvent actionEvent) {

        String email = emailField.getText();
        String contrasenia = passwordField.getText();

        if (email.isEmpty() || contrasenia.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos Vacios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor llena todos los campos");
            alert.showAndWait();
            return;
        }

        ConexionOracle conexion = new ConexionOracle();
        Connection conection = conexion.conectar();

        String sql = "SELECT * FROM DOCENTE WHERE CORREO = ? AND CONTRASENIA = ?";

        try (
                PreparedStatement stmt = conection.prepareStatement(sql)
        ) {
            stmt.setString(1, email.trim());
            stmt.setString(2, contrasenia.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if(rs.getString("CORREO").equals(email) && rs.getString("CONTRASENIA").equals(contrasenia)) {
                    try {
                        // 1. Cargar el archivo FXML
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Principal/ventanaPrincipal.fxml"));
                        Parent root = loader.load();

                        // 2. Obtener el Stage actual desde el botón que activó el evento
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                        // 3. Crear una nueva escena y asignarla al stage
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("UQuizzes - Home");

                        stage.centerOnScreen();
                        stage.show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("No se encontró el usuario.");
                alert.showAndWait();
            }

            rs.close();
            conection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
