create or replace NONEDITIONABLE FUNCTION empezarExamen(
    p_idExamen      IN NUMBER,
    p_idEstudiante  IN NUMBER
) RETURN NUMBER IS
    v_fecha       VARCHAR2(10) := TO_CHAR(SYSDATE, 'YYYY-MM-DD');
    v_horaActual  VARCHAR2(5)  := TO_CHAR(SYSDATE, 'HH24:MI');
BEGIN

INSERT INTO SOLUCIONEXAMENESTUDIANTE (
    EXAMEN_IDEXAMEN,
    ESTUDIANTE_IDESTUDIANTE,
    NOTAFINAL,
    CANTIDADCORRECTAS,
    TIEMPOTOMADOMINUTOS,
    IPEXAMEN,
    FECHAINICIO,
    HORAINICIO
) VALUES (
             p_idExamen,
             p_idEstudiante,
             NULL,
             NULL,
             NULL,
             '167.90.124.2',
             v_fecha,
             v_horaActual
         );

-- Devolver cuántas filas se insertaron (debería ser 1)
RETURN SQL%ROWCOUNT;

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RETURN 0;
WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(
          -20021,
          'Error al iniciar el examen: ' || SQLERRM
        );
END empezarExamen;
/