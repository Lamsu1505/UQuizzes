package org.example.Model.OpcionesRespuesta;

public class OpcionMultipleRespuesta {

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
