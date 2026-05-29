package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.EstadoPedido;
import com.squarestruct.domain.enums.RolUsuario;
import com.squarestruct.domain.model.Pedido;
import com.squarestruct.domain.model.Usuario;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryPedidoRepositoryTest {

    private final InMemoryPedidoRepository repository = new InMemoryPedidoRepository(false);

    @Test
    void debeCrearPedidoYAsignarId() {
        Pedido pedido = repository.create(crearPedido(null, EstadoPedido.PENDIENTE, LocalDate.of(2026, 4, 20)));

        assertEquals(1L, pedido.getId());
        assertEquals(EstadoPedido.PENDIENTE, repository.findById(1L).orElseThrow().getEstado());
    }

    @Test
    void debeActualizarPedidoExistente() {
        Pedido pedido = repository.create(crearPedido(null, EstadoPedido.PENDIENTE, LocalDate.of(2026, 4, 20)));

        pedido.setEstado(EstadoPedido.PAGADO);
        repository.update(pedido);

        assertEquals(EstadoPedido.PAGADO, repository.findById(pedido.getId()).orElseThrow().getEstado());
    }

    @Test
    void debeEliminarPedidoPorId() {
        Pedido pedido = repository.create(crearPedido(null, EstadoPedido.PENDIENTE, LocalDate.of(2026, 4, 20)));

        repository.deleteById(pedido.getId());

        assertFalse(repository.existsById(pedido.getId()));
    }

    @Test
    void debeBuscarPorUsuarioEstadoYFechas() {
        Pedido pedido = repository.create(crearPedido(null, EstadoPedido.PENDIENTE, LocalDate.of(2026, 4, 20)));
        repository.create(crearPedido(null, EstadoPedido.PAGADO, LocalDate.of(2026, 5, 10)));

        assertEquals(2, repository.findByUsuarioId(pedido.getUsuario().getId()).size());
        assertEquals(1, repository.findByEstado(EstadoPedido.PENDIENTE).size());
        assertEquals(1, repository.findByFechaPedidoBetween(
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 4, 30)
        ).size());
    }

    private Pedido crearPedido(Long id, EstadoPedido estado, LocalDate fechaPedido) {
        Usuario usuario = new Usuario(
                1L,
                "Usuario Test",
                "usuario@test.com",
                "password",
                RolUsuario.EMPLEADO
        );

        return new Pedido(
                id,
                usuario,
                Collections.emptyList(),
                estado,
                fechaPedido,
                150.0
        );
    }
}
