CREATE OR REPLACE NONEDITIONABLE TRIGGER TRG_VALIDAR_FECHA_HORA_INICIO_EXAMEN
BEFORE INSERT ON SolucionExamenEstudiante
FOR EACH ROW
DECLARE
v_limite  DATE;
  v_horaAct VARCHAR2(5);
BEGIN
  -- Construimos un DATE con fecha y hora:
SELECT TO_DATE(fechaLimite || ' ' || horaLimite,
               'YYYY-MM-DD HH24:MI')
INTO v_limite
FROM Examen
WHERE idExamen = :NEW.Examen_idExamen;

v_horaAct := TO_CHAR(SYSDATE, 'HH24:MI');

  IF SYSDATE > v_limite THEN
    RAISE_APPLICATION_ERROR(-20002,
      'No se puede registrar: el examen ya expiró.');
END IF;
END TRG_VALIDAR_FECHA_HORA_INICIO_EXAMEN;
/
