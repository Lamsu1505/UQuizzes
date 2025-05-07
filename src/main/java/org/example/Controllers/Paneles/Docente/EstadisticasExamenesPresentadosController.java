package org.example.Controllers.Paneles.Docente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    }

    @FXML
    void searchEvent(ActionEvent event) {

    }

    @FXML
    void searchFieldEvent(ActionEvent event) {

    }

}
