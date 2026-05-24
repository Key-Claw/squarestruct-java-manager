package com.squarestruct.domain.model;

import java.util.List;

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
}