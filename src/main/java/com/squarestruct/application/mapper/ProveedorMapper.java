package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.ProveedorDTO;
import com.squarestruct.domain.model.Proveedor;

public class ProveedorMapper {

    public static ProveedorDTO toDTO(Proveedor proveedor) {

        return new ProveedorDTO(
                proveedor.getId(),
                proveedor.getNombreEmpresa(),
                proveedor.getTelefono()
        );
    }
}