package com.squarestruct.infrastructure.persistence.factory;

import com.squarestruct.domain.repository.FacturaRepository;
import com.squarestruct.domain.repository.PedidoRepository;
import com.squarestruct.domain.repository.PlantillaRepository;
import com.squarestruct.domain.repository.PresupuestoRepository;
import com.squarestruct.domain.repository.ProductoRepository;
import com.squarestruct.domain.repository.ProveedorRepository;

/*
 * Contrato de construcción de repositorios.
 * Main trabaja con esta interfaz para no depender de memoria, MySQL u otra infraestructura futura.
 */
public interface RepositoryFactory {

    ProductoRepository productoRepository();

    ProveedorRepository proveedorRepository();

    PedidoRepository pedidoRepository();

    FacturaRepository facturaRepository();

    PresupuestoRepository presupuestoRepository();

    PlantillaRepository plantillaRepository();
}
