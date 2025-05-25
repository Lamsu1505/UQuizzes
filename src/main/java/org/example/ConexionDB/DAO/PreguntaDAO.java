package org.example.ConexionDB.DAO;

import oracle.jdbc.internal.OracleTypes;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.PruebaPreguntas;

import java.sql.*;
import java.util.*;

public class PreguntaDAO {

    public static boolean validarRespuesta(int idPregunta, String respuesta) {

        System.out.println("en validarRespuestaUnicaRespuesta idPregunta: " + idPregunta);
        System.out.println("en validarRespuestaUnicaRespuesta respuesta: " + respuesta);

        String call = "{ ? = call validarRespuestaUnicaRespuesta(?, ?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(call)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, respuesta);

            stmt.execute();

            int resultado = stmt.getInt(1);

            if(resultado == 1){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validarRespuestaVerdaderoFalso(int idPregunta, String respuesta) {
        System.out.println("en validarRespuestaVerdaderoFalso idPregunta: " + idPregunta);
        System.out.println("en validarRespuestaVerdaderoFalso respuesta: " + respuesta);

        String call = "{ ? = call validarRespuestaVerdaderoFalso(?, ?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(call)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, respuesta);

            stmt.execute();

            int resultado = stmt.getInt(1);

            if(resultado == 1){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validarRespuestaCorta(int idPregunta, List<String> listaOpciones) {
        System.out.println("en validarRespuestaCorta idPregunta: " + idPregunta);
        System.out.println("en validarRespuestaCorta respuesta: " + listaOpciones);

        String call = "{ ? = call validarRespuestaCorta(?, ?) }";

        for(String respuesta : listaOpciones){
            try (Connection conn = ConexionOracle.conectar();
                 CallableStatement stmt = conn.prepareCall(call)) {

                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setInt(2, idPregunta);
                stmt.setString(3, respuesta);

                stmt.execute();

                int resultado = stmt.getInt(1);

                if(resultado == 1){
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean validarRespuestaMultiple(int idPregunta, List<OpcionMultipleRespuesta> opcionesSeleccionadas) {
        System.out.println("en validarRespuestaMultiple idPregunta: " + idPregunta);
        System.out.println("en validarRespuestaMultiple respuesta: " + opcionesSeleccionadas);

        int cantidadOpcionesCorrectas = getCantidadCorrectasPorPregunta(idPregunta);

        int auxiliar=0;

        String call = "{ ? = call validarRespuestaMultiple(?, ?) }";

        for(OpcionMultipleRespuesta opcion :opcionesSeleccionadas){

            try (Connection conn = ConexionOracle.conectar();
                 CallableStatement stmt = conn.prepareCall(call)) {

                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setInt(2, idPregunta);
                stmt.setString(3, opcion.getTexto());

                stmt.execute();

                int resultado = stmt.getInt(1);

                if(resultado == 1){
                    auxiliar++;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(auxiliar==cantidadOpcionesCorrectas && auxiliar == opcionesSeleccionadas.size()){
            return true;
        }
        else{
            return false;
        }
    }

    private static int getCantidadCorrectasPorPregunta(int idPregunta) {
        String call = "{ ? = call getCantidadCorrectasPorPregunta(?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(call)) {

            stmt.registerOutParameter(1, OracleTypes.INTEGER);
            stmt.setInt(2, idPregunta);

            stmt.execute();

            int respuesta = stmt.getInt(1);

            return respuesta;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean validarRespuestaEmparejar(List<String> respuestasUsuario, int idPregunta) {
        System.out.println("en validarRespuestaEmparejar idPregunta: " + idPregunta);
        System.out.println("en validarRespuestaEmparejar respuesta: " + respuestasUsuario);

        int aux =0;
        int cantidadOpcionesCorrectas = getCantidadCorrectasPorPregunta(idPregunta);

        String call = "{ ? = call validarRespuestaEmparejar(?, ?) }";

        for (String respuesta : respuestasUsuario) {

            try (Connection conn = ConexionOracle.conectar();
                 CallableStatement stmt = conn.prepareCall(call)) {

                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.setInt(2, idPregunta);
                stmt.setString(3, respuesta);

                stmt.execute();

                int resultado = stmt.getInt(1);
                if (resultado == 1) {
                    aux++;
                }


            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        if(aux == cantidadOpcionesCorrectas && aux == respuestasUsuario.size()){
            return true;
        }
        else{
            return false;
        }
    }

    public static Map<PruebaPreguntas, List<OpcionMultipleRespuesta>> obtenerPreguntasPorExamen(int idExamen) {
        Map<PruebaPreguntas, List<OpcionMultipleRespuesta>> preguntasMap = new LinkedHashMap<>();

        String call = "{ ? = call getPreguntasByExamen(?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setInt(2, idExamen);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            Map<Integer, PruebaPreguntas> preguntasPorId = new HashMap<>();

            while (rs.next()) {
                System.out.println("en preguntaDao se capturo la pregunta " + rs.getString("enunciado"));

                int idPregunta = rs.getInt("idPregunta");
                PruebaPreguntas pregunta = preguntasPorId.getOrDefault(idPregunta, new PruebaPreguntas());

                if (!preguntasPorId.containsKey(idPregunta)) {
                    pregunta.setIdPregunta(idPregunta);
                    pregunta.setEnunciado(rs.getString("enunciado"));
                    pregunta.setIdTipoPregunta(rs.getInt("idTipoPregunta"));

                    preguntasPorId.put(idPregunta, pregunta);

                    preguntasMap.put(pregunta, new ArrayList<>());
                }

                String textoOpcion = rs.getString("descripcion");
                if (textoOpcion != null) {
                    OpcionMultipleRespuesta opcion = new OpcionMultipleRespuesta(textoOpcion);
                    opcion.setEsCorrecta("S".equalsIgnoreCase(rs.getString("esCorrecta")));
                    preguntasMap.get(pregunta).add(opcion);
                }
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preguntasMap;
    }

    public static int registrarPreguntaDetalleEnSolucion(int idPregunta, int idUsuario, int idExamen) {
        String call = "{ ? = call registrarPreguntaDetalleEnSolucion(?, ?, ?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(call)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setInt(3, idUsuario);
            stmt.setInt(4, idExamen);

            stmt.execute();

            return stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return -1;
    }

    public static boolean guardarRespuesta(int idPregunta, String respuesta, boolean esCorrecta) {
        String respuestaUsuario = "";
        if(esCorrecta){
            respuestaUsuario="S";
        }
        else{
            respuestaUsuario="N";
        }

        String call = "{ ? = call guardarRespuesta(?, ?, ?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(call)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, idPregunta);
            stmt.setString(3, respuesta);
            stmt.setString(4, respuestaUsuario);

            stmt.execute();

            int resultado = stmt.getInt(1);

            if(resultado == 1){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
