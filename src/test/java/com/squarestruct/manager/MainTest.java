package com.squarestruct.manager;

import com.squarestruct.infrastructure.persistence.memory.InMemoryProductoRepository;
import com.squarestruct.infrastructure.persistence.mysql.MySqlProductoRepository;
import com.squarestruct.manager.ui.menu.MainMenu;
import java.util.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/*
 * Comprueba la raíz de composición sin arrancar el bucle interactivo de consola.
 */
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

    private Properties properties(String persistenceType) {
        Properties properties = new Properties();
        properties.setProperty("persistence.type", persistenceType);
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/squarestruct");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "root");
        return properties;
    }
}
