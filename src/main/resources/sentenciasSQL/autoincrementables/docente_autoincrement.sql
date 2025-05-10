-- Docente
DECLARE
  v_max_id NUMBER;
  v_count  NUMBER;
BEGIN
  SELECT NVL(MAX(idDocente), 0) + 1 INTO v_max_id FROM Docente;
  SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'DOCENTE_SEQ';
  IF v_count = 0 THEN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE DOCENTE_SEQ START WITH ' || v_max_id || ' INCREMENT BY 1 NOCACHE';
  END IF;
END;
/
CREATE OR REPLACE TRIGGER trg_docente_seq
BEFORE INSERT ON Docente
FOR EACH ROW
BEGIN
  IF :NEW.idDocente IS NULL THEN
    SELECT DOCENTE_SEQ.NEXTVAL INTO :NEW.idDocente FROM dual;
  END IF;
END;
/
