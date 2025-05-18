-- Tipo para devolver un grupo
CREATE OR REPLACE TYPE GrupoDTO AS OBJECT (
    idGrupo NUMBER,
    nombre VARCHAR2(100)
);
/
-- Tabla de tipo GrupoDTO
CREATE OR REPLACE TYPE GrupoDTOList AS TABLE OF GrupoDTO;
/

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

