// Servicio de aplicación para validar pedidos y asegurar reglas mínimas de negocio.
package com.squarestruct.application.service;

import com.squarestruct.application.dto.PedidoDTO;

public class PedidoService {

    public void validarPedido(PedidoDTO pedidoDTO) {

        if (pedidoDTO == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }

        if (pedidoDTO.getId() == null || pedidoDTO.getId() <= 0) {
            throw new IllegalArgumentException("El identificador del pedido debe ser positivo");
        }

    }

}