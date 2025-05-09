create or replace FUNCTION iniciarSesionDocente(
    p_usuario  IN VARCHAR2,
    p_password IN VARCHAR2
) RETURN VARCHAR2 IS
  v_iddocente VARCHAR2(40);
BEGIN

SELECT iddocente
INTO v_iddocente
FROM docente
WHERE correo = p_usuario
  AND contrasenia = p_password;

RETURN v_iddocente;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN null;
END iniciarSesionDocente;
/