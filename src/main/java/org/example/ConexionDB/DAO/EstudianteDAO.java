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

    public static List<Map<String, Object>> getExamenesEstudianteByMateria(int idMateriaSeleccionada, String usuarioEnSesion) {
            int idEstudiante = Integer.parseInt(usuarioEnSesion);


        String call = "{ ? = call obtenerExamenesEstudianteByMateria(? , ?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);

            stmt.setInt(2, idEstudiante);
            stmt.setInt(3, idMateriaSeleccionada);

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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean empezarExamenBaseDatos(int idUsuario, int idExamen) throws SQLException {

        boolean respuesta = false;
        String call = "{ ? = call empezarExamen(?, ?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, Types.INTEGER);

            stmt.setInt(2, idExamen);
            stmt.setInt(3, idUsuario);

            stmt.execute();

            int resultado = stmt.getInt(1);

            if(resultado == 1){
                return true;
            }

        } catch (SQLException ex) {
            int errorCode = ex.getErrorCode();
            String mensaje   = ex.getMessage();
            switch (errorCode) {
                case 20001:
                case 20002:
                case 20003:
                case 20010:
                    // relanzamos la misma excepción para que suba al controlador
                    throw new SQLException(mensaje, ex.getSQLState(), errorCode);
                default:
                    // los demás los dejamos que suban normalmente
                    throw ex;
            }
        }
        return respuesta;
    }

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

    public List<Map<String, Object>> getMateriasEstudiante(String usuarioEnSesion) {
        String call = "{ ? = call obtenerMateriasEstudiante(?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);

            stmt.setString(2, usuarioEnSesion);

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
