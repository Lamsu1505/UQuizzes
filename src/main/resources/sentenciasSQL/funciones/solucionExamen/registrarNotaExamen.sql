CREATE OR REPLACE NONEDITIONABLE FUNCTION registrarNotaExamen(
    p_idSolucionEstudiante  IN NUMBER,
    p_idEstudiante          IN NUMBER,
    p_notaFinal             IN NUMBER,
    p_cantidadCorrectas     IN NUMBER
) RETURN NUMBER IS

    v_fechaFin     DATE := SYSDATE;
    v_fechaInicio  DATE;
    v_tiempoMin    NUMBER;

BEGIN

SELECT TO_DATE(fechaInicio || ' ' || horaInicio, 'YYYY-MM-DD HH24:MI')
INTO v_fechaInicio
FROM SolucionExamenEstudiante
WHERE examen_idExamen       = p_idSolucionEstudiante
  AND estudiante_idEstudiante = p_idEstudiante;

v_tiempoMin := ROUND((v_fechaFin - v_fechaInicio) * 24 * 60, 2);

UPDATE SolucionExamenEstudiante
SET notaFinal            = p_notaFinal,
    cantidadCorrectas    = p_cantidadCorrectas,
    tiempoTomadoMinutos  = v_tiempoMin
WHERE examen_idExamen       = p_idSolucionEstudiante
  AND estudiante_idEstudiante = p_idEstudiante;

IF SQL%ROWCOUNT > 0 THEN
        RETURN 1;
ELSE
        RETURN 0;
END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Si no encontró la fila de inicio, no se pudo calcular
        RETURN 0;
WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(
          -20060,
          'Error al registrar nota del examen: ' || SQLERRM
        );
END registrarNotaExamen;
/
