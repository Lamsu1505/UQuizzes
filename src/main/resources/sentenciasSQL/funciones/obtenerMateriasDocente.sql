create or replace FUNCTION obtenerMateriasDocente(
    p_idDocente IN VARCHAR2
) RETURN SYS_REFCURSOR
IS
    c_materias SYS_REFCURSOR;
BEGIN
OPEN c_materias FOR
SELECT DISTINCT  m.nombre AS nombre_materia, m.idmateria  AS id_materia
FROM Docente d
         JOIN Grupo g ON d.iddocente = g.docente_iddocente
         JOIN Materia m ON g.Materia_idMateria = m.idMateria
WHERE d.idDocente = p_idDocente
ORDER BY m.nombre;

RETURN c_materias;
END obtenerMateriasDocente;
