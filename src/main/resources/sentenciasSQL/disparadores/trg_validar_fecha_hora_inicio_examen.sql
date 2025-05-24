CREATE OR REPLACE TRIGGER TRG_VALIDAR_FECHA_HORA_INICIO_EXAMEN
BEFORE INSERT ON SolucionExamenEstudiante
FOR EACH ROW
DECLARE
v_fechaLimite DATE;
    v_horaLimite  VARCHAR2(5);
    v_horaActual  VARCHAR2(5);
BEGIN
    -- Obtener los valores límite de la tabla Examen
SELECT fechaLimite, horaLimite
INTO v_fechaLimite, v_horaLimite
FROM Examen
WHERE idExamen = :NEW.Examen_idExamen;

-- Obtener hora actual en formato HH24:MI
v_horaActual := TO_CHAR(SYSDATE, 'HH24:MI');

    -- Verificar si la fecha actual está después de la fecha límite
    IF SYSDATE > v_fechaLimite THEN
        RAISE_APPLICATION_ERROR(-20001, 'No se puede registrar: la fecha del examen ya ha expirado.');
    ELSIF SYSDATE = v_fechaLimite AND v_horaActual > v_horaLimite THEN
        RAISE_APPLICATION_ERROR(-20002, 'No se puede registrar: la hora del examen ya ha expirado.');
END IF;
END;
/