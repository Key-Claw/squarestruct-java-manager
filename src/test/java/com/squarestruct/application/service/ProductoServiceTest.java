// Pruebas unitarias del servicio de productos, centradas en validaciones de entrada.
package com.squarestruct.application.service;

import com.squarestruct.application.dto.ProductoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductoServiceTest {

    private final ProductoService productoService = new ProductoService();

    @Test
    @DisplayName("valida un producto correcto")
    void validarProductoConDatosValidosNoLanzaExcepcion() {
        ProductoDTO productoDTO = new ProductoDTO(1L, "Bloque Eco H80 Max", 114.0);

        assertDoesNotThrow(() -> productoService.validarProducto(productoDTO));
    }

    @Test
    @DisplayName("rechaza un producto nulo")
    void validarProductoNuloLanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productoService.validarProducto(null));

        assertEquals("El producto no puede ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("rechaza un nombre de producto vacío")
    void validarProductoConNombreVacioLanzaExcepcion() {
        ProductoDTO productoDTO = new ProductoDTO(1L, "   ", 114.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productoService.validarProducto(productoDTO));

        assertEquals("El nombre del producto no puede estar vacío", exception.getMessage());
    }

    @Test
    @DisplayName("rechaza un precio negativo")
    void validarProductoConPrecioNegativoLanzaExcepcion() {
        ProductoDTO productoDTO = new ProductoDTO(1L, "Bloque Eco H80 Max", -1.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productoService.validarProducto(productoDTO));

        assertEquals("El precio del producto no puede ser negativo", exception.getMessage());
    }
}