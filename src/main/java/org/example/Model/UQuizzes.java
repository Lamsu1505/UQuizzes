package org.example.Model;

import org.example.ConexionDB.ConexionOracle;

import java.sql.*;

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


    public ResultSet getUsuarioEnSesionSQL() throws SQLException {
        ConexionOracle conexionOracle = new ConexionOracle();
        Connection connection = conexionOracle.conectar();

        CallableStatement stmt = connection.prepareCall("{call obtener_usuario_en_sesion(?, ?, ?)}");
        stmt.setString(1, getUsuarioEnSesion());
        stmt.setBoolean(2, isEsDocente());
        stmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);

        stmt.execute();
        return (ResultSet) stmt.getObject(3);
    }


    public ResultSet getExamenesDocenteSQL() throws SQLException {
        ConexionOracle conexionOracle = new ConexionOracle();
        Connection connection = conexionOracle.conectar();

        CallableStatement stmt = connection.prepareCall("{call obtener_examenes_docente(?, ?)}");
        stmt.setString(1, getUsuarioEnSesion());
        stmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

        stmt.execute();
        return (ResultSet) stmt.getObject(2);
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
}

