package org.example.ConexionDB;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaDocentes extends Application {

    // Configura los datos de conexión a la base de datos Oracle
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe"; // Cambia si es necesario
    private static final String USER = "SYSTEM"; // Cambia a tu usuario
    private static final String PASSWORD = "oracle"; // Cambia a tu contraseña

    private TextArea resultArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Consulta de Estudiantes");

        // Crear componentes
        Label titleLabel = new Label("Listado de Estudiantes");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(300);

        Button queryButton = new Button("Consultar Estudiantes");
        queryButton.setOnAction(e -> consultarEstudiantes());

        // Organizar componentes
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(titleLabel, queryButton, resultArea);

        // Configurar escena
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void consultarEstudiantes() {
        resultArea.clear();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            resultArea.appendText("Conexión exitosa a la base de datos Oracle\n\n");

            // Primero, verificamos si la tabla ESTUDIANTE existe
            boolean tablaExiste = verificarTablaEstudiante(conn);

            if (!tablaExiste) {
                resultArea.appendText("La tabla ESTUDIANTE no existe o no es accesible.\n");
                return;
            }

            // Consultar todos los estudiantes
            String sql = "SELECT nombre FROM docente";


            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {


                if (!rs.isBeforeFirst()) {
                    resultArea.appendText("No se encontraron estudiantes en la tabla.\n");
                } else {
                    resultArea.appendText("Estudiantes encontrados:\n\n");
                    while (rs.next()) {
                        // Aquí obtenemos el nombre (ajusta según la estructura real de tu tabla)
                        String nombre = rs.getString("NOMBRE");

                        // Si hay más columnas que quieras mostrar:
                        // int id = rs.getInt("ID");
                        // String apellido = rs.getString("APELLIDO");

                        resultArea.appendText("- " + nombre + "\n");
                    }
                }
            }

        } catch (SQLException e) {
            resultArea.appendText("Error al conectar con la base de datos: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private boolean verificarTablaEstudiante(Connection conn) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, USER, "Docente", new String[]{"TABLE"});
        return rs.next(); // Devuelve true si la tabla existe
    }

    public static void main(String[] args) {
        launch(args);
    }
}