-- Estudiante
DECLARE
  v_max_id NUMBER;
  v_count  NUMBER;
BEGIN
  SELECT NVL(MAX(idEstudiante), 0) + 1 INTO v_max_id FROM Estudiante;
  SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'ESTUDIANTE_SEQ';
  IF v_count = 0 THEN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE ESTUDIANTE_SEQ START WITH ' || v_max_id || ' INCREMENT BY 1 NOCACHE';
  END IF;
END;
/
CREATE OR REPLACE TRIGGER trg_estudiante_seq
BEFORE INSERT ON Estudiante
FOR EACH ROW
BEGIN
  IF :NEW.idEstudiante IS NULL THEN
    SELECT ESTUDIANTE_SEQ.NEXTVAL INTO :NEW.idEstudiante FROM dual;
  END IF;
END;
/