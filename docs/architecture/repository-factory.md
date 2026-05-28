# Factory de repositorios

Este documento describe el patrón Factory usado para seleccionar la persistencia de la aplicación.

## Objetivo

La aplicación puede ejecutarse con repositorios en memoria o con repositorios MySQL. La decisión no debe estar repartida por servicios, menú o pruebas; debe existir un punto único de construcción del grafo de persistencia.

Ese punto está en:

```text
com.squarestruct.infrastructure.persistence.factory
```

## Clases principales

`RepositoryFactory` es el contrato común. Expone métodos para obtener repositorios por interfaz:

```java
ProductoRepository productoRepository();
ProveedorRepository proveedorRepository();
PedidoRepository pedidoRepository();
FacturaRepository facturaRepository();
PresupuestoRepository presupuestoRepository();
PlantillaRepository plantillaRepository();
```

`InMemoryRepositoryFactory` crea repositorios en memoria para todos los agregados principales.

`MySqlRepositoryFactory` crea repositorios MySQL cuando existen. Actualmente devuelve `MySqlProductoRepository` para productos y delega el resto en una `InMemoryRepositoryFactory(false)`.

`RepositoryFactoryProvider` lee la configuración y decide qué fábrica usar.

## Configuración

La propiedad se lee desde:

```text
src/main/resources/application.properties
```

Modo en memoria:

```properties
persistence.type=memory
```

Valores aceptados:

```text
memory
inmemory
in-memory
```

Modo MySQL/MariaDB:

```properties
persistence.type=mysql
db.url=jdbc:mysql://localhost:3306/squarestruct?useSSL=false&serverTimezone=UTC
db.user=root
db.password=root
```

Valores aceptados:

```text
mysql
mariadb
```

Si `persistence.type` no existe o está vacío, se usa `memory`.

Un valor no soportado lanza `IllegalArgumentException`.

## Integración con Main

`Main` actúa como raíz de composición:

```java
RepositoryFactory repositoryFactory = RepositoryFactoryProvider.getFactory(properties);
```

Después crea servicios pasando interfaces:

```java
new ProductoService(repositoryFactory.productoRepository());
new ProveedorService(repositoryFactory.proveedorRepository());
```

Finalmente entrega los servicios a `MainMenu`.

## Ventaja arquitectónica

Con esta fábrica, la aplicación puede cambiar de memoria a MySQL modificando configuración, no reglas de negocio. Los servicios siguen dependiendo de `ProductoRepository`, `PedidoRepository` o `PresupuestoRepository`, sin conocer clases concretas.

## Estado actual

El modo en memoria es la opción completa para desarrollo y pruebas.

El modo MySQL está preparado para ejecución real de productos. El resto de módulos todavía necesita repositorios JDBC propios. Esta decisión queda localizada en `MySqlRepositoryFactory`, por lo que completar MySQL no debería afectar a servicios ni menú.
