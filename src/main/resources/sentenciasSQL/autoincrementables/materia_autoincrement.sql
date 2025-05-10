-- Materia
DECLARE
  v_max_id NUMBER;
  v_count  NUMBER;
BEGIN
  SELECT NVL(MAX(idMateria), 0) + 1 INTO v_max_id FROM Materia;
  SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'MATERIA_SEQ';
  IF v_count = 0 THEN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE MATERIA_SEQ START WITH ' || v_max_id || ' INCREMENT BY 1 NOCACHE';
  END IF;
END;
/
CREATE OR REPLACE TRIGGER trg_materia_seq
BEFORE INSERT ON Materia
FOR EACH ROW
BEGIN
  IF :NEW.idMateria IS NULL THEN
    SELECT MATERIA_SEQ.NEXTVAL INTO :NEW.idMateria FROM dual;
  END IF;
END;
/