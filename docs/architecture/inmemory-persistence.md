# Persistencia en memoria

Este documento describe la implementacion en memoria creada para desarrollo inicial, pruebas y convivencia futura con otras persistencias.

## Punto de partida

La issue anterior dejo definidos los contratos de repositorio en:

```text
com.squarestruct.domain.repository
```

Esos contratos separan el dominio de la persistencia concreta, pero todavia no existia ninguna implementacion que permitiera crear, listar, actualizar o eliminar datos.

## Cambio realizado

Se ha creado el paquete:

```text
src/main/java/com/squarestruct/infrastructure/persistence/memory
```

Dentro de ese paquete se han anadido:

- `InMemoryCrudRepository`
- `InMemoryProductoRepository`
- `InMemoryProveedorRepository`
- `InMemoryPedidoRepository`
- `InMemoryFacturaRepository`
- `InMemoryPresupuestoRepository`
- `InMemoryPlantillaRepository`

Cada repositorio concreto implementa su interfaz correspondiente del dominio.

## Funcionamiento

La persistencia se basa en estructuras Java en memoria:

- `LinkedHashMap<Long, T>` como almacenamiento temporal.
- IDs `Long` generados incrementalmente cuando la entidad llega sin identificador.
- `Optional` para busquedas por id o por relacion unica.
- `List` para listados y busquedas con varios resultados.

`InMemoryCrudRepository` concentra el comportamiento comun:

- `create`
- `findById`
- `findAll`
- `update`
- `deleteById`
- `existsById`

Los repositorios concretos anaden las busquedas propias de cada entidad, como productos por proveedor, pedidos por usuario, facturas por pedido, presupuestos por producto o plantillas por producto usado en sus bloques.

## Datos minimos de prueba

Cada repositorio en memoria incluye un constructor por defecto con datos minimos de ejemplo para facilitar el desarrollo manual.

Tambien existe un constructor con parametro booleano:

```java
new InMemoryProductoRepository(false)
```

Con `false`, el repositorio se crea vacio. Esto permite usarlo con comodidad en tests unitarios sin datos precargados.

## Decisiones de diseno

Las clases concretas viven en `infrastructure.persistence.memory` porque son detalles de infraestructura, no reglas del dominio.

No se ha usado MySQL, JDBC, `Connection`, `ResultSet` ni ninguna clase de base de datos. La implementacion solo depende de Java Collections y de los modelos e interfaces del proyecto.

La base comun evita duplicar el CRUD en los seis repositorios y mantiene las diferencias en las busquedas especificas de cada contrato.

## Encaje arquitectonico

La capa queda preparada para que un futuro servicio dependa de interfaces como:

```java
ProductoRepository productoRepository
```

Ese servicio podra recibir una implementacion en memoria durante pruebas o desarrollo, y una implementacion MySQL en produccion, sin cambiar la logica de negocio.

Ejemplo de convivencia futura:

```text
com.squarestruct.infrastructure.persistence.memory
com.squarestruct.infrastructure.persistence.mysql
```

Ambas implementaciones podran cumplir los mismos contratos definidos en `domain.repository`.
