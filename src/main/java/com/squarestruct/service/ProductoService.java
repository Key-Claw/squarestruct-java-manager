// Servicio de aplicación para validar datos básicos de productos antes de procesarlos.
package com.squarestruct.application.service;

import com.squarestruct.application.dto.ProductoDTO;
import com.squarestruct.domain.repository.ProductoRepository;

public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService() {
        this(null);
    }

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

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

    public ProductoRepository getProductoRepository() {
        return productoRepository;
    }

}
