/*
 * Repositorio de dominio para productos.
 * Expone CRUD y busquedas habituales por nombre, tipo, material y proveedor.
 */
package com.squarestruct.domain.repository;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Producto;
import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByTipo(TipoProducto tipo);

    List<Producto> findByMaterial(String material);

    List<Producto> findByProveedorId(Long proveedorId);
}
