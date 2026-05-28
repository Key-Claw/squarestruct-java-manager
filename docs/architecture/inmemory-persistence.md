# Persistencia en memoria

Este documento describe la implementación en memoria usada por `squarestruct-java-manager`.

## Cuándo se usa

La persistencia en memoria es el modo por defecto:

```properties
persistence.type=memory
```

Sirve para:

- arrancar la aplicación sin base de datos;
- probar servicios y repositorios de forma rápida;
- trabajar en el menú de consola durante desarrollo;
- mantener una alternativa temporal para agregados que aún no tienen implementación MySQL.

Los datos se pierden al finalizar el proceso.

## Paquete

Las clases viven en:

```text
src/main/java/com/squarestruct/infrastructure/persistence/memory
```

Implementaciones actuales:

- `InMemoryCrudRepository`
- `InMemoryProductoRepository`
- `InMemoryProveedorRepository`
- `InMemoryPedidoRepository`
- `InMemoryFacturaRepository`
- `InMemoryPresupuestoRepository`
- `InMemoryPlantillaRepository`

## Funcionamiento interno

`InMemoryCrudRepository` concentra el CRUD común con:

- `LinkedHashMap<Long, T>` como almacén temporal;
- generación incremental de IDs cuando la entidad llega sin identificador;
- `Optional` para búsquedas por ID;
- `List` para listados y búsquedas con varios resultados.

Los repositorios concretos implementan las búsquedas específicas definidas en `domain.repository`.

## Datos semilla

Los repositorios tienen constructores con datos de ejemplo:

```java
new InMemoryProductoRepository()
new InMemoryProductoRepository(true)
```

Para pruebas o fábricas auxiliares se puede crear un repositorio vacío:

```java
new InMemoryProductoRepository(false)
```

`InMemoryRepositoryFactory` usa datos semilla por defecto. `MySqlRepositoryFactory` usa una fábrica en memoria sin semillas como alternativa temporal para agregados MySQL todavía pendientes.

## Encaje con servicios

Los servicios no dependen de `InMemory...Repository`. Reciben interfaces:

```java
new PresupuestoService(repositoryFactory.presupuestoRepository());
```

Así el mismo servicio puede trabajar con memoria, MySQL u otra persistencia futura.

## Limitaciones

La persistencia en memoria no garantiza durabilidad, concurrencia entre procesos ni integridad referencial de base de datos. Es una implementación útil para desarrollo, pruebas y prototipado, no una sustitución de MySQL en producción.
