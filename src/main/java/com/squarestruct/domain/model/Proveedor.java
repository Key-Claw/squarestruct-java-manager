package com.squarestruct.domain.model;

public class Proveedor {

    private Long id;
    private String nombreEmpresa;
    private String telefono;
    private String sitioWeb;
    private boolean validado;

    public Proveedor() {
    }

    public Proveedor(Long id, String nombreEmpresa, String telefono, String sitioWeb, boolean validado) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.telefono = telefono;
        this.sitioWeb = sitioWeb;
        this.validado = validado;
    }
}