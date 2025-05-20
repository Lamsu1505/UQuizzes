CREATE OR REPLACE FUNCTION get_examen_by_grupo (
    p_idGrupo IN EXAMEN.grupo_idgrupo%TYPE
) RETURN SYS_REFCURSOR
AS
    v_cursor SYS_REFCURSOR;
BEGIN
OPEN v_cursor FOR
SELECT id_examen, nombre_examen
FROM examen
WHERE id_grupo = p_idGrupo;

RETURN v_cursor;
END;
/
