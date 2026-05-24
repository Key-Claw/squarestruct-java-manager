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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }
}