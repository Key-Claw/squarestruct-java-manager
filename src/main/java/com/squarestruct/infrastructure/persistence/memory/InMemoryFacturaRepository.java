package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.EstadoPedido;
import com.squarestruct.domain.enums.RolUsuario;
import com.squarestruct.domain.model.Factura;
import com.squarestruct.domain.model.Pedido;
import com.squarestruct.domain.model.Usuario;
import com.squarestruct.domain.repository.FacturaRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * Repositorio en memoria de facturas.
 * Implementa las búsquedas de facturas sin usar infraestructura de base de datos.
 */
public class InMemoryFacturaRepository extends InMemoryCrudRepository<Factura>
        implements FacturaRepository {

    public InMemoryFacturaRepository() {
        this(true);
    }

    public InMemoryFacturaRepository(boolean seedData) {
        super(seedData ? seedData() : Collections.emptyMap());
    }

    @Override
    public Optional<Factura> findByPedidoId(Long pedidoId) {
        return stream()
                .filter(factura -> factura.getPedido() != null)
                .filter(factura -> Objects.equals(factura.getPedido().getId(), pedidoId))
                .findFirst();
    }

    @Override
    public List<Factura> findByMetodoPago(String metodoPago) {
        return stream()
                .filter(factura -> equalsIgnoreCase(factura.getMetodoPago(), metodoPago))
                .collect(Collectors.toList());
    }

    @Override
    public List<Factura> findByFechaFacturaBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return stream()
                .filter(factura -> isBetweenInclusive(factura.getFechaFactura(), fechaInicio, fechaFin))
                .collect(Collectors.toList());
    }

    @Override
    protected Long getId(Factura factura) {
        return factura.getId();
    }

    @Override
    protected void setId(Factura factura, Long id) {
        factura.setId(id);
    }

    private static Map<Long, Factura> seedData() {
        Usuario usuario = new Usuario(1L, "Admin SquareStruct", "admin@sqst.com",
                "password", RolUsuario.ADMIN);
        Pedido pedido = new Pedido(1L, usuario, Collections.emptyList(),
                EstadoPedido.PAGADO, LocalDate.now(), 228.00);
        Map<Long, Factura> facturas = new LinkedHashMap<>();

        facturas.put(1L, new Factura(1L, pedido, LocalDate.now(), 228.00, "tarjeta"));
        return facturas;
    }
}
