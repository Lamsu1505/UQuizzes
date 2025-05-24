package org.example.Controllers.Paneles.Docente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamenPaginaPrincipalController{

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

    private int idExamen;


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

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void irVentanaEditarExamen(ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/Paneles/Docente/editarQuiz.fxml"));
            Parent root = loader.load();

            EditarQuizController controller = loader.getController();
            controller.setIdExamen(idExamen);


            Stage popupStage = new Stage();
            popupStage.setTitle("Editar Examen");
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);
            popupStage.show();


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
