package com.squarestruct.domain.repository;

import com.squarestruct.domain.model.PlantillaConstructiva;
import java.util.List;

/*
 * Repositorio de dominio para plantillas constructivas.
 * Expone CRUD y búsquedas por nombre y productos usados en sus bloques.
 */
public interface PlantillaRepository extends CrudRepository<PlantillaConstructiva, Long> {

    List<PlantillaConstructiva> findByNombreContaining(String nombre);

    List<PlantillaConstructiva> findByProductoId(Long productoId);
}
