package com.squarestruct.application.service;

import com.squarestruct.application.dto.ProveedorDTO;
import com.squarestruct.domain.repository.ProveedorRepository;

/*
 * Servicio de aplicación de proveedores.
 * Centraliza validaciones y conserva el punto de inyección del repositorio para futuras operaciones CRUD.
 */
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService() {
        this(null);
    }

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public void validarProveedor(ProveedorDTO proveedorDTO) {

        if (proveedorDTO == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo");
        }

    }

    public ProveedorRepository getProveedorRepository() {
        return proveedorRepository;
    }

}
