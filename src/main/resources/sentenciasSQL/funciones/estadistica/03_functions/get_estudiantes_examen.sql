CREATE OR REPLACE PROCEDURE get_estudiantes_examen (
    p_idExamen IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
OPEN p_cursor FOR
SELECT estudiante_examen_obj(
               s.fechaInicio,
               s.horaInicio,
               e.codigo,
               e.nombre,
               e.apellido,
               s.notaFinal
       )
FROM
    SolucionExamenEstudiante s
        JOIN
    Estudiante e ON s.Estudiante_idEstudiante = e.idEstudiante
WHERE
    s.Examen_idExamen = p_idExamen;
END;
/
