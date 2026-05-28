package com.squarestruct.infrastructure.persistence.mysql;

import com.squarestruct.manager.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Fábrica de conexiones JDBC.
 * Lee la configuración externa y deja a los repositorios solo la responsabilidad de ejecutar SQL.
 */
public class MySqlConnectionFactory {

    private final String url;
    private final String user;
    private final String password;

    public MySqlConnectionFactory() {
        this(DatabaseConfig.load());
    }

    public MySqlConnectionFactory(Properties properties) {
        this.url = DatabaseConfig.getRequiredProperty(properties, "db.url");
        this.user = DatabaseConfig.getRequiredProperty(properties, "db.user");
        this.password = DatabaseConfig.getRequiredProperty(properties, "db.password");
    }

    public Connection getConnection() {
        try {
            /*
             * Cada llamada abre una conexión nueva. Los repositorios la cierran con try-with-resources
             * alrededor de cada operación JDBC.
             */
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Error al conectar con la base de datos MySQL", e);
        }
    }
}
