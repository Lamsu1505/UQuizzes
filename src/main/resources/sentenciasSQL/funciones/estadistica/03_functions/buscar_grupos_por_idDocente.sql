CREATE OR REPLACE FUNCTION obtenerGruposPorDocente(
    p_idDocente IN GRUPO.docente_idDocente%TYPE
) RETURN GrupoDTOList
AS
    resultado GrupoDTOList := GrupoDTOList();
BEGIN
SELECT GrupoDTO(g.idGrupo, g.nombre)
           BULK COLLECT INTO resultado
FROM Grupo g
WHERE g.docente_idDocente = p_idDocente;

RETURN resultado;
END;
/

