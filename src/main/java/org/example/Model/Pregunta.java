package org.example.Model;

public class Pregunta {
    private int idPregunta;
    private String enunciado;
    private String nivel;

    public Pregunta(int idPregunta, String enunciado, String nivel) {
        this.idPregunta = idPregunta;
        this.enunciado = enunciado;
        this.nivel = nivel;
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