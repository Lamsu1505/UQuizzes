create or replace NONEDITIONABLE TRIGGER trg_verificar_fecha_hora_respuesta_examen
BEFORE INSERT ON PreguntaDetalle
FOR EACH ROW
DECLARE
v_fecha DATE;
    v_hora VARCHAR2(5);
    v_fechaHoraLimite DATE;
BEGIN
    -- Obtener la fecha y hora límite del examen correspondiente
SELECT e.fechaLimite, e.horaLimite
INTO v_fecha, v_hora
FROM Examen e
         JOIN SolucionExamenEstudiante s ON s.Examen_idExamen = e.idExamen
WHERE s.estudiante_idEstudiante = :NEW.SolucionExamen_idEstudiante
  AND s.Examen_idExamen = :NEW.SolucionEstudiante_idExamen;

-- Unir la fecha y la hora en un solo valor tipo DATE
v_fechaHoraLimite := TO_DATE(TO_CHAR(v_fecha, 'YYYY-MM-DD') || ' ' || v_hora, 'YYYY-MM-DD HH24:MI');

    -- Comparar con la fecha y hora actual
    IF SYSDATE > v_fechaHoraLimite THEN
        RAISE_APPLICATION_ERROR(-20001, 'El examen ya ha vencido. No se puede registrar la respuesta.');
END IF;
END;
/