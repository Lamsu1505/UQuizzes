package org.example.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {


        String carpetaSQL = "/sentenciasSQL";

        try {
            // Obtener el path real en el sistema de archivos desde los recursos
            Path path = Paths.get(getClass().getResource(carpetaSQL).toURI());

            System.out.println(path);
            // Recorrer todos los archivos .sql dentro de la carpeta y subcarpetas
            List<String> archivosSQL = Files.walk(path)
                    .filter(p -> p.toString().endsWith(".sql"))
                    .map(p -> carpetaSQL + "/" + path.relativize(p).toString().replace("\\", "/"))
                    .collect(Collectors.toList());

            for (String archivo : archivosSQL) {
                InputStream input = getClass().getResourceAsStream(archivo);

                if (input != null) {
                    ConexionOracle.ejecutarSQLDesdeArchivo(input);
                } else {
                    System.err.println("No se encontró el archivo: " + archivo);
                }
            }

        } catch (SQLException | URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error cargando archivos SQL", e);
        }


        // Cargar la interfaz gráfica
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Interfaces/Login/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("UQuizzes - Iniciar sesión");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error cargando la interfaz", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}