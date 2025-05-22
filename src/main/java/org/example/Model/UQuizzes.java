package org.example.Model;

import org.example.ConexionDB.ConexionOracle;
import org.example.ConexionDB.DAO.DocenteDAO;
import org.example.ConexionDB.DAO.EstudianteDAO;
import org.example.ConexionDB.DAO.PreguntaDAO;
import org.example.Controllers.Paneles.Docente.CrearQuizController;
import org.example.Controllers.Paneles.Docente.TiposPregunta.SeleccionMultipleController;
import org.example.Model.Docente.EstudianteExamenInfo;
import org.example.Model.Docente.ExamenDTO;
import org.example.Model.Docente.GrupoDTO;
import org.example.Model.Docente.MateriaDTO;
import org.example.Model.OpcionesRespuesta.OpcionMultipleRespuesta;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class UQuizzes {

    private static UQuizzes uQuizzes;
    private String usuarioEnSesion;
    private boolean esDocente;

    public DocenteDAO docenteDAO = new DocenteDAO();
    public EstudianteDAO estudianteDAO = new EstudianteDAO();

    private int idPreguntaRecienCreada;


    private UQuizzes() {
        usuarioEnSesion = null;
        idPreguntaRecienCreada = 0;
    }

    public static UQuizzes getInstance()  {
        if (uQuizzes == null){
            uQuizzes = new UQuizzes();
        }
        return uQuizzes;
    }


    public List<Map<String, Object>> getExamenesDocenteSQL() throws SQLException {
        return docenteDAO.getExamenes(getUsuarioEnSesion());
    }

    public List<Map<String, Object>> getMateriasDocente(String idDocente) throws SQLException {

        return docenteDAO.getMateriasDocente(idDocente);
    }

    public int getIdPreguntaRecienCreada() {
        return idPreguntaRecienCreada;
    }

    public void setIdPreguntaRecienCreada(int idPreguntaRecienCreada) {
        this.idPreguntaRecienCreada = idPreguntaRecienCreada;
    }

    public String getUsuarioEnSesion() {
        return usuarioEnSesion;
    }

    public void setUsuarioEnSesion(String usuarioEnSesion) {
        this.usuarioEnSesion = usuarioEnSesion;
    }

    public boolean isEsDocente() {
        return esDocente;
    }

    public void setEsDocente(boolean esDocente) {
        this.esDocente = esDocente;
    }

    public List<Map<String, Object>> getGruposDocenteByMateria(String usuarioEnSesion, String idMateria) throws SQLException {

        return docenteDAO.getGruposByMateria(usuarioEnSesion,idMateria);
    }

    public List<Map<String, Object>> getUnidadesDocenteByMateria(String idMateria) throws SQLException {

        return docenteDAO.getUnidadesByMateria(idMateria);
    }

    public List<Map<String, Object>> getTemasDocenteByUnidad(String idUnidadSeleccionada) throws SQLException {

        return docenteDAO.getTemasByUnidad(idUnidadSeleccionada);
    }


    public int crearPregunta(String idTemaSeleccionado, String idTipoPreguntaSeleccionado, String idPreguntaPadre, String idNivelPreguntaSeleccionado, Boolean isPublica, String enunciado, String peso, String tiempoPregunta) {
        return docenteDAO.crearPregunta(idTemaSeleccionado , idTipoPreguntaSeleccionado , idPreguntaPadre , idNivelPreguntaSeleccionado , isPublica, enunciado , peso , tiempoPregunta);
    }

    public boolean guardarPreguntaUnicaRespuestaDocente(OpcionMultipleRespuesta opcion , int idPregunta) {
        return docenteDAO.crearPreguntaUnicaRespuestaDocente(opcion , idPregunta);
    }

    public boolean guardarPreguntaVerdaderoFalsoDocente(Boolean respuesta, int idPregunta , String texto) {
        return docenteDAO.crearPreguntaVerdaderoFalsoDocente(respuesta , idPregunta  , texto);
    }

    public boolean guardarPreguntaRespuestaCortaDocente(String texto, int idPregunta) {
        return docenteDAO.guardarPreguntaRespuestaCorta(texto , idPregunta);
    }

    public boolean guardarPreguntaEmparejarDocente(String elementoA, String elementoB, int idPregunta) {
        return docenteDAO.guardarPreguntaEmparejarDocente(elementoA , elementoB , idPregunta);
    }

    public boolean guardarPreguntaMultipleRespuestaDocente(SeleccionMultipleController.OpcionMultiple opcion, int idPregunta) {
        return docenteDAO.guardarPreguntaMultipleRespuestaDocente(opcion , idPregunta);
    }


    public int crearQuiz(int idDocente, int idGrupo, int idMateria, String nombreQuiz, String fechaInicio, int cantidadPreguntas, int tiempo, String hora, String descripcion, int pesoMateria, String tieneTiempo, double notaMinimaPasar , String fechaFin , String horaLimite , int cantidadBanco) {
        return docenteDAO.crearQuiz(idDocente , idGrupo , idMateria , nombreQuiz , fechaInicio , cantidadPreguntas , tiempo , hora , descripcion , pesoMateria , tieneTiempo , notaMinimaPasar , fechaFin , horaLimite ,  cantidadBanco);
    }

    public int crearBancoPreguntas(int idExamenCreado, int cantidadPreguntasBanco) {
        return docenteDAO.crearBancoPreguntasExamen(idExamenCreado , cantidadPreguntasBanco);
    }

    public List<Map<String, Object>> getExamenesEstudianteSQL() {
        return estudianteDAO.getExamenesEstudianteSQL(getUsuarioEnSesion());
    }

    public int agregarPreguntasAlBanco(int idBancoCreado, List<Integer> temasSeleccionados) {
        return docenteDAO.agregarPreguntasAlBanco(idBancoCreado , temasSeleccionados);
    }

    public List<Map<String, Object>> get_examen_by_grupo (String idGrupo) throws SQLException {
        return docenteDAO.get_examen_by_grupo(idGrupo);
    }

    public List<EstudianteExamenInfo> obtenerEstudiantesPorExamen(int idExamen) {
        return docenteDAO.obtenerEstudiantesPorExamen(idExamen);
    }

    public List<Map<String, Object>> getMateriasEstudiante(String usuarioEnSesion) {
        return estudianteDAO.getMateriasEstudiante(usuarioEnSesion);
    }

    public boolean validarRespuestaUnicaRespuesta(int idPregunta, String respuesta) {
        return PreguntaDAO.validarRespuestaUnicaRespuesta(idPregunta, respuesta);
    }

    public boolean validarRespuestaVerdaderoFalso(int idPregunta, String respuesta) {
        return PreguntaDAO.validarRespuestaVerdaderoFalso(idPregunta, respuesta);
    }

    public boolean validarRespuestaCorta(int idPregunta, List<String> listaOpciones) {
        return PreguntaDAO.validarRespuestaCorta(idPregunta, listaOpciones);
    }

    public boolean validarRespuestaMultiple(int idPregunta, List<OpcionMultipleRespuesta> opcionesSeleccionadas) {
        return PreguntaDAO.validarRespuestaMultiple(idPregunta, opcionesSeleccionadas);
    }

    public boolean validarRespuestaEmparejar(List<String> respuestasUsuario, int idPregunta) {
        return PreguntaDAO.validarRespuestaEmparejar(respuestasUsuario, idPregunta);
    }

    public int agregarPreguntasAlBancoManual(int idBancoCreado, List<Pregunta> filasSeleccionadas) {
        return DocenteDAO.agregarPreguntasAlBancoManual(idBancoCreado , filasSeleccionadas );
    }

    public int getCantidadPreguntasBanco(int idExamenCreado) {
        return DocenteDAO.getCantidadPreguntasBanco(idExamenCreado);
    }
}

