package org.example.Controllers.Paneles.Docente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Controllers.Paneles.Docente.TiposPregunta.*;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.UQuizzes;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ContenedorGeneralOpcionesRespuesta implements Initializable {

    @FXML
    private ComboBox<String> tipoPreguntaComboBox;

    @FXML
    private Button cancelarButton;

    @FXML
    private Button siguienteButton;

    @FXML
    private Label idPreguntaLabel;

    @FXML
    private TextArea enunciadoTextArea;

    @FXML
    private Label lblUnidad;

    @FXML
    private Label lblTema;

    @FXML
    private Label lblMateria;

    @FXML
    private StackPane panelCambiante;


    private OpcionMultipleUnicaRespuestaController unicaRespuestaController;
    private VerdaderoFalsoController verdaderoFalsoRespuestaController;
    private RespuestaCortaController respuestaCortaController;
    private SeleccionMultipleController seleccionMultipleController;
    private EmparejamientoController emparejamientoController;

    UQuizzes uQuizzes = UQuizzes.getInstance();

    private int idTipoPregunta;
    private int idPregunta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hacer no editable el TextArea de enunciado
        enunciadoTextArea.setEditable(false);

        // Hacer no editable el ComboBox de tipo de pregunta
        tipoPreguntaComboBox.setEditable(false);

        // Deshabilitar la edición del ComboBox
        tipoPreguntaComboBox.setMouseTransparent(true);
        tipoPreguntaComboBox.setFocusTraversable(false);

        try{
            this.idPregunta = uQuizzes.getIdPreguntaRecienCreada();
            idPreguntaLabel.setText("Id de pregunta " + idPregunta);

            //para agarrar info de la pregunta
            String enunciado= "";
            String materia ="";
            String tema ="";
            String unidad ="";
            String tipoPregunta ="";
            ConexionOracle conexion = new ConexionOracle();

            if (conexion == null) {
                mostrarAlerta("Error de conexión con la base de datos.");
                return;
            }

            String sql = "SELECT " +
                    "P.idPregunta AS idPregunta, " +
                    "P.enunciado AS enunciado, " +
                    "G.idGrupo, " +
                    "G.nombre AS nombreGrupo, " +
                    "M.idMateria, " +
                    "M.nombre AS nombreMateria, " +
                    "U.idUnidad, " +
                    "U.nombre AS nombreUnidad, " +
                    "T.idTema, " +
                    "T.nombre AS nombreTema, " +
                    "TP.idTipoPregunta AS idTipoPregunta, " +
                    "TP.nombre AS nombreTipoPregunta " +
                    "FROM Pregunta P " +
                    "JOIN Tema T ON P.tema_idTema = T.idTema " +
                    "JOIN Unidad U ON T.unidad_idUnidad = U.idUnidad " +
                    "JOIN Materia M ON U.materia_idMateria = M.idMateria " +
                    "JOIN Grupo G ON G.materia_idMateria = M.idMateria " +
                    "JOIN TipoPregunta TP ON P.tipoPregunta_idTipo = TP.idTipoPregunta " +
                    "WHERE P.idPregunta = '" + idPregunta + "'";

            try (Connection connection = conexion.conectar();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        enunciado = rs.getString("enunciado");
                        materia = rs.getString("nombreMateria");
                        tema = rs.getString("nombreTema");
                        unidad = rs.getString("nombreUnidad");
                        tipoPregunta = rs.getString("nombreTipoPregunta");
                        idTipoPregunta = rs.getInt("idTipoPregunta");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            enunciadoTextArea.setText(enunciado);
            lblUnidad.setText(unidad);
            lblTema.setText(tema);
            lblMateria.setText(materia);
            tipoPreguntaComboBox.setValue(tipoPregunta);

            cargarTiposPregunta();

            //Añadir el panel segun su eleccion de tipo de pregunta
            try {
                cargarPanelOpcionesRespuesta(idTipoPregunta);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Se elimina el listener de cambio de tipo de pregunta para evitar modificaciones
    }

    private void cargarPanelOpcionesRespuesta(int idTipoPregunta) throws Exception {
        if (idTipoPregunta == 1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelUnicaRespuesta.fxml"));
            Parent panel = loader.load();
            unicaRespuestaController = loader.getController();
            this.panelCambiante.getChildren().add(panel);
        }
        if(idTipoPregunta==2){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelVerdaderoFalso.fxml"));
            Parent panel = loader.load();
            verdaderoFalsoRespuestaController = loader.getController();
            this.panelCambiante.getChildren().add(panel);
        }
        if(idTipoPregunta==3){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelRespuestaCorta.fxml"));
            Parent panel = loader.load();
            respuestaCortaController = loader.getController();
            this.panelCambiante.getChildren().add(panel);
        }
        if(idTipoPregunta==4){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelEmparejar.fxml"));
            Parent panel = loader.load();
            emparejamientoController = loader.getController();
            this.panelCambiante.getChildren().add(panel);
        }
        if (idTipoPregunta==5){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelMultipleRespuesta.fxml"));
            Parent panel = loader.load();
            seleccionMultipleController = loader.getController();
            this.panelCambiante.getChildren().add(panel);
        }
    }

    private void cargarTiposPregunta() {
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

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cancelar(javafx.event.ActionEvent actionEvent) {
        //TODO si le da a cancelar, se elimina la pregunta tambien de la base de datos

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Ventanas/ventanaInicioDocente.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("UQuizzes - Home");
            stage.setWidth(1450);
            stage.setHeight(800);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

        }catch (Exception e) {e.printStackTrace();}
    }

    public boolean crearPregunta(javafx.event.ActionEvent actionEvent) {
        try {

            // Crear conexión a la base de datos
            ConexionOracle conexion = new ConexionOracle();
            Connection connection = conexion.conectar();

            // Determinar qué controlador específico de opciones usar según el tipo de pregunta
            switch (idTipoPregunta) {

                case 1: // Única Respuesta
                    if( guardarPreguntaUnicaRespuesta()){
                        mostrarAlerta("Pregunta creada exitosamente");
                        irPanelInicioDocente();
                        return true;
                    };
                    break;

                case 2: // Verdadero Falso
                    if( guardarPreguntaVerdaderoFalso()){
                        mostrarAlerta("Pregunta creada exitosamente");
                        irPanelInicioDocente();
                        return true;
                    };
                    break;

                case 3: // Respuesta Corta
                    if( guardarPreguntaRespuestaCorta()){
                        mostrarAlerta("Pregunta creada exitosamente");
                        irPanelInicioDocente();
                        return true;
                    };
                    break;

                case 4: // Emparejar
                    if( guardarPreguntaEmparejar()){
                        mostrarAlerta("Pregunta creada exitosamente");
                        irPanelInicioDocente();
                        return true;
                    };

                case 5: // Múltiple Respuesta
                    if( guardarPreguntaMultipleRespuesta()){
                        mostrarAlerta("Pregunta creada exitosamente");
                        irPanelInicioDocente();
                        return true;
                    };

                default:
                    throw new IllegalArgumentException("Tipo de pregunta no reconocido");
            }


        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error al crear la pregunta: " + e.getMessage());
        }

        return true;
    }


    private void irPanelInicioDocente() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Ventanas/ventanaInicioDocente.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("UQuizzes - Home");
            stage.setWidth(1450);
            stage.setHeight(800);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

            Stage stageActual = (Stage) siguienteButton.getScene().getWindow();
            stageActual.close();

        }catch (Exception e) {e.printStackTrace();}

    }


    private boolean guardarPreguntaUnicaRespuesta() throws Exception {
        List<OpcionMultipleRespuesta> opciones = unicaRespuestaController.getListaOpciones();


        System.out.println("");
        for (OpcionMultipleRespuesta opcion : opciones) {

            try{
                if(uQuizzes.guardarPreguntaUnicaRespuestaDocente(opcion , idPregunta)){
                    System.out.println("Guardando la opcion: " + opcion.getTexto());

                }
                else {
                    mostrarAlerta("Error al guardar la opcion: " + opcion.getTexto());
                    throw new Exception("Error creando las opciones");
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }


    private boolean guardarPreguntaVerdaderoFalso() {
        //si es true es pq la respuesta correcta es verdadero
        Boolean respuesta = verdaderoFalsoRespuestaController.obtenerRespuestaSeleccionada();

        if (respuesta == null) {
            mostrarAlerta("Debe seleccionar una opción: Verdadero o Falso.");
            return false;
        }

        try {
            if (respuesta == true) {
                uQuizzes.guardarPreguntaVerdaderoFalsoDocente(true , idPregunta , "Verdadero");
                uQuizzes.guardarPreguntaVerdaderoFalsoDocente(false , idPregunta , "Falso");

            } else {
                uQuizzes.guardarPreguntaVerdaderoFalsoDocente(true , idPregunta , "Falso");
                uQuizzes.guardarPreguntaVerdaderoFalsoDocente(false , idPregunta , "Verdadero");
            }
            mostrarAlerta("Pregunta guardada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar: " + e.getMessage());
        }
        return true;
    }

    private boolean guardarPreguntaRespuestaCorta() {
        List<String> opciones = respuestaCortaController.getListaOpciones();

        for (String opcion : opciones) {
            try {
                if (uQuizzes.guardarPreguntaRespuestaCortaDocente(opcion, idPregunta)) {
                    System.out.println("Se guardo la opcion : " + opcion);
                } else {
                    mostrarAlerta("Error al guardar la opcion: " + opcion);
                    throw new Exception("Error creando las opciones");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;

    }

    private boolean guardarPreguntaEmparejar() {
        List<EmparejamientoController.ParElementos> pares = null;

        if (emparejamientoController.validarPares()) {
            pares = emparejamientoController.obtenerPares();
            for (EmparejamientoController.ParElementos par : pares) {
                try {
                    if (uQuizzes.guardarPreguntaEmparejarDocente(par.getElementoA(), par.getElementoB(), idPregunta)) {
                        System.out.println("Se guardo el par: " + par.getElementoA() + " - " + par.getElementoB());
                    } else {
                        mostrarAlerta("Error al guardar el par: " + par.getElementoA() + " - " + par.getElementoB());
                        throw new Exception("Error creando los pares");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            mostrarAlerta("Error: No se pueden guardar pares vacíos o duplicados.");
            return false;
        }
        return true;
    }

    private boolean guardarPreguntaMultipleRespuesta() {
        List<SeleccionMultipleController.OpcionMultiple> opciones = seleccionMultipleController.obtenerOpciones();

        for (SeleccionMultipleController.OpcionMultiple opcion : opciones) {
            try {
                if (uQuizzes.guardarPreguntaMultipleRespuestaDocente(opcion, idPregunta)) {
                    System.out.println("Guardando la opcion: " + opcion.getTexto());
                } else {
                    mostrarAlerta("Error al guardar la opcion: " + opcion.getTexto());
                    throw new Exception("Error creando las opciones");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }





}
