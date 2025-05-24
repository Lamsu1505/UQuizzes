CREATE OR REPLACE FUNCTION obtenerInformacionExamen(
    p_idExamen IN NUMBER
) RETURN SYS_REFCURSOR
IS
    c_info SYS_REFCURSOR;
BEGIN
OPEN c_info FOR
SELECT
    e.idExamen                 AS idexamen,
    e.nombre                   AS nombre,
    e.materia_idMateria        AS idmateria,
    m.nombre                   AS nombre_materia,
    e.grupo_idGrupo            AS idgrupo,
    g.nombre                   AS nombregrupo,
    e.fecha                     AS fecha,
    e.hora                      AS hora,
    e.fechaLimite               AS fechalimite,
    e.horaLimite                AS horalimite,
    e.cantidadPreguntas         AS cantidadpreguntas,
    e.tiempoMinutos             AS tiempominutos,
    e.descripcion               AS descripcion,
    e.pesoMateria               AS pesomateria,
    e.tieneLimiteTiempo         AS tienelimite_tiempo,
    e.notaMinimaPasar           AS notaminima_pasar,
    e.cantidadPreguntasBanco    AS cantidadpreguntasbanco
FROM examen e
         JOIN materia m ON e.materia_idMateria = m.idMateria
         JOIN grupo   g ON e.grupo_idGrupo     = g.idGrupo
WHERE e.idExamen = p_idExamen;
RETURN c_info;
END obtenerInformacionExamen;
/
