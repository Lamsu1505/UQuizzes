create or replace NONEDITIONABLE TRIGGER TRG_VERIFICAR_FECHA_HORA_RESPUESTA_EXAMEN
BEFORE UPDATE ON PreguntaDetalle
                  FOR EACH ROW
DECLARE
v_fechaLimite      DATE;
  v_horaLimite       VARCHAR2(5);
  v_fechaHoraLimite  DATE;
BEGIN
  -- 1) Buscamos la fecha y hora límite desde Examen (convertimos fechaLimite con TO_DATE)
SELECT
    TO_DATE(e.fechaLimite, 'YYYY-MM-DD'),
    e.horaLimite
INTO
    v_fechaLimite,
    v_horaLimite
FROM Examen e
         JOIN SolucionExamenEstudiante s
              ON s.Examen_idExamen         = e.idExamen
                  AND s.Estudiante_idEstudiante = :NEW.SolucionExamen_idEstudiante
WHERE s.Examen_idExamen = :NEW.SolucionEstudiante_idExamen;

-- 2) Construimos un único DATE con fecha y hora
v_fechaHoraLimite := TO_DATE(
                         TO_CHAR(v_fechaLimite, 'YYYY-MM-DD')
                         || ' '
                         || v_horaLimite,
                         'YYYY-MM-DD HH24:MI'
                       );

  -- 3) Comparamos SYSDATE contra ese límite
  IF SYSTIMESTAMP > CAST(v_fechaHoraLimite AS TIMESTAMP) THEN
    RAISE_APPLICATION_ERROR(
      -20001,
      'El examen ya ha vencido. No se puede registrar la respuesta.'
    );
END IF;
END TRG_VERIFICAR_FECHA_HORA_RESPUESTA_EXAMEN;
/