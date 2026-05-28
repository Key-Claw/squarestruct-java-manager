// Servicio de aplicación para validar presupuestos y sus condiciones de negocio.
package com.squarestruct.application.service;

import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.repository.PresupuestoRepository;

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

    public void validarPresupuesto(Presupuesto presupuesto) {

        if (presupuesto == null) {
            throw new IllegalArgumentException("El presupuesto no puede ser nulo");
        }

        if (presupuesto.getNombreProyecto() == null || presupuesto.getNombreProyecto().isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }

        if (presupuesto.getProductos() == null || presupuesto.getProductos().isEmpty()) {
            throw new IllegalArgumentException("El presupuesto debe incluir al menos un producto");
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

}
