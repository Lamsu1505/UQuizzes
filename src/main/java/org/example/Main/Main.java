package org.example.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException, IOException {

        String[] archivosSQL = {
                "/sentenciasSQL/funciones/getTemasDocenteByUnidad.sql",
                "/sentenciasSQL/funciones/getUnidadesDocenteByMateria.sql",
                "/sentenciasSQL/funciones/iniciarSesionDocente.sql",
                "/sentenciasSQL/funciones/obtenerExamenesDocente.sql",
                "/sentenciasSQL/funciones/obtenerGruposDocenteByMateria.sql",
                "/sentenciasSQL/funciones/obtenerMateriasDocente.sql"
        };

        for (String archivo : archivosSQL) {
            InputStream input = getClass().getResourceAsStream(archivo);
            if (input != null) {
                ConexionOracle.ejecutarSQLDesdeArchivo(input);
            } else {
                System.err.println("No se encontró el archivo: " + archivo);
            }
        }




       try{
           // Cargar el archivo FXML
           Parent root = FXMLLoader.load(getClass().getResource("/Interfaces/Login/login.fxml"));

           // Crear la escena
           Scene scene = new Scene(root);

           // Configurar el escenario (ventana)
           primaryStage.setTitle("UQuizzes - Iniciar sesion");
           primaryStage.setScene(scene);

           // Mostrar la ventana
           primaryStage.show();
       } catch (IOException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }


    }

    public static void main(String[] args) {
        launch(args);
    }
}