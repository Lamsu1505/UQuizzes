create or replace FUNCTION getTemasDocenteByUnidad(
    p_idUnidad IN VARCHAR2
) RETURN SYS_REFCURSOR
IS
    c_temas SYS_REFCURSOR;
BEGIN
OPEN c_temas FOR

SELECT
    t.idtema AS id_tema,
    t.nombre AS nombre
FROM UNIDAD u
         JOIN TEMA t ON u.idunidad = t.unidad_idunidad
WHERE  u.idunidad = p_idUnidad
ORDER BY nombre;

RETURN c_temas;
END getTemasDocenteByUnidad;
/