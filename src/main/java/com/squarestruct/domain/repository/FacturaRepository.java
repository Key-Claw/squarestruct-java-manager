package com.squarestruct.domain.repository;

import com.squarestruct.domain.model.Factura;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 * Repositorio de dominio para facturas.
 * Expone CRUD y búsquedas por pedido, método de pago y rango de fechas.
 */
public interface FacturaRepository extends CrudRepository<Factura, Long> {

    Optional<Factura> findByPedidoId(Long pedidoId);

    List<Factura> findByMetodoPago(String metodoPago);

    List<Factura> findByFechaFacturaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
