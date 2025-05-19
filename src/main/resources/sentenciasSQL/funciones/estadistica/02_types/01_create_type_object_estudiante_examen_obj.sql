-- Tipo de objeto
CREATE OR REPLACE TYPE estudiante_examen_obj AS OBJECT (
  fecha           VARCHAR2(25),
  codigo          VARCHAR2(20),
  nombre          VARCHAR2(50),
  apellido        VARCHAR2(50),
  notaFinal       NUMBER,
  tiempoTomado    NUMBER
);