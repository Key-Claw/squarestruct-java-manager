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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getCosteTotal() {
        return costeTotal;
    }

    public void setCosteTotal(double costeTotal) {
        this.costeTotal = costeTotal;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}