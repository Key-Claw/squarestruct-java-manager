package com.squarestruct.application.dto;

/*
 * DTO mínimo de pedido usado por la capa de aplicación para validaciones simples.
 */
public class PedidoDTO {

    private Long id;

    public PedidoDTO() {
    }

    public PedidoDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
