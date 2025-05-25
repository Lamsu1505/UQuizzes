create or replace NONEDITIONABLE TRIGGER trg_validar_fechas_horas_examen
BEFORE INSERT OR UPDATE ON Examen --INSERT OR
FOR EACH ROW
DECLARE
v_fecha_actual DATE := TRUNC(SYSDATE);          -- solo la fecha
    v_fecha_hora_actual DATE := SYSDATE;            -- fecha y hora completas
BEGIN


    IF :NEW.cantidadPreguntasBanco < :NEW.cantidadPreguntas THEN
        RAISE_APPLICATION_ERROR(-20010, 'La cantidad de preguntas en el banco debe ser mayor o igual a la cantidad de preguntas del examen.');
    END IF;

       -- Validar que la fecha de inicio no sea menor que hoy
    IF TO_DATE(:NEW.fecha, 'YYYY-MM-DD') < v_fecha_actual THEN
        RAISE_APPLICATION_ERROR(-20001, 'La fecha de inicio del examen no puede ser anterior a hoy.');
END IF;

    -- Validar que la fecha límite sea posterior a la fecha de inicio
    IF TO_DATE(:NEW.fechaLimite, 'YYYY-MM-DD') < TO_DATE(:NEW.fecha, 'YYYY-MM-DD') THEN
        RAISE_APPLICATION_ERROR(-20002, 'La fecha límite no puede ser anterior a la fecha de inicio.');
END IF;

    -- Validar hora si el examen inicia hoy
    IF TO_DATE(:NEW.fecha, 'YYYY-MM-DD') = v_fecha_actual THEN
        IF TO_DATE(:NEW.hora, 'HH24:MI') < TO_DATE(TO_CHAR(SYSDATE, 'HH24:MI'), 'HH24:MI') THEN
            RAISE_APPLICATION_ERROR(-20003, 'La hora de inicio no puede ser anterior a la hora actual.');
END IF;
END IF;
END;
/