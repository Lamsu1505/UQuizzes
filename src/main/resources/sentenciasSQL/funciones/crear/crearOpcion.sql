CREATE OR REPLACE FUNCTION crearOpcion(
    P_preguntaId IN NUMBER,
    P_esCorrecta IN VARCHAR2,
    P_descripcionOpcion IN VARCHAR2
) RETURN NUMBER IS
    v_idOpcion NUMBER;
BEGIN
    -- Obtener el valor siguiente de la secuencia, aunque el trigger también lo manejará
SELECT OPCIONES_SEQ.NEXTVAL INTO v_idOpcion FROM dual;

-- Insertar opción (usamos v_idOpcion directamente, pero también funcionaría con NULL si se confía 100% en el trigger)
INSERT INTO Opciones (
    idOpcion,
    pregunta_idPregunta,
    esCorrecta,
    descripcionOpcion
) VALUES (
             null,
             P_preguntaId,
             P_esCorrecta,
             P_descripcionOpcion
         );

RETURN v_idOpcion;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al insertar en Opciones: ' || SQLERRM);
RETURN 0;
END crearOpcion;
/
