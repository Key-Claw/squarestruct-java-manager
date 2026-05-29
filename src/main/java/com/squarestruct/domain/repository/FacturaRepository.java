/*
 * Repositorio de dominio para facturas.
 * Expone CRUD y busquedas por pedido, metodo de pago y rango de fechas.
 */
package com.squarestruct.domain.repository;

import com.squarestruct.domain.model.Factura;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends CrudRepository<Factura, Long> {

    Optional<Factura> findByPedidoId(Long pedidoId);

    List<Factura> findByMetodoPago(String metodoPago);

    List<Factura> findByFechaFacturaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
