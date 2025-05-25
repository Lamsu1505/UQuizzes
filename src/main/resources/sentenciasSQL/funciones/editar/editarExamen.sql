CREATE OR REPLACE FUNCTION editarExamen(
    p_idExamen           IN NUMBER,
    p_idDocente          IN NUMBER,
    p_nombre             IN VARCHAR2,
    p_fecha              IN VARCHAR2,
    p_cantidadPreguntas  IN NUMBER,
    p_tiempoMinutos      IN NUMBER,
    p_hora               IN VARCHAR2,
    p_descripcion        IN VARCHAR2,
    p_pesoMateria        IN NUMBER,
    p_tieneLimiteTiempo  IN VARCHAR2,
    p_notaMinimaPasar    IN NUMBER,
    p_fechaLimite        IN VARCHAR2,
    p_horaLimite         IN VARCHAR2,
    p_cantidadBanco      IN NUMBER
) RETURN NUMBER IS
    v_rows NUMBER;
BEGIN
UPDATE Examen
SET docente_idDocente      = p_idDocente,
    nombre                 = p_nombre,
    fecha                  = p_fecha,
    hora                   = p_hora,
    fechaLimite            = p_fechaLimite,
    horaLimite             = p_horaLimite,
    cantidadPreguntas      = p_cantidadPreguntas,
    tiempoMinutos          = p_tiempoMinutos,
    descripcion            = p_descripcion,
    pesoMateria            = p_pesoMateria,
    tieneLimiteTiempo      = p_tieneLimiteTiempo,
    notaMinimaPasar        = p_notaMinimaPasar,
    cantidadPreguntasBanco = p_cantidadBanco
WHERE idExamen = p_idExamen;

v_rows := SQL%ROWCOUNT;

    IF v_rows = 0 THEN
        -- no había ningún examen con ese ID
        RETURN 0;
ELSE
        -- se actualizó correctamente
        RETURN 1;
END IF;
END editarExamen;
/
