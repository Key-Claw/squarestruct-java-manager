/*
 * Repositorio de dominio para proveedores.
 * Expone CRUD y busquedas por nombre de empresa y estado de validacion.
 */
package com.squarestruct.domain.repository;

import com.squarestruct.domain.model.Proveedor;
import java.util.List;

public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {

    List<Proveedor> findByNombreEmpresaContaining(String nombreEmpresa);

    List<Proveedor> findByValidado(boolean validado);
}
