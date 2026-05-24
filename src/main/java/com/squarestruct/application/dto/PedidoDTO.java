package com.squarestruct.application.dto;

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