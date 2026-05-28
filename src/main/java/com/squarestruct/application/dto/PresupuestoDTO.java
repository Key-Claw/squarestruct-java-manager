package com.squarestruct.application.dto;

import java.time.LocalDate;
import java.util.List;

public class PresupuestoDTO {

    private Long id;
    private String nombreProyecto;
    private List<PresupuestoDetalleDTO> detalles;
    private double costeTotal;
    private LocalDate fechaCreacion;

    public PresupuestoDTO() {
    }

    public PresupuestoDTO(Long id, String nombreProyecto, List<PresupuestoDetalleDTO> detalles,
                          double costeTotal, LocalDate fechaCreacion) {
        this.id = id;
        this.nombreProyecto = nombreProyecto;
        this.detalles = detalles;
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

    public List<PresupuestoDetalleDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PresupuestoDetalleDTO> detalles) {
        this.detalles = detalles;
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
}
