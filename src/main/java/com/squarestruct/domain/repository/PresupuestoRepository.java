/*
 * Repositorio de dominio para presupuestos.
 * Expone CRUD y busquedas por proyecto, producto incluido y rango de fechas.
 */
package com.squarestruct.domain.repository;

import com.squarestruct.domain.model.Presupuesto;
import java.time.LocalDate;
import java.util.List;

public interface PresupuestoRepository extends CrudRepository<Presupuesto, Long> {

    List<Presupuesto> findByNombreProyectoContaining(String nombreProyecto);

    List<Presupuesto> findByProductoId(Long productoId);

    List<Presupuesto> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
