package com.squarestruct.application.service;

import com.squarestruct.application.dto.ProductoDTO;
import com.squarestruct.domain.repository.ProductoRepository;

/*
 * Servicio de aplicación de productos.
 * Mantiene validaciones de entrada y recibe el repositorio por interfaz para no acoplarse a la persistencia.
 */
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
