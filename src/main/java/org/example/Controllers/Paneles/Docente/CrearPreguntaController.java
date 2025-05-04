package org.example.Controllers.Paneles.Docente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CrearPreguntaController implements Initializable {

    @FXML
    private ComboBox<String> tipoPreguntaComboBox;

    @FXML
    private Label subpreguntaLabel;

    @FXML
    private TextField tiempoTextField;

    @FXML
    private Label unidadLabel;

    @FXML
    private ComboBox<String> temaComboBox;

    @FXML
    private Label materiaLabel;

    @FXML
    private Label tituloLabel;

    @FXML
    private ComboBox<String> materiaComboBox;

    @FXML
    private TextArea enunciadoTextArea;

    @FXML
    private Label temaLabel;

    @FXML
    private TextField pesoTextField;

    @FXML
    private Label tipoPreguntaLabel;

    @FXML
    private Label enunciadoLabel;

    @FXML
    private ComboBox<String> unidadComboBox;

    @FXML
    private Button cancelarButton;

    @FXML
    private Button siguienteButton;

    @FXML
    private CheckBox publicaCheckBox;

    @FXML
    private CheckBox subpreguntaCheckBox;

    @FXML
    private Label tiempoLabel;

    @FXML
    private Label pesoLabel;

    @FXML
    private Label publicaLabel;

    private String idUsuarioEnSesion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idUsuarioEnSesion = UQuizzes.getInstance().getUsuarioEnSesion();

        try {
            cargarMaterias();
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar materias: " + e.getMessage());
        }
    }

    private void cargarMaterias() throws SQLException {
        ObservableList<String> materias = FXCollections.observableArrayList();
        ConexionOracle conexion = new ConexionOracle();

        if (conexion == null) {
            mostrarAlerta("Error de conexión con la base de datos.");
            return;
        }

        String sql = "SELECT m.nombre " +
                "FROM Docente d " +
                "JOIN Grupo g ON d.iddocente = g.docente_iddocente " +
                "JOIN Materia m ON g.materia_idmateria = m.idmateria " +
                "WHERE d.iddocente = ? " +
                "ORDER BY m.nombre";

        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, idUsuarioEnSesion);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materias.add(rs.getString("nombre"));
                }
            }
        }

        materiaComboBox.setItems(materias);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
