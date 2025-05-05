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
    private ComboBox<String> grupoComboBox;

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

    private String nombreMateriaSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idUsuarioEnSesion = UQuizzes.getInstance().getUsuarioEnSesion();

        try {

            cargarTipoPregunta();
            cargarMaterias();

            //esto es para actualizar los grupos segun la materia que elija
            materiaComboBox.setOnAction(event -> {
                String materiaSeleccionada = materiaComboBox.getValue();
                if (materiaSeleccionada != null) {
                    nombreMateriaSeleccionada = materiaSeleccionada;
                    try {
                        cargarGrupos(materiaSeleccionada);
                        cargarUnidades(materiaSeleccionada);
                    } catch (SQLException e) {
                        mostrarAlerta("Error al cargar grupos: " + e.getMessage());
                    }
                }
            });

        } catch (SQLException e) {
            mostrarAlerta("Error al cargar info " + e.getMessage());
        }
    }

    private void cargarUnidades(String materiaSeleccionada) throws SQLException {

        ObservableList<String> unidades = FXCollections.observableArrayList();
        ConexionOracle conexion = new ConexionOracle();

        if (conexion == null) {
            mostrarAlerta("Error de conexión con la base de datos.");
            return;
        }

        String sql = "select * from unidad u " +
                "JOIN materia m ON u.materia_idmateria = m.idmateria " +
                "where m.nombre = ?";

        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, materiaSeleccionada);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    unidades.add(rs.getString("nombre"));
                }
            }
        }

        unidadComboBox.setItems(unidades);
    }

    private void cargarTipoPregunta() {

        ObservableList<String> tipoPreguntas = FXCollections.observableArrayList();
        ConexionOracle conexion = new ConexionOracle();

        if (conexion == null) {
            mostrarAlerta("Error de conexión con la base de datos.");
            return;
        }

        String sql = "select * " +
                "from tipopregunta";

        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tipoPreguntas.add(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tipoPreguntaComboBox.setItems(tipoPreguntas);
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


    private void cargarGrupos(String nombreMateriaSeleccionada) throws SQLException {
        ObservableList<String> grupos = FXCollections.observableArrayList();
        ConexionOracle conexion = new ConexionOracle();

        if (conexion == null) {
            mostrarAlerta("Error de conexión con la base de datos.");
            return;
        }

        String sql = "SELECT g.nombre AS nombreGrupo, m.nombre AS nombreMateria " +
                "FROM Docente d " +
                "JOIN Grupo g ON d.iddocente = g.docente_iddocente " +
                "JOIN Materia m ON g.materia_idmateria = m.idmateria " +
                "WHERE d.iddocente = ? AND m.nombre = ? " +
                "ORDER BY m.nombre";


        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, idUsuarioEnSesion);
            stmt.setString(2, nombreMateriaSeleccionada);


            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    grupos.add(rs.getString("nombreGrupo"));
                }
            }
        }

        grupoComboBox.setItems(grupos);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
