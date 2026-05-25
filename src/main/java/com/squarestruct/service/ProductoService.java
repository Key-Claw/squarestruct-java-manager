package com.squarestruct.application.service;

import com.squarestruct.application.dto.ProductoDTO;

public class ProductoService {

    public void validarProducto(ProductoDTO productoDTO) {

        if (productoDTO == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }

        if (productoDTO.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo");
        }

    }

}