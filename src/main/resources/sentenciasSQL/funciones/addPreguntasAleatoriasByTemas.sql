CREATE OR REPLACE FUNCTION addPreguntasAleatoriasByTemas( --pruebaaa
    p_idBanco IN NUMBER,
    p_temasCSV IN VARCHAR2
) RETURN NUMBER IS
    v_totalInsertadas NUMBER := 0;
    v_cantidadTotal NUMBER;
    v_nTemas NUMBER;
    v_porTema NUMBER;
    v_extra NUMBER;
    v_temaID VARCHAR2(10);
    v_limite NUMBER;
    v_index NUMBER := 1;
BEGIN
    -- Obtener cantidad total permitida del banco
SELECT cantidadPreguntas INTO v_cantidadTotal
FROM BancoPreguntasExamen
WHERE idBanco = p_idBanco;

-- Contar cuántos temas hay
v_nTemas := REGEXP_COUNT(p_temasCSV, '[^,]+');

    -- Repartir preguntas
    v_porTema := TRUNC(v_cantidadTotal / v_nTemas);
    v_extra := MOD(v_cantidadTotal, v_nTemas);

    WHILE v_index <= v_nTemas LOOP
        v_temaID := REGEXP_SUBSTR(p_temasCSV, '[^,]+', 1, v_index);
        v_limite := v_porTema + CASE WHEN v_index <= v_extra THEN 1 ELSE 0 END;

INSERT INTO PreguntasBanco (BancoEx_idBanco, Pregunta_idPregunta)
SELECT p_idBanco, sub.idPregunta
FROM (
         SELECT p.idPregunta
         FROM Pregunta p
         WHERE p.Tema_idTema = TO_NUMBER(v_temaID)
           AND NOT EXISTS (
             SELECT 1 FROM PreguntasBanco pb
             WHERE pb.BancoEx_idBanco = p_idBanco
               AND pb.Pregunta_idPregunta = p.idPregunta
         )
         ORDER BY DBMS_RANDOM.VALUE
     ) sub
WHERE ROWNUM <= v_limite;

v_totalInsertadas := v_totalInsertadas + SQL%ROWCOUNT;
        v_index := v_index + 1;
END LOOP;

RETURN v_totalInsertadas;
END;
/
