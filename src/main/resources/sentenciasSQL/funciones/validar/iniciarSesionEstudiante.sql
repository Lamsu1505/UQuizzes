CREATE OR REPLACE TYPE T_Temas AS TABLE OF NUMBER;

create or replace FUNCTION iniciarSesionEstudiante(
    p_usuario  IN VARCHAR2,
    p_password IN VARCHAR2
) RETURN VARCHAR2 IS
  v_idEstudiante VARCHAR2(40);
BEGIN

SELECT idEstudiante
INTO v_idEstudiante
FROM estudiante
WHERE correo = p_usuario
  AND contrasenia = p_password;

RETURN v_idEstudiante;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN null;
END iniciarSesionEstudiante;
/