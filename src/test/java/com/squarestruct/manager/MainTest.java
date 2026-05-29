package com.squarestruct.manager;

import com.squarestruct.infrastructure.persistence.memory.InMemoryProductoRepository;
import com.squarestruct.infrastructure.persistence.mysql.MySqlProductoRepository;
import com.squarestruct.manager.ui.menu.MainMenu;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void debeInicializarMenuConPersistenciaEnMemoria() {
        MainMenu mainMenu = Main.createMainMenu(properties("memory"));

        assertEquals("memory", mainMenu.getPersistenceType());
        assertInstanceOf(
                InMemoryProductoRepository.class,
                mainMenu.getProductoService().getProductoRepository()
        );
    }

    @Test
    void debeInicializarMenuConPersistenciaMysql() {
        MainMenu mainMenu = Main.createMainMenu(properties("mysql"));

        assertEquals("mysql", mainMenu.getPersistenceType());
        assertInstanceOf(
                MySqlProductoRepository.class,
                mainMenu.getProductoService().getProductoRepository()
        );
    }

    @Test
    void debeListarPlantillasSinMensajePendiente() {
        PrintStream salidaOriginal = System.out;
        java.io.InputStream entradaOriginal = System.in;
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        String entrada = "6\n1\n0\n0\n";

        System.setIn(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(salida, true, StandardCharsets.UTF_8));

        try {
            Main.createMainMenu(properties("memory")).iniciar();
        } finally {
            System.setOut(salidaOriginal);
            System.setIn(entradaOriginal);
        }

        String consola = salida.toString(StandardCharsets.UTF_8);

        assertTrue(consola.contains("Plantilla muro basico"));
        assertFalse(consola.contains("Funcionalidad " + "pendiente"));
    }

    private Properties properties(String persistenceType) {
        Properties properties = new Properties();
        properties.setProperty("persistence.type", persistenceType);
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/squarestruct");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "root");
        return properties;
    }
}
