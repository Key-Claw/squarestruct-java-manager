package com.squarestruct.repository;

import com.squarestruct.domain.enums.EstadoPedido;
import com.squarestruct.domain.enums.RolUsuario;
import com.squarestruct.domain.model.Pedido;
import com.squarestruct.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Pruebas de comportamiento CRUD básico sobre una estructura en memoria de pedidos.
 */
class InMemoryPedidoRepositoryTest {

    private final Map<Long, Pedido> pedidos = new HashMap<>();

    @Test
    void debeGuardarPedidoEnMemoria() {
        Pedido pedido = crearPedido();

        pedidos.put(pedido.getId(), pedido);

        assertEquals(1, pedidos.size());
        assertEquals(EstadoPedido.PENDIENTE, pedidos.get(1L).getEstado());
    }

    @Test
    void debeBuscarPedidoPorId() {
        Pedido pedido = crearPedido();
        pedidos.put(pedido.getId(), pedido);

        Pedido resultado = pedidos.get(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void debeActualizarPedidoEnMemoria() {
        Pedido pedido = crearPedido();
        pedidos.put(pedido.getId(), pedido);

        pedido.setEstado(EstadoPedido.PAGADO);
        pedidos.put(pedido.getId(), pedido);

        assertEquals(EstadoPedido.PAGADO, pedidos.get(1L).getEstado());
    }

    @Test
    void debeEliminarPedidoEnMemoria() {
        Pedido pedido = crearPedido();
        pedidos.put(pedido.getId(), pedido);

        pedidos.remove(1L);

        assertTrue(pedidos.isEmpty());
    }

    private Pedido crearPedido() {
        Usuario usuario = new Usuario(
                1L,
                "Usuario Test",
                "usuario@test.com",
                "password",
                RolUsuario.EMPLEADO
        );

        return new Pedido(
                1L,
                usuario,
                Collections.emptyList(),
                EstadoPedido.PENDIENTE,
                LocalDate.now(),
                150.0
        );
    }
}
