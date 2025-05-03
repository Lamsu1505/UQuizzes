package org.example.Model;

import org.example.ConexionDB.ConexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UQuizzes {

    private static UQuizzes uQuizzes;
    private String usuarioEnSesion = null;
    private boolean esDocente;


    private UQuizzes() {

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

        if (connection == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        String query="";

        if(isEsDocente()){
            query = "SELECT * FROM DOCENTE WHERE IDDOCENTE  = ?";
        }
        else{
            query = "SELECT * FROM ESTUDIANTE WHERE IDESTUDIANTE  = ?";
        }

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, getUsuarioEnSesion());

        return stmt.executeQuery();
    }

    public void cerrarConexion(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

