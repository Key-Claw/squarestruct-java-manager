package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.enums.EstadoPedido;
import com.squarestruct.domain.enums.RolUsuario;
import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Pedido;
import com.squarestruct.domain.model.PedidoDetalle;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import com.squarestruct.domain.model.Usuario;
import com.squarestruct.domain.repository.PedidoRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Repositorio en memoria de pedidos.
 * Guarda pedidos temporalmente y permite buscar por usuario, estado y fechas.
 */
public class InMemoryPedidoRepository extends InMemoryCrudRepository<Pedido>
        implements PedidoRepository {

    public InMemoryPedidoRepository() {
        this(true);
    }

    public InMemoryPedidoRepository(boolean seedData) {
        super(seedData ? seedData() : Collections.emptyMap());
    }

    @Override
    public List<Pedido> findByUsuarioId(Long usuarioId) {
        return stream()
                .filter(pedido -> pedido.getUsuario() != null)
                .filter(pedido -> Objects.equals(pedido.getUsuario().getId(), usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findByEstado(EstadoPedido estado) {
        return stream()
                .filter(pedido -> pedido.getEstado() == estado)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findByFechaPedidoBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return stream()
                .filter(pedido -> isBetweenInclusive(pedido.getFechaPedido(), fechaInicio, fechaFin))
                .collect(Collectors.toList());
    }

    @Override
    protected Long getId(Pedido pedido) {
        return pedido.getId();
    }

    @Override
    protected void setId(Pedido pedido, Long id) {
        pedido.setId(id);
    }

    private static Map<Long, Pedido> seedData() {
        Usuario usuario = new Usuario(1L, "Admin SquareStruct", "admin@sqst.com",
                "password", RolUsuario.ADMIN);
        Proveedor proveedor = new Proveedor(1L, "Plásticos renovables ByFusion",
                "+18332925625", "https://byfusion.com/", true);
        Producto producto = new Producto(1L, "Bloque Eco H80 Max",
                "Bloque eco modular de gran formato.", 114.00, TipoProducto.BLOQUE,
                "Plástico reciclable", 20.00, 20.00, 80.00, proveedor);
        PedidoDetalle detalle = new PedidoDetalle(1L, producto, 2, 228.00);
        Map<Long, Pedido> pedidos = new LinkedHashMap<>();

        pedidos.put(1L, new Pedido(1L, usuario, Collections.singletonList(detalle),
                EstadoPedido.PENDIENTE, LocalDate.now(), 228.00));
        return pedidos;
    }
}
