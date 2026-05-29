/*
 * Repositorio de dominio para pedidos.
 * Expone CRUD y busquedas por usuario, estado y rango de fechas.
 */
package com.squarestruct.domain.repository;

import com.squarestruct.domain.enums.EstadoPedido;
import com.squarestruct.domain.model.Pedido;
import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByFechaPedidoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
