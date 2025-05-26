CREATE OR REPLACE NONEDITIONABLE FUNCTION obtenerEstadisticasExamenesPresentados(
    p_nombreExamen IN VARCHAR2,
    p_idGrupo      IN NUMBER
) RETURN SYS_REFCURSOR IS

    rc SYS_REFCURSOR;

BEGIN
OPEN rc FOR
SELECT
    s.fechaInicio AS fechaInicio,
    s.horaInicio AS horaInicio,
    st.codigo AS codigo,
    st.nombre AS nombre,
    st.apellido AS apellido,
    s.notaFinal AS notaFinal,
    s.tiempoTomadoMinutos AS tiempoTomado
FROM SolucionExamenEstudiante s
         JOIN Examen e ON s.Examen_idExamen = e.idExamen
         JOIN Estudiante st ON s.Estudiante_idEstudiante = st.idEstudiante
WHERE e.nombre = p_nombreExamen
  AND e.grupo_idGrupo = p_idGrupo
ORDER BY s.fechaInicio, s.horaInicio;

RETURN rc;
END obtenerEstadisticasExamenesPresentados;
/
