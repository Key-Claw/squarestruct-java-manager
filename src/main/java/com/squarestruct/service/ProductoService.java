package com.squarestruct.application.service;

import com.squarestruct.application.dto.ProductoDTO;

public class ProductoService {

    public void validarProducto(ProductoDTO productoDTO) {

        if (productoDTO == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

    }

}