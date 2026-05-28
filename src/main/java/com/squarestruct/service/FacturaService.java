package com.squarestruct.application.service;

import com.squarestruct.domain.repository.FacturaRepository;

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
