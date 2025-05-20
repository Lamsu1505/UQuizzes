CREATE OR REPLACE FUNCTION get_examen_by_grupo_docente(
    p_idGrupo IN GRUPO.idgrupo%TYPE,
    p_idDocente IN DOCENTE.idDocente%TYPE
) RETURN ExamenDTOList
AS
    resultado ExamenDTOList := ExamenDTOList();
BEGIN
SELECT ExamenDTO(e.idExamen, e.nombre)
           BULK COLLECT INTO resultado
FROM Examen e
WHERE e.grupo_idGrupo = p_idGrupo AND e.docente_idDocente = p_idDocente;

RETURN resultado;
END;
/