create or replace NONEDITIONABLE TRIGGER TRG_VERIFICAR_FECHA_HORA_RESPUESTA_EXAMEN
BEFORE INSERT OR UPDATE ON PreguntaDetalle
                            FOR EACH ROW
DECLARE
PRAGMA AUTONOMOUS_TRANSACTION;
  v_fechaVencimiento TIMESTAMP;
  v_hoy             TIMESTAMP;
  v_msg             VARCHAR2(200);
BEGIN
  -- 1) Obtener fecha+hora límite desde SolucionExamenEstudiante → Examen
SELECT TO_TIMESTAMP(
               e.fechaLimite || ' ' || e.horaLimite,
               'YYYY-MM-DD HH24:MI'
       )
INTO v_fechaVencimiento
FROM SOLUCIONEXAMENESTUDIANTE s
         JOIN EXAMEN e
              ON s.examen_idExamen = e.idExamen
WHERE s.examen_idExamen       = :NEW.SolucionEstudiante_idExamen
  AND s.estudiante_idEstudiante = :NEW.SolucionExamen_idEstudiante;

-- 2) Obtener timestamp actual
SELECT SYSTIMESTAMP
INTO v_hoy
FROM DUAL;

-- 3) Preparar mensaje y loguear
IF v_hoy > v_fechaVencimiento THEN
    v_msg := 'VENCIDO: Detalle '||:NEW.idPreguntaDetalle
          ||' a las '||TO_CHAR(v_hoy,'HH24:MI:SS')
          ||' (límite '||TO_CHAR(v_fechaVencimiento,'HH24:MI:SS')||')';
ELSE
    v_msg := 'VIGENTE: Detalle '||:NEW.idPreguntaDetalle
          ||' a las '||TO_CHAR(v_hoy,'HH24:MI:SS')
          ||' (límite '||TO_CHAR(v_fechaVencimiento,'HH24:MI:SS')||')';
END IF;

INSERT INTO trigger_log(evento) VALUES(v_msg);
COMMIT;

-- 4) Si ya venció, abortamos la DML
IF v_hoy > v_fechaVencimiento THEN
    RAISE_APPLICATION_ERROR(-20050,
      'El examen ya ha vencido. No se puede registrar la respuesta.');
END IF;
END TRG_VERIFICAR_FECHA_HORA_RESPUESTA_EXAMEN;