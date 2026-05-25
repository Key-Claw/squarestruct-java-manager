// Pruebas unitarias del servicio de presupuestos con escenarios de negocio aislados.
package com.squarestruct.application.service;

import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.model.Producto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PresupuestoServiceTest {

    private final PresupuestoService presupuestoService = new PresupuestoService();

    @Test
    @DisplayName("valida un presupuesto correcto")
    void validarPresupuestoConDatosValidosNoLanzaExcepcion() {
        Presupuesto presupuesto = new Presupuesto(1L, "Proyecto modular", Collections.singletonList(new Producto()), 114.0,
                LocalDate.of(2026, 4, 20));

        assertDoesNotThrow(() -> presupuestoService.validarPresupuesto(presupuesto));
    }

    @Test
    @DisplayName("rechaza un presupuesto nulo")
    void validarPresupuestoNuloLanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> presupuestoService.validarPresupuesto(null));

        assertEquals("El presupuesto no puede ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("rechaza un presupuesto sin productos")
    void validarPresupuestoSinProductosLanzaExcepcion() {
        Presupuesto presupuesto = new Presupuesto(1L, "Proyecto modular", Collections.emptyList(), 114.0,
                LocalDate.of(2026, 4, 20));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> presupuestoService.validarPresupuesto(presupuesto));

        assertEquals("El presupuesto debe incluir al menos un producto", exception.getMessage());
    }

    @Test
    @DisplayName("rechaza un coste total negativo")
    void validarPresupuestoConCosteNegativoLanzaExcepcion() {
        Presupuesto presupuesto = new Presupuesto(1L, "Proyecto modular", Collections.singletonList(new Producto()), -1.0,
                LocalDate.of(2026, 4, 20));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> presupuestoService.validarPresupuesto(presupuesto));

        assertEquals("El coste total no puede ser negativo", exception.getMessage());
    }
}