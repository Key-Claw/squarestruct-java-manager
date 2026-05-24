package com.squarestruct.domain.model;

public class BloquePlantilla {

    private Long id;
    private Producto producto;
    private int posicionX;
    private int posicionY;
    private int posicionZ;
    private int cantidad;

    public BloquePlantilla() {
    }

    public BloquePlantilla(Long id, Producto producto, int posicionX, int posicionY, int posicionZ, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.posicionZ = posicionZ;
        this.cantidad = cantidad;
    }
}