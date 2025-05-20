package org.example.Controllers.Paneles.Docente;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Model.Docente.EstudianteExamenInfo;
import org.example.Model.Docente.ExamenDTO;
import org.example.Model.Docente.GrupoDTO;
import org.example.Model.Docente.MateriaDTO;
import org.example.Model.UQuizzes;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EstadisticasExamenesPresentadosController implements Initializable {

    @FXML
    private TableView<EstudianteExamenInfo> TableViewReporte;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnFecha;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> columnHoraInicio;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> columnCodigo;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnPrimerNombre;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnApellido;

    @FXML
    private TableColumn<EstudianteExamenInfo, Double> ColumnPuntaje;

    @FXML
    private TableColumn<EstudianteExamenInfo, String> ColumnTiempoTomado;

    @FXML
    private TableColumn<?,?> ColumnReporte;

    @FXML
    private ComboBox<MateriaDTO> comboBoxMateria;

    @FXML
    private ComboBox<ExamenDTO> comboBoxExamen;

    @FXML
    private ComboBox<GrupoDTO> comboBoxGrupos;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;



    private UQuizzes uQuizzes = UQuizzes.getInstance();

    private int idDocente = Integer.parseInt(uQuizzes.getUsuarioEnSesion());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargarMaterias();

        ColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        columnHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ColumnPrimerNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        ColumnPuntaje.setCellValueFactory(new PropertyValueFactory<>("notaFinal"));
        ColumnTiempoTomado.setCellValueFactory(new PropertyValueFactory<>("tiempoTomado"));
    }


    private void cargarMaterias(){
        List<MateriaDTO> materias = uQuizzes.obtenerMateriaPorDocente(idDocente);
        comboBoxMateria.setItems(FXCollections.observableArrayList(materias));
    }

    @FXML
    void homeEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Ventanas/ventanaInicioDocente.fxml"));
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
    void searchEvent(ActionEvent event) {

    }

    @FXML
    void searchFieldEvent(ActionEvent event) {

    }

    @FXML
    void seleccionarExamen(ActionEvent event) {
        ExamenDTO examenSeleccionado = comboBoxExamen.getSelectionModel().getSelectedItem();

        if (examenSeleccionado != null) {
            int idExamen = examenSeleccionado.getIdExamen();


            List<EstudianteExamenInfo> estudiantes = uQuizzes.obtenerEstudiantesPorExamen(idExamen);

            // Cargar en TableView
            TableViewReporte.setItems(FXCollections.observableArrayList(estudiantes));
        }
    }

    @FXML
    void seleccionarGrupo(ActionEvent event) {
        GrupoDTO grupoSeleccionado = comboBoxGrupos.getSelectionModel().getSelectedItem();
        if (grupoSeleccionado != null) {
            List<ExamenDTO> examenes = uQuizzes.obtenerExamenesPorGrupoYDocente(grupoSeleccionado.getIdGrupo(), idDocente);
            comboBoxExamen.setItems(FXCollections.observableArrayList(examenes));
        }
    }

    @FXML
    void seleccionarMateria(ActionEvent event) {
        MateriaDTO materiaSeleccionada = comboBoxMateria.getSelectionModel().getSelectedItem();

        if (materiaSeleccionada != null) {
            int idMateria = materiaSeleccionada.getIdMateria();


            List<GrupoDTO> grupos = uQuizzes.obtenerGruposPorDocenteYMateria(idDocente, idMateria);
        }
    }
}
