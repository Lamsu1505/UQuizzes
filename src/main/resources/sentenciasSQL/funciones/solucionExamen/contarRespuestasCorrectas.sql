create or replace NONEDITIONABLE FUNCTION contarRespuestasCorrectas(
    p_idSolucionEstudiante      IN NUMBER,
    p_idEstudiante  IN NUMBER
) RETURN NUMBER IS
v_cantidad NUMBER := 0;
BEGIN
SELECT COUNT(*)
INTO v_cantidad
FROM PreguntaDetalle pd
WHERE pd.SolucionEstudiante_idExamen = p_idSolucionEstudiante
  AND pd.SolucionExamen_idEstudiante = p_idEstudiante
  AND pd.esCorrecta = 'S';

RETURN v_cantidad;
end contarRespuestasCorrectas;
/