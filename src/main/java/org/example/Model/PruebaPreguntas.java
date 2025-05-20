package org.example.Model;

public class PruebaPreguntas {
    private int idPregunta;
    private int idTema;
    private int idTipoPregunta;
    private Integer idPreguntaPadre; // Puede ser null
    private String enunciado;
    private String respuestaEstudiante;
    private String respuestaCorrecta;
    private String temaComplementario;

    public PruebaPreguntas() {

    }
    // Constructor completo
    public PruebaPreguntas(int idPregunta, int idTema, int idTipoPregunta, Integer idPreguntaPadre,
                    String enunciado, String respuestaEstudiante, String respuestaCorrecta,
                    String temaComplementario) {
        this.idPregunta = idPregunta;
        this.idTema = idTema;
        this.idTipoPregunta = idTipoPregunta;
        this.idPreguntaPadre = idPreguntaPadre;
        this.enunciado = enunciado;
        this.respuestaEstudiante = respuestaEstudiante;
        this.respuestaCorrecta = respuestaCorrecta;
        this.temaComplementario = temaComplementario;
    }

    // Constructor sin idPreguntaPadre
    public PruebaPreguntas(int idPregunta, int idTema, int idTipoPregunta,
                    String enunciado, String respuestaEstudiante, String respuestaCorrecta,
                    String temaComplementario) {
        this(idPregunta, idTema, idTipoPregunta, null, enunciado, respuestaEstudiante, respuestaCorrecta, temaComplementario);
    }

    // Getters y setters
    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public int getIdTipoPregunta() {
        return idTipoPregunta;
    }

    public void setIdTipoPregunta(int idTipoPregunta) {
        this.idTipoPregunta = idTipoPregunta;
    }

    public Integer getIdPreguntaPadre() {
        return idPreguntaPadre;
    }

    public void setIdPreguntaPadre(Integer idPreguntaPadre) {
        this.idPreguntaPadre = idPreguntaPadre;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuestaEstudiante() {
        return respuestaEstudiante;
    }

    public void setRespuestaEstudiante(String respuestaEstudiante) {
        this.respuestaEstudiante = respuestaEstudiante;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getTemaComplementario() {
        return temaComplementario;
    }

    public void setTemaComplementario(String temaComplementario) {
        this.temaComplementario = temaComplementario;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "idPregunta=" + idPregunta +
                ", idTema=" + idTema +
                ", idTipoPregunta=" + idTipoPregunta +
                ", idPreguntaPadre=" + idPreguntaPadre +
                ", enunciado='" + enunciado + '\'' +
                ", respuestaEstudiante='" + respuestaEstudiante + '\'' +
                ", respuestaCorrecta='" + respuestaCorrecta + '\'' +
                ", temaComplementario='" + temaComplementario + '\'' +
                '}';
    }
}
