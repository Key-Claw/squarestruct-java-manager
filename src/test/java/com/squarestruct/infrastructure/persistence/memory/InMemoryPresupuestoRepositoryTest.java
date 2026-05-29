package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryPresupuestoRepositoryTest {

    private final InMemoryPresupuestoRepository repository = new InMemoryPresupuestoRepository(false);

    @Test
    void debeCrearPresupuestoYAsignarId() {
        Presupuesto presupuesto = repository.create(crearPresupuesto(null, "Proyecto modular",
                LocalDate.of(2026, 4, 20)));

        assertEquals(1L, presupuesto.getId());
        assertEquals("Proyecto modular", repository.findById(1L).orElseThrow().getNombreProyecto());
    }

    @Test
    void debeActualizarPresupuestoExistente() {
        Presupuesto presupuesto = repository.create(crearPresupuesto(null, "Proyecto modular",
                LocalDate.of(2026, 4, 20)));

        presupuesto.setNombreProyecto("Proyecto actualizado");
        repository.update(presupuesto);

        assertEquals("Proyecto actualizado", repository.findById(presupuesto.getId()).orElseThrow()
                .getNombreProyecto());
    }

    @Test
    void debeEliminarPresupuestoPorId() {
        Presupuesto presupuesto = repository.create(crearPresupuesto(null, "Proyecto modular",
                LocalDate.of(2026, 4, 20)));

        repository.deleteById(presupuesto.getId());

        assertFalse(repository.existsById(presupuesto.getId()));
    }

    @Test
    void debeBuscarPorProyectoProductoYFechas() {
        Presupuesto presupuesto = repository.create(crearPresupuesto(null, "Proyecto modular",
                LocalDate.of(2026, 4, 20)));
        repository.create(crearPresupuesto(null, "Proyecto secundario", LocalDate.of(2026, 5, 10)));

        Long productoId = presupuesto.getProductos().get(0).getId();

        assertEquals(1, repository.findByNombreProyectoContaining("secundario").size());
        assertEquals(2, repository.findByProductoId(productoId).size());
        assertEquals(1, repository.findByFechaCreacionBetween(
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 4, 30)
        ).size());
    }

    private Presupuesto crearPresupuesto(Long id, String nombreProyecto, LocalDate fechaCreacion) {
        return new Presupuesto(
                id,
                nombreProyecto,
                Collections.singletonList(crearProducto()),
                2500.0,
                fechaCreacion
        );
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
                "Hormigon",
                30.0,
                30.0,
                60.0,
                proveedor
        );
    }
}
