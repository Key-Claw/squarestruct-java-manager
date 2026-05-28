# Factory de repositorios

Este documento resume la implementacion del patron Factory para seleccionar la persistencia de la aplicacion en tiempo de arranque.

## Que habia antes

El proyecto ya tenia una separacion clara entre contratos e implementaciones:

- Las interfaces de repositorio estaban en `com.squarestruct.domain.repository`.
- La persistencia en memoria estaba en `com.squarestruct.infrastructure.persistence.memory`.
- La persistencia MySQL estaba iniciada en `com.squarestruct.infrastructure.persistence.mysql`.
- Los servicios vivian en `com.squarestruct.application.service` y no debian depender de clases concretas de infraestructura.
- `Main` creaba directamente el menu y no construia todavia el grafo de dependencias de la aplicacion.

La seleccion de persistencia no estaba centralizada: no habia un punto unico que decidiera si usar memoria o MySQL.

## Factories creadas

Se ha creado el paquete:

```text
com.squarestruct.infrastructure.persistence.factory
```

Contiene:

- `RepositoryFactory`: contrato comun que expone los repositorios por sus interfaces de dominio.
- `InMemoryRepositoryFactory`: construye repositorios `InMemory...Repository`.
- `MySqlRepositoryFactory`: construye repositorios MySQL cuando existe implementacion JDBC.
- `RepositoryFactoryProvider`: lee la configuracion y devuelve la factory adecuada.

`RepositoryFactory` no devuelve implementaciones concretas. Devuelve interfaces como:

```java
ProductoRepository productoRepository();
ProveedorRepository proveedorRepository();
PedidoRepository pedidoRepository();
FacturaRepository facturaRepository();
PresupuestoRepository presupuestoRepository();
PlantillaRepository plantillaRepository();
```

## Seleccion por configuracion

La seleccion se realiza desde:

```text
src/main/resources/application.properties
```

Propiedad usada:

```properties
persistence.type=memory
```

Valores soportados:

- `memory`
- `inmemory`
- `in-memory`
- `mysql`
- `mariadb`

Si no se informa `persistence.type`, `RepositoryFactoryProvider` usa `memory` como valor por defecto.

## Inyeccion de dependencias

`Main` actua como composition root de la aplicacion:

1. Carga la factory mediante `RepositoryFactoryProvider.getFactory()`.
2. Pide repositorios a la factory.
3. Crea los servicios pasando interfaces de repositorio.
4. Entrega los servicios al `MainMenu`.

Ejemplo del flujo:

```java
RepositoryFactory repositoryFactory = RepositoryFactoryProvider.getFactory();

new ProductoService(repositoryFactory.productoRepository());
new ProveedorService(repositoryFactory.proveedorRepository());
```

Los servicios reciben `ProductoRepository`, `ProveedorRepository`, etc. No reciben `InMemoryProductoRepository` ni `MySqlProductoRepository`.

## Reduccion de acoplamiento

La logica de negocio queda desacoplada porque los servicios solo conocen contratos del dominio.

Cambiar de memoria a MySQL no requiere modificar servicios, validaciones, DTOs, mappers ni menus. La decision queda concentrada en:

```text
RepositoryFactoryProvider
InMemoryRepositoryFactory
MySqlRepositoryFactory
```

Esto permite sustituir infraestructura sin tocar reglas de negocio.

## Estado actual de MySQL

El codigo existente ya incluia `MySqlProductoRepository`. Por eso `MySqlRepositoryFactory` devuelve una implementacion JDBC real para productos.

Los demas agregados todavia no tienen repositorios MySQL concretos en el proyecto actual. Para mantener el contrato completo de `RepositoryFactory` sin romper el arranque, `MySqlRepositoryFactory` delega temporalmente esos repositorios en una `InMemoryRepositoryFactory` vacia.

Cuando se implementen `MySqlProveedorRepository`, `MySqlPedidoRepository`, `MySqlFacturaRepository`, `MySqlPresupuestoRepository` o `MySqlPlantillaRepository`, solo habra que cambiar `MySqlRepositoryFactory`. Los servicios seguiran igual porque ya dependen de interfaces.

## Cambio de persistencia

Para usar memoria:

```properties
persistence.type=memory
```

Para usar MySQL:

```properties
persistence.type=mysql
db.url=jdbc:mysql://localhost:3306/squarestruct?useSSL=false&serverTimezone=UTC
db.user=root
db.password=root
```

Con este enfoque, la aplicacion queda preparada para alternar persistencia desde configuracion y mantener estable la capa de servicios.
