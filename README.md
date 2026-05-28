<p align="center">
  <img src="./assets/squarestruct-logo.jpg" alt="SquareStruct Logo" width="240" />
</p>

<p align="center">
  <a href="https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white&style=flat-square" alt="Java"/></a>
  <a href="https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white&style=flat-square" alt="Maven"/></a>
  <a href="https://img.shields.io/badge/MySQL%2FMariaDB-4479A1?logo=mysql&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/MySQL%2FMariaDB-4479A1?logo=mysql&logoColor=white&style=flat-square" alt="MySQL/MariaDB"/></a>
  <a href="https://img.shields.io/badge/JUnit_5-25A162?logo=junit5&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/JUnit_5-25A162?logo=junit5&logoColor=white&style=flat-square" alt="JUnit 5"/></a>
</p>

<h1 align="center">SquareStruct Java Manager</h1>

SquareStruct Java Manager es una aplicación Java de consola para gestión interna del ecosistema SquareStruct. El proyecto modela productos, proveedores, pedidos, facturas, presupuestos y plantillas constructivas, y sirve como base técnica para una herramienta administrativa desacoplada del proyecto web principal.

El estado actual prioriza arquitectura, validaciones de servicios, repositorios intercambiables y una consola inicial. La persistencia en memoria está disponible para desarrollo y pruebas; la persistencia MySQL/MariaDB está iniciada con un repositorio JDBC real para productos y preparada para ampliar el resto de agregados.

## Requisitos

- Java 17
- Maven 3.x
- MySQL o MariaDB solo si se activa `persistence.type=mysql`
- IntelliJ IDEA u otro IDE Java, opcional pero recomendado

## Ejecución rápida

Por defecto la aplicación usa memoria, así que no hace falta base de datos para arrancar.

```bash
mvn compile
mvn exec:java "-Dexec.mainClass=com.squarestruct.manager.Main"
```

También puede ejecutarse desde el IDE lanzando:

```text
com.squarestruct.manager.Main
```

El menú de consola muestra los módulos principales:

- productos
- proveedores
- pedidos
- facturas
- presupuestos
- plantillas constructivas

En esta iteración, el menú está conectado al grafo real de servicios y repositorios. Varias acciones CRUD siguen marcadas como pendientes en consola; el módulo de presupuestos ya puede listar resúmenes guardados desde el servicio configurado.

## Configuración de persistencia

La configuración se carga desde:

```text
src/main/resources/application.properties
```

Modo en memoria, recomendado para empezar:

```properties
persistence.type=memory
```

Alias aceptados:

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

Alias aceptados:

```text
mysql
mariadb
```

Para preparar la base de datos:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS squarestruct CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p squarestruct < sql/schema.sql
mysql -u root -p squarestruct < sql/seeds.sql
```

Nota: `docker-compose.yml` existe en el repositorio, pero actualmente no define servicios. Para usar MySQL/MariaDB hay que levantar una instancia externa o completar esa configuración.

## Arquitectura

El proyecto sigue una arquitectura por capas con separación entre dominio, aplicación, infraestructura y entrada por consola.

```text
com.squarestruct
|-- domain
|   |-- model
|   |-- enums
|   `-- repository
|-- application
|   |-- dto
|   |-- mapper
|   `-- service
|-- infrastructure
|   `-- persistence
|       |-- factory
|       |-- memory
|       `-- mysql
`-- manager
    |-- config
    |-- Main
    `-- ui.menu
```

La dirección de dependencias buscada es:

```text
Consola -> Servicios -> Interfaces de repositorio -> Implementaciones de infraestructura
```

El dominio contiene modelos, enumerados y contratos. La aplicación contiene servicios, DTOs y mapeadores. La infraestructura implementa persistencia en memoria o MySQL. `Main` construye el grafo de dependencias y entrega los servicios al menú de consola.

## DTOs y mapeadores

Los DTOs (`ProductoDTO`, `ProveedorDTO`, `PedidoDTO`, `PresupuestoDTO`, etc.) transportan datos entre capas sin exponer directamente todo el modelo de dominio. Los mapeadores convierten modelos de dominio en DTOs preparados para validaciones, listados o salida por consola.

Ejemplo de flujo:

```text
Producto -> ProductoMapper -> ProductoDTO -> ProductoService
```

En presupuestos, `PresupuestoMapper` prepara un resumen con líneas, cantidades, subtotales y total calculado.

## Patrón Repository

Los repositorios se definen como interfaces en:

```text
src/main/java/com/squarestruct/domain/repository
```

`CrudRepository<T, ID>` define operaciones comunes (`create`, `findById`, `findAll`, `update`, `deleteById`, `existsById`) y cada repositorio específico añade búsquedas propias del agregado. Los servicios dependen de estas interfaces, no de JDBC ni de colecciones en memoria.

## Patrón Factory

La selección de persistencia se centraliza en:

```text
com.squarestruct.infrastructure.persistence.factory
```

`RepositoryFactoryProvider` lee `persistence.type` y devuelve una implementación de `RepositoryFactory`:

- `InMemoryRepositoryFactory`, para desarrollo, pruebas y ejecución sin base de datos.
- `MySqlRepositoryFactory`, para usar JDBC contra MySQL/MariaDB.

Con esto, cambiar de memoria a MySQL no requiere modificar servicios ni menú: basta con cambiar `application.properties`.

## Estado de MySQL

La capa MySQL incluye:

- `MySqlConnectionFactory`
- `MySqlProductoRepository`

`MySqlProductoRepository` implementa CRUD y búsquedas de productos contra la tabla `productos`. El resto de repositorios todavía no tiene implementación JDBC propia; cuando `persistence.type=mysql`, `MySqlRepositoryFactory` delega temporalmente esos agregados en repositorios en memoria vacíos para mantener estable el contrato de la aplicación.

## Pruebas

El conjunto de pruebas usa JUnit 5 y se ejecuta con Maven:

```bash
mvn test
```

Para ejecutar una clase concreta:

```bash
mvn -Dtest=PresupuestoServiceTest test
```

Las pruebas actuales cubren:

- servicios de aplicación y validaciones de negocio;
- cálculo y resumen de presupuestos;
- repositorios en memoria;
- selección de fábricas de repositorio;
- arranque del menú desde `Main` con memoria o MySQL.

El flujo de integración continua ejecuta `mvn validate`, `mvn compile` y `mvn test` en GitHub Actions.

## Relación con SquareStruct web

Este repositorio complementa al proyecto web principal:

- [squarestruct-app](https://github.com/Key-Claw/squarestruct-app)

La aplicación Java no sustituye a la interfaz web ni al servidor web principal. Su papel es actuar como herramienta interna y laboratorio de arquitectura Java para reglas de negocio, persistencia y operaciones administrativas que comparten vocabulario con SquareStruct web: usuarios, productos, proveedores, pedidos, presupuestos y futuras plantillas constructivas.

La relación técnica actual se basa en el modelo de datos y los archivos SQL (`sql/schema.sql` y `sql/seeds.sql`). No existe todavía integración HTTP, API REST ni sincronización automática entre ambos repositorios.

## Evolución futura e IFC

El proyecto está preparado para crecer hacia funcionalidades más cercanas al dominio constructivo de SquareStruct. La integración IFC se plantea como una evolución futura para leer o generar información BIM y conectarla con presupuestos, productos y plantillas constructivas.

La intención es mantener esa integración fuera del dominio puro: analizadores IFC, adaptadores de archivos o conectores externos deberían vivir como infraestructura o casos de aplicación, mientras los servicios seguirían trabajando con modelos y repositorios propios del proyecto.

## Documentación técnica

- [Arquitectura general](docs/architecture/overview.md)
- [Capa de repositorios](docs/architecture/repository-layer.md)
- [Factory de repositorios](docs/architecture/repository-factory.md)
- [Persistencia en memoria](docs/architecture/inmemory-persistence.md)
- [Persistencia MySQL](docs/architecture/mysql-persistence.md)
- [Gestión de presupuestos](docs/architecture/budget-management.md)
- [DTOs y mapeadores](docs/java/dto-mapper-arquitectura-java.md)
- [Pruebas unitarias](docs/java/tests-unitarios-servicios-issue-8.md)
- [GitHub Actions CI](docs/java/github-actions-ci.md)
- [Instalación Java + Maven en Windows](docs/java/instalacion-java-maven-windows.md)
- [Instalación Java + Maven en macOS](docs/java/instalacion-java-maven-macos.md)
- [Conceptos clave de Maven](docs/java/maven-conceptos-clave.md)
