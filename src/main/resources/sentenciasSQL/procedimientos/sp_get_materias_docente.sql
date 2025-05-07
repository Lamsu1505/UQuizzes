CREATE OR REPLACE PROCEDURE sp_get_materias_docente (
    p_id_docente IN VARCHAR2,
    p_resultado OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN p_resultado FOR
        SELECT DISTINCT m.nombre
        FROM Docente d
                 JOIN Grupo g ON d.iddocente = g.docente_iddocente
                 JOIN Materia m ON g.materia_idmateria = m.idmateria
        WHERE d.iddocente = p_id_docente
        ORDER BY m.nombre;
END;
