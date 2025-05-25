create or replace NONEDITIONABLE FUNCTION calcularNotaExamen(
    p_idSolucionEstudiante      IN NUMBER,
    p_idEstudiante  IN NUMBER
) RETURN NUMBER IS
    v_totalPeso   NUMBER;
    v_notaFinal   NUMBER := 0;
    v_maxNota     CONSTANT NUMBER := 5;  -- nota máxima del examen

BEGIN
    -- Suma el peso de todas las preguntas que le tocaron al alumno
SELECT NVL(SUM(p.pesoPorcentaje),0)
INTO v_totalPeso
FROM PreguntaDetalle pd
         JOIN Pregunta        p
              ON pd.Pregunta_idPregunta = p.idPregunta
WHERE pd.SolucionEstudiante_idExamen    = p_idSolucionEstudiante
  AND pd.SolucionExamen_idEstudiante = p_idEstudiante;

IF v_totalPeso = 0 THEN
       RETURN 0;  -- sin preguntas, nota 0
END IF;

    -- Recorrer cada fila de PreguntaDetalle para ese examen/estudiante
FOR rec IN (
      SELECT p.pesoPorcentaje   AS peso,
             pd.esCorrecta      AS correcta
      FROM PreguntaDetalle pd
      JOIN Pregunta        p
        ON pd.Pregunta_idPregunta = p.idPregunta
     WHERE pd.SolucionEstudiante_idExamen    = p_idSolucionEstudiante
       AND pd.SolucionExamen_idEstudiante = p_idEstudiante
    ) LOOP
      IF rec.correcta = 'S' THEN
        -- regla de 3: (peso / pesoTotal * 100) es el %
        v_notaFinal := v_notaFinal + v_maxNota * (rec.peso / v_totalPeso);
END IF;
END LOOP;

RETURN ROUND(v_notaFinal, 2);

EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(
        -20050,
        'Error al calcular nota: ' || SQLERRM
      );
END calcularNotaExamen;
/