create or replace FUNCTION obtenerGruposDocenteByMateria(
    p_idDocente IN VARCHAR2,
    p_idMateria IN VARCHAR2
) RETURN SYS_REFCURSOR
IS
    c_grupos SYS_REFCURSOR;
BEGIN
OPEN c_grupos FOR
SELECT
    g.idgrupo AS id_grupo,
    g.nombre AS nombre_grupo
FROM GRUPO g
         JOIN MATERIA m ON g.materia_idmateria = m.idmateria
WHERE g.docente_idDocente = p_idDocente AND m.idmateria = p_idMateria
ORDER BY 1;

RETURN c_grupos;
END obtenerGruposDocenteByMateria;
/

