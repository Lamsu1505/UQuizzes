create or replace NONEDITIONABLE FUNCTION validarRespuestaEmparejar(
    p_idPregunta pregunta.idpregunta%type,
    p_respuestaEstudiante VARCHAR2
)
RETURN NUMBER IS
    v_retorno NUMBER :=0;

BEGIN
SELECT COUNT(*)
INTO v_retorno
FROM opciones
WHERE pregunta_idPregunta = p_idpregunta
  AND esCorrecta = 'S'
  AND descripcionOpcion = p_respuestaEstudiante;

return v_retorno;
EXCEPTION
    WHEN OTHERS THEN
        RETURN 0;
end validarRespuestaEmparejar;
/