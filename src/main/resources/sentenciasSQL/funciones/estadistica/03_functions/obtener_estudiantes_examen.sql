CREATE OR REPLACE FUNCTION obtener_estudiantes_examen(
  p_idExamen IN NUMBER
) RETURN estudiante_examen_tab
AS
  resultado estudiante_examen_tab := estudiante_examen_tab();
BEGIN
SELECT estudiante_examen_obj(
               s.fechaInicio,       -- suponiendo que "fecha" viene de aquí
               e.codigo,
               e.nombre,
               e.apellido,
               s.notaFinal,
               s.tiempoTomadoMinutos
       )
           BULK COLLECT INTO resultado
FROM Grupo g
         JOIN DetalleGrupo dg ON g.idGrupo = dg.Grupo_idGrupo
         JOIN Estudiante e ON dg.Estudiante_idEstudiante = e.idEstudiante
         JOIN SolucionExamenEstudiante s ON s.Estudiante_idEstudiante = e.idEstudiante
WHERE g.idGrupo = p_idGrupo
  AND s.Examen_idExamen = p_idExamen;

RETURN resultado;
END;
/