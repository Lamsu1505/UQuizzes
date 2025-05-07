CREATE OR REPLACE PROCEDURE obtener_examenes_docente (
    p_id_docente IN VARCHAR2,
    p_resultado OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_resultado FOR
        SELECT
            e.NOMBRE AS nombre_examen,
            m.NOMBRE AS nombre_materia,
            g.NOMBRE AS nombre_grupo,
            e.FECHA,
            e.HORA
        FROM EXAMEN e
        JOIN GRUPO g ON e.GRUPO_IDGRUPO = g.IDGRUPO
        JOIN MATERIA m ON g.MATERIA_IDMATERIA = m.IDMATERIA
        WHERE e.DOCENTE_IDDOCENTE = p_id_docente
        ORDER BY e.FECHA;
END;

