-- PreguntaDetalle
DECLARE
  v_max_id NUMBER;
  v_count  NUMBER;
BEGIN
  SELECT NVL(MAX(idPreguntaDetalle), 0) + 1 INTO v_max_id FROM PreguntaDetalle;
  SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'PREGUNTADETALLE_SEQ';
  IF v_count = 0 THEN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE PREGUNTADETALLE_SEQ START WITH ' || v_max_id || ' INCREMENT BY 1 NOCACHE';
  END IF;
END;
/
CREATE OR REPLACE TRIGGER trg_preguntadetalle_seq
BEFORE INSERT ON PreguntaDetalle
FOR EACH ROW
BEGIN
  IF :NEW.idPreguntaDetalle IS NULL THEN
    SELECT PREGUNTADETALLE_SEQ.NEXTVAL INTO :NEW.idPreguntaDetalle FROM dual;
  END IF;
END;
/