create or replace FUNCTION getPreguntasByExamen(
    p_idExamen examen.idExamen%TYPE
)
RETURN SYS_REFCURSOR
IS
    c_preguntas SYS_REFCURSOR;
BEGIN
OPEN c_preguntas FOR

SELECT DISTINCT p.idPregunta as idPregunta,
        p.enunciado as enunciado,
        o.descripcionopcion as descripcion,
        o.esCorrecta as esCorrecta,
        p.tipoPregunta_idtipo as idTipoPregunta
FROM Examen e
         JOIN Bancopreguntasexamen bc ON e.idExamen = bc.examen_idexamen
         JOIN preguntasBanco pb ON bc.idBanco = pb.bancoex_idbanco
         JOIN pregunta p ON pb.pregunta_idpregunta = p.idpregunta
         JOIN opciones o ON p.idpregunta = o.pregunta_idPregunta
WHERE e.idExamen = p_idExamen ;

RETURN c_preguntas;
END getPreguntasByExamen;
/




"SELECT p.idPregunta, p.enunciado, p.TipoPregunta_idTipo, o.descripcionOpcion, o.esCorrecta " +
                "FROM BancoPreguntasExamen bpe " +
                "JOIN PreguntasBanco pb ON bpe.idBanco = pb.BancoEx_idBanco " +
                "JOIN Pregunta p ON pb.Pregunta_idPregunta = p.idPregunta " +
                "LEFT JOIN Opciones o ON p.idPregunta = o.Pregunta_idPregunta " +
                "WHERE bpe.Examen_idExamen = ? " +
                "ORDER BY p.idPregunta, o.idOpcion";