package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.PedidoDTO;
import com.squarestruct.domain.model.Pedido;

/*
 * Mapeador de salida para pedidos.
 * Actualmente expone solo el identificador usado por las validaciones de servicio.
 */
public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido pedido) {

        return new PedidoDTO(
                pedido.getId()
        );
    }
}
