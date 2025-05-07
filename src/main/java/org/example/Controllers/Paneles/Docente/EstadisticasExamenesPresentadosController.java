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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EstadisticasExamenesPresentadosController {

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
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

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

}
