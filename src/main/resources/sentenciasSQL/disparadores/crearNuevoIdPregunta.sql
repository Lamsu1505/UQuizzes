create or replace TRIGGER nuevoIdPregunta
BEFORE INSERT ON Pregunta
FOR EACH ROW
BEGIN
    IF :NEW.idPregunta IS NULL THEN
    :NEW.idPregunta := 90;
END IF;
END;
/