package com.squarestruct.infrastructure.persistence.factory;

import com.squarestruct.domain.repository.FacturaRepository;
import com.squarestruct.domain.repository.PedidoRepository;
import com.squarestruct.domain.repository.PlantillaRepository;
import com.squarestruct.domain.repository.PresupuestoRepository;
import com.squarestruct.domain.repository.ProductoRepository;
import com.squarestruct.domain.repository.ProveedorRepository;
import com.squarestruct.infrastructure.persistence.mysql.MySqlConnectionFactory;
import com.squarestruct.infrastructure.persistence.mysql.MySqlProductoRepository;
import java.util.Properties;

public class MySqlRepositoryFactory implements RepositoryFactory {

    private final ProductoRepository productoRepository;
    private final RepositoryFactory fallbackRepositoryFactory;

    public MySqlRepositoryFactory() {
        this(new MySqlConnectionFactory(), new InMemoryRepositoryFactory(false));
    }

    public MySqlRepositoryFactory(Properties properties) {
        this(new MySqlConnectionFactory(properties), new InMemoryRepositoryFactory(false));
    }

    public MySqlRepositoryFactory(MySqlConnectionFactory connectionFactory) {
        this(connectionFactory, new InMemoryRepositoryFactory(false));
    }

    public MySqlRepositoryFactory(MySqlConnectionFactory connectionFactory,
                                  RepositoryFactory fallbackRepositoryFactory) {
        this.productoRepository = new MySqlProductoRepository(connectionFactory);
        this.fallbackRepositoryFactory = fallbackRepositoryFactory;
    }

    @Override
    public ProductoRepository productoRepository() {
        return productoRepository;
    }

    @Override
    public ProveedorRepository proveedorRepository() {
        return fallbackRepositoryFactory.proveedorRepository();
    }

    @Override
    public PedidoRepository pedidoRepository() {
        return fallbackRepositoryFactory.pedidoRepository();
    }

    @Override
    public FacturaRepository facturaRepository() {
        return fallbackRepositoryFactory.facturaRepository();
    }

    @Override
    public PresupuestoRepository presupuestoRepository() {
        return fallbackRepositoryFactory.presupuestoRepository();
    }

    @Override
    public PlantillaRepository plantillaRepository() {
        return fallbackRepositoryFactory.plantillaRepository();
    }
}
