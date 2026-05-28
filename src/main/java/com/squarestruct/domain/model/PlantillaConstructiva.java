package com.squarestruct.domain.model;

import java.util.List;

/*
 * Entidad de dominio que agrupa bloques constructivos reutilizables.
 * Será la base para plantillas que puedan alimentar presupuestos o futuras integraciones IFC.
 */
public class PlantillaConstructiva {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<BloquePlantilla> bloques;

    public PlantillaConstructiva() {
    }

    public PlantillaConstructiva(Long id, String nombre, String descripcion, List<BloquePlantilla> bloques) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.bloques = bloques;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<BloquePlantilla> getBloques() {
        return bloques;
    }

    public void setBloques(List<BloquePlantilla> bloques) {
        this.bloques = bloques;
    }
}
