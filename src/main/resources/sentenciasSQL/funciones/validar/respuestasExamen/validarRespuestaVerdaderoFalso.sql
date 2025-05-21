CREATE OR REPLACE FUNCTION validarRespuestaVerdaderoFalso (
    p_idPregunta  IN pregunta.idpregunta%TYPE,
    p_respuesta   IN VARCHAR2
) RETURN NUMBER
AS
    v_retorno NUMBER := 0;
BEGIN
SELECT COUNT(*)
INTO v_retorno
FROM opciones
WHERE pregunta_idPregunta = p_idPregunta
  AND esCorrecta = 'S'
  AND descripcionopcion = p_respuesta;

RETURN v_retorno;
EXCEPTION
    WHEN OTHERS THEN
        RETURN 0; -- En caso de error, devuelve 0 (opcionalmente puedes loguear el error)
END validarRespuestaVerdaderoFalso;
/