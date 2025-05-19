-- Tipo para devolver un grupo
CREATE OR REPLACE TYPE GrupoDTO AS OBJECT (
    idGrupo NUMBER,
    nombre VARCHAR2(100)
);