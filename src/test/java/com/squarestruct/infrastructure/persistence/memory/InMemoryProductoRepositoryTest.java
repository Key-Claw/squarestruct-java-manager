package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryProductoRepositoryTest {

    private final InMemoryProductoRepository repository = new InMemoryProductoRepository(false);

    @Test
    void debeCrearProductoYAsignarId() {
        Producto producto = crearProducto(null, "Bloque modular", TipoProducto.BLOQUE);

        Producto creado = repository.create(producto);

        assertEquals(1L, creado.getId());
        assertEquals("Bloque modular", repository.findById(1L).orElseThrow().getNombre());
    }

    @Test
    void debeActualizarProductoExistente() {
        Producto producto = repository.create(crearProducto(null, "Bloque modular", TipoProducto.BLOQUE));

        producto.setNombre("Bloque actualizado");
        repository.update(producto);

        assertEquals("Bloque actualizado", repository.findById(producto.getId()).orElseThrow().getNombre());
    }

    @Test
    void debeEliminarProductoPorId() {
        Producto producto = repository.create(crearProducto(null, "Bloque modular", TipoProducto.BLOQUE));

        repository.deleteById(producto.getId());

        assertFalse(repository.existsById(producto.getId()));
    }

    @Test
    void debeBuscarPorCamposEspecificos() {
        Producto bloque = repository.create(crearProducto(null, "Bloque modular", TipoProducto.BLOQUE));
        repository.create(crearProducto(null, "Pilar modular", TipoProducto.PILAR));

        assertEquals(1, repository.findByNombreContaining("bloque").size());
        assertEquals(1, repository.findByTipo(TipoProducto.PILAR).size());
        assertEquals(2, repository.findByMaterial("Hormigon").size());
        assertTrue(repository.findByProveedorId(bloque.getProveedor().getId()).contains(bloque));
    }

    private Producto crearProducto(Long id, String nombre, TipoProducto tipo) {
        Proveedor proveedor = new Proveedor(
                1L,
                "Proveedor Test",
                "600000000",
                "https://proveedor-test.com",
                true
        );

        return new Producto(
                id,
                nombre,
                "Bloque de prueba",
                100.0,
                tipo,
                "Hormigon",
                30.0,
                30.0,
                60.0,
                proveedor
        );
    }
}
