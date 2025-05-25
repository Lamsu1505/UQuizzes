create or replace NONEDITIONABLE FUNCTION guardarRespuesta(
    p_idPreguntaDetalle IN NUMBER,
    p_respuesta         IN VARCHAR2,
    p_isCorrecta        IN VARCHAR2
) RETURN NUMBER IS
    v_rows NUMBER;
BEGIN
UPDATE PreguntaDetalle
SET RESPUESTAESTUDIANTE   = p_respuesta,
    esCorrecta  = p_isCorrecta
WHERE idPreguntaDetalle = p_idPreguntaDetalle;

v_rows := SQL%ROWCOUNT;

RETURN v_rows;
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(
          -20030,
          'Error al guardar la respuesta: ' || SQLERRM
        );
END guardarRespuesta;
/