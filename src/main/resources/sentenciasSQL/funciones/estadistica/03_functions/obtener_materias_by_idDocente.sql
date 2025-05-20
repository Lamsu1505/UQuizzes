CREATE OR REPLACE NONEDITIONABLE FUNCTION obtener_materias_by_idDocente (
    p_idDocente IN DOCENTE.idDocente%TYPE
) RETURN MateriaDTOList
AS
    resultado MateriaDTOList := MateriaDTOList();
BEGIN
SELECT DISTINCT MateriaDTO(m.idMateria, m.nombre)
                    BULK COLLECT INTO resultado
FROM Grupo g
         JOIN Materia m ON g.Materia_idMateria = m.idMateria
WHERE g.Docente_idDocente = p_idDocente;

RETURN resultado;
END;
/