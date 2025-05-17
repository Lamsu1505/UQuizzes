create or replace FUNCTION getUnidadesDocenteByMateria(
    p_idMateria IN VARCHAR2
) RETURN SYS_REFCURSOR
IS
    c_unidades SYS_REFCURSOR;
BEGIN
OPEN c_unidades FOR

SELECT
    u.idunidad AS id_unidad,
    u.nombre AS nombre_unidad
FROM UNIDAD u
         JOIN MATERIA m ON u.materia_idmateria = m.idmateria
WHERE  m.idmateria = p_idmateria;

RETURN c_unidades;
END getUnidadesDocenteByMateria;
/