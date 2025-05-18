package org.example.Model.Docente;

public class EstudianteExamenInfo {
    private String fecha;
    private String codigo;
    private String nombre;
    private String apellido;
    private double notaFinal;
    private int tiempoTomado;

    public EstudianteExamenInfo(String fecha, String codigo, String nombre, String apellido, double notaFinal, int tiempoTomado) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.notaFinal = notaFinal;
        this.tiempoTomado = tiempoTomado;
    }

    // Getters y setters
    public String getFecha() {
        return fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
