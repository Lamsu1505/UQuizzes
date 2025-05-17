create or replace FUNCTION crearPregunta(
    p_idPregunta IN NUMBER,
    P_idTema IN NUMBER,
    P_idTipoPregunta IN NUMBER,
    P_idPreguntaPadre IN NUMBER,
    P_idNivelPregunta IN NUMBER,
    P_esPublica IN VARCHAR2,
    P_enunciado IN VARCHAR2,
    P_pesoPorcentaje IN NUMBER,
    P_tiempoResponderMinutos IN NUMBER
) RETURN NUMBER IS
    v_idPregunta NUMBER;
BEGIN
    -- Generar el ID desde la secuencia manualmente
SELECT PREGUNTA_SEQ.NEXTVAL INTO v_idPregunta FROM dual;

-- Insertar la pregunta con el ID generado
INSERT INTO Pregunta (
    idPregunta,
    tema_idTema,
    tipoPregunta_idTipo,
    pregunta_idPregunta,
    nivelPregunta_idNivelPregunta,
    esPublica,
    enunciado,
    pesoPorcentaje,
    tiempoResponderMinutos
) VALUES (
             null,
             P_idTema,
             P_idTipoPregunta,
             P_idPreguntaPadre,
             P_idNivelPregunta,
             P_esPublica,
             P_enunciado,
             P_pesoPorcentaje,
             P_tiempoResponderMinutos
         );

RETURN v_idPregunta;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al insertar en Pregunta: ' || SQLERRM);
RETURN 0;
END crearPregunta;