package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.ProductoDTO;

import com.squarestruct.application.dto.ProductoDTO;
import com.squarestruct.domain.model.Producto;

// Crea o construye un ProductoDTO a partir de un objeto Producto, extrayendo solo los datos necesarios para la transferencia sin exponer la entidad completa del producto.
public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio()
        );
    }
}