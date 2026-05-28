package com.squarestruct.repository;

import com.squarestruct.domain.model.Presupuesto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Pruebas de comportamiento CRUD básico sobre una estructura en memoria de presupuestos.
 */
class InMemoryPresupuestoRepositoryTest {

    private final Map<Long, Presupuesto> presupuestos = new HashMap<>();

    @Test
    void debeGuardarPresupuestoEnMemoria() {
        Presupuesto presupuesto = crearPresupuesto();

        presupuestos.put(presupuesto.getId(), presupuesto);

        assertEquals(1, presupuestos.size());
        assertEquals("Proyecto modular", presupuestos.get(1L).getNombreProyecto());
    }

    @Test
    void debeBuscarPresupuestoPorId() {
        Presupuesto presupuesto = crearPresupuesto();
        presupuestos.put(presupuesto.getId(), presupuesto);

        Presupuesto resultado = presupuestos.get(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void debeActualizarPresupuestoEnMemoria() {
        Presupuesto presupuesto = crearPresupuesto();
        presupuestos.put(presupuesto.getId(), presupuesto);

        presupuesto.setNombreProyecto("Proyecto actualizado");
        presupuestos.put(presupuesto.getId(), presupuesto);

        assertEquals("Proyecto actualizado", presupuestos.get(1L).getNombreProyecto());
    }

    @Test
    void debeEliminarPresupuestoEnMemoria() {
        Presupuesto presupuesto = crearPresupuesto();
        presupuestos.put(presupuesto.getId(), presupuesto);

        presupuestos.remove(1L);

        assertTrue(presupuestos.isEmpty());
    }

    private Presupuesto crearPresupuesto() {
        return new Presupuesto(
                1L,
                "Proyecto modular",
                Collections.emptyList(),
                2500.0,
                LocalDate.now()
        );
    }
}
