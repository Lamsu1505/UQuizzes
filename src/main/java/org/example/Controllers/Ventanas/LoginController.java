package org.example.Controllers.Ventanas;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.ConexionDB.DAO.DocenteDAO;
import org.example.Model.UQuizzes;

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

    private DocenteDAO docenteDAO = new DocenteDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EventHandler<KeyEvent> enterHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        };

        passwordField.setOnKeyPressed(enterHandler);
        emailField.setOnKeyPressed(enterHandler);
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

        try {
            String idDocente = docenteDAO.iniciarSesion(email, contrasenia);
            if (idDocente != null) {

                UQuizzes uQuizzes = UQuizzes.getInstance();
                uQuizzes.setEsDocente(true);
                uQuizzes.setUsuarioEnSesion(idDocente);

                cargarVentana("/Interfaces/Ventanas/ventanaInicioDocente.fxml", actionEvent);
            }
            else{
                //String idEstudiante = uquizzes.iniciarSesionEstudiante();
                //TODO hacer el procedimiento para iniciar sesion de estudiante
            }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Credenciales incorrecas");
                alert.setHeaderText(null);
                alert.setContentText("Credenciales incorrectas, por favor verifique su email o su contraseña");
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
        stage.setWidth(1450);
        stage.setHeight(800);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }


}
