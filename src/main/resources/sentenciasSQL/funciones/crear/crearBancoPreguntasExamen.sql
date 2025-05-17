create or replace FUNCTION crearBancoPreguntasExamen(
    p_idExamen IN NUMBER,
    p_cantidadPreguntas IN NUMBER,
    p_isPublica IN VARCHAR
)
RETURN NUMBER IS
    v_idBanco NUMBER;
BEGIN

SELECT BANCOPREGUNTASEXAMEN_SEQ.NEXTVAL INTO v_idBanco FROM dual;

INSERT INTO bancoPreguntasExamen (
    idBanco,
    examen_idExamen,
    cantidadPreguntas,
    esPublico
) VALUES (
             null,
             p_idExamen,
             p_cantidadPreguntas,
             p_isPublica
         );

RETURN v_idBanco;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al insertar en Opciones: ' || SQLERRM);
RETURN 0;
END crearBancoPreguntasExamen;
/