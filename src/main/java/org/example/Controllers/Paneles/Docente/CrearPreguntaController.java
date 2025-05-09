package org.example.Controllers.Paneles.Docente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Map;
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

    @FXML
    private ComboBox<String> nivelComboBox;

    @FXML
    private Label nivelLabel;

    private String idUsuarioEnSesion;

    private String idMateriaSeleccionada;
    private String idUnidadSeleccionada;


    private UQuizzes uQuizzes = UQuizzes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            cargarTipoPregunta();
            cargarMaterias();

            //esto es para actualizar los grupos segun la materia que elija
            materiaComboBox.setOnAction(event -> {
                String nombreMateriaSeleccionada = materiaComboBox.getValue();
                if (nombreMateriaSeleccionada != null) {

                    ConexionOracle conexion = new ConexionOracle();

                    if (conexion == null) {
                        mostrarAlerta("Error de conexión con la base de datos.");
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
                        cargarGrupos(idMateriaSeleccionada);
                        cargarUnidades(idMateriaSeleccionada);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        mostrarAlerta("Error al cargar grupos en crearPreguntaController: " + e.getMessage());
                    }
                }
            });


            unidadComboBox.setOnAction(event -> {
                try{
                    String nombreUnidadSeleccionada = unidadComboBox.getValue();
                    if (nombreUnidadSeleccionada != null) {
                        ConexionOracle conexion = new ConexionOracle();
                        if (conexion == null) {
                            mostrarAlerta("Error de conexión con la base de datos.");
                            return;
                        }

                        String sql = "select idunidad " +
                                "from unidad where nombre = '" + nombreUnidadSeleccionada + "'";

                        try (Connection connection = conexion.conectar();
                             PreparedStatement stmt = connection.prepareStatement(sql)) {

                            try (ResultSet rs = stmt.executeQuery()) {
                                while (rs.next()) {
                                    idUnidadSeleccionada = rs.getString("IDUNIDAD");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        cargarTemas(idUnidadSeleccionada);

                    } catch (SQLException e) {
                        e.printStackTrace();
                        mostrarAlerta("Error al cargar temas en crearPreguntaController: " + e.getMessage());
                    }


                }
                catch(Exception e){
                    e.printStackTrace();
                }
            });


        } catch (SQLException e) {
            mostrarAlerta("Error al cargar info " + e.getMessage());
        }
    }

    private void cargarTemas(String idUnidadSeleccionada) throws SQLException {
        ObservableList<String> temas = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getTemasDocenteByUnidad(idUnidadSeleccionada);

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreGrupo = listaSQL.get(i).get("NOMBRE_TEMA").toString();
            temas.add(nombreGrupo);
        }
        temaComboBox.setItems(temas);
    }


    private void cargarUnidades(String materiaSeleccionada) throws SQLException {
        ObservableList<String> unidades = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getUnidadesDocenteByMateria(idMateriaSeleccionada);

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreGrupo = listaSQL.get(i).get("NOMBRE_UNIDAD").toString();
            unidades.add(nombreGrupo);
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
        List<Map<String, Object>> listaSQL = uQuizzes.getMateriasDocente(uQuizzes.getUsuarioEnSesion());

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreMateria = listaSQL.get(i).get("NOMBRE_MATERIA").toString();
            materias.add(nombreMateria);
            System.out.println(nombreMateria);
        }

        materiaComboBox.setItems(materias);


    }



    private void cargarGrupos(String nombreMateriaSeleccionada) throws SQLException {
        ObservableList<String> grupos = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getGruposDocenteByMateria(uQuizzes.getUsuarioEnSesion() , idMateriaSeleccionada);

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreGrupo = listaSQL.get(i).get("NOMBRE_GRUPO").toString();
            grupos.add(nombreGrupo);
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
