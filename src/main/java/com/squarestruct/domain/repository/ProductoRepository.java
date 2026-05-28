package com.squarestruct.domain.repository;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Producto;
import java.util.List;

/*
 * Repositorio de dominio para productos.
 * Expone CRUD y búsquedas habituales por nombre, tipo, material y proveedor.
 */
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByTipo(TipoProducto tipo);

    List<Producto> findByMaterial(String material);

    List<Producto> findByProveedorId(Long proveedorId);
}
