package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.PedidoDTO;
import com.squarestruct.domain.model.Pedido;

public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido pedido) {

        return new PedidoDTO(
                pedido.getId()
        );
    }
}