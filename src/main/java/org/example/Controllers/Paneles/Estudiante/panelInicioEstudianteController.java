package org.example.Controllers.Paneles.Estudiante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class panelInicioEstudianteController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label lblExamenesRecientes;

    @FXML
    private Label lblBienvenido;

    @FXML private VBox VBoxContenedorExamenes;

    @FXML private ComboBox<String> comboBoxMateria;

    UQuizzes uQuizzes = UQuizzes.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = new ConexionOracle().conectar();
        System.out.println("se iniciando el panel de inicio estudiante");
        //cargarMateriasComboBox();

        try {
            String sql = "select nombre , apellido from estudiante where idEstudiante = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1 , uQuizzes.getUsuarioEnSesion());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                lblBienvenido.setText("Buen dia " + nombre + " " + apellido + " (Estudiante)");
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

    private void cargarMateriasComboBox() throws SQLException {
        ObservableList<String> materias = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getMateriasEstudiante(uQuizzes.getUsuarioEnSesion());

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreMateria = listaSQL.get(i).get("NOMBREMATERIA").toString();
            materias.add(nombreMateria);
            System.out.println("materia " + nombreMateria);
        }

        comboBoxMateria.setItems(materias);
    }


    private void cargarExamenes() throws SQLException {
        List<Map<String, Object>> lista = uQuizzes.getExamenesEstudianteSQL();
        boolean hayExamenesDisponibles = false;

        try {
            for (Map<String, Object> fila : lista) {

                if(presentoExamen(fila)) {
                    continue; // Si ya presentó el examen, no lo agregues a la lista
                }

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/Interfaces/Paneles/Estudiante/s/examenPaginaPrincipal.fxml")
                );
                Node examenNode = loader.load();

                ExamenPaginaPrincipalController ctrl = loader.getController();

                ctrl.setNombreExamen("" + fila.get("NOMBRE_EXAMEN"));
                ctrl.setMateria("" + fila.get("NOMBRE_MATERIA"));
                ctrl.setGrupo("" + fila.get("NOMBRE_GRUPO"));
                ctrl.setFecha(("" + fila.get("FECHA")).substring(0, 10));
                ctrl.setHora(("" + fila.get("HORA")).toString());

                VBoxContenedorExamenes.getChildren().add(examenNode);
                hayExamenesDisponibles = true;
            }

            if (!hayExamenesDisponibles) {
                Label mensaje = new Label("No tienes exámenes disponibles.");
                VBoxContenedorExamenes.getChildren().add(mensaje);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo FXML: " + e.getMessage());
            e.printStackTrace();
        }

    }


    private boolean presentoExamen(Map<String, Object> examen) throws SQLException {
        int idExamen = Integer.parseInt(examen.get("IDEXAMEN").toString()); // Asegúrate que la clave sea IDEXAMEN (todo mayúsculas)
        String idEstudiante = uQuizzes.getUsuarioEnSesion();

        String sql = "SELECT COUNT(*) " +
                "FROM examen e " +
                "JOIN solucionexamenestudiante se ON e.idExamen = se.examen_idexamen " +
                "WHERE e.idExamen = ? AND se.estudiante_idestudiante = ?";

        ConexionOracle conexion = new ConexionOracle();

        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idExamen);
            stmt.setString(2, idEstudiante);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
