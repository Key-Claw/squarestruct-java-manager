// Pruebas unitarias del servicio de presupuestos con escenarios de negocio aislados.
package com.squarestruct.application.service;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.model.PresupuestoDetalle;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.infrastructure.persistence.memory.InMemoryPresupuestoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PresupuestoServiceTest {

    private final PresupuestoService presupuestoService = new PresupuestoService();

    @Test
    @DisplayName("valida un presupuesto correcto")
    void validarPresupuestoConDatosValidosNoLanzaExcepcion() {
        Presupuesto presupuesto = new Presupuesto(1L, "Proyecto modular",
                Collections.singletonList(crearProducto(1L, "Bloque Eco H80 Max", 114.0)), 114.0,
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
        Presupuesto presupuesto = new Presupuesto(1L, "Proyecto modular",
                Collections.singletonList(crearProducto(1L, "Bloque Eco H80 Max", 114.0)), -1.0,
                LocalDate.of(2026, 4, 20));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> presupuestoService.validarPresupuesto(presupuesto));

        assertEquals("El coste total no puede ser negativo", exception.getMessage());
    }

    @Test
    @DisplayName("calcula subtotales por linea y total del presupuesto")
    void calcularPresupuestoCalculaLineasYTotal() {
        Producto bloque = crearProducto(1L, "Bloque Eco H80 Max", 114.0);
        Producto pilar = crearProducto(2L, "Pilar H80 Refuerzo", 210.0);
        List<PresupuestoDetalle> detalles = Arrays.asList(
                new PresupuestoDetalle(null, bloque, 2, 999.0),
                new PresupuestoDetalle(null, pilar, 1, 999.0)
        );

        Presupuesto presupuesto = presupuestoService.calcularPresupuesto("Proyecto modular", detalles);

        assertEquals(438.0, presupuesto.getCosteTotal(), 0.001);
        assertEquals(2, presupuesto.getDetalles().size());
        assertEquals(228.0, presupuesto.getDetalles().get(0).getSubtotal(), 0.001);
        assertEquals(210.0, presupuesto.getDetalles().get(1).getSubtotal(), 0.001);
        assertNotNull(presupuesto.getFechaCreacion());
    }

    @Test
    @DisplayName("calcula una linea de producto")
    void calcularLineaCalculaSubtotalDeProductoYCantidad() {
        Producto bloque = crearProducto(1L, "Bloque Eco H80 Max", 114.0);

        PresupuestoDetalle detalle = presupuestoService.calcularLinea(bloque, 3);

        assertEquals(bloque, detalle.getProducto());
        assertEquals(3, detalle.getCantidad());
        assertEquals(342.0, detalle.getSubtotal(), 0.001);
    }

    @Test
    @DisplayName("rechaza cantidades no positivas")
    void calcularLineaConCantidadNoPositivaLanzaExcepcion() {
        Producto bloque = crearProducto(1L, "Bloque Eco H80 Max", 114.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> presupuestoService.calcularLinea(bloque, 0));

        assertEquals("La cantidad debe ser mayor que cero", exception.getMessage());
    }

    @Test
    @DisplayName("rechaza precios negativos en el calculo")
    void calcularPresupuestoConPrecioNegativoLanzaExcepcion() {
        Producto bloque = crearProducto(1L, "Bloque Eco H80 Max", -1.0);
        List<PresupuestoDetalle> detalles = Collections.singletonList(
                new PresupuestoDetalle(null, bloque, 1, 0.0)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> presupuestoService.calcularPresupuesto("Proyecto modular", detalles));

        assertEquals("El precio del producto no puede ser negativo", exception.getMessage());
    }

    @Test
    @DisplayName("muestra un resumen del presupuesto por consola")
    void mostrarResumenImprimeLineasYTotal() {
        Producto bloque = crearProducto(1L, "Bloque Eco H80 Max", 114.0);
        Presupuesto presupuesto = presupuestoService.calcularPresupuesto("Proyecto modular",
                Collections.singletonList(new PresupuestoDetalle(null, bloque, 2, 0.0)));
        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        System.setOut(new PrintStream(salida, true, StandardCharsets.UTF_8));
        try {
            presupuestoService.mostrarResumen(presupuesto);
        } finally {
            System.setOut(salidaOriginal);
        }

        String resumen = salida.toString(StandardCharsets.UTF_8);

        assertTrue(resumen.contains("Resumen del presupuesto: Proyecto modular"));
        assertTrue(resumen.contains("- Bloque Eco H80 Max | precio unitario: 114.00 | cantidad: 2 | subtotal: 228.00"));
        assertTrue(resumen.contains("Total: 228.00"));
    }

    @Test
    @DisplayName("muestra resumen de presupuestos antiguos con lista de productos")
    void mostrarResumenDePresupuestoConProductosGeneraLineasDesdeServicio() {
        Producto bloque = crearProducto(1L, "Bloque Eco H80 Max", 114.0);
        Presupuesto presupuesto = new Presupuesto(1L, "Proyecto modular",
                Collections.singletonList(bloque), 114.0, LocalDate.of(2026, 4, 20));
        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        System.setOut(new PrintStream(salida, true, StandardCharsets.UTF_8));
        try {
            presupuestoService.mostrarResumen(presupuesto);
        } finally {
            System.setOut(salidaOriginal);
        }

        String resumen = salida.toString(StandardCharsets.UTF_8);

        assertTrue(resumen.contains("- Bloque Eco H80 Max | precio unitario: 114.00 | cantidad: 1 | subtotal: 114.00"));
        assertTrue(resumen.contains("Total: 114.00"));
    }

    @Test
    @DisplayName("crea y guarda el presupuesto cuando hay repositorio")
    void crearPresupuestoGuardaEnRepositorioConfigurado() {
        InMemoryPresupuestoRepository repository = new InMemoryPresupuestoRepository(false);
        PresupuestoService service = new PresupuestoService(repository);
        Producto bloque = crearProducto(1L, "Bloque Eco H80 Max", 114.0);

        Presupuesto presupuesto = service.crearPresupuesto("Proyecto modular",
                Collections.singletonList(new PresupuestoDetalle(null, bloque, 2, 0.0)));

        assertEquals(1L, presupuesto.getId());
        assertEquals(1, repository.findAll().size());
        assertEquals(228.0, repository.findById(1L).orElseThrow().getCosteTotal(), 0.001);
    }

    private Producto crearProducto(Long id, String nombre, double precio) {
        return new Producto(
                id,
                nombre,
                "Producto para presupuesto",
                precio,
                TipoProducto.BLOQUE,
                "Plastico reciclable",
                20.0,
                20.0,
                80.0,
                null
        );
    }
}
