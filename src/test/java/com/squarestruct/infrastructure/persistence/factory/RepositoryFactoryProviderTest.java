package com.squarestruct.infrastructure.persistence.factory;

import com.squarestruct.infrastructure.persistence.memory.InMemoryProductoRepository;
import com.squarestruct.infrastructure.persistence.mysql.MySqlProductoRepository;
import java.util.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RepositoryFactoryProviderTest {

    @Test
    void debeSeleccionarPersistenciaEnMemoria() {
        RepositoryFactory factory = RepositoryFactoryProvider.getFactory(properties("memory"));

        assertInstanceOf(InMemoryRepositoryFactory.class, factory);
        assertInstanceOf(InMemoryProductoRepository.class, factory.productoRepository());
    }

    @Test
    void debeSeleccionarPersistenciaMysql() {
        RepositoryFactory factory = RepositoryFactoryProvider.getFactory(properties("mysql"));

        assertInstanceOf(MySqlRepositoryFactory.class, factory);
        assertInstanceOf(MySqlProductoRepository.class, factory.productoRepository());
    }

    @Test
    void debeUsarMemoriaSiNoHayTipoConfigurado() {
        Properties properties = properties("memory");
        properties.remove(RepositoryFactoryProvider.PERSISTENCE_TYPE_PROPERTY);

        RepositoryFactory factory = RepositoryFactoryProvider.getFactory(properties);

        assertInstanceOf(InMemoryRepositoryFactory.class, factory);
    }

    @Test
    void debeRechazarTipoNoSoportado() {
        Properties properties = properties("archivo");

        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactoryProvider.getFactory(properties));
    }

    private Properties properties(String persistenceType) {
        Properties properties = new Properties();
        properties.setProperty(RepositoryFactoryProvider.PERSISTENCE_TYPE_PROPERTY, persistenceType);
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/squarestruct");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "root");
        return properties;
    }
}
