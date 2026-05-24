package com.squarestruct.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Presupuesto {

    private Long id;
    private String nombreProyecto;
    private List<Producto> productos;
    private double costeTotal;
    private LocalDate fechaCreacion;

    public Presupuesto() {
    }

    public Presupuesto(Long id, String nombreProyecto, List<Producto> productos,
                       double costeTotal, LocalDate fechaCreacion) {
        this.id = id;
        this.nombreProyecto = nombreProyecto;
        this.productos = productos;
        this.costeTotal = costeTotal;
        this.fechaCreacion = fechaCreacion;
    }
}