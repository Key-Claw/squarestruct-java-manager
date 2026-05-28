package com.squarestruct.application.mapper;

import com.squarestruct.application.dto.PresupuestoDTO;
import com.squarestruct.application.dto.PresupuestoDetalleDTO;
import com.squarestruct.domain.model.Presupuesto;
import com.squarestruct.domain.model.PresupuestoDetalle;
import com.squarestruct.domain.model.Producto;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Mapeador de salida para resúmenes de presupuesto.
 * Convierte líneas calculadas en DTOs listos para consola o futuras interfaces externas.
 */
public class PresupuestoMapper {

    private PresupuestoMapper() {
    }

    public static PresupuestoDTO toDTO(Presupuesto presupuesto) {
        if (presupuesto == null) {
            return null;
        }

        return new PresupuestoDTO(
                presupuesto.getId(),
                presupuesto.getNombreProyecto(),
                toDetalleDTOList(presupuesto.getDetalles()),
                presupuesto.getCosteTotal(),
                presupuesto.getFechaCreacion()
        );
    }

    private static List<PresupuestoDetalleDTO> toDetalleDTOList(List<PresupuestoDetalle> detalles) {
        if (detalles == null) {
            return Collections.emptyList();
        }

        return detalles.stream()
                .filter(Objects::nonNull)
                .map(PresupuestoMapper::toDetalleDTO)
                .collect(Collectors.toList());
    }

    private static PresupuestoDetalleDTO toDetalleDTO(PresupuestoDetalle detalle) {
        Producto producto = detalle.getProducto();

        return new PresupuestoDetalleDTO(
                producto != null ? producto.getId() : null,
                producto != null ? producto.getNombre() : null,
                producto != null ? producto.getPrecio() : 0.0,
                detalle.getCantidad(),
                detalle.getSubtotal()
        );
    }
}
