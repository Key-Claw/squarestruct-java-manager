package com.squarestruct.infrastructure.persistence.factory;

import com.squarestruct.domain.repository.FacturaRepository;
import com.squarestruct.domain.repository.PedidoRepository;
import com.squarestruct.domain.repository.PlantillaRepository;
import com.squarestruct.domain.repository.PresupuestoRepository;
import com.squarestruct.domain.repository.ProductoRepository;
import com.squarestruct.domain.repository.ProveedorRepository;
import com.squarestruct.infrastructure.persistence.memory.InMemoryFacturaRepository;
import com.squarestruct.infrastructure.persistence.memory.InMemoryPedidoRepository;
import com.squarestruct.infrastructure.persistence.memory.InMemoryPlantillaRepository;
import com.squarestruct.infrastructure.persistence.memory.InMemoryPresupuestoRepository;
import com.squarestruct.infrastructure.persistence.memory.InMemoryProductoRepository;
import com.squarestruct.infrastructure.persistence.memory.InMemoryProveedorRepository;

/*
 * Fábrica completa para ejecución sin base de datos.
 * El parámetro seedData permite arrancar con datos de demo o crear repositorios vacíos para pruebas.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final PedidoRepository pedidoRepository;
    private final FacturaRepository facturaRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final PlantillaRepository plantillaRepository;

    public InMemoryRepositoryFactory() {
        this(true);
    }

    public InMemoryRepositoryFactory(boolean seedData) {
        this.productoRepository = new InMemoryProductoRepository(seedData);
        this.proveedorRepository = new InMemoryProveedorRepository(seedData);
        this.pedidoRepository = new InMemoryPedidoRepository(seedData);
        this.facturaRepository = new InMemoryFacturaRepository(seedData);
        this.presupuestoRepository = new InMemoryPresupuestoRepository(seedData);
        this.plantillaRepository = new InMemoryPlantillaRepository(seedData);
    }

    @Override
    public ProductoRepository productoRepository() {
        return productoRepository;
    }

    @Override
    public ProveedorRepository proveedorRepository() {
        return proveedorRepository;
    }

    @Override
    public PedidoRepository pedidoRepository() {
        return pedidoRepository;
    }

    @Override
    public FacturaRepository facturaRepository() {
        return facturaRepository;
    }

    @Override
    public PresupuestoRepository presupuestoRepository() {
        return presupuestoRepository;
    }

    @Override
    public PlantillaRepository plantillaRepository() {
        return plantillaRepository;
    }
}
