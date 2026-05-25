package com.squarestruct.infrastructure.persistence.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Fábrica de conexiones para MySQL que carga la configuración desde un archivo properties
public class MySqlConnectionFactory {

    private static final String CONFIG_FILE = "application.properties";
    private final String url;
    private final String user;
    private final String password;

    // Constructor que carga la configuración de la base de datos desde el archivo properties
    public MySqlConnectionFactory() {
        Properties properties = loadProperties();

        this.url = getRequiredProperty(properties, "db.url");
        this.user = getRequiredProperty(properties, "db.user");
        this.password = getRequiredProperty(properties, "db.password");
    }

    // Método para obtener una conexión a la base de datos MySQL
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Error al conectar con la base de datos MySQL", e);
        }
    }

    // Método privado para cargar las propiedades desde el archivo de configuración
    private Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("No se ha encontrado el archivo " + CONFIG_FILE);
            }

            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar la configuración de base de datos", e);
        }
    }

    // Método privado para obtener una propiedad requerida y lanzar una excepción si no está presente o es vacía
    private String getRequiredProperty(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Falta la propiedad obligatoria: " + key);
        }

        return value;
    }
}