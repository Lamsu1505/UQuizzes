package org.example.Controllers.Paneles.Docente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import oracle.sql.TIMESTAMPTZ;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class panelInicioDocenteController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label lblExamenesRecientes;

    @FXML
    private Label lblBienvenido;

    @FXML private VBox VBoxContenedorExamenes;
    UQuizzes uQuizzes = UQuizzes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Connection conn = new ConexionOracle().conectar();

        try {
            String sql = "select nombre , apellido from docente where iddocente = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1 , uQuizzes.getUsuarioEnSesion());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                lblBienvenido.setText("Buen dia " + nombre + " " + apellido + " (Docente)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            cargarExamenes();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarExamenes() throws SQLException {
        List<Map<String, Object>> lista = uQuizzes.getExamenesDocenteSQL();
        try {
            for (Map<String,Object> fila : lista) {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/Interfaces/Paneles/Docente/examenPaginaPrincipal.fxml")
                );
                Node examenNode = loader.load();

                // 2) Obtén el controller y pásale los datos
                ExamenPaginaPrincipalController ctrl = loader.getController();

                ctrl.setIdExamen(Integer.parseInt(fila.get("IDEXAMEN").toString()));
                ctrl.setNombreExamen("" + fila.get("NOMBRE_EXAMEN"));
                ctrl.setMateria      ("" + fila.get("NOMBRE_MATERIA"));
                ctrl.setGrupo        ("" + fila.get("NOMBRE_GRUPO"));
                ctrl.setFecha(("" + fila.get("FECHA")).substring(0, 10));
                ctrl.setHora(("" + fila.get("HORA")).toString());

                // 3) Añádelo al VBox
                VBoxContenedorExamenes.getChildren().add(examenNode);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
