# Pruebas del proyecto

Este documento resume la estrategia de pruebas actual de `squarestruct-java-manager`.

## Herramientas

El proyecto usa:

- JUnit 5
- Maven Surefire
- Maven como punto de entrada para el conjunto de pruebas

La configuración está en `pom.xml`.

## Ejecutar pruebas

Suite completa:

```bash
mvn test
```

Una clase concreta:

```bash
mvn -Dtest=PresupuestoServiceTest test
```

Un paquete o patrón concreto puede ejecutarse con las reglas habituales de Surefire:

```bash
mvn -Dtest=*ServiceTest test
```

## Organización

Las pruebas viven en:

```text
src/test/java
```

Áreas cubiertas:

- `com.squarestruct.application.service`
- `com.squarestruct.repository`
- `com.squarestruct.infrastructure.persistence.factory`
- `com.squarestruct.manager`

## Servicios

Las pruebas de servicios validan reglas de aplicación y comportamiento observable:

- productos: DTO nulo, nombre vacío y precio negativo;
- pedidos: DTO nulo e identificador no positivo;
- plantillas: plantilla nula, nombre vacío y bloques vacíos;
- presupuestos: cálculo de líneas, subtotales, total, validaciones y salida de resumen.

`PresupuestoServiceTest` también cubre el guardado cuando el servicio recibe un `InMemoryPresupuestoRepository`.

## Repositorios en memoria

Las pruebas de repositorio verifican operaciones básicas sobre datos en memoria y búsquedas relacionadas con los agregados. El modo en memoria permite probar sin MySQL ni recursos externos.

Cuando un repositorio necesita partir vacío, se usa el constructor con `false`:

```java
new InMemoryPresupuestoRepository(false)
```

## Factory y arranque

`RepositoryFactoryProviderTest` comprueba:

- selección de `InMemoryRepositoryFactory`;
- selección de `MySqlRepositoryFactory`;
- valor por defecto `memory`;
- rechazo de tipos de persistencia no soportados.

`MainTest` comprueba que `Main.createMainMenu` construye el menú con la persistencia esperada y conecta los servicios con los repositorios correctos.

## Base de datos en pruebas

El conjunto de pruebas actual no requiere una base de datos MySQL levantada. Las pruebas de MySQL existentes comprueban selección y construcción de repositorios, no ejecución real de consultas JDBC contra una instancia externa.

Si se añaden pruebas de integración MySQL en el futuro, deberían separarse claramente del conjunto de pruebas unitarias para no hacer obligatorio un servicio externo en cada ejecución local.

## CI

El flujo de GitHub Actions ejecuta:

```bash
mvn validate
mvn compile
mvn test
```

Más detalle en [GitHub Actions CI](github-actions-ci.md).
