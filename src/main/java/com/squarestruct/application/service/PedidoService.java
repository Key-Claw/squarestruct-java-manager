package com.squarestruct.application.service;

import com.squarestruct.application.dto.PedidoDTO;
import com.squarestruct.domain.repository.PedidoRepository;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService() {
        this(null);
    }

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void validarPedido(PedidoDTO pedidoDTO) {

        if (pedidoDTO == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }

        if (pedidoDTO.getId() == null || pedidoDTO.getId() <= 0) {
            throw new IllegalArgumentException("El identificador del pedido debe ser positivo");
        }

    }

    public PedidoRepository getPedidoRepository() {
        return pedidoRepository;
    }

}
