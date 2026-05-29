/*
 * Repositorio de dominio para plantillas constructivas.
 * Expone CRUD y busquedas por nombre y productos usados en sus bloques.
 */
package com.squarestruct.domain.repository;

import com.squarestruct.domain.model.PlantillaConstructiva;
import java.util.List;

public interface PlantillaRepository extends CrudRepository<PlantillaConstructiva, Long> {

    List<PlantillaConstructiva> findByNombreContaining(String nombre);

    List<PlantillaConstructiva> findByProductoId(Long productoId);
}
