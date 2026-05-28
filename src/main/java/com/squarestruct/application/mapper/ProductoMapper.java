package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.ProductoDTO;
import com.squarestruct.domain.model.Producto;

/*
 * Mapeador de salida para productos.
 * Expone solo los campos que necesita la capa de aplicación y evita filtrar la entidad completa.
 */
public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio()
        );
    }
}
