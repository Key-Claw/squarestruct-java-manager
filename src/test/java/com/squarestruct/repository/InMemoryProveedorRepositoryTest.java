package com.squarestruct.repository;

import com.squarestruct.domain.model.Proveedor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProveedorRepositoryTest {

    private final Map<Long, Proveedor> proveedores = new HashMap<>();

    @Test
    void debeGuardarProveedorEnMemoria() {
        Proveedor proveedor = crearProveedor();

        proveedores.put(proveedor.getId(), proveedor);

        assertEquals(1, proveedores.size());
        assertEquals("Proveedor Test", proveedores.get(1L).getNombreEmpresa());
    }

    @Test
    void debeBuscarProveedorPorId() {
        Proveedor proveedor = crearProveedor();
        proveedores.put(proveedor.getId(), proveedor);

        Proveedor resultado = proveedores.get(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void debeActualizarProveedorEnMemoria() {
        Proveedor proveedor = crearProveedor();
        proveedores.put(proveedor.getId(), proveedor);

        proveedor.setNombreEmpresa("Proveedor actualizado");
        proveedores.put(proveedor.getId(), proveedor);

        assertEquals("Proveedor actualizado", proveedores.get(1L).getNombreEmpresa());
    }

    @Test
    void debeEliminarProveedorEnMemoria() {
        Proveedor proveedor = crearProveedor();
        proveedores.put(proveedor.getId(), proveedor);

        proveedores.remove(1L);

        assertTrue(proveedores.isEmpty());
    }

    private Proveedor crearProveedor() {
        return new Proveedor(
                1L,
                "Proveedor Test",
                "600000000",
                "https://proveedor-test.com",
                true
        );
    }
}