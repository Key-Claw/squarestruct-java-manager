package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import com.squarestruct.domain.repository.ProductoRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Repositorio en memoria de productos.
 * Implementa el contrato de dominio usando colecciones Java temporales.
 */
public class InMemoryProductoRepository extends InMemoryCrudRepository<Producto>
        implements ProductoRepository {

    public InMemoryProductoRepository() {
        this(true);
    }

    public InMemoryProductoRepository(boolean seedData) {
        super(seedData ? seedData() : Collections.emptyMap());
    }

    @Override
    public List<Producto> findByNombreContaining(String nombre) {
        return stream()
                .filter(producto -> containsIgnoreCase(producto.getNombre(), nombre))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByTipo(TipoProducto tipo) {
        return stream()
                .filter(producto -> producto.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByMaterial(String material) {
        return stream()
                .filter(producto -> equalsIgnoreCase(producto.getMaterial(), material))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByProveedorId(Long proveedorId) {
        return stream()
                .filter(producto -> producto.getProveedor() != null)
                .filter(producto -> Objects.equals(producto.getProveedor().getId(), proveedorId))
                .collect(Collectors.toList());
    }

    @Override
    protected Long getId(Producto producto) {
        return producto.getId();
    }

    @Override
    protected void setId(Producto producto, Long id) {
        producto.setId(id);
    }

    private static Map<Long, Producto> seedData() {
        Proveedor proveedorPlastico = new Proveedor(1L, "Plásticos renovables ByFusion",
                "+18332925625", "https://byfusion.com/", true);
        Proveedor proveedorHormigon = new Proveedor(2L, "Hormigon Forpol Group",
                "+34977881287", "https://www.forpol.es/", true);
        Map<Long, Producto> productos = new LinkedHashMap<>();

        productos.put(1L, new Producto(1L, "Bloque Eco H80 Max",
                "Bloque eco modular de gran formato.", 114.00, TipoProducto.BLOQUE,
                "Plástico reciclable", 20.00, 20.00, 80.00, proveedorPlastico));
        productos.put(2L, new Producto(2L, "Pilar H80 Refuerzo",
                "Pilar de hormigon para refuerzo vertical.", 210.00, TipoProducto.PILAR,
                "Hormigon", 120.00, 40.00, 40.00, proveedorHormigon));
        return productos;
    }
}
