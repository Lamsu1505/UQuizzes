package org.example.Model.Docente;

public class GrupoDTO {
    private int idGrupo;
    private String nombre;

    public GrupoDTO(int idGrupo, String nombre) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
    }

    public int getIdGrupo() { return idGrupo; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre;
    }
}

