CREATE OR REPLACE PROCEDURE obtener_usuario_en_sesion (
    p_id_usuario IN VARCHAR2,
    p_es_docente IN BOOLEAN,
    p_resultado OUT SYS_REFCURSOR
)
IS
BEGIN
    IF p_es_docente THEN
        OPEN p_resultado FOR
SELECT * FROM DOCENTE WHERE IDDOCENTE = p_id_usuario;
ELSE
        OPEN p_resultado FOR
SELECT * FROM ESTUDIANTE WHERE IDESTUDIANTE = p_id_usuario;
END IF;
END;
