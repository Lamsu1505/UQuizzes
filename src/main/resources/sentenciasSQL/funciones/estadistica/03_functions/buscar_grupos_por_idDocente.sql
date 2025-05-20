CREATE OR REPLACE FUNCTION obtenerGruposPorDocenteYMateria(
    p_idDocente IN GRUPO.docente_idDocente%TYPE,
    p_idMateria IN GRUPO.materia_idMateria%TYPE
) RETURN GrupoDTOList
AS
    resultado GrupoDTOList := GrupoDTOList();
BEGIN
SELECT GrupoDTO(g.idGrupo, g.nombre)
           BULK COLLECT INTO resultado
FROM Grupo g
WHERE g.docente_idDocente = p_idDocente AND
      g.materia_idMateria = p_idMateria;

RETURN resultado;
END;
/

