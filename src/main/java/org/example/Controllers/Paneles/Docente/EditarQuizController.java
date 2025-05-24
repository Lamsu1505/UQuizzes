package org.example.Controllers.Paneles.Docente;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.UQuizzes;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditarQuizController implements Initializable {

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
    private ListView<CrearQuizController.TemaCheck> temasListView;

    @FXML
    private ComboBox<String> grupoComboBox;

    @FXML
    private ComboBox<String> dificultadComboBox;

    @FXML
    private ComboBox<String> comboBoxAutomaticoManual;

    @FXML
    private TextField cantidadPreguntasBancoTextField;

    @FXML
    private TextField fechaLimiteTextField;

    @FXML
    private TextField horaLimiteTextField;

    private ObservableList<CrearQuizController.TemaCheck> temasData = FXCollections.observableArrayList();

    private String idMateriaSeleccionada;
    private String idUnidadSeleccionada;
    private String idGrupoSeleccionado;

    List<CrearQuizController.TemaCheck> temasSeleccionados = new ArrayList<>();

    private UQuizzes uQuizzes = UQuizzes.getInstance();
    private int idExamen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar componentes
        configurarListView();

        ObservableList<String> dificultad = FXCollections.observableArrayList();
        dificultad.add("Facil");
        dificultad.add("Medio");
        dificultad.add("Dificil");
        dificultadComboBox.setItems(dificultad);

        ObservableList<String> automaticoManual = FXCollections.observableArrayList();
        automaticoManual.add("Automatico");
        automaticoManual.add("Manual");
        comboBoxAutomaticoManual.setItems(automaticoManual);

        grupoComboBox.setEditable(false);
        materiaComboBox.setEditable(false);
        unidadComboBox.setEditable(false);
    }

    private void configurarListView() {
        // Configurar el ListView para usar CheckBoxes
        temasListView.setCellFactory(CheckBoxListCell.forListView(CrearQuizController.TemaCheck::seleccionadoProperty));
    }

    private void cargarInformacionExamen() {
        // Cargar información del examen desde la base de datos
        List<Map<String, Object>> lista = uQuizzes.obtenerExamenById(idExamen);
        if(lista.size() > 1) {
            throw new UnsupportedOperationException("Hay mas de un examen con el mismo id");
        }
        else{
            Map<String, Object> examen = lista.get(0);
            System.out.println("Examen: " + examen.get("IDEXAMEN"));

            nombreQuizTextField.setText(examen.get("NOMBRE").toString());
            fechaInicioTextField.setText(examen.get("FECHA").toString());
            horaTextField.setText(examen.get("HORA").toString());
            descripcionTextField.setText(examen.get("DESCRIPCION").toString());
            cantidadPreguntasTextField.setText(examen.get("CANTIDADPREGUNTAS").toString());
            cantidadPreguntasBancoTextField.setText(examen.get("CANTIDADPREGUNTASBANCO").toString());
            pesoMateriaTextField.setText(examen.get("PESOMATERIA").toString());
            fechaLimiteTextField.setText(examen.get("FECHALIMITE").toString());
            horaLimiteTextField.setText(examen.get("HORALIMITE").toString());
            tiempoTextField.setText(examen.get("TIEMPOMINUTOS").toString());

            //TODO en el metodo sql hacer join con materia, unidad y grupo para moestrar nombres
            System.out.println("El examen es de " + examen.get("IDMATERIA"));
            materiaComboBox.setValue(examen.get("IDMATERIA").toString());


            //TODO luego de poner informacion, poder editar el examen correctamente desde la base de datos

        }
    }


    @FXML
    private void cancelarEvent() {
        limpiarCampos();
    }

    @FXML
    private void siguienteEvent() throws IOException {

        //obtiene si manual o automatico
        if(comboBoxAutomaticoManual.getValue() == null){
            mostrarAlerta("Error", "Debe seleccionar un modo de creación.");
            return;
        }else {

            if (!validarCampos()) {
                return;
            }

            // Obtener temas seleccionados
            for (CrearQuizController.TemaCheck tema : temasData) {
                if (tema.isSeleccionado()) {
                    temasSeleccionados.add(tema);
                }
            }

            if (temasSeleccionados.isEmpty()) {
                mostrarAlerta("Error", "Debe seleccionar al menos un tema.");
                return;
            }
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
            String dificultad = dificultadComboBox.getValue();

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
            int idExamenCreado= 1+ uQuizzes.crearQuiz(idDocente, idGrupo, idMateria, nombreQuiz, fechaInicio, cantidadPreguntas, tiempo , hora , descripcion , pesoMateria , tieneTiempo , notaMinimaPasar , fechaFin , horaLimite , cantidadPreguntasBanco);
            int idBancoCreado = 0;

            if(idExamenCreado != 0){
                mostrarInfo("Éxito", "El quiz ha sido creado exitosamente.");

                //Crea el banco
                System.out.println("la cantidad de preguntasBanco es "+cantidadPreguntasBanco);
                idBancoCreado =1+ uQuizzes.crearBancoPreguntas(idExamenCreado, cantidadPreguntasBanco);
                mostrarInfo("Éxito", "El banco de preguntas ha sido creado exitosamente.");
            }


            String modoCreacion = comboBoxAutomaticoManual.getValue();

            if (modoCreacion.equals("Automatico")) {

                try{
                    if(idExamenCreado != 0){
                        mostrarInfo("Éxito", "El quiz ha sido creado exitosamente.");

                        //Crea el banco

                        if(idBancoCreado != 0){

                            ArrayList<Integer> listaIdTemasSeleccionado= new ArrayList<>();
                            for (CrearQuizController.TemaCheck tema : temasSeleccionados) {
                                listaIdTemasSeleccionado.add(tema.getId());
                                System.out.println("Tema agregado a la lista " + tema.getId());
                            }


                            if (uQuizzes.agregarPreguntasAlBanco(idBancoCreado, listaIdTemasSeleccionado) > 0) {
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

            } else if (modoCreacion.equals("Manual")) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/seleccionar_preguntas.fxml"));
                Parent root = loader.load();

                SeleccionarPreguntasController controller = loader.getController();
                controller.setIdExamenCreado(idExamenCreado);
                controller.setInfo(nombreQuiz , cantidadPreguntasBanco , dificultad , idBancoCreado);

                Stage popupStage = new Stage();
                popupStage.setTitle("Agregar preguntas manualmente");
                popupStage.setScene(new Scene(root));
                popupStage.setResizable(false);

                Stage owner = (Stage) nombreQuizTextField.getScene().getWindow();
                popupStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
                popupStage.initOwner(owner);

                popupStage.centerOnScreen();
                popupStage.showAndWait();

            }
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

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
        cargarInformacionExamen();
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
