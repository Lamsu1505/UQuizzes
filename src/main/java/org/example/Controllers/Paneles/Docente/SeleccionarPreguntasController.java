package org.example.Controllers.Paneles.Docente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.Controllers.Paneles.Estudiante.ResponderQuizController;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.Pregunta;
import org.example.Model.PruebaPreguntas;
import org.example.ConexionDB.ConexionOracle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeleccionarPreguntasController {

    @FXML
    private Button AgregarButton;

    @FXML
    private TableColumn<PruebaPreguntas, String> ColumnPregunta;

    @FXML
    private TableColumn<Pregunta, String> ColumnDificultadPregunta;

    @FXML
    private Button DesagregarButton;

    @FXML
    private TableView<Pregunta> TableViewPregunta;

    @FXML
    private Button volverButton;

    @FXML
    void agregarButton(ActionEvent event) {

    }

    @FXML
    void desagregarButton(ActionEvent event) {

    }

    @FXML
    void volverEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/panelTiposPregunta.fxml"));
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
    public void initialize() {
        List<Pregunta> preguntas = obtenerPreguntasConNivel();
        TableViewPregunta.getItems().addAll(preguntas);

        ColumnPregunta.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEnunciado()));
        ColumnDificultadPregunta.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNivel()));
    }


    public List<Pregunta> obtenerPreguntasConNivel() {
        String query = "SELECT p.idpregunta, p.enunciado, n.nivel " +
                "FROM pregunta p " +
                "JOIN nivelpregunta n ON p.nivelpregunta_idnivelpregunta = n.idnivelpregunta";

        List<Pregunta> listaPreguntas = new ArrayList<>();

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer idpregunta = rs.getInt("idpregunta");
                String enunciado = rs.getString("enunciado");
                String nivel = rs.getString("nivel");

                Pregunta pregunta = new Pregunta(idpregunta,  enunciado, nivel);
                listaPreguntas.add(pregunta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPreguntas;
    }

}