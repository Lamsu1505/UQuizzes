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
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.io.IOException;
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
    private String idTemaSeleccionado;
    private String idTipoPreguntaSeleccionado;
    private String idNivelPreguntaSeleccionado;


    private UQuizzes uQuizzes = UQuizzes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cargarTipoPregunta();
            cargarNiveles();
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
                        cargarGrupos();
                        cargarUnidades();
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

            temaComboBox.setOnAction(event -> {
                try{
                    String nombreTemaSeleccionado = temaComboBox.getValue();
                    if (nombreTemaSeleccionado != null) {
                        ConexionOracle conexion = new ConexionOracle();
                        if (conexion == null) {
                            mostrarAlerta("Error de conexión con la base de datos.");
                            return;
                        }

                        String sql = "select idtema " +
                                "from tema where nombre = '" + nombreTemaSeleccionado + "'";

                        try (Connection connection = conexion.conectar();
                             PreparedStatement stmt = connection.prepareStatement(sql)) {

                            try (ResultSet rs = stmt.executeQuery()) {
                                while (rs.next()) {
                                    idTemaSeleccionado = rs.getString("IDTEMA");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }


                }
                catch(Exception e){
                    e.printStackTrace();
                }
            });

            tipoPreguntaComboBox.setOnAction(event -> {
                try{
                    String nombreTipoSeleccionado = tipoPreguntaComboBox.getValue();
                    if (nombreTipoSeleccionado != null) {
                        ConexionOracle conexion = new ConexionOracle();
                        if (conexion == null) {
                            mostrarAlerta("Error de conexión con la base de datos.");
                            return;
                        }

                        String sql = "select IDTIPOPREGUNTA " +
                                "from tipopregunta where nombre = '" + nombreTipoSeleccionado + "'";

                        try (Connection connection = conexion.conectar();
                             PreparedStatement stmt = connection.prepareStatement(sql)) {

                            try (ResultSet rs = stmt.executeQuery()) {
                                while (rs.next()) {
                                    idTipoPreguntaSeleccionado = rs.getString("IDTIPOPREGUNTA");
                                    System.out.println("se seteo el idTipoPreguntaSeleccionado: " + idTipoPreguntaSeleccionado);
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }


                }
                catch(Exception e){
                    e.printStackTrace();
                }
            });

            nivelComboBox.setOnAction(event -> {
                try{
                    String nivelSeleccionado = nivelComboBox.getValue();
                    if (nivelSeleccionado != null) {
                        ConexionOracle conexion = new ConexionOracle();
                        if (conexion == null) {
                            mostrarAlerta("Error de conexión con la base de datos.");
                            return;
                        }

                        String sql = "select idnivelpregunta " +
                                "from nivelPregunta where nivel = '" + nivelSeleccionado + "'";

                        try (Connection connection = conexion.conectar();
                             PreparedStatement stmt = connection.prepareStatement(sql)) {

                            try (ResultSet rs = stmt.executeQuery()) {
                                while (rs.next()) {
                                    idNivelPreguntaSeleccionado = rs.getString("idnivelpregunta");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
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

    private void cargarNiveles() {
        ObservableList<String> niveles = FXCollections.observableArrayList();
        ConexionOracle conexion = new ConexionOracle();

        if (conexion == null) {
            mostrarAlerta("Error de conexión con la base de datos.");
            return;
        }

        String sql = "select * " +
                "from nivelPregunta";

        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    niveles.add(rs.getString("nivel"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nivelComboBox.setItems(niveles);
    }

    private void cargarTemas(String idUnidadSeleccionada) throws SQLException {
        ObservableList<String> temas = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getTemasDocenteByUnidad(idUnidadSeleccionada);

        for(int i =0 ; i < listaSQL.size() ; i++){
            String nombreGrupo = listaSQL.get(i).get("NOMBRE").toString();
            temas.add(nombreGrupo);
        }
        temaComboBox.setItems(temas);
    }


    private void cargarUnidades() throws SQLException {
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



    private void cargarGrupos() throws SQLException {
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


    public void crearPregunta(ActionEvent actionEvent) throws IOException {
        if (validarCamposObligatorios()) {
            if (subpreguntaCheckBox.isSelected()) {
                // TODO: lógica si es subpregunta
            } else {
                boolean isPublica = publicaCheckBox.isSelected();
                String idPreguntaPadre = null;
                String enunciado = enunciadoTextArea.getText();
                String peso = pesoTextField.getText();
                String tiempoPregunta = tiempoTextField.getText();

                int idPregunta = uQuizzes.crearPregunta(idTemaSeleccionado , idTipoPreguntaSeleccionado , idPreguntaPadre , idNivelPreguntaSeleccionado , isPublica , enunciado , peso , tiempoPregunta);
                if(idPregunta != 0 ){
                    mostrarAlerta("Pregunta creada con éxito");

                    uQuizzes.setIdPreguntaRecienCreada(idPregunta + 1);
                    limpiarCampos();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/contenedorGeneralOpciones.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("UQuizzes - Opciones de respuesta");
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();

                } else {
                    mostrarAlerta("Algo falló al crear la pregunta.");
                }
            }
        }
    }

    public void cancelar(ActionEvent actionEvent) {

        limpiarCampos();

    }

    private boolean validarCamposObligatorios() {
        // Implementar validación de campos obligatorios
        // Por ejemplo:
        if (tipoPreguntaComboBox.getValue() == null) {
            mostrarAlerta("Debe seleccionar un tipo de pregunta.");
            return false;
        }

        if (enunciadoTextArea.getText().trim().isEmpty()) {
            mostrarAlerta("Debe ingresar un enunciado.");
            return false;
        }

        if (materiaComboBox.getValue() == null) {
            mostrarAlerta("Debe seleccionar una materia");
            return false;
        }

        if (unidadComboBox.getValue() == null) {
            mostrarAlerta("Debe seleccionar una unidad");
            return false;
        }

        if (temaComboBox.getValue() == null) {
            mostrarAlerta("Debe seleccionar un tema");
            return false;
        }

        if (grupoComboBox.getValue() == null) {
            mostrarAlerta("Debe seleccionar un grupo");
            return false;
        }

        if (pesoTextField.getText().equals("")) {
            mostrarAlerta("Debe añadir un peso");
            return false;
        }

        // Añadir otras validaciones según sea necesario

        return true;
    }

    private void limpiarCampos() {
        enunciadoTextArea.clear();
        tiempoTextField.clear();
        pesoTextField.clear();

        // Limpiar ComboBox (quitar selección)
        tipoPreguntaComboBox.getSelectionModel().clearSelection();
        grupoComboBox.getSelectionModel().clearSelection();
        materiaComboBox.getSelectionModel().clearSelection();
        unidadComboBox.getSelectionModel().clearSelection();
        temaComboBox.getSelectionModel().clearSelection();
        nivelComboBox.getSelectionModel().clearSelection();

        // Desmarcar CheckBox
        publicaCheckBox.setSelected(false);
        subpreguntaCheckBox.setSelected(false);
    }
}
