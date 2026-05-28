package com.squarestruct.domain.repository;

import com.squarestruct.domain.model.Proveedor;
import java.util.List;

/*
 * Repositorio de dominio para proveedores.
 * Expone CRUD y búsquedas por nombre de empresa y estado de validación.
 */
public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {

    List<Proveedor> findByNombreEmpresaContaining(String nombreEmpresa);

    List<Proveedor> findByValidado(boolean validado);
}
