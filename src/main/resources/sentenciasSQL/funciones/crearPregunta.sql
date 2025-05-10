create or replace FUNCTION crearPregunta(
    p_idPregunta IN NUMBER,
    P_idTema IN NUMBER,
    P_idTipoPregunta IN NUMBER,
    P_idPreguntaPadre IN NUMBER,
    P_idNivelPregunta IN NUMBER,
    P_esPublica IN VARCHAR2 ,
    P_enunciado IN VARCHAR2,
    P_pesoPorcentaje IN NUMBER,
    P_tiempoResponderMinutos IN NUMBER
) RETURN NUMBER IS

BEGIN
INSERT INTO Pregunta (
    idpregunta,
    tema_idTema,
    tipoPregunta_idTipo,
    pregunta_idPregunta,
    nivelPregunta_idNivelPregunta,
    esPublica,
    enunciado,
    pesoPorcentaje,
    tiempoResponderMinutos
) VALUES (
             p_idPregunta,
             P_idTema,
             P_idTipoPregunta,
             P_idPreguntaPadre,
             P_idNivelPregunta,
             p_esPublica,
             P_enunciado,
             P_pesoPorcentaje,
             P_tiempoResponderMinutos
         );

RETURN 1;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al insertar en Pregunta: ' || SQLERRM);
RETURN 0;
END crearPregunta;
/