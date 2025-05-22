package org.example.Controllers.Paneles.Docente;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Model.UQuizzes;

import java.net.URL;
import java.util.ResourceBundle;

public class SeleccionarPreguntasController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Pregunta> preguntas = obtenerPreguntas();
        TableViewPregunta.setItems(FXCollections.observableArrayList(preguntas));

        ColumnPregunta.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getEnunciado()));

        ColumnDificultadPregunta.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getNivel()));

        columnaTema.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getTema()));


        TableViewPregunta.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Pregunta item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (filasSeleccionadas.contains(item)) {
                    setStyle("-fx-background-color: #138f31;"); // Color verde suave
                } else {
                    setStyle("");
                }
            }
        });
    }

    @FXML
    private Button AgregarButton;

    @FXML
    private Button btnFinalizar;

    @FXML
    private TableColumn<Pregunta, String> ColumnPregunta;

    @FXML
    private TableColumn<Pregunta, String> ColumnDificultadPregunta;

    @FXML
    private TableColumn<Pregunta, String> columnaTema;

    @FXML
    private Button DesagregarButton;

    @FXML
    private TableView<Pregunta> TableViewPregunta;

    @FXML
    private Button volverButton;

    @FXML
    private Label lblExamen;

    @FXML
    private Label lblCantidadBanco;

    @FXML
    private Label lblDificultad;

    private UQuizzes uQuizzes = UQuizzes.getInstance();

    private final List<Pregunta> filasSeleccionadas = new ArrayList<>();
    private int idExamenCreado;
    private int idBancoCreado;

    @FXML
    void agregarButton(ActionEvent event) {
        Pregunta seleccionada = TableViewPregunta.getSelectionModel().getSelectedItem();

        // Obtener el límite desde el label
        int limiteSeleccion = obtenerCantidadMaxima();

        if (seleccionada != null && !filasSeleccionadas.contains(seleccionada)) {
            if (filasSeleccionadas.size() >= limiteSeleccion) {
                mostrarAlerta("Límite alcanzado", "Solo puedes seleccionar hasta " + limiteSeleccion + " preguntas (Banco examen)");
            } else {
                filasSeleccionadas.add(seleccionada);
                TableViewPregunta.refresh();
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private int obtenerCantidadMaxima() {
        try {
            String texto = lblCantidadBanco.getText(); // "Cantidad banco: 5"
            String numero = texto.replaceAll("[^0-9]", "");
            return Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE; // No aplica límite si el label está mal
        }
    }

    @FXML
    void desagregarButton(ActionEvent event) {
        Pregunta seleccionada = TableViewPregunta.getSelectionModel().getSelectedItem();
        if (seleccionada != null && filasSeleccionadas.contains(seleccionada)) {
            filasSeleccionadas.remove(seleccionada);
            TableViewPregunta.refresh();
        }
    }

    public List<Pregunta> obtenerPreguntas() {
        String query = "SELECT p.idpregunta, p.enunciado, n.nivel , t.nombre AS nombreTema " +
                "FROM pregunta p " +
                "JOIN nivelpregunta n ON p.nivelpregunta_idnivelpregunta = n.idnivelpregunta " +
                "JOIN tema t ON p.tema_idtema = t.idtema ";

        List<Pregunta> listaPreguntas = new ArrayList<>();

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idpregunta");
                String enunciado = rs.getString("enunciado");
                String nivel = rs.getString("nivel");
                String tema = rs.getString("nombreTema");

                Pregunta pregunta = new Pregunta(id, enunciado, nivel , tema);
                listaPreguntas.add(pregunta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPreguntas;
    }



    public void setInfo(String nombreQuiz, int cantidadPreguntasBanco, String dificultad, int idBancoCreado) {
        lblExamen.setText("Examen: " + nombreQuiz);
        lblCantidadBanco.setText("Cantidad banco: " + cantidadPreguntasBanco);
        lblDificultad.setText("Dificultad: " + dificultad);
        this.idBancoCreado = idBancoCreado;
    }

    public void setIdExamenCreado(int idExamenCreado) {
        this.idExamenCreado = idExamenCreado;
    }


    public void crearExamen(ActionEvent actionEvent) {

        if (filasSeleccionadas.isEmpty() ) {
            mostrarAlerta("No se han seleccionado preguntas", "Por favor, selecciona al menos una pregunta.");
            return;
        }
        if(filasSeleccionadas.size() < obtenerCantidadMaxima()){
            mostrarAlerta("Faltan preguntas", "Por favor, selecciona " + obtenerCantidadMaxima() + " preguntas.");
            return;
        }

        if(idBancoCreado != 0){

            if (uQuizzes.agregarPreguntasAlBancoManual(idBancoCreado, filasSeleccionadas )  == obtenerCantidadMaxima()) {
               if(uQuizzes.getCantidadPreguntasBanco(idExamenCreado) == obtenerCantidadMaxima()){
                    mostrarAlerta("Éxito", "Preguntas añadidas al banco");





                   Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                   stage.close();
                }
                else{
                    mostrarAlerta("Error", "No se han añadido todas las preguntas al banco");
                }



            }

        }


    }
}