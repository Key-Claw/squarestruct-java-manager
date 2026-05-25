package com.squarestruct.application.service;

import com.squarestruct.application.dto.ProveedorDTO;

public class ProveedorService {

    public void validarProveedor(ProveedorDTO proveedorDTO) {

        if (proveedorDTO == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo");
        }

    }

}