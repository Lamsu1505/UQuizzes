create or replace NONEDITIONABLE FUNCTION obtenerMateriasEstudiante(
    p_idEstudiante IN VARCHAR2
) RETURN SYS_REFCURSOR
IS
    c_materias SYS_REFCURSOR;
BEGIN
OPEN c_materias FOR

SELECT e.idestudiante as idEstudiante,
       e.nombre as nombreEstudiante,
       m.nombre as nombreMateria,
       m.idmateria as idmateria
FROM estudiante e
         JOIN detalleGrupo dg ON e.idEstudiante = dg.estudiante_idestudiante
         JOIN grupo g ON g.idgrupo = dg.grupo_idgrupo
         JOIN materia m ON g.materia_idmateria = m.idmateria
WHERE e.idEstudiante = p_idEstudiante
ORDER BY m.nombre;


RETURN c_materias;
END obtenerMateriasEstudiante;
/