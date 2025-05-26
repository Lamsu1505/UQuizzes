package org.example.Model;

public class InfoExamenPresentadoEstudiante { private String nombreExamen;
    private String fechaExamen;
    private double notaMinimaPasar;
    private double notaFinal;
    private double tiempoTomadoMinutos;
    private String materia;

    public InfoExamenPresentadoEstudiante(String nombreExamen, String fechaExamen, double notaMinimaPasar, double notaFinal, double tiempoTomadoMinutos ,  String materia) {
        this.nombreExamen = nombreExamen;
        this.fechaExamen = fechaExamen;
        this.notaMinimaPasar = notaMinimaPasar;
        this.notaFinal = notaFinal;
        this.tiempoTomadoMinutos = tiempoTomadoMinutos;
        this.materia = materia;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public String getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(String fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public double getNotaMinimaPasar() {
        return notaMinimaPasar;
    }

    public void setNotaMinimaPasar(double notaMinimaPasar) {
        this.notaMinimaPasar = notaMinimaPasar;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public double getTiempoTomadoMinutos() {
        return tiempoTomadoMinutos;
    }

    public void setTiempoTomadoMinutos(double tiempoTomadoMinutos) {
        this.tiempoTomadoMinutos = tiempoTomadoMinutos;
    }
}
