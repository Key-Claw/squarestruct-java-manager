package com.squarestruct.infrastructure.persistence.mysql;

import com.squarestruct.manager.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Fabrica de conexiones para MySQL que carga la configuracion desde un archivo properties.
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
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Error al conectar con la base de datos MySQL", e);
        }
    }
}
