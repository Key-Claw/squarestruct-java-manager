# Tests unitarios de servicios - Issue #8

Documento de trabajo que resume la revisión de la capa de servicios, los criterios de prueba elegidos y el resultado final de la validación.

## Estado inicial

Antes de esta tarea, la capa de servicios del proyecto estaba muy poco desarrollada:

- `ProductoService` y `PedidoService` solo validaban `null`.
- `PresupuestoService` y `PlantillaService` solo imprimían un mensaje en consola.
- No existía carpeta `src/test/java` ni dependencias de test en `pom.xml`.
- No se estaba usando Mockito porque no había dependencias colaboradoras que aislar.

La arquitectura real ya estaba separada por capas en `application`, `domain`, `infrastructure` y `manager`, así que los tests debían seguir esa misma organización y no crear una estructura paralela.

## Enfoque seguido

Se eligió un enfoque de tests unitarios puros, centrados en comportamiento observable:

- cada servicio se instancia directamente en el test
- no se usan repositorios, conexión a base de datos ni recursos externos
- no se introduce Mockito porque la capa de servicios no depende de otros objetos todavía
- los tests validan entradas correctas, casos erróneos y excepciones esperadas

Para que la capa de servicios tuviera reglas de negocio testeables, se añadieron validaciones mínimas y coherentes con el dominio actual:

- `ProductoService`: producto nulo, nombre vacío y precio negativo
- `PedidoService`: pedido nulo e identificador no positivo
- `PresupuestoService`: presupuesto nulo, proyecto vacío, lista de productos vacía, coste negativo y fecha nula
- `PlantillaService`: plantilla nula, nombre vacío y bloques vacíos

## Cobertura añadida

Se crearon estos tests:

- `ProductoServiceTest`
- `PedidoServiceTest`
- `PresupuestoServiceTest`
- `PlantillaServiceTest`

Cada uno cubre al menos:

- un caso feliz
- un caso de error por `null`
- una validación de negocio relevante
- una excepción con mensaje esperado

## Dependencias y aislamiento

En `pom.xml` se añadió:

- `org.junit.jupiter:junit-jupiter`
- `maven-surefire-plugin` para ejecutar correctamente JUnit 5

No se añadió Mockito porque no hacía falta para este slice funcional. La capa de servicios quedó aislada mediante objetos de dominio/DTO construidos directamente dentro de cada test.

## Resultado final

La verificación se ejecutó con Maven y el resultado fue correcto:

- 14 tests ejecutados
- 0 fallos
- 0 errores
- build exitoso

La validación se realizó con Maven 3.9.9 en un contenedor oficial porque la terminal local no tenía `mvn` instalado.

## Flujo seguido

1. Se revisó la arquitectura real, los contratos de repositorio, los DTOs y la capa de servicios.
2. Se confirmó que la capa de servicios no tenía lógica de negocio real ni tests previos.
3. Se añadió soporte de JUnit 5 en Maven.
4. Se implementaron validaciones mínimas y testeables en los servicios.
5. Se crearon tests unitarios independientes por servicio.
6. Se ejecutó la suite con Maven y se verificó que todo pasa.
