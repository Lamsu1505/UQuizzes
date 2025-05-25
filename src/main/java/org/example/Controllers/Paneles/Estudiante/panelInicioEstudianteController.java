package org.example.Controllers.Paneles.Estudiante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class panelInicioEstudianteController implements Initializable {

    @FXML private ScrollPane scrollPane;
    @FXML private Label lblExamenesRecientes;
    @FXML private Label lblBienvenido;
    @FXML private VBox vBoxContenedorExamenes;
    @FXML private ComboBox<String> comboBoxMateria;


    private int idMateriaSeleccionada;
    private final UQuizzes uQuizzes = UQuizzes.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1) Cargar materias en el ComboBox
        try {
            cargarMateriasComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 2) Seleccionar "Todas las materias" y disparar la primera carga
        comboBoxMateria.getSelectionModel().selectFirst();

        comboBoxMateria.setOnAction(event -> {
            String sel = comboBoxMateria.getValue();
            limpiarContenedorExamenes();

            if ("Todas las materias".equals(sel)) {
                try {
                    cargarExamenes();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                // Obtener idMateria vía parámetro
                String sql = "SELECT idmateria FROM materia WHERE nombre = ?";
                try (Connection c = new ConexionOracle().conectar();
                     PreparedStatement p = c.prepareStatement(sql)) {

                    p.setString(1, sel);
                    try (ResultSet rs = p.executeQuery()) {
                        if (rs.next()) {
                            idMateriaSeleccionada = rs.getInt("idmateria");
                        }
                    }

                    cargarExamenesByMateria(idMateriaSeleccionada);

                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 3) Cargar nombre del estudiante
        try (Connection conn = new ConexionOracle().conectar();
             PreparedStatement stm = conn.prepareStatement(
                     "SELECT nombre, apellido FROM estudiante WHERE idEstudiante = ?")) {

            stm.setString(1, uQuizzes.getUsuarioEnSesion());
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    lblBienvenido.setText(
                            String.format("Buen día %s %s (Estudiante)", nombre, apellido)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 4) Cargar exámenes iniciales
        try {

            cargarExamenes();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiarContenedorExamenes() {
        vBoxContenedorExamenes.getChildren().clear();
        Label msg = new Label("Cargando exámenes...");
        vBoxContenedorExamenes.getChildren().add(msg);
    }

    private void cargarMateriasComboBox() throws SQLException {
        ObservableList<String> materias = FXCollections.observableArrayList();
        materias.add("Todas las materias");
        List<Map<String, Object>> listaSQL =
                uQuizzes.getMateriasEstudiante(uQuizzes.getUsuarioEnSesion());

        for (Map<String, Object> fila : listaSQL) {
            materias.add(fila.get("NOMBREMATERIA").toString());
        }
        comboBoxMateria.setItems(materias);
    }

    private void cargarExamenes() throws SQLException, IOException {
        vBoxContenedorExamenes.getChildren().clear();
        List<Map<String, Object>> lista = uQuizzes.getExamenesEstudianteSQL();
        boolean hay = false;

        for (Map<String, Object> fila : lista) {


            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Interfaces/Paneles/Estudiante/s/examenPaginaPrincipal.fxml")
            );
            Node examenNode = loader.load();
            ExamenPaginaPrincipalController ctrl = loader.getController();

            ctrl.setNombreExamen(fila.get("NOMBRE_EXAMEN").toString());
            ctrl.setMateria(fila.get("NOMBRE_MATERIA").toString());
            ctrl.setGrupo(fila.get("NOMBRE_GRUPO").toString());
            ctrl.setFecha(fila.get("FECHALIMITE").toString().substring(0, 10));
            ctrl.setHora(fila.get("HORALIMITE").toString());

            vBoxContenedorExamenes.getChildren().add(examenNode);
            hay = true;

            if (presentoExamen(fila)){
                ctrl.deshabilitarBotonEmpezar();
                ctrl.setNombreExamen(fila.get("NOMBRE_EXAMEN").toString() + " (YA PRESENTADO)");
                continue;
            };
        }

        if (!hay) {
            Label msg = new Label("No tienes exámenes disponibles.");
            vBoxContenedorExamenes.getChildren().add(msg);
        }
    }

    private void cargarExamenesByMateria(int idMateria) throws SQLException, IOException {
        vBoxContenedorExamenes.getChildren().clear();
        List<Map<String, Object>> lista = uQuizzes.getExamenesEstudianteByMateria(idMateria);
        boolean hay = false;

        for (Map<String, Object> fila : lista) {
            System.out.println("IDEXAMEN: " + fila.get("nombre_examen"));
            if (presentoExamen(fila)) continue;

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Interfaces/Paneles/Estudiante/s/examenPaginaPrincipal.fxml")
            );
            Node examenNode = loader.load();
            ExamenPaginaPrincipalController ctrl = loader.getController();

            ctrl.setNombreExamen(fila.get("NOMBRE_EXAMEN").toString());
            ctrl.setMateria(fila.get("NOMBRE_MATERIA").toString());
            ctrl.setGrupo(fila.get("NOMBRE_GRUPO").toString());
            ctrl.setFecha(fila.get("FECHA").toString().substring(0, 10));
            ctrl.setHora(fila.get("HORA").toString());

            vBoxContenedorExamenes.getChildren().add(examenNode);
            hay = true;
        }

        if (!hay) {
            Label msg = new Label("No tienes exámenes disponibles para esta materia.");
            vBoxContenedorExamenes.getChildren().add(msg);
        }
    }

    private boolean presentoExamen(Map<String, Object> examen) throws SQLException {
        int idExamen = Integer.parseInt(examen.get("IDEXAMEN").toString());
        String idEstudiante = uQuizzes.getUsuarioEnSesion();

        String sql = """
            SELECT COUNT(*)
            FROM examen e
            JOIN solucionexamenestudiante se
              ON e.idExamen = se.examen_idexamen
            WHERE e.idExamen = ?
              AND se.estudiante_idestudiante = ?
            """;

        try (Connection c = new ConexionOracle().conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, idExamen);
            p.setString(2, idEstudiante);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
