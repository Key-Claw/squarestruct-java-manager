package com.squarestruct.application.dto;

public class ProveedorDTO {

    private Long id;
    private String nombreEmpresa;
    private String telefono;

    public ProveedorDTO(Long id, String nombreEmpresa, String telefono) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.telefono = telefono;
    }

    public ProveedorDTO() {
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
}
