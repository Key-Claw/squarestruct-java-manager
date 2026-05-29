# Capa de repositorios

Este documento describe la capa de interfaces de repositorio y el flujo seguido para desacoplar la persistencia del dominio.

## Punto de partida

Antes de esta tarea, el proyecto tenia:

- Modelos de dominio en `com.squarestruct.domain.model`.
- Enumerados de dominio en `com.squarestruct.domain.enums`.
- DTOs y mappers en `com.squarestruct.application`.
- Paquetes `config` y `connection` dentro de `com.squarestruct.manager`, todavia sin implementacion.
- Dependencia Maven del conector MySQL, pero sin servicios ni DAOs usando JDBC directamente.

La documentacion inicial mencionaba una arquitectura por capas con `dao`, `service`, `model` y `ui`, pero el codigo real ya habia empezado a organizarse alrededor de `domain` y `application`. Por eso la abstraccion de persistencia se ha colocado en el dominio, como contratos de repositorio, sin crear implementaciones concretas todavia.

## Cambio realizado

Se ha creado el paquete:

```text
src/main/java/com/squarestruct/domain/repository
```

Dentro de ese paquete se han definido los contratos:

- `CrudRepository<T, ID>`
- `ProductoRepository`
- `ProveedorRepository`
- `PedidoRepository`
- `FacturaRepository`
- `PresupuestoRepository`
- `PlantillaRepository`

`CrudRepository<T, ID>` concentra las operaciones comunes:

- `create`
- `findById`
- `findAll`
- `update`
- `deleteById`
- `existsById`

Las seis interfaces especificas extienden ese contrato comun y anaden busquedas propias del modelo actual.

## Busquedas especificas

`ProductoRepository` permite buscar por:

- nombre parcial
- tipo de producto
- material
- proveedor

`ProveedorRepository` permite buscar por:

- nombre de empresa parcial
- estado de validacion

`PedidoRepository` permite buscar por:

- usuario
- estado
- rango de fechas

`FacturaRepository` permite buscar por:

- pedido asociado
- metodo de pago
- rango de fechas

`PresupuestoRepository` permite buscar por:

- nombre de proyecto parcial
- producto incluido
- rango de fechas

`PlantillaRepository` permite buscar por:

- nombre parcial
- producto usado en sus bloques

Estas busquedas salen de las relaciones y atributos ya presentes en los modelos (`Producto`, `Proveedor`, `Pedido`, `Factura`, `Presupuesto` y `PlantillaConstructiva`) y de las consultas previsibles del schema actual, como productos por proveedor o pedidos por usuario.

## Decisiones de diseno

Los repositorios son interfaces porque representan puertos de persistencia, no una tecnologia concreta.

Las firmas trabajan con objetos del dominio, `Long` como identificador, `List` para colecciones y `Optional` cuando una busqueda puede no encontrar resultado unico.

No se ha introducido ninguna dependencia a MySQL, JDBC, `Connection`, `ResultSet` ni clases equivalentes en la capa de repositorios. Esto evita que los servicios de aplicacion tengan que conocer la base de datos concreta cuando se creen.

No se han creado implementaciones MySQL ni en memoria en esta tarea porque el objetivo de la issue es definir la abstraccion. Las implementaciones futuras podran vivir en paquetes separados, por ejemplo:

```text
com.squarestruct.infrastructure.persistence.memory
com.squarestruct.infrastructure.persistence.mysql
```

Ambas podran implementar las mismas interfaces y ser inyectadas en servicios sin cambiar la logica de negocio.

## Estado final

En el estado actual del proyecto, la capa queda preparada para que:

- La logica de servicio dependa de `ProductoRepository`, `PedidoRepository`, etc.
- MySQL sea solo una implementacion posible.
- Una implementacion en memoria pueda usarse para pruebas o desarrollo local.
- Las operaciones CRUD sean homogeneas en todos los agregados principales.
- Las busquedas especificas queden cerca del contrato de cada entidad.

Las implementaciones en memoria ya existen para los agregados principales. En MySQL existe implementacion concreta para productos; el resto de agregados se mantienen desacoplados mediante sus contratos y el fallback definido en la factory de repositorios.
