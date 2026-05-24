package com.squarestruct.application.dto;

// Guarda datos de un producto para transferirlos entre capas sin exponer la entidad completa del producto.
public class ProductoDTO {

    private Long id;
    private String nombre;
    private double precio;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}