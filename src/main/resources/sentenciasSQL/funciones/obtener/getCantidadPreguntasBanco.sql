create or replace NONEDITIONABLE FUNCTION getCantidadPreguntasBanco(
    p_idExamen   IN NUMBER
) RETURN NUMBER IS
    v_retorno NUMBER := 0;
BEGIN

SELECT COUNT(*) INTO v_retorno
FROM bancoPreguntasExamen bp
         JOIN preguntasBanco pb ON bp.idBanco = pb.bancoEx_idbanco
WHERE bp.examen_idExamen = p_idExamen;

RETURN v_retorno;

EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/