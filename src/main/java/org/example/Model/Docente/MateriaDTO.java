package org.example.Model.Docente;

public class MateriaDTO {

    private int idMateria;
    private String nombre;

    public MateriaDTO(){

    }

    public MateriaDTO(int idMateria, String nombre) {
        this.idMateria = idMateria;
        this.nombre = nombre;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
