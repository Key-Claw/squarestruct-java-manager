package com.squarestruct.domain.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Entidad de dominio para presupuestos de proyecto.
 * Mantiene compatibilidad con la lista antigua de productos y usa detalles para cálculos precisos.
 */
public class Presupuesto {

    private Long id;
    private String nombreProyecto;
    private List<Producto> productos;
    private List<PresupuestoDetalle> detalles;
    private double costeTotal;
    private LocalDate fechaCreacion;

    public Presupuesto() {
    }

    public Presupuesto(Long id, String nombreProyecto, List<Producto> productos,
                       double costeTotal, LocalDate fechaCreacion) {
        this.id = id;
        this.nombreProyecto = nombreProyecto;
        setProductos(productos);
        this.costeTotal = costeTotal;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public List<Producto> getProductos() {
        /*
         * Compatibilidad con datos antiguos: si solo existen detalles, se reconstruye la lista simple
         * de productos para consumidores que todavía no trabajan con PresupuestoDetalle.
         */
        if ((productos == null || productos.isEmpty()) && detalles != null) {
            return detalles.stream()
                    .filter(Objects::nonNull)
                    .map(PresupuestoDetalle::getProducto)
                    .collect(Collectors.toList());
        }

        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        this.detalles = null;
    }

    public List<PresupuestoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PresupuestoDetalle> detalles) {
        this.detalles = detalles;
        this.productos = extraerProductos(detalles);
    }

    public double getCosteTotal() {
        return costeTotal;
    }

    public void setCosteTotal(double costeTotal) {
        this.costeTotal = costeTotal;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    private List<Producto> extraerProductos(List<PresupuestoDetalle> detalles) {
        if (detalles == null) {
            return Collections.emptyList();
        }

        return detalles.stream()
                .filter(Objects::nonNull)
                .map(PresupuestoDetalle::getProducto)
                .collect(Collectors.toList());
    }
}
