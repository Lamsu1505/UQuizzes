CREATE OR REPLACE FUNCTION obtenerExamenesEstudiante(
    p_idEstudiante IN VARCHAR2
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
         JOIN grupo g ON e.grupo_idgrupo = g.idGrupo
         JOIN detalleGrupo dg ON g.idGrupo = dg.grupo_idgrupo
         JOIN estudiante es ON dg.estudiante_idestudiante = es.idestudiante
         JOIN materia m ON e.materia_idmateria = m.idmateria
WHERE es.idEstudiante = p_idEstudiante
ORDER BY fecha;

RETURN c_examenes;
END obtenerExamenesEstudiante;
/