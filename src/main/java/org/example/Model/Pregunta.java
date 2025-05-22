package org.example.Model;

public class Pregunta {
    private int idPregunta;
    private String enunciado;
    private String nivel;
    private String tema;

    public Pregunta(int idPregunta, String enunciado, String nivel , String nombreTema) {
        this.idPregunta = idPregunta;
        this.enunciado = enunciado;
        this.nivel = nivel;
        this.tema = nombreTema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}