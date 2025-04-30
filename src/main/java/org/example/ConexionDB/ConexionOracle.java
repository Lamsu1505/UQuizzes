package org.example.ConexionDB;

import java.sql.*;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:XE";
    private static final String USUARIO = "SYSTEM";
    private static final String CONTRASENA = "oracle";

    public static Connection conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexion exitosa");
            return conexion;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}










