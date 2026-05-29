package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.ProductoDTO;
import com.squarestruct.domain.model.Producto;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio()
        );
    }
}
