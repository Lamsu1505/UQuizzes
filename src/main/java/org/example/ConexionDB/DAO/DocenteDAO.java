// src/main/java/org/example/ConexionDB/DAO/DocenteDAO.java
package org.example.ConexionDB.DAO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import oracle.jdbc.internal.OracleTypes;
import org.example.ConexionDB.ConexionOracle;
import org.example.Controllers.Paneles.Docente.TiposPregunta.SeleccionMultipleController;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;

import java.sql.*;
import java.util.*;

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

    public List<Map<String, Object>> getGruposByMateria(String idDocente , String idMateria) throws SQLException {
        String call = "{ ? = call obtenerGruposDocenteByMateria(?, ?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            // 1) Registrar salida: el cursor
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            // 2) Parametro IN: idDocente
            stmt.setString(2, idDocente);
            stmt.setString(3, idMateria);
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

    public List<Map<String, Object>> getMateriasDocente(String idDocente) throws SQLException {
        String call = "{ ? = call obtenerMateriasDocente(?) }";

        List<Map<String, Object>> listaMaterias = new ArrayList<>();

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, idDocente);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    for (int i = 1; i <= cols; i++) {
                        fila.put(md.getColumnLabel(i), rs.getObject(i));
                    }
                    listaMaterias.add(fila);
                }
            }
        }
        return listaMaterias;
    }

    public List<Map<String, Object>> getUnidadesByMateria(String idMateria) throws SQLException {

        String call = "{ ? = call getUnidadesDocenteByMateria(?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, idMateria);

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

    public List<Map<String, Object>> getTemasByUnidad(String idUnidadSeleccionada) throws SQLException {

        String call = "{ ? = call getTemasDocenteByUnidad(?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, idUnidadSeleccionada);

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

    public int crearPregunta(String idTemaSeleccionado, String idTipoPreguntaSeleccionado, String idPreguntaPadre, String idNivelPreguntaSeleccionado, boolean isPublica, String enunciado, String peso, String tiempoPregunta) {

        int respuesta = 0;

        Integer idTema = Integer.parseInt(idTemaSeleccionado);
        Integer idTipoPregunta = Integer.parseInt(idTipoPreguntaSeleccionado);
        Integer idNivelPregunta = Integer.parseInt(idNivelPreguntaSeleccionado);
        String esPublica;
        if(isPublica){
            esPublica = "S";
        }
        else {
            esPublica = "N";
        }
        Integer iPeso = Integer.parseInt(peso);
        Integer iTiempoPregunta = Integer.parseInt(tiempoPregunta);

        String call = "{ ? = call crearPregunta(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {


            stmt.registerOutParameter(1, Types.INTEGER);


            stmt.setNull(2, Types.INTEGER); //no importa pq hay un trigger que cambia

            stmt.setInt(3, idTema);
            stmt.setInt(4, idTipoPregunta);

            if (idPreguntaPadre==null) {

                //IMPORTANTE EL SETNULL SINO NO FUNCIONA
                stmt.setNull(5, Types.INTEGER);
            } else {
                //TODO aca es cuando si tiene padre
            }

            stmt.setInt(6, idNivelPregunta);
            stmt.setString(7, esPublica);
            stmt.setString(8, enunciado);
            stmt.setInt(9, iPeso);
            stmt.setInt(10, iTiempoPregunta);

            stmt.execute();
            System.out.println(stmt.getInt(1));

            respuesta = stmt.getInt(1);

            return respuesta;




        } catch (Exception e) {
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();


        }
        return respuesta;
    }

    public boolean crearPreguntaUnicaRespuestaDocente(OpcionMultipleRespuesta opcion , int idPregunta) {


        boolean respuesta = false;

        String texto = opcion.getTexto();
        boolean esCorrecta = opcion.isEsCorrecta();
        String isCorrecta ="";


        if(esCorrecta){
            isCorrecta = "S";
        }
        else {
            isCorrecta = "N";
        }

        String call = "{ ? = call crearOpcion(?, ?, ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {



            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, isCorrecta);
            stmt.setString(4, texto);

            stmt.execute();

            int resultado = stmt.getInt(1);

            System.out.println("Resultado de la insercion de la opcion: " + resultado);

            if(resultado != 0){
                respuesta = true;
            }

            return respuesta;

        } catch (Exception e) {
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();


        }
        return respuesta;
    }

    public boolean crearPreguntaVerdaderoFalsoDocente(Boolean respuesta, int idPregunta , String texto) {
        boolean retorno = false;
        String isCorrecta = "";
        if(respuesta){
            isCorrecta = "S";
        }
        else {
            isCorrecta = "N";
        }


        String call = "{ ? = call crearOpcion(?, ?, ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {



            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, isCorrecta);
            stmt.setString(4, texto);

            stmt.execute();

            int resultado = stmt.getInt(1);

            System.out.println("Resultado de la insercion de la opcion: " + resultado);

            if(resultado != 0){
                retorno = true;
            }

            return retorno;

        } catch (Exception e) {
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();


        }
        return retorno;
    }

    public boolean guardarPreguntaRespuestaCorta(String texto, int idPregunta) {
        boolean retorno = false;

        String call = "{ ? = call crearOpcion(?, ?, ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {



            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, "S");
            stmt.setString(4, texto);

            stmt.execute();

            int resultado = stmt.getInt(1);

            System.out.println("Resultado de la insercion de la opcion: " + resultado);

            if(resultado != 0){
                retorno = true;
            }

            return retorno;

        } catch (Exception e) {
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();


        }
        return retorno;
    }

    public boolean guardarPreguntaEmparejarDocente(String elementoA, String elementoB, int idPregunta) {
        boolean retorno = false;
        String texto = elementoA + ";" + elementoB;

        String call = "{ ? = call crearOpcion(?, ?, ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, "S");
            stmt.setString(4, texto);

            stmt.execute();

            int resultado = stmt.getInt(1);

            System.out.println("Resultado de la insercion de la opcion: " + resultado);

            if(resultado != 0){
                retorno = true;
            }

            return retorno;

        } catch (Exception e) {
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();


        }
        return retorno;
    }

    public boolean guardarPreguntaMultipleRespuestaDocente(SeleccionMultipleController.OpcionMultiple opcion, int idPregunta) {
        boolean respuesta = false;

        String texto = opcion.getTexto();
        boolean esCorrecta = opcion.isSeleccionada();
        String isCorrecta ="";


        if(esCorrecta){
            isCorrecta = "S";
        }
        else {
            isCorrecta = "N";
        }

        String call = "{ ? = call crearOpcion(?, ?, ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {



            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, isCorrecta);
            stmt.setString(4, texto);

            stmt.execute();

            int resultado = stmt.getInt(1);

            System.out.println("Resultado de la insercion de la opcion: " + resultado);

            if(resultado != 0){
                respuesta = true;
            }

            return respuesta;

        } catch (Exception e) {
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();


        }
        return respuesta;
    }


    public int crearQuiz(int idDocente, int idGrupo, int idMateria, String nombreQuiz, String fechaInicio, int cantidadPreguntas, int tiempo, String hora, String descripcion, int pesoMateria, String tieneTiempo, double notaMinimaPasar) {

        int respuesta = 0;


        String call = "{ ? = call crearExamen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {


            stmt.registerOutParameter(1, Types.INTEGER);

            stmt.setInt(2, idDocente);
            stmt.setInt(3, idGrupo);
            stmt.setInt(4, idMateria);
            stmt.setString(5, nombreQuiz);
            stmt.setString(6, fechaInicio);
            stmt.setInt(7, cantidadPreguntas);
            stmt.setInt(8, tiempo);
            stmt.setString(9, hora);
            stmt.setString(10, descripcion);
            stmt.setInt(11, pesoMateria);
            stmt.setString(12, tieneTiempo);
            stmt.setDouble(13, notaMinimaPasar);


            stmt.execute();
            System.out.println("el quiz es el numero " + stmt.getInt(1));

            respuesta = stmt.getInt(1);

            return respuesta;




        } catch (Exception e) {
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();


        }
        return respuesta;
    }
}
