package org.example.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML
            Parent root = FXMLLoader.load(getClass().getResource("/Interfaces/Principal/ventanaPrincipal.fxml"));

            // Crear la escena
            Scene scene = new Scene(root);

            // Configurar el escenario (ventana)
            primaryStage.setTitle("UQuizzes - Sistema de Gestión de Exámenes");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(600);

            // Mostrar la ventana
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar el archivo FXML: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}