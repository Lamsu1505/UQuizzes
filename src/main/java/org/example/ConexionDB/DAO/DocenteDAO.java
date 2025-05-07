// src/main/java/org/example/ConexionDB/DAO/DocenteDAO.java
package org.example.ConexionDB.DAO;

import javafx.scene.control.Alert;
import oracle.jdbc.internal.OracleTypes;
import org.example.ConexionDB.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocenteDAO {

    public List<Map<String, Object>> getExamenes(String idDocente) throws SQLException {
        String call = "{ ? = call obtenerExamenesDocente(?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            // 1) Registrar salida: el cursor
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            // 2) Parametro IN: idDocente
            stmt.setString(2, idDocente);
            // 3) Ejecutar
            stmt.execute();

            // 4) Obtener el ResultSet del cursor
            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                List<Map<String, Object>> lista = new ArrayList<>();
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();

                while (rs.next()) {
                    Map<String,Object> fila = new HashMap<>();
                    for (int i = 1; i <= cols; i++) {
                        fila.put(md.getColumnLabel(i), rs.getObject(i));
                    }
                    lista.add(fila);
                }
                return lista;
            }
        }
    }

    /**
     * Llama a la función iniciarSesionDocente (que devuelve VARCHAR2) y
     * retorna directamente ese String, o null si no hay coincidencia o hay error.
     */
    public String iniciarSesion(String usuario, String password) {
        String call = "{ ? = call iniciarSesionDocente(?, ?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            // 1) El primer parámetro es la salida VARCHAR2
            stmt.registerOutParameter(1, Types.VARCHAR);
            // 2) Parámetros de entrada: usuario y password
            stmt.setString(2, usuario);
            stmt.setString(3, password);
            // 3) Ejecuto la llamada
            stmt.execute();
            // 4) Leo el String devuelto (iddocente) — será null si no encontró
            String idDocente = stmt.getString(1);
            // 5) Devuelvo el valor (o null)
            return idDocente;

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Error al iniciar sesión:\n" + e.getMessage()
            ).showAndWait();
            return null;
        }
    }
}
