package org.example.Model.Docente;

public class EstudianteExamenInfo {
    private String fechaInicio;
    private String horaInicio;
    private String codigo;
    private String nombre;
    private String apellido;
    private double notaFinal;
    private double tiempoTomado;

    public EstudianteExamenInfo(){

    }

    public EstudianteExamenInfo(String fechaInicio, String horaInicio, String codigo, String nombre, String apellido, double notaFinal, double tiempoTomado) {
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.notaFinal = notaFinal;
        this.tiempoTomado = tiempoTomado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTiempoTomado(double tiempoTomado) {
        this.tiempoTomado = tiempoTomado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public double getTiempoTomado() {
        return tiempoTomado;
    }

    public void setTiempoTomado(int tiempoTomado) {
        this.tiempoTomado = tiempoTomado;
    }
}
