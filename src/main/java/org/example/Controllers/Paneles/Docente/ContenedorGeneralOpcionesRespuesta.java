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
import org.example.Model.UQuizzes;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    UQuizzes uQuizzes = UQuizzes.getInstance();

    private int idTipoPregunta;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            int idPregunta = uQuizzes.getIdPreguntaRecienCreada();
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


        tipoPreguntaComboBox.setOnAction(event -> {
            String nombreTipoSeleccionado = tipoPreguntaComboBox.getValue();
            if (nombreTipoSeleccionado != null) {

                ConexionOracle conexion = new ConexionOracle();

                if (conexion == null) {
                    mostrarAlerta("Error de conexión con la base de datos.");
                    return;
                }

                String sql = "select idtipopregunta " +
                        "from tipopregunta where nombre = '" + nombreTipoSeleccionado + "'";

                try (Connection connection = conexion.conectar();
                     PreparedStatement stmt = connection.prepareStatement(sql)) {

                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            idTipoPregunta = rs.getInt("idtipopregunta");
                        }
                        try{
                            cargarPanelOpcionesRespuesta(idTipoPregunta);
                        }catch (Exception e){e.printStackTrace();}

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void cargarPanelOpcionesRespuesta(int idTipoPregunta) throws Exception {

        if(idTipoPregunta==1){
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelUnicaRespuesta.fxml"));
            this.panelCambiante.getChildren().clear();
            this.panelCambiante.getChildren().addAll(panel);
        }
        if(idTipoPregunta==2){
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelVerdaderoFalso.fxml"));
            this.panelCambiante.getChildren().clear();
            this.panelCambiante.getChildren().addAll(panel);
        }
        if(idTipoPregunta==3){
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelRespuestaCorta.fxml"));
            this.panelCambiante.getChildren().clear();
            this.panelCambiante.getChildren().addAll(panel);
        }
        if(idTipoPregunta==4){
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelEmparejar.fxml"));
            this.panelCambiante.getChildren().clear();
            this.panelCambiante.getChildren().addAll(panel);
        }
        if (idTipoPregunta==5){
            Parent panel = FXMLLoader.load(getClass().getResource("/Interfaces/Paneles/Docente/OpcionesRespuesta/PanelMultipleRespuesta.fxml"));
            this.panelCambiante.getChildren().clear();
            this.panelCambiante.getChildren().addAll(panel);
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

    public void crearPregunta(javafx.event.ActionEvent actionEvent) {
        //TODO agarrar la info de las opciones y agregarsela a la pregunta en cuestion

    }



}
