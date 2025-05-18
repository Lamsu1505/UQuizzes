package org.example.Controllers.Paneles.Docente;

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
import org.example.Model.UQuizzes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EstadisticasExamenesPresentadosController implements Initializable {

    @FXML
    private TableColumn<?, ?> ColumnApellido;

    @FXML
    private TableColumn<?, ?> ColumnFecha;

    @FXML
    private TableColumn<?, ?> ColumnPrimerNombre;

    @FXML
    private TableColumn<?, ?> ColumnPuntaje;

    @FXML
    private TableColumn<?, ?> ColumnReporte;

    @FXML
    private TableColumn<?, ?> ColumnTiempoTomado;

    @FXML
    private TableView<?> TableViewReporte;

    @FXML
    private TableColumn<?, ?> columnCodigo;

    @FXML
    private ComboBox<?> comboBoxExamen;

    @FXML
    private ComboBox<?> comboBoxGrupos;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;



    private UQuizzes uQuizzes = UQuizzes.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ColumnPrimerNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        ColumnPuntaje.setCellValueFactory(new PropertyValueFactory<>("notaFinal"));
        ColumnTiempoTomado.setCellValueFactory(new PropertyValueFactory<>("tiempoTomado"));
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

    }

    @FXML
    void seleccionarGrupo(ActionEvent event) {

    }

}
