package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.model.Proveedor;
import com.squarestruct.domain.repository.ProveedorRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Repositorio en memoria de proveedores.
 * Permite trabajar con proveedores sin conectar con MySQL.
 */
public class InMemoryProveedorRepository extends InMemoryCrudRepository<Proveedor>
        implements ProveedorRepository {

    public InMemoryProveedorRepository() {
        this(true);
    }

    public InMemoryProveedorRepository(boolean seedData) {
        super(seedData ? seedData() : Collections.emptyMap());
    }

    @Override
    public List<Proveedor> findByNombreEmpresaContaining(String nombreEmpresa) {
        return stream()
                .filter(proveedor -> containsIgnoreCase(proveedor.getNombreEmpresa(), nombreEmpresa))
                .collect(Collectors.toList());
    }

    @Override
    public List<Proveedor> findByValidado(boolean validado) {
        return stream()
                .filter(proveedor -> proveedor.isValidado() == validado)
                .collect(Collectors.toList());
    }

    @Override
    protected Long getId(Proveedor proveedor) {
        return proveedor.getId();
    }

    @Override
    protected void setId(Proveedor proveedor, Long id) {
        proveedor.setId(id);
    }

    private static Map<Long, Proveedor> seedData() {
        Map<Long, Proveedor> proveedores = new LinkedHashMap<>();
        proveedores.put(1L, new Proveedor(1L, "Plásticos renovables ByFusion", "+18332925625",
                "https://byfusion.com/", true));
        proveedores.put(2L, new Proveedor(2L, "Hormigon Forpol Group", "+34977881287",
                "https://www.forpol.es/", true));
        return proveedores;
    }
}
