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


    private UQuizzes() {
        usuarioEnSesion = null;
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
        DocenteDAO docenteDAO = new DocenteDAO();

        return docenteDAO.getExamenes(getUsuarioEnSesion());
    }

    public List<Map<String, Object>> getMateriasDocente(String idDocente) throws SQLException {
        DocenteDAO docenteDAO = new DocenteDAO();
        return docenteDAO.getMateriasDocente(idDocente);
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
        DocenteDAO docenteDAO = new DocenteDAO();
        return docenteDAO.getGruposByMateria(usuarioEnSesion,idMateria);
    }

    public List<Map<String, Object>> getUnidadesDocenteByMateria(String idMateria) throws SQLException {
        DocenteDAO docenteDAO = new DocenteDAO();
        return docenteDAO.getUnidadesByMateria(idMateria);
    }

    public List<Map<String, Object>> getTemasDocenteByUnidad(String idUnidadSeleccionada) throws SQLException {
        DocenteDAO docenteDAO = new DocenteDAO();
        return docenteDAO.getTemasByUnidad(idUnidadSeleccionada);
    }
}

