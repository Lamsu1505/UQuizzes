CREATE OR REPLACE FUNCTION obtenerExamenesDocente(
    p_idDocente IN VARCHAR2
) RETURN SYS_REFCURSOR
IS
    c_examenes SYS_REFCURSOR;
BEGIN
OPEN c_examenes FOR
SELECT
    e.idExamen AS idExamen,
    e.nombre As nombre_examen,
    m.nombre AS nombre_materia,
    g.nombre AS nombre_grupo,
    e.fecha AS fecha,
    e.hora AS hora
FROM examen e
         JOIN materia m ON e.materia_idmateria = m.idmateria
         JOIN grupo g ON e.grupo_idgrupo = g.idgrupo
WHERE e.docente_idDocente = p_idDocente
ORDER BY fecha;

RETURN c_examenes;
END obtenerExamenesDocente;
/