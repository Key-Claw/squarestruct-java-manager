package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.model.Proveedor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryProveedorRepositoryTest {

    private final InMemoryProveedorRepository repository = new InMemoryProveedorRepository(false);

    @Test
    void debeCrearProveedorYAsignarId() {
        Proveedor proveedor = repository.create(crearProveedor(null, "Proveedor Test", true));

        assertEquals(1L, proveedor.getId());
        assertEquals("Proveedor Test", repository.findById(1L).orElseThrow().getNombreEmpresa());
    }

    @Test
    void debeActualizarProveedorExistente() {
        Proveedor proveedor = repository.create(crearProveedor(null, "Proveedor Test", true));

        proveedor.setNombreEmpresa("Proveedor actualizado");
        repository.update(proveedor);

        assertEquals("Proveedor actualizado", repository.findById(proveedor.getId()).orElseThrow().getNombreEmpresa());
    }

    @Test
    void debeEliminarProveedorPorId() {
        Proveedor proveedor = repository.create(crearProveedor(null, "Proveedor Test", true));

        repository.deleteById(proveedor.getId());

        assertFalse(repository.existsById(proveedor.getId()));
    }

    @Test
    void debeBuscarPorNombreYEstadoDeValidacion() {
        repository.create(crearProveedor(null, "Proveedor validado", true));
        repository.create(crearProveedor(null, "Proveedor pendiente", false));

        assertEquals(2, repository.findByNombreEmpresaContaining("proveedor").size());
        assertEquals(1, repository.findByValidado(true).size());
        assertEquals(1, repository.findByValidado(false).size());
    }

    private Proveedor crearProveedor(Long id, String nombreEmpresa, boolean validado) {
        return new Proveedor(
                id,
                nombreEmpresa,
                "600000000",
                "https://proveedor-test.com",
                validado
        );
    }
}
