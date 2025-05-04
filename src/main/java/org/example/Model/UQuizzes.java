package org.example.Model;

import org.example.ConexionDB.ConexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public ResultSet getExamenesDocenteSQL() throws SQLException {

        ConexionOracle conexionOracle = new ConexionOracle();
        Connection connection = conexionOracle.conectar();

        String sql = "SELECT NOMBRE, MATERIA_IDMATERIA , GRUPO_IDGRUPO, FECHA, HORA " +
                     "FROM EXAMEN " +
                        "WHERE DOCENTE_IDDOCENTE = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, this.getUsuarioEnSesion());

        System.out.println("en UQuizzes el usuario en sesion es " + this.getUsuarioEnSesion());
        return ps.executeQuery();
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

