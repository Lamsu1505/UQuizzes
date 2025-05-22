create or replace NONEDITIONABLE FUNCTION addPreguntasAlBancoManual(
    p_idBanco   IN NUMBER,
    p_idPregunta IN NUMBER
) RETURN NUMBER IS
BEGIN
INSERT INTO PreguntasBanco (BancoEx_idBanco, Pregunta_idPregunta)
VALUES (p_idBanco, p_idPregunta);

RETURN 1;

EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END;
/
