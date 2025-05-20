package org.example.ConexionDB.DAO;

import oracle.jdbc.internal.OracleTypes;
import org.example.ConexionDB.ConexionOracle;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;
import org.example.Model.PruebaPreguntas;

import java.sql.*;
import java.util.*;

public class PreguntaDAO {

    public Map<PruebaPreguntas, List<OpcionMultipleRespuesta>> obtenerPreguntasPorExamen(int idExamen) {
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

}
