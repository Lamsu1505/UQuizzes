package org.example.ConexionDB.DAO;

import javafx.scene.control.Alert;
import oracle.jdbc.internal.OracleTypes;
import org.example.ConexionDB.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstudianteDAO {

    public String iniciarSesion(String usuario, String password) {
        String call = "{ ? = call iniciarSesionEstudiante(?, ?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, Types.VARCHAR);

            stmt.setString(2, usuario);
            stmt.setString(3, password);

            stmt.execute();

            String idDocente = stmt.getString(1);

            return idDocente;

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Error al iniciar sesión:\n" + e.getMessage()
            ).showAndWait();
            return null;
        }
    }

    public List<Map<String, Object>> getExamenesEstudianteSQL(String usuario) {
        String call = "{ ? = call obtenerExamenesEstudiante(?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);

            stmt.setString(2, usuario);

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
