package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.BloquePlantilla;
import com.squarestruct.domain.model.PlantillaConstructiva;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import com.squarestruct.domain.repository.PlantillaRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Repositorio en memoria de plantillas constructivas.
 * Mantiene plantillas temporales y búsquedas sobre sus bloques.
 */
public class InMemoryPlantillaRepository extends InMemoryCrudRepository<PlantillaConstructiva>
        implements PlantillaRepository {

    public InMemoryPlantillaRepository() {
        this(true);
    }

    public InMemoryPlantillaRepository(boolean seedData) {
        super(seedData ? seedData() : Collections.emptyMap());
    }

    @Override
    public List<PlantillaConstructiva> findByNombreContaining(String nombre) {
        return stream()
                .filter(plantilla -> containsIgnoreCase(plantilla.getNombre(), nombre))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantillaConstructiva> findByProductoId(Long productoId) {
        return stream()
                .filter(plantilla -> containsProductoId(plantilla, productoId))
                .collect(Collectors.toList());
    }

    @Override
    protected Long getId(PlantillaConstructiva plantilla) {
        return plantilla.getId();
    }

    @Override
    protected void setId(PlantillaConstructiva plantilla, Long id) {
        plantilla.setId(id);
    }

    private boolean containsProductoId(PlantillaConstructiva plantilla, Long productoId) {
        if (plantilla.getBloques() == null) {
            return false;
        }

        return plantilla.getBloques().stream()
                .filter(Objects::nonNull)
                .map(BloquePlantilla::getProducto)
                .filter(Objects::nonNull)
                .anyMatch(producto -> Objects.equals(producto.getId(), productoId));
    }

    private static Map<Long, PlantillaConstructiva> seedData() {
        Proveedor proveedor = new Proveedor(1L, "Plásticos renovables ByFusion",
                "+18332925625", "https://byfusion.com/", true);
        Producto producto = new Producto(1L, "Bloque Eco H80 Max",
                "Bloque eco modular de gran formato.", 114.00, TipoProducto.BLOQUE,
                "Plástico reciclable", 20.00, 20.00, 80.00, proveedor);
        BloquePlantilla bloque = new BloquePlantilla(1L, producto, 0, 0, 0, 4);
        Map<Long, PlantillaConstructiva> plantillas = new LinkedHashMap<>();

        plantillas.put(1L, new PlantillaConstructiva(1L, "Plantilla muro básico",
                "Plantilla inicial para pruebas de construcción.",
                Collections.singletonList(bloque)));
        return plantillas;
    }
}
