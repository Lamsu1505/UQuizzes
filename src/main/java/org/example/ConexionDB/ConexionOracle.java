package org.example.ConexionDB;

import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:XE";
    private static final String USUARIO = "SYSTEM";
    private static final String CONTRASENA = "oracle";

    private static final long TIMEOUT = 3; // tiempo en segundos

    public static Connection conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexion exitosa");

            // Crear un temporizador para cerrar la conexión después de un tiempo de vida
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> cerrarConexion(conexion), TIMEOUT, TimeUnit.SECONDS);

            return conexion;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada por timeout");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}










