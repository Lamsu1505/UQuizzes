package org.example.Controllers.Paneles.Docente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.Docente.EstudianteExamenInfo;
import org.example.Model.Docente.ExamenDTO;
import org.example.Model.Docente.GrupoDTO;
import org.example.Model.Docente.MateriaDTO;
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

public class EstadisticasExamenesPresentadosController implements Initializable {

    @FXML
    private TableView<EstudianteExamenInfo> TableViewReporte;


    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnFecha;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> columnHoraInicio;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> columnCodigo;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnPrimerNombre;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnApellido;

    @FXML
    private TableColumn<EstudianteExamenInfo, Double> ColumnPuntaje;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnTiempoTomado;

    @FXML
    private TableColumn<?,?> ColumnReporte;

    @FXML
    private ComboBox<String> comboBoxMateria;

    @FXML
    private ComboBox<String> comboBoxExamen;

    @FXML
    private ComboBox<String> comboBoxGrupos;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;



    private UQuizzes uQuizzes = UQuizzes.getInstance();
    private String idMateriaSeleccionada;
    private String idGrupoSeleccionado;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            cargarMaterias();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        columnHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ColumnPrimerNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        ColumnPuntaje.setCellValueFactory(new PropertyValueFactory<>("notaFinal"));
        ColumnTiempoTomado.setCellValueFactory(new PropertyValueFactory<>("tiempoTomado"));
    }


    private void cargarMaterias() throws SQLException {
        ObservableList<String> materias = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getMateriasDocente(uQuizzes.getUsuarioEnSesion());

        for (int i = 0; i < listaSQL.size(); i++) {
            String nombreMateria = listaSQL.get(i).get("NOMBRE_MATERIA").toString();
            materias.add(nombreMateria);
            System.out.println(nombreMateria);
        }

        comboBoxMateria.setItems(materias);
    }

    @FXML
    void homeEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Ventanas/ventanaInicioDocente.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchEvent(ActionEvent event) {

    }

    @FXML
    void searchFieldEvent(ActionEvent event) {

    }

    @FXML
    void cargarTabla(ActionEvent event) {
        String nombreExamenSeleccionado = comboBoxExamen.getValue();
        List<Map<String, Object>> listaExamenes = uQuizzes.obtenerEstadisticasExamenesPresentados(nombreExamenSeleccionado , idGrupoSeleccionado);
        ObservableList<EstudianteExamenInfo> listaEstudiantes = FXCollections.observableArrayList();
        for (Map<String, Object> fila : listaExamenes) {
            String fechaInicio = fila.get("FECHAINICIO").toString();
            String horaInicio = fila.get("HORAINICIO").toString();
            String codigo = fila.get("CODIGO").toString();
            String nombre = fila.get("NOMBRE").toString();
            String apellido = fila.get("APELLIDO").toString();
            double notaFinal = Double.parseDouble(fila.get("NOTAFINAL").toString());
            double tiempoTomado =  Double.parseDouble(fila.get("TIEMPOTOMADO").toString());

            EstudianteExamenInfo infoEstudiante = new EstudianteExamenInfo(fechaInicio, horaInicio, codigo, nombre, apellido, notaFinal, tiempoTomado);
            listaEstudiantes.add(infoEstudiante);
        }
        TableViewReporte.setItems(listaEstudiantes);
    }

    @FXML
    void seleccionarGrupo(ActionEvent event) {
        System.out.println("entro a seleccionar grupo");
        String nombreGrupoSeleccionada = comboBoxGrupos.getValue();
        if (nombreGrupoSeleccionada != null) {

            ConexionOracle conexion = new ConexionOracle();

            if (conexion == null) {
                mostrarAlerta("Error de conexion", "Error de conexión con la base de datos.");
                return;
            }

            String sql = "select idgrupo " +
                    "from grupo where nombre = '" + nombreGrupoSeleccionada + "'";

            try (Connection connection = conexion.conectar();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        idGrupoSeleccionado = rs.getString("IDGRUPO");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                cargarExamenes();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarExamenes() throws SQLException {
        ObservableList<String> examenes = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.get_examen_by_grupo(idGrupoSeleccionado);

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreGrupo = listaSQL.get(i).get(("NOMBRE").toString()).toString();
            examenes.add(nombreGrupo);
        }
        comboBoxExamen.setItems(examenes);


    }

    @FXML
    void seleccionarMateria(ActionEvent event) {
        String nombreMateriaSeleccionada = comboBoxMateria.getValue();
        if (nombreMateriaSeleccionada != null) {

            ConexionOracle conexion = new ConexionOracle();

            if (conexion == null) {
                mostrarAlerta("Error de conexion", "Error de conexión con la base de datos.");
                return;
            }

            String sql = "select idmateria " +
                    "from materia where nombre = '" + nombreMateriaSeleccionada + "'";

            try (Connection connection = conexion.conectar();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        idMateriaSeleccionada = rs.getString("IDMATERIA");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                cargarGrupos();
            }catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error" , "Error al cargar grupos en crearPreguntaController: " + e.getMessage());
            }
        }
    }

    private void cargarGrupos() throws SQLException {
        ObservableList<String> grupos = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getGruposDocenteByMateria(uQuizzes.getUsuarioEnSesion() , idMateriaSeleccionada);

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreGrupo = listaSQL.get(i).get("NOMBRE_GRUPO").toString();
            grupos.add(nombreGrupo);
        }
        comboBoxGrupos.setItems(grupos);


    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
