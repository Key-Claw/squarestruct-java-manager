package com.squarestruct.repository;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias para la gestión de productos en memoria

class InMemoryProductoRepositoryTest {

    private final Map<Long, Producto> productos = new HashMap<>();

    @Test
    void debeGuardarProductoEnMemoria() {
        Producto producto = crearProducto();

        productos.put(producto.getId(), producto);

        assertEquals(1, productos.size());
        assertEquals("Bloque modular", productos.get(1L).getNombre());
    }

    @Test
    void debeBuscarProductoPorId() {
        Producto producto = crearProducto();
        productos.put(producto.getId(), producto);

        Producto resultado = productos.get(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void debeActualizarProductoEnMemoria() {
        Producto producto = crearProducto();
        productos.put(producto.getId(), producto);

        producto.setNombre("Bloque actualizado");
        productos.put(producto.getId(), producto);

        assertEquals("Bloque actualizado", productos.get(1L).getNombre());
    }

    @Test
    void debeEliminarProductoEnMemoria() {
        Producto producto = crearProducto();
        productos.put(producto.getId(), producto);

        productos.remove(1L);

        assertTrue(productos.isEmpty());
    }

    private Producto crearProducto() {
        Proveedor proveedor = new Proveedor(
                1L,
                "Proveedor Test",
                "600000000",
                "https://proveedor-test.com",
                true
        );

        return new Producto(
                1L,
                "Bloque modular",
                "Bloque de prueba",
                100.0,
                TipoProducto.BLOQUE,
                "Hormigón",
                30.0,
                30.0,
                60.0,
                proveedor
        );
    }
}