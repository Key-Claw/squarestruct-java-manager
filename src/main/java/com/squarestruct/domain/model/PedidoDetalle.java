package com.squarestruct.domain.model;

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
}