create or replace TRIGGER trg_bloquear_cambios_examen
BEFORE UPDATE ON EXAMEN
FOR EACH ROW
DECLARE
v_existe NUMBER;
BEGIN
SELECT COUNT(*) INTO v_existe
FROM SOLUCIONEXAMENESTUDIANTE
WHERE Examen_idExamen = :OLD.idExamen;

-- Verifica si el examen ya fue presentado por al menos un estudiante
IF v_existe > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'No se puede modificar o eliminar un examen que ya fue presentado por al menos un estudiante.');
END IF;
END;
/