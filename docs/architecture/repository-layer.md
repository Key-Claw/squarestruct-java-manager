# Capa de repositorios

Este documento explica el uso del patrón Repository en `squarestruct-java-manager`.

## Objetivo

Los repositorios aíslan la lógica de aplicación de la tecnología de persistencia. Un servicio debe poder trabajar con productos, pedidos o presupuestos sin saber si los datos vienen de memoria, MySQL u otra fuente futura.

Por eso los contratos viven en el dominio:

```text
src/main/java/com/squarestruct/domain/repository
```

## Contrato común

`CrudRepository<T, ID>` define las operaciones comunes:

```text
create
findById
findAll
update
deleteById
existsById
```

Las búsquedas que pueden no encontrar datos usan `Optional`. Las consultas con varios resultados devuelven `List`.

## Repositorios específicos

Cada agregado principal tiene su propio contrato:

- `ProductoRepository`
- `ProveedorRepository`
- `PedidoRepository`
- `FacturaRepository`
- `PresupuestoRepository`
- `PlantillaRepository`

Estos contratos extienden `CrudRepository` y añaden consultas propias.

## Consultas por agregado

`ProductoRepository` permite buscar por nombre parcial, tipo, material y proveedor.

`ProveedorRepository` permite buscar por nombre de empresa parcial y estado de validación.

`PedidoRepository` permite buscar por usuario, estado y rango de fechas.

`FacturaRepository` permite buscar por pedido asociado, método de pago y rango de fechas.

`PresupuestoRepository` permite buscar por nombre de proyecto, producto incluido y rango de fechas.

`PlantillaRepository` permite buscar por nombre parcial y producto usado en bloques.

## Implementaciones actuales

Las interfaces no guardan datos por sí mismas. Las implementaciones concretas están en infraestructura:

```text
com.squarestruct.infrastructure.persistence.memory
com.squarestruct.infrastructure.persistence.mysql
```

Estado actual:

- memoria: implementada para productos, proveedores, pedidos, facturas, presupuestos y plantillas;
- MySQL: implementado de forma real para productos;
- MySQL para el resto de agregados: pendiente, con una alternativa temporal en memoria vacía desde `MySqlRepositoryFactory`.

## Uso desde servicios

Los servicios reciben interfaces:

```java
public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
}
```

No reciben implementaciones concretas. Esto mantiene estable la lógica de negocio cuando cambia la persistencia.

## Decisiones de diseño

Los repositorios pertenecen al dominio porque describen lo que la aplicación necesita consultar o guardar, no cómo lo hace técnicamente.

Las implementaciones pertenecen a infraestructura porque usan detalles reemplazables: colecciones Java, JDBC, SQL, conexiones o cualquier mecanismo futuro.

No se usa un paquete `dao` separado en el diseño actual. El papel que tradicionalmente podría tener un DAO queda cubierto por repositorios de infraestructura que implementan contratos de dominio.

## Añadir un nuevo repositorio MySQL

Para completar un agregado en MySQL:

1. Mantener o ampliar el contrato en `domain.repository`.
2. Crear `MySql...Repository` en `infrastructure.persistence.mysql`.
3. Implementar CRUD y búsquedas con JDBC.
4. Actualizar `MySqlRepositoryFactory` para devolver la implementación real.
5. Cubrir el comportamiento con pruebas unitarias o de integración según el alcance.
