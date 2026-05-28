package com.squarestruct.application.dto;

/*
 * DTO de línea de presupuesto con precio unitario, cantidad y subtotal ya calculado.
 */
public class PresupuestoDetalleDTO {

    private Long productoId;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;

    public PresupuestoDetalleDTO() {
    }

    public PresupuestoDetalleDTO(Long productoId, String nombreProducto, double precioUnitario,
                                 int cantidad, double subtotal) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
