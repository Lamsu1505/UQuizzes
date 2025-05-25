create or replace NONEDITIONABLE FUNCTION registrarPreguntaDetalleEnSolucion(
    v_idPregunta IN NUMBER,
    v_idUsuario IN NUMBER,
    v_idExamen IN NUMBER
) RETURN NUMBER IS
    v_idPreguntaDetalle NUMBER;

BEGIN
SELECT PREGUNTADETALLE_SEQ.NEXTVAL INTO v_idPreguntaDetalle FROM dual;

INSERT INTO PREGUNTADETALLE (
    IDPREGUNTADETALLE,
    PREGUNTA_IDPREGUNTA,
    RESPUESTAESTUDIANTE,
    ESCORRECTA,
    SOLUCIONESTUDIANTE_IDEXAMEN,
    SOLUCIONEXAMEN_IDESTUDIANTE

) VALUES (
             null,
             v_idPregunta,
             NULL,
             NULL,
             v_idExamen,
             v_idUsuario
         );

RETURN (v_idPreguntaDetalle-1);

RETURN 0;
END registrarPreguntaDetalleEnSolucion;
/