package org.example.Controllers.Paneles.Docente;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class CrearQuizController implements Initializable {

    @FXML
    private Label tituloLabel;

    @FXML
    private TextField nombreQuizTextField;

    @FXML
    private ComboBox<String> materiaComboBox;

    @FXML
    private ComboBox<String> unidadComboBox;

    @FXML
    private TextField fechaInicioTextField;

    @FXML
    private TextField fechaFinTextField;

    @FXML
    private TextField horaTextField;

    @FXML
    private TextField descripcionTextField;

    @FXML
    private TextField tiempoTextField;

    @FXML
    private TextField pesoMateriaTextField;

    @FXML
    private TextField cantidadPreguntasTextField;

    @FXML
    private ListView<TemaCheck> temasListView;

    @FXML
    private ComboBox<String> grupoComboBox;

    @FXML
    private ComboBox<String> comboBoxAutomaticoManual;

    @FXML
    private TextField cantidadPreguntasBancoTextField;

    @FXML
    private TextField fechaLimiteTextField;

    @FXML
    private TextField horaLimiteTextField;



    private ObservableList<TemaCheck> temasData = FXCollections.observableArrayList();

    private String idMateriaSeleccionada;
    private String idUnidadSeleccionada;
    private String idGrupoSeleccionado;

    List<TemaCheck> temasSeleccionados = new ArrayList<>();

    private UQuizzes uQuizzes = UQuizzes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar componentes
        configurarListView();

        ObservableList<String> automaticoManual = FXCollections.observableArrayList();
        automaticoManual.add("Automatico");
        automaticoManual.add("Manual");
        comboBoxAutomaticoManual.setItems(automaticoManual);

        try {
            cargarMaterias();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Listener para cargar unidades cuando se selecciona una materia
        materiaComboBox.setOnAction(event -> {
            unidadComboBox.getSelectionModel().clearSelection();
            unidadComboBox.getItems().clear();
            temasData.clear();
            temasListView.getItems().clear();


            String nombreMateriaSeleccionada = materiaComboBox.getValue();
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
                    cargarUnidades();
                } catch (SQLException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error" , "Error al cargar grupos en crearPreguntaController: " + e.getMessage());
                }
            }
        });

        // Listener para cargar temas cuando se selecciona una unidad
        unidadComboBox.setOnAction(event -> {
            temasListView.getItems().clear();
            try{
                String nombreUnidadSeleccionada = unidadComboBox.getValue();
                if (nombreUnidadSeleccionada != null) {
                    ConexionOracle conexion = new ConexionOracle();
                    if (conexion == null) {
                        mostrarAlerta("Error" , "Error de conexión con la base de datos.");
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
                    cargarTemas();

                } catch (SQLException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error" , "Error al cargar temas en crearPreguntaController: " + e.getMessage());
                }


            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
    }

    private void configurarListView() {
        // Configurar el ListView para usar CheckBoxes
        temasListView.setCellFactory(CheckBoxListCell.forListView(TemaCheck::seleccionadoProperty));
        // Asignar los datos al ListView
        //temasListView.setItems(temasData);
    }

    private void cargarMaterias() throws SQLException {
        ObservableList<String> materias = FXCollections.observableArrayList();
        List<Map<String, Object>> listaSQL = uQuizzes.getMateriasDocente(uQuizzes.getUsuarioEnSesion());

        for (int i = 0; i < listaSQL.size(); i++) {
            String nombreMateria = listaSQL.get(i).get("NOMBRE_MATERIA").toString();
            materias.add(nombreMateria);
            System.out.println(nombreMateria);
        }

        materiaComboBox.setItems(materias);
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

    private void cargarTemas() throws SQLException {
        temasData.clear();
        List<Map<String, Object>> listaSQL = uQuizzes.getTemasDocenteByUnidad(idUnidadSeleccionada);

        System.out.println("la lista tiene " + listaSQL.size() + " elementos");

        for (int i = 0; i < listaSQL.size(); i++) {
            String nombreTema = listaSQL.get(i).get("NOMBRE").toString();
            int idTema = Integer.parseInt(listaSQL.get(i).get("ID_TEMA").toString());
            temasData.add(new TemaCheck(idTema, nombreTema, false));
        }

        temasListView.setItems(temasData);

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

    @FXML
    private void cancelarEvent() {
            limpiarCampos();
    }

    @FXML
    private void siguienteEvent() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        // Obtener temas seleccionados
        for (TemaCheck tema : temasData) {
            if (tema.isSeleccionado()) {
                temasSeleccionados.add(tema);
            }
        }

        if (temasSeleccionados.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar al menos un tema.");
            return;
        }

        try{

            int idDocente = Integer.parseInt(uQuizzes.getUsuarioEnSesion());
            int idGrupo = getIdGrupoSeleccionado();
            int idMateria = Integer.parseInt(idMateriaSeleccionada);
            String nombreQuiz = nombreQuizTextField.getText();
            String fechaInicio = fechaInicioTextField.getText();
            int cantidadPreguntas = Integer.parseInt(cantidadPreguntasTextField.getText());
            String hora = horaTextField.getText();
            String descripcion = descripcionTextField.getText();
            int pesoMateria = Integer.parseInt(pesoMateriaTextField.getText());
            double notaMinimaPasar=3.0;
            int cantidadPreguntasBanco = Integer.parseInt(cantidadPreguntasBancoTextField.getText());
            String fechaFin = fechaLimiteTextField.getText();
            String horaLimite = horaLimiteTextField.getText();

            String tieneTiempo;
            int tiempo;
            if(tiempoTextField.getText().isEmpty()){
                tiempo = 0;
                tieneTiempo="N";
            }
            else{
                tiempo = Integer.parseInt(tiempoTextField.getText());
                tieneTiempo="S";
            }

            //crea el examen sin preguntas
            int idExamenCreado= uQuizzes.crearQuiz(idDocente, idGrupo, idMateria, nombreQuiz, fechaInicio, cantidadPreguntas, tiempo , hora , descripcion , pesoMateria , tieneTiempo , notaMinimaPasar , fechaFin , horaLimite , cantidadPreguntasBanco);

            if(idExamenCreado != 0){
                mostrarInfo("Éxito", "El quiz ha sido creado exitosamente.");

                //Crea el banco
                System.out.println("la cantidad de preguntasBanco es "+cantidadPreguntasBanco);
                int idBancoCreado = uQuizzes.crearBancoPreguntas((idExamenCreado + 1), cantidadPreguntasBanco);
                if(idBancoCreado != 0){
                    mostrarInfo("Éxito", "El banco de preguntas ha sido creado exitosamente.");

                    ArrayList<Integer> listaIdTemasSeleccionado= new ArrayList<>();
                    for (TemaCheck tema : temasSeleccionados) {
                        listaIdTemasSeleccionado.add(tema.getId());
                        System.out.println("Tema agregado a la lista " + tema.getId());
                    }


                    if (uQuizzes.agregarPreguntasAlBanco(idBancoCreado + 1, listaIdTemasSeleccionado) > 0) {
                        mostrarInfo("Éxito", "Preguntas añadidas al banco");

                        limpiarCampos();
                    }

                }
                else{
                    mostrarAlerta("Error", "No se pudo crear el banco de preguntas.");
                }
            } else {
                mostrarAlerta("Error", "No se pudo crear el quiz.");
            }
        }catch (Exception e){
            mostrarAlerta("Error", "Error al crear el quiz: " + e.getMessage());
            e.printStackTrace();
        }


        // Guardar datos y proceder a la siguiente pantalla
        //guardarQuiz(temasSeleccionados);
    }

    private void limpiarCampos() {
        descripcionTextField.setText("");
        cantidadPreguntasTextField.setText("");
        cantidadPreguntasBancoTextField.setText("");
        tiempoTextField.setText("");
        fechaInicioTextField.setText("");
        fechaLimiteTextField.setText("");
        horaTextField.setText("");
        horaLimiteTextField.setText("");
        nombreQuizTextField.setText("");
        unidadComboBox.getSelectionModel().clearSelection();
        unidadComboBox.getItems().clear();
        temasData.clear();
        temasListView.getItems().clear();
        grupoComboBox.getSelectionModel().clearSelection();
        grupoComboBox.getItems().clear();
        materiaComboBox.getSelectionModel().clearSelection();
        materiaComboBox.getItems().clear();
        temasSeleccionados.clear();
    }

    private int getIdGrupoSeleccionado() {

        String nombreGrupoSeleccionado = grupoComboBox.getValue();

        ConexionOracle conexion = new ConexionOracle();

        if (conexion == null) {
            mostrarAlerta("Error de conexion", "Error de conexión con la base de datos.");
            return 0;
        }

        String sql = "select idGrupo " +
                "from GRUPO where nombre = '" + nombreGrupoSeleccionado + "'";

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
        return Integer.parseInt(idGrupoSeleccionado);
    }

    private boolean validarCampos() {
        // Implementar validación de campos
        if (nombreQuizTextField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un nombre para el quiz.");
            return false;
        }

        if (materiaComboBox.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una materia.");
            return false;
        }

        if (unidadComboBox.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una unidad.");
            return false;
        }

        if (fechaInicioTextField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una fecha de inicio.");
            return false;
        }
        if(pesoMateriaTextField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un peso de materia.");
            return false;
        }

        if(cantidadPreguntasTextField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una cantidad de preguntas.");
            return false;
        }

        if(cantidadPreguntasBancoTextField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una cantidad de preguntas en el banco.");
            return false;
        }

        // Validar que tiempo sea un número
        try {
            Integer.parseInt(tiempoTextField.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El tiempo límite debe ser un número.");
            return false;
        }

        // Validar que peso materia sea un número
        if (!pesoMateriaTextField.getText().isEmpty()) {
            try {
                Double.parseDouble(pesoMateriaTextField.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "El peso de la materia debe ser un número.");
                return false;
            }
        }

        // Validar que cantidad de preguntas sea un número
        if (!cantidadPreguntasTextField.getText().isEmpty()) {
            try {
                Integer.parseInt(cantidadPreguntasTextField.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "La cantidad de preguntas debe ser un número.");
                return false;
            }
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public class TemaCheck {
        private final IntegerProperty id;
        private final StringProperty nombre;
        private final BooleanProperty seleccionado;

        public TemaCheck(int id, String nombre, boolean seleccionado) {
            this.id = new SimpleIntegerProperty(id);
            this.nombre = new SimpleStringProperty(nombre);
            this.seleccionado = new SimpleBooleanProperty(seleccionado);
        }

        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public IntegerProperty idProperty() {
            return id;
        }

        public String getNombre() {
            return nombre.get();
        }

        public void setNombre(String nombre) {
            this.nombre.set(nombre);
        }

        public StringProperty nombreProperty() {
            return nombre;
        }

        public boolean isSeleccionado() {
            return seleccionado.get();
        }

        public void setSeleccionado(boolean seleccionado) {
            this.seleccionado.set(seleccionado);
        }

        public BooleanProperty seleccionadoProperty() {
            return seleccionado;
        }

        @Override
        public String toString() {
            return nombre.get();
        }
    }
}