-- Tipo para devolver un examen
CREATE OR REPLACE TYPE ExamenDTO AS OBJECT (
    idExamen NUMBER,
    nombre VARCHAR2(100)
);