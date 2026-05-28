package com.squarestruct.application.service;

import com.squarestruct.domain.repository.FacturaRepository;

/*
 * Servicio de aplicación de facturas.
 * La validación actual es un marcador temporal mientras se implementan casos de uso de facturación.
 */
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService() {
        this(null);
    }

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public void validarFactura() {

        System.out.println("Validación de factura correcta");

    }

    public FacturaRepository getFacturaRepository() {
        return facturaRepository;
    }

}
