CREATE OR REPLACE PROCEDURE sp_get_unidades_materia (
    p_nombre_materia IN VARCHAR2,
    p_resultado OUT SYS_REFCURSOR
)
AS
BEGIN
OPEN p_resultado FOR
SELECT u.nombre
FROM unidad u
         JOIN materia m ON u.materia_idmateria = m.idmateria
WHERE m.nombre = p_nombre_materia;
END;