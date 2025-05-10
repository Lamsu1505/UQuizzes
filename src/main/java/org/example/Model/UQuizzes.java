package org.example.Model;

import oracle.jdbc.internal.OracleTypes;
import org.example.ConexionDB.ConexionOracle;
import org.example.ConexionDB.DAO.DocenteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UQuizzes {

    private static UQuizzes uQuizzes;
    private String usuarioEnSesion;
    private boolean esDocente;

    public DocenteDAO docenteDAO = new DocenteDAO();

    private int idPreguntaRecienCreada;


    private UQuizzes() {
        usuarioEnSesion = null;
        idPreguntaRecienCreada = 0;
    }

    public static UQuizzes getInstance()  {
        if (uQuizzes == null){
            uQuizzes = new UQuizzes();
        }
        return uQuizzes;
    }


    public List<Map<String, Object>> getExamenesDocenteSQL() throws SQLException {
        ConexionOracle conexionOracle = new ConexionOracle();
        Connection connection = conexionOracle.conectar();


        return docenteDAO.getExamenes(getUsuarioEnSesion());
    }

    public List<Map<String, Object>> getMateriasDocente(String idDocente) throws SQLException {

        return docenteDAO.getMateriasDocente(idDocente);
    }

    public int getIdPreguntaRecienCreada() {
        return idPreguntaRecienCreada;
    }

    public void setIdPreguntaRecienCreada(int idPreguntaRecienCreada) {
        this.idPreguntaRecienCreada = idPreguntaRecienCreada;
    }

    public String getUsuarioEnSesion() {
        return usuarioEnSesion;
    }

    public void setUsuarioEnSesion(String usuarioEnSesion) {
        this.usuarioEnSesion = usuarioEnSesion;
    }

    public boolean isEsDocente() {
        return esDocente;
    }

    public void setEsDocente(boolean esDocente) {
        this.esDocente = esDocente;
    }

    public List<Map<String, Object>> getGruposDocenteByMateria(String usuarioEnSesion, String idMateria) throws SQLException {

        return docenteDAO.getGruposByMateria(usuarioEnSesion,idMateria);
    }

    public List<Map<String, Object>> getUnidadesDocenteByMateria(String idMateria) throws SQLException {

        return docenteDAO.getUnidadesByMateria(idMateria);
    }

    public List<Map<String, Object>> getTemasDocenteByUnidad(String idUnidadSeleccionada) throws SQLException {

        return docenteDAO.getTemasByUnidad(idUnidadSeleccionada);
    }


    public int crearPregunta(String idTemaSeleccionado, String idTipoPreguntaSeleccionado, String idPreguntaPadre, String idNivelPreguntaSeleccionado, Boolean isPublica, String enunciado, String peso, String tiempoPregunta) {
        return docenteDAO.crearPregunta(idTemaSeleccionado , idTipoPreguntaSeleccionado , idPreguntaPadre , idNivelPreguntaSeleccionado , isPublica, enunciado , peso , tiempoPregunta);
    }
}

