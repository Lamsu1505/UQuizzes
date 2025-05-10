package org.example.Controllers.Paneles.Docente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    UQuizzes uQuizzes = UQuizzes.getInstance();


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
                    "TP.idTipoPregunta, " +
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



        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
    }

    public void crearPregunta(javafx.event.ActionEvent actionEvent) {
    }
}
