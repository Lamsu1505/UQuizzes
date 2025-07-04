package org.example.Controllers.Paneles.Estudiante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.Model.InfoExamenPresentadoEstudiante;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.ConexionDB.ConexionOracle;
import javafx.fxml.Initializable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Model.UQuizzes;

import java.net.URL;
import java.util.ResourceBundle;


public class ExamenesEstudianteController implements Initializable {

    private int idEstudiante; // Puedes cambiar esto con la sesión

    @FXML
    private TableColumn<InfoExamenPresentadoEstudiante, String> ColumnNombreQuiz;

    @FXML
    private TableColumn<InfoExamenPresentadoEstudiante, String> ColumnMateriaQuiz;

    @FXML
    private TableColumn<InfoExamenPresentadoEstudiante, String> ColumnTemaQuiz;

    @FXML
    private TableColumn<InfoExamenPresentadoEstudiante, String> ColumnUnidadQuiz;

    @FXML
    private TableColumn<InfoExamenPresentadoEstudiante, String> ColumnNotaQuiz;

    @FXML
    private TableView<InfoExamenPresentadoEstudiante> TableViewReporte;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    void homeEvent(ActionEvent event) {

    }

    @FXML
    void searchEvent(ActionEvent event) {

    }

    @FXML
    void searchFieldEvent(ActionEvent event) {

    }
    public List<InfoExamenPresentadoEstudiante> obtenerResumenesExamenPorEstudiante(int idEstudiante) {
        String query = "SELECT " +
                "e.nombre AS nombreExamen, " +
                "e.fecha AS fechaExamen, " +
                "e.notaminimapasar, " +
                "s.notafinal, " +
                "s.tiempotomadominutos, " +
                "m.nombre as materia " +
                "FROM examen e " +
                "JOIN solucionexamenestudiante s ON e.idexamen = s.examen_idexamen " +
                "JOIN materia m ON e.materia_idmateria = m.idmateria " +
                "WHERE s.estudiante_idestudiante = ?";

        List<InfoExamenPresentadoEstudiante> listaResumenes = new ArrayList<>();

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idEstudiante);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombreExamen = rs.getString("nombreExamen");
                    String fechaExamen = rs.getString("fechaExamen");
                    double notaMinima = rs.getDouble("notaminimapasar");
                    double notaFinal = rs.getDouble("notafinal");
                    double tiempoTomado = rs.getDouble("tiempotomadominutos");
                    String materia = rs.getString("materia");

                    InfoExamenPresentadoEstudiante resumen = new InfoExamenPresentadoEstudiante(
                            nombreExamen, fechaExamen, notaMinima, notaFinal, tiempoTomado , materia
                    );
                    listaResumenes.add(resumen);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaResumenes;
    }

    private UQuizzes uQuizzes = UQuizzes.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idEstudiante = Integer.parseInt(uQuizzes.getUsuarioEnSesion());

        List<InfoExamenPresentadoEstudiante> resumenes = obtenerResumenesExamenPorEstudiante(idEstudiante);

        TableViewReporte.setItems(FXCollections.observableArrayList(resumenes));

        ColumnNombreQuiz.setCellValueFactory(new PropertyValueFactory<>("nombreExamen"));
        ColumnMateriaQuiz.setCellValueFactory(new PropertyValueFactory<>("materia"));
        ColumnNotaQuiz.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.format("%.2f", cellData.getValue().getNotaFinal())));
        ColumnUnidadQuiz.setCellValueFactory(new PropertyValueFactory<>("fechaExamen"));
    }


}
