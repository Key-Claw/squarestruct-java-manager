package com.squarestruct.application.service;

import com.squarestruct.application.dto.PedidoDTO;

public class PedidoService {

    public void validarPedido(PedidoDTO pedidoDTO) {

        if (pedidoDTO == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }

    }

}