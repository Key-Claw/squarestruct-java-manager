package com.squarestruct.infrastructure.persistence.factory;

import com.squarestruct.domain.repository.FacturaRepository;
import com.squarestruct.domain.repository.PedidoRepository;
import com.squarestruct.domain.repository.PlantillaRepository;
import com.squarestruct.domain.repository.PresupuestoRepository;
import com.squarestruct.domain.repository.ProductoRepository;
import com.squarestruct.domain.repository.ProveedorRepository;

public interface RepositoryFactory {

    ProductoRepository productoRepository();

    ProveedorRepository proveedorRepository();

    PedidoRepository pedidoRepository();

    FacturaRepository facturaRepository();

    PresupuestoRepository presupuestoRepository();

    PlantillaRepository plantillaRepository();
}
