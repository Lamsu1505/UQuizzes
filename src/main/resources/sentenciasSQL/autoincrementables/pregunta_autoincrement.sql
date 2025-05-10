-- Pregunta
DECLARE
  v_max_id NUMBER;
  v_count  NUMBER;
BEGIN
  SELECT NVL(MAX(idPregunta), 0) + 1 INTO v_max_id FROM Pregunta;
  SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'PREGUNTA_SEQ';
  IF v_count = 0 THEN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE PREGUNTA_SEQ START WITH ' || v_max_id || ' INCREMENT BY 1 NOCACHE';
  END IF;
END;
/
CREATE OR REPLACE TRIGGER trg_pregunta_seq
BEFORE INSERT ON Pregunta
FOR EACH ROW
BEGIN
  IF :NEW.idPregunta IS NULL THEN
    SELECT PREGUNTA_SEQ.NEXTVAL INTO :NEW.idPregunta FROM dual;
  END IF;
END;
/