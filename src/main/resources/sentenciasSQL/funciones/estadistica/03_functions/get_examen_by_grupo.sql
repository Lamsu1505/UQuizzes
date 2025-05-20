create or replace  FUNCTION get_examen_by_grupo (
    p_idGrupo IN EXAMEN.grupo_idgrupo%TYPE
) RETURN SYS_REFCURSOR
AS
    v_cursor SYS_REFCURSOR;
BEGIN
OPEN v_cursor FOR
SELECT idexamen AS idExamen, nombre AS nombre
FROM examen
WHERE grupo_idgrupo = p_idGrupo;

RETURN v_cursor;
END;
/
