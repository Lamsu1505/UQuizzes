package org.example.Controllers.Paneles.Estudiante;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

}
