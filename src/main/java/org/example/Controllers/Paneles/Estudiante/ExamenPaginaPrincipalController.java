package org.example.Controllers.Paneles.Estudiante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ConexionDB.ConexionOracle;
import org.example.Controllers.Ventanas.Estudiante.VentanaPrincipalEstudianteController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExamenPaginaPrincipalController {

    @FXML
    private Label lblFecha;

    @FXML
    private Button btnStats;

    @FXML
    private Label lblNombreExamen;

    @FXML
    private Label lblGrupo;

    @FXML
    private Label lblMateria;

    @FXML
    private Label lblHora;

    @FXML
    private Button btnEditar;


    public void setNombreExamen(String nombre) {
        lblNombreExamen.setText(nombre);
    }

    public void setMateria(String mat) {
        lblMateria.setText(mat);
    }

    public void setGrupo(String grp) {
        lblGrupo.setText(grp);
    }

    public void setFecha(String fecha) {
        lblFecha.setText(fecha);
    }
    public void setHora(String hora) {
        lblHora.setText(hora);
    }

    public void empezarQuiz(ActionEvent actionEvent) {

        try {
            ConexionOracle conn = new ConexionOracle();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Estudiante/s/infoExamen.fxml"));
            Parent root = loader.load();


            InfoExamenController controller = loader.getController();
            int idExamen = 0;

            String sql =
                    "SELECT " +
                            "e.nombre AS nombreExamen, " +
                            "e.idExamen AS idExamen " +
                            "FROM " +
                            "Examen e " +
                            "WHERE e.nombre = '" + lblNombreExamen.getText() + "'";



            try (Connection connection = conn.conectar();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("entro al while");
                        idExamen = rs.getInt("idExamen");

                    }
                }

                System.out.println("el id es " + idExamen);

            controller.getInfoExamen(idExamen);

            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Detalles del Examen");
            popupStage.setWidth(600);
            popupStage.setHeight(400);
            popupStage.setResizable(false);


            popupStage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.centerOnScreen();
            popupStage.show();


        }catch (Exception e) {}


    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
