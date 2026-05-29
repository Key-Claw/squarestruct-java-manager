package com.squarestruct.domain.model;

/*
 * Bloque individual dentro de una plantilla constructiva.
 * Relaciona un producto con su posición tridimensional y la cantidad necesaria.
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public int getPosicionZ() {
        return posicionZ;
    }

    public void setPosicionZ(int posicionZ) {
        this.posicionZ = posicionZ;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
