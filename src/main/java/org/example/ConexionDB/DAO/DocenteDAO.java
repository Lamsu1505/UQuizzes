// src/main/java/org/example/ConexionDB/DAO/DocenteDAO.java
package org.example.ConexionDB.DAO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import oracle.jdbc.internal.OracleTypes;
import org.example.ConexionDB.ConexionOracle;
import org.example.Controllers.Paneles.Docente.TiposPregunta.SeleccionMultipleController;
import org.example.Model.Docente.EstudianteExamenInfo;
import org.example.Model.Docente.ExamenDTO;
import org.example.Model.Docente.GrupoDTO;
import org.example.Model.Docente.MateriaDTO;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.Pregunta;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DocenteDAO {


    public static List<Map<String, Object>> obtenerExamenById(int idExamen) {
        String call = "{ ? = call obtenerInformacionExamen(?) }";

        List<Map<String, Object>> listaExamenes = new ArrayList<>();

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setInt(2, idExamen);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    for (int i = 1; i <= cols; i++) {
                        fila.put(md.getColumnLabel(i), rs.getObject(i));
                    }
                    listaExamenes.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();}
        return listaExamenes;
    }

    public static boolean editarQuiz(
            int idExamen,
            int idDocente,
            String nombreQuiz,
            String fechaInicio,
            int cantidadPreguntas,
            int tiempo,
            String hora,
            String descripcion,
            int pesoMateria,
            String tieneTiempo,
            double notaMinimaPasar,
            String fechaLimite,
            String horaLimite,
            int cantidadPreguntasBanco
    ) throws SQLException {
        boolean respuesta = false;

        // Ahora tenemos 14 parámetros IN, así que 14 signos de ? dentro de los paréntesis:
        String call = "{ ? = call editarExamen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            stmt.registerOutParameter(1, Types.INTEGER);

            // 1er IN  → p_idExamen
            stmt.setInt(2, idExamen);
            // 2º IN  → p_idDocente
            stmt.setInt(3, idDocente);
            // 3º IN  → p_nombre
            stmt.setString(4, nombreQuiz);
            // 4º IN  → p_fecha
            stmt.setString(5, fechaInicio);
            // 5º IN  → p_cantidadPreguntas
            stmt.setInt(6, cantidadPreguntas);
            // 6º IN  → p_tiempoMinutos
            stmt.setInt(7, tiempo);
            // 7º IN  → p_hora
            stmt.setString(8, hora);
            // 8º IN  → p_descripcion
            stmt.setString(9, descripcion);
            // 9º IN  → p_pesoMateria
            stmt.setInt(10, pesoMateria);
            // 10º IN → p_tieneLimiteTiempo
            stmt.setString(11, tieneTiempo);
            // 11º IN → p_notaMinimaPasar
            stmt.setDouble(12, notaMinimaPasar);
            // 12º IN → p_fechaLimite
            stmt.setString(13, fechaLimite);
            // 13º IN → p_horaLimite
            stmt.setString(14, horaLimite);
            // 14º IN → p_cantidadBanco
            stmt.setInt(15, cantidadPreguntasBanco);

            stmt.execute();

            // el RETURN de la función PL/SQL llega en getInt(1)
            respuesta = (stmt.getInt(1) == 1);
            return respuesta;

        } catch (SQLException ex) {
            // tu bloque personalizado de catch, sin tocar
            int errorCode = ex.getErrorCode();
            String mensaje   = ex.getMessage();
            switch (errorCode) {
                case 20001:
                case 20002:
                case 20003:
                case 20010:
                    throw new SQLException(mensaje, ex.getSQLState(), errorCode);
                default:
                    throw ex;
            }
        }
    }


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
            System.out.println(" EL RESULTADO DE INSERTAR PREGUNTA ES " +stmt.getInt(1));

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
        System.out.println(texto);
        boolean esCorrecta = opcion.isEsCorrecta();
        System.out.println(esCorrecta);

        String isCorrecta ;


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


    public int crearQuiz(int idDocente, int idGrupo, int idMateria, String nombreQuiz, String fechaInicio, int cantidadPreguntas, int tiempo, String hora, String descripcion, int pesoMateria, String tieneTiempo, double notaMinimaPasar ,  String fechaLimite , String horaLimite , int cantidadBanco) throws SQLException {

        int respuesta = 0;


        String call = "{ ? = call crearExamen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ? , ?)}";

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
            stmt.setString(14, fechaLimite);
            stmt.setString(15, horaLimite);
            stmt.setInt(16, cantidadBanco);


            stmt.execute();
            System.out.println("el quiz es el numero " + stmt.getInt(1));

            respuesta = stmt.getInt(1);

            return respuesta;


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

    }

    public int crearBancoPreguntasExamen(int idExamenCreado, int cantidadPreguntasBanco) {
        int respuesta = 0;

        String call = "{ ? = call crearBancoPreguntasExamen(?, ? , ?)}";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, Types.INTEGER);

            stmt.setInt(2, idExamenCreado);
            stmt.setInt(3, cantidadPreguntasBanco);
            stmt.setString(4,"S");

            stmt.execute();

            int resultado = stmt.getInt(1);

            System.out.println("el resultado es " + resultado);

            if(resultado != 0){
                respuesta = resultado;
            }


        }catch (Exception e){
            e.printStackTrace();
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR,
                    "Error al crear la pregunta:\n" + e.getMessage()).showAndWait();
        }

        return respuesta;

    }

    public int agregarPreguntasAlBanco(int idBancoCreado, List<Integer> temasSeleccionados) {
        int respuesta = 0;
        String call = "{ ? = call addPreguntasAleatoriasByTemas(?, ?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            // Convertimos la lista a una cadena separada por comas
            String temasCSV = temasSeleccionados.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idBancoCreado);
            stmt.setString(3, temasCSV);

            stmt.execute();

            int resultado = stmt.getInt(1);
            System.out.println("Resultado agregarPreguntasAlBanco: " + resultado);
            respuesta = resultado;

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error al agregar preguntas al banco:\n" + e.getMessage()).showAndWait();
        }

        return respuesta;
    }

    public List<Map<String, Object>> get_examen_by_grupo(String idGrupo) throws SQLException {
        String call = "{ ? = call get_examen_by_grupo(?) }";

        List<Map<String, Object>> listaExamenes = new ArrayList<>();

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setInt(2, Integer.parseInt(idGrupo));
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    for (int i = 1; i <= cols; i++) {
                        fila.put(md.getColumnLabel(i), rs.getObject(i));
                    }
                    listaExamenes.add(fila);
                }
            }
        }
        return listaExamenes;
    }

    public List<EstudianteExamenInfo> obtenerEstudiantesPorExamen(int idExamen) {
        List<EstudianteExamenInfo> lista = new ArrayList<>();

        try {
            Connection conn = new ConexionOracle().conectar();
            CallableStatement stmt = conn.prepareCall("{? = call obtener_estudiantes_examen(?) }");

            stmt.registerOutParameter(1, OracleTypes.ARRAY, "ESTUDIANTEEXAMENINFO");
            stmt.setInt(2, idExamen);

            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                EstudianteExamenInfo dto = new EstudianteExamenInfo();
                dto.setFechaInicio(rs.getString("fechaInicio"));
                dto.setHoraInicio(rs.getString("horaInicio"));;
                dto.setCodigo(rs.getString("codigo"));
                dto.setNombre(rs.getString("nombre"));
                dto.setApellido(rs.getString("apellido"));
                dto.setNotaFinal(rs.getDouble("notaFinal"));

                lista.add(dto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public static int agregarPreguntasAlBancoManual(int idBancoCreado, List<Pregunta> filasSeleccionadas) {
        int suma=0;

        String call = "{ ? = call addPreguntasAlBancoManual(?, ?) }";

        for(Pregunta pregunta : filasSeleccionadas){
            try (
                    Connection conn = new ConexionOracle().conectar();
                    CallableStatement stmt = conn.prepareCall(call)
            ) {

                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setInt(2, idBancoCreado);
                stmt.setInt(3, pregunta.getIdPregunta());


                stmt.execute();

                int resultado = stmt.getInt(1);


                suma=suma + resultado;


            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error al agregar preguntas al banco:\n" + e.getMessage()).showAndWait();
            }
        }
        return suma;
    }


    public static int getCantidadPreguntasBanco(int idExamenCreado) {
        int cantidadPreguntasBanco = 0;

        String call = "{ ? = call getCantidadPreguntasBanco(?) }";

        try (
                Connection conn = new ConexionOracle().conectar();
                CallableStatement stmt = conn.prepareCall(call)
        ) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idExamenCreado);

            stmt.execute();

            cantidadPreguntasBanco = stmt.getInt(1);

            return cantidadPreguntasBanco;

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error al obtener la cantidad de preguntas del banco:\n" + e.getMessage()).showAndWait();
        }
        return -1;
    }

}
