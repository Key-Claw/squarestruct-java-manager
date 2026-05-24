package com.squarestruct.domain.model;

import java.time.LocalDate;

public class Factura {

    private Long id;
    private Pedido pedido;
    private LocalDate fechaFactura;
    private double total;
    private String metodoPago;

    public Factura() {
    }

    public Factura(Long id, Pedido pedido, LocalDate fechaFactura, double total, String metodoPago) {
        this.id = id;
        this.pedido = pedido;
        this.fechaFactura = fechaFactura;
        this.total = total;
        this.metodoPago = metodoPago;
    }
}