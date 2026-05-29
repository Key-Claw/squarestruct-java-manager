package com.squarestruct.domain.model;

import java.time.LocalDate;

/*
 * Entidad de dominio que representa la factura asociada a un pedido.
 * Registra fecha, total y método de pago para consultas administrativas.
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDate getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDate fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
