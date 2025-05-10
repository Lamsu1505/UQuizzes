-- Crear la secuencia solo si no existe
DECLARE
  v_max_id NUMBER;
  v_count  NUMBER;
BEGIN
  -- Obtener el siguiente valor para la secuencia
  SELECT NVL(MAX(idbanco), 0) + 1 INTO v_max_id FROM bancopreguntasexamen;

  -- Verificar si la secuencia ya existe
  SELECT COUNT(*) INTO v_count
  FROM user_sequences
  WHERE sequence_name = 'BANCOPREGUNTASEXAMEN_SEQ';

  -- Si no existe, crearla
  IF v_count = 0 THEN
    EXECUTE IMMEDIATE '
      CREATE SEQUENCE bancopreguntasexamen_seq
      START WITH ' || v_max_id || '
      INCREMENT BY 1
      NOCACHE';
  END IF;
END;
/


CREATE OR REPLACE TRIGGER trg_banco_id_seq
BEFORE INSERT ON bancopreguntasexamen
FOR EACH ROW
BEGIN
  IF :NEW.idbanco IS NULL THEN
    SELECT bancopreguntasexamen_seq.NEXTVAL
    INTO :NEW.idbanco
    FROM dual;
  END IF;
END;
/


