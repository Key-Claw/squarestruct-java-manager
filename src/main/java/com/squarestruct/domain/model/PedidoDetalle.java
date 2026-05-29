package com.squarestruct.domain.model;

/*
 * Línea de pedido con producto, cantidad y subtotal.
 * Es el detalle económico que compone el total de un pedido.
 */
public class PedidoDetalle {

    private Long id;
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public PedidoDetalle() {
    }

    public PedidoDetalle(Long id, Producto producto, int cantidad, double subtotal) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
