-- Tipo para devolver un examen
CREATE OR REPLACE TYPE ExamenDTO AS OBJECT (
    idExamen NUMBER,
    nombre VARCHAR2(100)
);

/
-- Tabla de tipo ExamenDTO
CREATE OR REPLACE TYPE ExamenDTOList AS TABLE OF ExamenDTO;
/

CREATE OR REPLACE FUNCTION obtenerExamenesPorGrupoYDocente(
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