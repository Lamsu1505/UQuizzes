create or replace NONEDITIONABLE FUNCTION getCantidadCorrectasPorPregunta(
    p_idPregunta pregunta.idpregunta%type
)
RETURN NUMBER IS
    v_retorno NUMBER :=0;

BEGIN
SELECT COUNT(*)
INTO v_retorno
FROM opciones
WHERE pregunta_idPregunta = p_idPregunta
  AND esCorrecta = 'S';

return v_retorno;
EXCEPTION
    WHEN OTHERS THEN
        RETURN 0;
end getCantidadCorrectasPorPregunta;
/