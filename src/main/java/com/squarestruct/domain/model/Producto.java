package com.squarestruct.domain.model;

import com.squarestruct.domain.enums.TipoProducto;

public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private TipoProducto tipo;
    private String material;
    private double alto;
    private double ancho;
    private double largo;
    private Proveedor proveedor;

    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, double precio, TipoProducto tipo,
                    String material, double alto, double ancho, double largo, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.material = material;
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
        this.proveedor = proveedor;
    }
}