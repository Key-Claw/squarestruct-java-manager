# Persistencia MySQL

Este documento resume la implementación realizada para la capa de persistencia MySQL del proyecto `squarestruct-java-manager`.

## Punto de partida

El proyecto ya cuenta con una arquitectura en capas. El dominio define contratos de repositorio en:

`src/main/java/com/squarestruct/domain/repository`

Las implementaciones concretas de persistencia se colocan en la capa de infraestructura.

## Objetivos

- Proveer una capa de persistencia desacoplada del dominio.
- Centralizar la conexión JDBC contra MySQL.
- Externalizar la configuración de conexión.
- Implementar el primer repositorio MySQL real para productos.
- Mantener compatibilidad con la persistencia en memoria existente.

## Organización de paquetes

La implementación MySQL se añade en:

`src/main/java/com/squarestruct/infrastructure/persistence/mysql`

Actualmente incorpora:

- `MySqlConnectionFactory`
- `MySqlProductoRepository`

Los demas agregados todavia no tienen repositorios MySQL concretos. Cuando la aplicacion se configura con `persistence.type=mysql`, `MySqlRepositoryFactory` usa `MySqlProductoRepository` para productos y conserva un fallback en memoria vacio para proveedores, pedidos, facturas, presupuestos y plantillas.

Las interfaces de repositorio del dominio permanecen en:

`src/main/java/com/squarestruct/domain/repository`

Esto permite mantener separada la lógica de negocio de la infraestructura concreta.

## MySqlConnectionFactory

Se implementa una clase encargada de centralizar la creación de conexiones JDBC contra MySQL.

Responsabilidades principales:

- cargar `application.properties`
- recuperar propiedades de conexión
- validar configuración obligatoria
- crear conexiones mediante `DriverManager`
- gestionar errores de conexión

Esto evita duplicar lógica JDBC dentro de cada repositorio.

## Configuración externa

La configuración de conexión se externaliza en:

`src/main/resources/application.properties`

Configuración utilizada:

```properties
persistence.type=mysql
db.url=jdbc:mysql://localhost:3306/squarestruct?useSSL=false&serverTimezone=UTC
db.user=root
db.password=root
```

Para desarrollo sin base de datos externa puede usarse:

```properties
persistence.type=memory
```
