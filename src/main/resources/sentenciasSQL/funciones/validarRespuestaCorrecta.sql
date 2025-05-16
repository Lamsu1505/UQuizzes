--Funcion que retorna si una pregunta fue contestada correctamente, y pone 's' en preguntaDetalle.esCorrecta
create or replace FUNCTION validarRespuestaCorrecta(
    p_respuesta IN PREGUNTADETALLE.RESPUESTAESTUDIANTE%TYPE,
    p_idPregunta IN PREGUNTADETALLE.PREGUNTA_IDPREGUNTA%TYPE,
    p_idEstudiante IN SOLUCIONEXAMENESTUDIANTE.Estudiante_idEstudiante%TYPE,
    p_idExamen IN SOLUCIONEXAMENESTUDIANTE.Examen_idExamen%TYPE
)
RETURN VARCHAR2 IS
v_respuestaCorrecta OPCIONES.ESCORRECTA%TYPE ;
v_isCorrecta PREGUNTADETALLE.ESCORRECTA%TYPE;


BEGIN


SELECT
    o.descripcionOpcion INTO v_respuestaCorrecta
FROM PREGUNTA p
         JOIN OPCIONES o ON p.idPregunta = o.Pregunta_idPregunta
WHERE p.idPregunta = p_idPregunta AND o.ESCORRECTA = 'S';


IF v_respuestaCorrecta = p_respuesta
    THEN v_isCorrecta := 'S';
ELSE v_isCorrecta := 'N';
END IF;


UPDATE PREGUNTADETALLE
SET esCorrecta = v_isCorrecta
WHERE SolucionEstudiante_idExamen = p_idExamen AND SolucionExamen_idEstudiante = p_idEstudiante;


Return  v_isCorrecta;


end validarRespuestaCorrecta;
