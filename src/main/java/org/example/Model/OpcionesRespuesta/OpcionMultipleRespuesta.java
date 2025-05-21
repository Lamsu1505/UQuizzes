package org.example.Model.OpcionesRespuesta;

public class OpcionMultipleRespuesta {

    private int idPreguntaa;
    private String texto;
    private boolean esCorrecta;


    public OpcionMultipleRespuesta(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public int getIdPregunta() {return idPreguntaa;}

    public void setIdPregunta(int idPregunta) {this.idPreguntaa = idPregunta;
        System.out.println("se cambio el idPregunta por " + this.idPreguntaa);}

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    @Override
    public String toString() {
        return "Opcion{" +
                "texto='" + texto + '\'' +
                ", esCorrecta=" + esCorrecta +
                '}';
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
