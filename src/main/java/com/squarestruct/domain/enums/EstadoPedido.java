package com.squarestruct.domain.enums;

/*
 * Estados principales del ciclo de vida de un pedido.
 * Los repositorios y servicios los usan para validar y consultar el avance del pedido.
 */
public enum EstadoPedido {
    PENDIENTE,
    PAGADO,
    ENVIADO,
    COMPLETADO,
    CANCELADO
}
