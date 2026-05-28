package com.squarestruct.manager;

import com.squarestruct.application.service.FacturaService;
import com.squarestruct.application.service.PedidoService;
import com.squarestruct.application.service.PlantillaService;
import com.squarestruct.application.service.PresupuestoService;
import com.squarestruct.application.service.ProductoService;
import com.squarestruct.application.service.ProveedorService;
import com.squarestruct.infrastructure.persistence.factory.RepositoryFactory;
import com.squarestruct.infrastructure.persistence.factory.RepositoryFactoryProvider;
import com.squarestruct.manager.config.DatabaseConfig;
import com.squarestruct.manager.ui.menu.MainMenu;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        MainMenu mainMenu = createMainMenu();
        mainMenu.iniciar();
    }

    public static MainMenu createMainMenu() {
        return createMainMenu(DatabaseConfig.load());
    }

    static MainMenu createMainMenu(Properties properties) {
        RepositoryFactory repositoryFactory = RepositoryFactoryProvider.getFactory(properties);

        return new MainMenu(
                new ProductoService(repositoryFactory.productoRepository()),
                new ProveedorService(repositoryFactory.proveedorRepository()),
                new PedidoService(repositoryFactory.pedidoRepository()),
                new FacturaService(repositoryFactory.facturaRepository()),
                new PresupuestoService(repositoryFactory.presupuestoRepository()),
                new PlantillaService(repositoryFactory.plantillaRepository()),
                RepositoryFactoryProvider.getConfiguredPersistenceType(properties)
        );
    }
}
