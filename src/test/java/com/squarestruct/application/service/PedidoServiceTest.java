package com.squarestruct.application.service;

import com.squarestruct.application.dto.PedidoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PedidoServiceTest {

    private final PedidoService pedidoService = new PedidoService();

    @Test
    @DisplayName("valida un pedido correcto")
    void validarPedidoConDatosValidosNoLanzaExcepcion() {
        PedidoDTO pedidoDTO = new PedidoDTO(10L);

        assertDoesNotThrow(() -> pedidoService.validarPedido(pedidoDTO));
    }

    @Test
    @DisplayName("rechaza un pedido nulo")
    void validarPedidoNuloLanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.validarPedido(null));

        assertEquals("El pedido no puede ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("rechaza un identificador de pedido inválido")
    void validarPedidoConIdInvalidoLanzaExcepcion() {
        PedidoDTO pedidoDTO = new PedidoDTO(0L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.validarPedido(pedidoDTO));

        assertEquals("El identificador del pedido debe ser positivo", exception.getMessage());
    }
}