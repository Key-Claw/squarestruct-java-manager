package com.squarestruct.infrastructure.persistence.factory;

import com.squarestruct.manager.config.DatabaseConfig;
import java.util.Locale;
import java.util.Properties;

/*
 * Selector central de persistencia.
 * Traduce application.properties a una fábrica concreta y evita decisiones de infraestructura en servicios.
 */
public final class RepositoryFactoryProvider {

    public static final String PERSISTENCE_TYPE_PROPERTY = "persistence.type";
    public static final String DEFAULT_PERSISTENCE_TYPE = "memory";

    private RepositoryFactoryProvider() {
    }

    public static RepositoryFactory getFactory() {
        return getFactory(DatabaseConfig.load());
    }

    public static RepositoryFactory getFactory(Properties properties) {
        String persistenceType = DatabaseConfig.getProperty(
                properties,
                PERSISTENCE_TYPE_PROPERTY,
                DEFAULT_PERSISTENCE_TYPE
        );

        /*
         * Se aceptan alias habituales para no propagar detalles de configuración
         * fuera del punto central de selección de persistencia.
         */
        return switch (normalize(persistenceType)) {
            case "memory", "inmemory", "in-memory" -> new InMemoryRepositoryFactory();
            case "mysql", "mariadb" -> new MySqlRepositoryFactory(properties);
            default -> throw new IllegalArgumentException(
                    "Tipo de persistencia no soportado: " + persistenceType
            );
        };
    }

    public static RepositoryFactory create() {
        return getFactory();
    }

    public static RepositoryFactory create(Properties properties) {
        return getFactory(properties);
    }

    public static String getConfiguredPersistenceType() {
        return getConfiguredPersistenceType(DatabaseConfig.load());
    }

    public static String getConfiguredPersistenceType(Properties properties) {
        return normalize(DatabaseConfig.getProperty(
                properties,
                PERSISTENCE_TYPE_PROPERTY,
                DEFAULT_PERSISTENCE_TYPE
        ));
    }

    private static String normalize(String persistenceType) {
        return persistenceType.trim().toLowerCase(Locale.ROOT);
    }
}
