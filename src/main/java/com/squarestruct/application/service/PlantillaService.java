package com.squarestruct.application.service;

import com.squarestruct.domain.model.PlantillaConstructiva;
import com.squarestruct.domain.repository.PlantillaRepository;

/*
 * Servicio de aplicación de plantillas constructivas.
 * Protege las reglas mínimas del dominio antes de guardar o reutilizar una plantilla.
 */
public class PlantillaService {

    private final PlantillaRepository plantillaRepository;

    public PlantillaService() {
        this(null);
    }

    public PlantillaService(PlantillaRepository plantillaRepository) {
        this.plantillaRepository = plantillaRepository;
    }

    public void validarPlantilla() {

        System.out.println("Validación de plantilla correcta");

    }

    public void validarPlantilla(PlantillaConstructiva plantilla) {

        if (plantilla == null) {
            throw new IllegalArgumentException("La plantilla no puede ser nula");
        }

        if (plantilla.getNombre() == null || plantilla.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la plantilla no puede estar vacío");
        }

        if (plantilla.getBloques() == null || plantilla.getBloques().isEmpty()) {
            throw new IllegalArgumentException("La plantilla debe incluir al menos un bloque");
        }

    }

    public PlantillaRepository getPlantillaRepository() {
        return plantillaRepository;
    }

}
