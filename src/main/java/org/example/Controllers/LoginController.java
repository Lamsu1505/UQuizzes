package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
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


    }


}
