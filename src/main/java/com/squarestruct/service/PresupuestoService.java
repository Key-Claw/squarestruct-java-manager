// Servicio de aplicación para validar y calcular presupuestos.
package com.squarestruct.application.service;

import com.squarestruct.application.dto.PresupuestoDTO;
import com.squarestruct.application.dto.PresupuestoDetalleDTO;
import com.squarestruct.application.mapper.PresupuestoMapper;
import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.model.PresupuestoDetalle;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.repository.PresupuestoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;

    public PresupuestoService() {
        this(null);
    }

    public PresupuestoService(PresupuestoRepository presupuestoRepository) {
        this.presupuestoRepository = presupuestoRepository;
    }

    public void validarPresupuesto() {

        System.out.println("Validación de presupuesto correcta");

    }

    public Presupuesto calcularPresupuesto(String nombreProyecto, List<PresupuestoDetalle> detalles) {
        validarNombreProyecto(nombreProyecto);
        validarDetalles(detalles);

        List<PresupuestoDetalle> detallesCalculados = detalles.stream()
                .map(detalle -> calcularLinea(detalle.getProducto(), detalle.getCantidad()))
                .collect(Collectors.toList());

        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setNombreProyecto(nombreProyecto);
        presupuesto.setDetalles(detallesCalculados);
        presupuesto.setCosteTotal(calcularCosteTotal(detallesCalculados));
        presupuesto.setFechaCreacion(LocalDate.now());

        validarPresupuesto(presupuesto);
        return presupuesto;
    }

    public Presupuesto crearPresupuesto(String nombreProyecto, List<PresupuestoDetalle> detalles) {
        Presupuesto presupuesto = calcularPresupuesto(nombreProyecto, detalles);

        if (presupuestoRepository == null) {
            return presupuesto;
        }

        return presupuestoRepository.create(presupuesto);
    }

    public PresupuestoDetalle calcularLinea(Producto producto, int cantidad) {
        return new PresupuestoDetalle(null, producto, cantidad, calcularSubtotal(producto, cantidad));
    }

    public double calcularSubtotal(Producto producto, int cantidad) {
        validarLinea(producto, cantidad);
        return producto.getPrecio() * cantidad;
    }

    public void mostrarResumen(Presupuesto presupuesto) {
        validarPresupuesto(presupuesto);

        PresupuestoDTO presupuestoDTO = PresupuestoMapper.toDTO(prepararPresupuestoParaResumen(presupuesto));

        System.out.println("Resumen del presupuesto: " + presupuestoDTO.getNombreProyecto());
        System.out.println("Fecha: " + presupuestoDTO.getFechaCreacion());
        System.out.println("Lineas:");

        for (PresupuestoDetalleDTO detalleDTO : presupuestoDTO.getDetalles()) {
            System.out.println(String.format(
                    Locale.ROOT,
                    "- %s | precio unitario: %.2f | cantidad: %d | subtotal: %.2f",
                    nombreProducto(detalleDTO),
                    detalleDTO.getPrecioUnitario(),
                    detalleDTO.getCantidad(),
                    detalleDTO.getSubtotal()
            ));
        }

        System.out.println(String.format(Locale.ROOT, "Total: %.2f", presupuestoDTO.getCosteTotal()));
    }

    public void mostrarResumenesGuardados() {
        if (presupuestoRepository == null) {
            System.out.println("No hay repositorio de presupuestos configurado.");
            return;
        }

        List<Presupuesto> presupuestos = presupuestoRepository.findAll();

        if (presupuestos.isEmpty()) {
            System.out.println("No hay presupuestos registrados.");
            return;
        }

        presupuestos.forEach(this::mostrarResumen);
    }

    public void validarPresupuesto(Presupuesto presupuesto) {

        if (presupuesto == null) {
            throw new IllegalArgumentException("El presupuesto no puede ser nulo");
        }

        validarNombreProyecto(presupuesto.getNombreProyecto());

        if (tieneDetalles(presupuesto)) {
            validarDetallesCalculados(presupuesto.getDetalles());
        } else {
            validarProductos(presupuesto.getProductos());
        }

        if (presupuesto.getCosteTotal() < 0) {
            throw new IllegalArgumentException("El coste total no puede ser negativo");
        }

        if (presupuesto.getFechaCreacion() == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula");
        }

    }

    public PresupuestoRepository getPresupuestoRepository() {
        return presupuestoRepository;
    }

    private double calcularCosteTotal(List<PresupuestoDetalle> detalles) {
        return detalles.stream()
                .mapToDouble(PresupuestoDetalle::getSubtotal)
                .sum();
    }

    private Presupuesto prepararPresupuestoParaResumen(Presupuesto presupuesto) {
        if (tieneDetalles(presupuesto)) {
            return presupuesto;
        }

        List<PresupuestoDetalle> detallesCalculados = presupuesto.getProductos().stream()
                .map(producto -> calcularLinea(producto, 1))
                .collect(Collectors.toList());

        Presupuesto resumen = new Presupuesto();
        resumen.setId(presupuesto.getId());
        resumen.setNombreProyecto(presupuesto.getNombreProyecto());
        resumen.setDetalles(detallesCalculados);
        resumen.setCosteTotal(calcularCosteTotal(detallesCalculados));
        resumen.setFechaCreacion(presupuesto.getFechaCreacion());

        return resumen;
    }

    private void validarNombreProyecto(String nombreProyecto) {
        if (nombreProyecto == null || nombreProyecto.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
    }

    private void validarDetalles(List<PresupuestoDetalle> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("El presupuesto debe incluir al menos un producto");
        }

        detalles.forEach(detalle -> {
            if (detalle == null) {
                throw new IllegalArgumentException("La linea del presupuesto no puede ser nula");
            }

            validarLinea(detalle.getProducto(), detalle.getCantidad());
        });
    }

    private void validarDetallesCalculados(List<PresupuestoDetalle> detalles) {
        validarDetalles(detalles);

        detalles.forEach(detalle -> {
            if (detalle.getSubtotal() < 0) {
                throw new IllegalArgumentException("El subtotal de la linea no puede ser negativo");
            }
        });
    }

    private void validarProductos(List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("El presupuesto debe incluir al menos un producto");
        }

        productos.forEach(this::validarProducto);
    }

    private void validarLinea(Producto producto, int cantidad) {
        validarProducto(producto);

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto del presupuesto no puede ser nulo");
        }

        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo");
        }
    }

    private boolean tieneDetalles(Presupuesto presupuesto) {
        return presupuesto.getDetalles() != null && !presupuesto.getDetalles().isEmpty();
    }

    private String nombreProducto(PresupuestoDetalleDTO detalleDTO) {
        if (detalleDTO.getNombreProducto() == null || detalleDTO.getNombreProducto().isBlank()) {
            return "Producto sin nombre";
        }

        return detalleDTO.getNombreProducto();
    }

}
