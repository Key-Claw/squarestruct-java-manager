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
}