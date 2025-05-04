package org.example.Controllers.Paneles.Docente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.Model.UQuizzes;

import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class panelInicioDocenteController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label lblExamenesRecientes;

    @FXML
    private Label lblBienvenido;

    @FXML private VBox VBoxContenedorExamenes;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UQuizzes uQuizzes = UQuizzes.getInstance();

        try {
            ResultSet rs = uQuizzes.getUsuarioEnSesionSQL();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                lblBienvenido.setText("Buen dia " + nombre + " " + apellido + " (Docente)");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("en panelInicioDocenteController voy a cargar los exmanes del usuario");
        cargarExamenes();
    }

    private void cargarExamenes() {

        System.out.println("estoy en cargar examenes");

        UQuizzes uQuizzes = UQuizzes.getInstance();
        try (ResultSet rs = uQuizzes.getExamenesDocenteSQL()) {
            VBoxContenedorExamenes.getChildren().clear();

            while (rs.next()) {

                // 1) Carga el FXML de cada examen
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/Interfaces/Paneles/Docente/examenPaginaPrincipal.fxml")
                );
                Node examenNode = loader.load();

                // 2) Obtén el controller y pásale los datos
                ExamenPaginaPrincipalController ctrl = loader.getController();
                ctrl.setNombreExamen(rs.getString("nombre_examen"));
                ctrl.setMateria      (rs.getString("nombre_materia"));
                ctrl.setGrupo        (rs.getString("nombre_grupo"));
                ctrl.setFecha        (rs.getDate  ("fecha"));      // java.sql.Date
                ctrl.setHora         (rs.getTime  ("hora"));       // java.sql.Time

                // 3) Añádelo al VBox
                VBoxContenedorExamenes.getChildren().add(examenNode);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
