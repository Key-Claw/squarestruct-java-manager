package com.squarestruct.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DatabaseConfig {

    private static final String CONFIG_FILE = "application.properties";

    private DatabaseConfig() {
    }

    public static Properties load() {
        Properties properties = new Properties();

        try (InputStream inputStream = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("No se ha encontrado el archivo " + CONFIG_FILE);
            }

            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar la configuracion de la aplicacion", e);
        }
    }

    public static String getRequiredProperty(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Falta la propiedad obligatoria: " + key);
        }

        return value.trim();
    }

    public static String getProperty(Properties properties, String key, String defaultValue) {
        String value = properties.getProperty(key);
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}
