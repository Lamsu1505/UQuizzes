package org.example.Controllers.Paneles.Docente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SeleccionarPreguntasController {

    @FXML
    private Button AgregarButton;

    @FXML
    private TableColumn<?, ?> ColumnTipoPregunta;

    @FXML
    private TableColumn<Object, String> ColumnPregunta;

    @FXML
    private Button DesagregarButton;

    @FXML
    private TableView<Object> TableViewPregunta;

    @FXML
    private Button VolverButton;

    @FXML
    private void initialize() {
        ColumnPregunta.setCellFactory(tc -> new javafx.scene.control.TableCell<>() {
            private final javafx.scene.text.Text text = new javafx.scene.text.Text();

            {
                text.wrappingWidthProperty().bind(ColumnPregunta.widthProperty().subtract(10));
                setGraphic(text);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    text.setText(null);
                } else {
                    text.setText(item);
                }
            }
        });

        TableViewPregunta.setFixedCellSize(60); // Optional: adjust height to fit wrapped text

        VolverButton.setOnAction(event -> {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/panelTiposPregunta.fxml"));
                javafx.scene.Parent root = loader.load();
                javafx.scene.Scene scene = new javafx.scene.Scene(root);
                javafx.stage.Stage stage = (javafx.stage.Stage) VolverButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
