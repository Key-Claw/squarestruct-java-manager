# Persistencia MySQL

Este documento resume la persistencia MySQL/MariaDB disponible en `squarestruct-java-manager`.

## Objetivo

La capa MySQL permite reemplazar repositorios en memoria por implementaciones JDBC sin cambiar servicios ni menú. Las interfaces siguen estando en `domain.repository` y los detalles SQL viven en infraestructura.

## Paquete

La implementación actual está en:

```text
src/main/java/com/squarestruct/infrastructure/persistence/mysql
```

Clases disponibles:

- `MySqlConnectionFactory`
- `MySqlProductoRepository`

## Configuración

Las propiedades se cargan desde:

```text
src/main/resources/application.properties
```

Ejemplo:

```properties
persistence.type=mysql
db.url=jdbc:mysql://localhost:3306/squarestruct?useSSL=false&serverTimezone=UTC
db.user=root
db.password=root
```

`MySqlConnectionFactory` valida `db.url`, `db.user` y `db.password`. Si falta una propiedad obligatoria, lanza `IllegalStateException`.

## Preparar la base de datos

El repositorio incluye archivos SQL:

```text
sql/schema.sql
sql/seeds.sql
```

Flujo recomendado:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS squarestruct CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p squarestruct < sql/schema.sql
mysql -u root -p squarestruct < sql/seeds.sql
```

`schema.sql` crea las tablas principales (`usuarios`, `proveedores`, `productos`, `pedidos`, `pedidoDetalles`) y define una propuesta comentada para planos. `seeds.sql` carga datos iniciales de proveedores, usuarios, productos y pedidos.

`docker-compose.yml` está presente pero no define servicios en el estado actual del repositorio.

## Repositorio de productos

`MySqlProductoRepository` implementa `ProductoRepository` con JDBC:

- `create`
- `findById`
- `findAll`
- `update`
- `deleteById`
- `existsById`
- búsqueda por nombre parcial
- búsqueda por tipo
- búsqueda por material
- búsqueda por proveedor

La tabla usada es `productos`, con relación a `proveedores` mediante `idProveedor`.

## Estado parcial de MySQL

Solo productos tiene repositorio MySQL real. Los demás agregados (`Proveedor`, `Pedido`, `Factura`, `Presupuesto`, `PlantillaConstructiva`) todavía no tienen implementación JDBC propia.

Para mantener el contrato completo de `RepositoryFactory`, `MySqlRepositoryFactory` delega temporalmente esos repositorios en una `InMemoryRepositoryFactory(false)`. Esto permite arrancar la aplicación con `persistence.type=mysql` sin afirmar que todo el sistema ya persiste en MySQL.

## Ampliación esperada

Para completar MySQL hay que implementar:

- `MySqlProveedorRepository`
- `MySqlPedidoRepository`
- `MySqlFacturaRepository`
- `MySqlPresupuestoRepository`
- `MySqlPlantillaRepository`

Cada implementación debe respetar su interfaz de dominio y después registrarse en `MySqlRepositoryFactory`.
