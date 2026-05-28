/*
 * Repositorio en memoria de presupuestos.
 * Almacena presupuestos de prueba y permite buscarlos por proyecto, producto y fechas.
 */
package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.model.PresupuestoDetalle;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import com.squarestruct.domain.repository.PresupuestoRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryPresupuestoRepository extends InMemoryCrudRepository<Presupuesto>
        implements PresupuestoRepository {

    public InMemoryPresupuestoRepository() {
        this(true);
    }

    public InMemoryPresupuestoRepository(boolean seedData) {
        super(seedData ? seedData() : Collections.emptyMap());
    }

    @Override
    public List<Presupuesto> findByNombreProyectoContaining(String nombreProyecto) {
        return stream()
                .filter(presupuesto -> containsIgnoreCase(presupuesto.getNombreProyecto(), nombreProyecto))
                .collect(Collectors.toList());
    }

    @Override
    public List<Presupuesto> findByProductoId(Long productoId) {
        return stream()
                .filter(presupuesto -> containsProductoId(presupuesto, productoId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Presupuesto> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return stream()
                .filter(presupuesto -> isBetweenInclusive(presupuesto.getFechaCreacion(), fechaInicio, fechaFin))
                .collect(Collectors.toList());
    }

    @Override
    protected Long getId(Presupuesto presupuesto) {
        return presupuesto.getId();
    }

    @Override
    protected void setId(Presupuesto presupuesto, Long id) {
        presupuesto.setId(id);
    }

    private boolean containsProductoId(Presupuesto presupuesto, Long productoId) {
        if (productoId == null) {
            return false;
        }

        if (presupuesto.getDetalles() != null && !presupuesto.getDetalles().isEmpty()) {
            return presupuesto.getDetalles().stream()
                    .filter(Objects::nonNull)
                    .map(PresupuestoDetalle::getProducto)
                    .filter(Objects::nonNull)
                    .anyMatch(producto -> Objects.equals(producto.getId(), productoId));
        }

        return presupuesto.getProductos() != null
                && presupuesto.getProductos().stream()
                .filter(Objects::nonNull)
                .anyMatch(producto -> Objects.equals(producto.getId(), productoId));
    }

    private static Map<Long, Presupuesto> seedData() {
        Proveedor proveedor = new Proveedor(1L, "Plasticos renovables ByFusion",
                "+18332925625", "https://byfusion.com/", true);
        Producto producto = new Producto(1L, "Bloque Eco H80 Max",
                "Bloque eco modular de gran formato.", 114.00, TipoProducto.BLOQUE,
                "Plastico reciclable", 20.00, 20.00, 80.00, proveedor);
        Map<Long, Presupuesto> presupuestos = new LinkedHashMap<>();

        presupuestos.put(1L, new Presupuesto(1L, "Proyecto modular inicial",
                Collections.singletonList(producto), 114.00, LocalDate.now()));
        return presupuestos;
    }
}
