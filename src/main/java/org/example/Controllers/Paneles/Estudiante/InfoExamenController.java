package org.example.Controllers.Paneles.Estudiante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Controllers.Paneles.Docente.ExamenPaginaPrincipalController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoExamenController {
    @FXML
    private Label cantidadPreguntasT;

    @FXML
    private Label lblUnidad;

    @FXML
    private Label lblTiempo;

    @FXML
    private Label lblNombreExamen;

    @FXML
    private Label nombreMateriaT;

    @FXML
    private Label lblMateria;

    @FXML
    private Label nombreUnidadT;

    @FXML
    private Label tiempoT;

    @FXML
    private Label lblCantidadPreguntas;

    @FXML
    private Label nombreExamenT;

    private int idExamen;


    public void iniciarExamen(ActionEvent actionEvent) throws SQLException, IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Estudiante/s/ResponderQuiz.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setWidth(900);
        stage.setHeight(650);
        stage.centerOnScreen();
        stage.show();

        ResponderQuizController controller = loader.getController();

        //Carga preguntas del examen
        controller.setIdExamen(idExamen);

    }

    public void getInfoExamen(int idExamen) throws SQLException {
    ConexionOracle conn = new ConexionOracle();
    this.idExamen = idExamen;
        String sql =
                "SELECT " +
                        "e.nombre AS nombreExamen, " +
                        "m.nombre AS materia, " +
                        "e.cantidadPreguntas AS cantidad, " +
                        "e.tiempominutos AS tiempo, " +
                        "u.nombre AS unidad, " +
                        "e.cantidadPreguntas AS cantidadPreguntasExamen, " +
                        "e.tiempoMinutos " +
                        "FROM " +
                        "Examen e " +
                        "JOIN Materia m ON e.Materia_idMateria = m.idMateria " +
                        "JOIN Unidad u ON m.idmateria = u.materia_idmateria " +
                        "WHERE e.idExamen = '" + idExamen + "'";



        try (Connection connection = conn.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    lblUnidad.setText(rs.getString("UNIDAD"));
                    lblMateria.setText(rs.getString("MATERIA"));
                    lblNombreExamen.setText(rs.getString("NOMBREEXAMEN"));
                    lblCantidadPreguntas.setText(String.valueOf(rs.getInt("CANTIDAD")));
                    lblTiempo.setText(rs.getString("TIEMPO"));
                }
            }

        }
        catch (Exception e){e.printStackTrace();}
    }
}