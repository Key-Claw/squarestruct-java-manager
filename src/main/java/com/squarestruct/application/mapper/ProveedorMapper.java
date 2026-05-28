package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.ProveedorDTO;
import com.squarestruct.domain.model.Proveedor;

/*
 * Mapeador de salida para proveedores.
 * Mantiene en el DTO solo los datos necesarios para listados o validaciones simples.
 */
public class ProveedorMapper {

    public static ProveedorDTO toDTO(Proveedor proveedor) {

        return new ProveedorDTO(
                proveedor.getId(),
                proveedor.getNombreEmpresa(),
                proveedor.getTelefono()
        );
    }
}
