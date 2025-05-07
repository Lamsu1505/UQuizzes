CREATE OR REPLACE PROCEDURE sp_get_grupos_docente_materia (
    p_id_docente IN VARCHAR2,
    p_nombre_materia IN VARCHAR2,
    p_resultado OUT SYS_REFCURSOR
)
AS
BEGIN
OPEN p_resultado FOR
SELECT g.nombre AS nombreGrupo
FROM Docente d
         JOIN Grupo g ON d.iddocente = g.docente_iddocente
         JOIN Materia m ON g.materia_idmateria = m.idmateria
WHERE d.iddocente = p_id_docente AND m.nombre = p_nombre_materia
ORDER BY m.nombre;
END;
