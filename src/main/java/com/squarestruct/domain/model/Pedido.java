package com.squarestruct.domain.model;

import com.squarestruct.domain.enums.EstadoPedido;
import java.time.LocalDate;
import java.util.List;

public class Pedido {

    private Long id;
    private Usuario usuario;
    private List<PedidoDetalle> detalles;
    private EstadoPedido estado;
    private LocalDate fechaPedido;
    private double total;

    public Pedido() {
    }

    public Pedido(Long id, Usuario usuario, List<PedidoDetalle> detalles,
                  EstadoPedido estado, LocalDate fechaPedido, double total) {
        this.id = id;
        this.usuario = usuario;
        this.detalles = detalles;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PedidoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalle> detalles) {
        this.detalles = detalles;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}