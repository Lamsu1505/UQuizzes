package org.example.Controllers.Paneles.Estudiante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ExamenesEstudianteController {

    @FXML
    private TableColumn<?, ?> ColumnFechaFinQuiz;

    @FXML
    private TableColumn<?, ?> ColumnMateriaQuiz;

    @FXML
    private TableColumn<?, ?> ColumnNombreQuiz;

    @FXML
    private TableColumn<?, ?> ColumnTemaQuiz;

    @FXML
    private TableColumn<?, ?> ColumnUnidadQuiz;

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
