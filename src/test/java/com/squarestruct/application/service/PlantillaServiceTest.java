// Pruebas unitarias del servicio de plantillas constructivas con validaciones mínimas.
package com.squarestruct.application.service;

import com.squarestruct.domain.model.BloquePlantilla;
import com.squarestruct.domain.model.PlantillaConstructiva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlantillaServiceTest {

    private final PlantillaService plantillaService = new PlantillaService();

    @Test
    @DisplayName("valida una plantilla correcta")
    void validarPlantillaConDatosValidosNoLanzaExcepcion() {
        PlantillaConstructiva plantilla = new PlantillaConstructiva(1L, "Plantilla muro basico",
                "Plantilla inicial", Collections.singletonList(new BloquePlantilla()));

        assertDoesNotThrow(() -> plantillaService.validarPlantilla(plantilla));
    }

    @Test
    @DisplayName("rechaza una plantilla nula")
    void validarPlantillaNulaLanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> plantillaService.validarPlantilla(null));

        assertEquals("La plantilla no puede ser nula", exception.getMessage());
    }

    @Test
    @DisplayName("rechaza una plantilla sin bloques")
    void validarPlantillaSinBloquesLanzaExcepcion() {
        PlantillaConstructiva plantilla = new PlantillaConstructiva(1L, "Plantilla muro basico",
                "Plantilla inicial", Collections.emptyList());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> plantillaService.validarPlantilla(plantilla));

        assertEquals("La plantilla debe incluir al menos un bloque", exception.getMessage());
    }
}