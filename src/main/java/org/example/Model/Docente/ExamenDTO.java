package org.example.Model.Docente;

public class ExamenDTO {
    private int idExamen;
    private String nombre;

    public ExamenDTO(int idExamen, String nombre) {
        this.idExamen = idExamen;
        this.nombre = nombre;
    }

    public int getIdExamen() { return idExamen; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre;
    }
}

