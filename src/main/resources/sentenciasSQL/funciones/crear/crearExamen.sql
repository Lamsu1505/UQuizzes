create or replace FUNCTION crearExamen(
    v_idDocente IN NUMBER,
    v_idGrupo IN NUMBER,
    v_idMateria IN NUMBER,
    v_nombre IN VARCHAR2,
    v_fecha IN VARCHAR2,
    v_cantidadPreguntas IN NUMBER,
    v_tiempoMinutos IN NUMBER,
    v_hora IN VARCHAR2,
    v_descripcion IN VARCHAR2,
    v_pesoMateria IN NUMBER,
    v_tieneLimiteTiempo IN VARCHAR2,
    v_notaMinimaPasar IN NUMBER,
    v_fechaLimite IN VARCHAR2,
    v_horaLimite IN VARCHAR2,
    v_cantidadBanco IN NUMBER

) RETURN NUMBER IS
    v_idQuiz NUMBER;

BEGIN
SELECT EXAMEN_SEQ.NEXTVAL INTO v_idQuiz FROM dual;

INSERT INTO Examen (
    idExamen,
    docente_idDocente,
    Grupo_idGrupo,
    Materia_idMateria,
    nombre,
    fecha,
    hora,
    fechaLimite,
    horaLimite,
    cantidadPreguntas,
    tiempoMinutos,
    descripcion,
    pesoMateria,
    tieneLimiteTiempo,
    notaMinimaPasar,
    cantidadPreguntasBanco

) VALUES (
             null,
             v_idDocente,
             v_idGrupo,
             v_idMateria,
             v_nombre,
             v_fecha,
             v_hora,
             v_fechaLimite,
             v_horaLimite,
             v_cantidadPreguntas,
             v_tiempoMinutos,
             v_descripcion,
             v_pesoMateria,
             v_tieneLimiteTiempo,
             v_notaMinimaPasar,
             v_cantidadBanco

         );

RETURN v_idQuiz;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al insertar en Opciones: ' || SQLERRM);
RETURN 0;
END crearExamen;
