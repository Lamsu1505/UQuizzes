create or replace PROCEDURE validarRespuestaCorrectaProcedimiento(
    p_respuesta      IN PREGUNTADETALLE.RESPUESTAESTUDIANTE%TYPE,
    p_idPregunta     IN PREGUNTADETALLE.PREGUNTA_IDPREGUNTA%TYPE,
    p_idEstudiante   IN SOLUCIONEXAMENESTUDIANTE.Estudiante_idEstudiante%TYPE,
    p_idExamen       IN SOLUCIONEXAMENESTUDIANTE.Examen_idExamen%TYPE
) AS
    v_respuestaCorrecta OPCIONES.DESCRIPCIONOPCION%TYPE;  -- Asegúrate del tipo correcto
    v_isCorrecta        PREGUNTADETALLE.ESCORRECTA%TYPE;
BEGIN
    -- Actualizar la respuesta del estudiante
UPDATE PREGUNTADETALLE
SET RESPUESTAESTUDIANTE = p_respuesta
WHERE PREGUNTA_IDPREGUNTA = p_idPregunta
  AND SOLUCIONEXAMEN_IDESTUDIANTE = p_idEstudiante
  AND SOLUCIONESTUDIANTE_IDEXAMEN = p_idExamen;

-- Obtener la opción correcta de la pregunta
SELECT o.DESCRIPCIONOPCION
INTO v_respuestaCorrecta
FROM OPCIONES o
WHERE o.PREGUNTA_IDPREGUNTA = p_idPregunta
  AND o.ESCORRECTA = 'S';

-- Comparar la respuesta del estudiante con la correcta
IF v_respuestaCorrecta = p_respuesta THEN
        v_isCorrecta := 'S';
ELSE
        v_isCorrecta := 'N';
END IF;

    -- Actualizar si la respuesta fue correcta
UPDATE PREGUNTADETALLE
SET ESCORRECTA = v_isCorrecta
WHERE PREGUNTA_IDPREGUNTA = p_idPregunta
  AND SOLUCIONEXAMEN_IDESTUDIANTE = p_idEstudiante
  AND SOLUCIONESTUDIANTE_IDEXAMEN = p_idExamen;

END;
/