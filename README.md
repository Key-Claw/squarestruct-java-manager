<p align="center">
  <img src="./assets/squarestruct-logo.jpg" alt="SquareStruct Logo" width="240" />
</p>

<p align="center">
  <a href="https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white&style=flat-square" alt="Java"/></a>
  <a href="https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white&style=flat-square" alt="Maven"/></a>
  <a href="https://img.shields.io/badge/MariaDB-11-003545?logo=mariadb&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/MariaDB-11-003545?logo=mariadb&logoColor=white&style=flat-square" alt="MariaDB"/></a>
  <a href="https://img.shields.io/badge/IntelliJ_IDEA-black?logo=intellijidea&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/IntelliJ_IDEA-black?logo=intellijidea&logoColor=white&style=flat-square" alt="IntelliJ"/></a>
</p>

<h1 align="center">SquareStruct Java Manager</h1>

Aplicacion corporativa de consola en Java para la gestion interna de SquareStruct. El proyecto esta planteado como material de estudio y portfolio: usa arquitectura en capas, repositorios desacoplados, DTOs, mappers, servicios de aplicacion, persistencia en memoria y una primera integracion JDBC con MariaDB/MySQL.

## Repositorio relacionado

Frontend y backend principal del ecosistema SquareStruct:

- [squarestruct-app](https://github.com/Key-Claw/squarestruct-app)

## Tecnologias

- Java 17
- Maven
- JUnit 5
- MariaDB/MySQL parcial mediante JDBC
- GitHub Actions para CI
- IntelliJ IDEA

Docker queda como mejora futura. El repositorio no incluye ahora mismo una configuracion Docker operativa.

## Arquitectura

El proyecto se organiza en cuatro capas principales:

```text
com.squarestruct.manager          -> arranque y menu de consola
com.squarestruct.application      -> servicios, DTOs y mappers
com.squarestruct.domain           -> modelos, enums y contratos de repositorio
com.squarestruct.infrastructure   -> configuracion y persistencia concreta
```

`Main` actua como punto de composicion: carga `application.properties`, selecciona la `RepositoryFactory`, crea los servicios con interfaces de repositorio y entrega el flujo interactivo a `MainMenu`.

## Persistencia

La persistencia se selecciona desde `src/main/resources/application.properties`:

```properties
persistence.type=memory
```

Valores soportados:

- `memory`, `inmemory`, `in-memory`
- `mysql`, `mariadb`

Con `memory`, todos los modulos usan repositorios en memoria con datos semilla. Con `mysql`, productos usa `MySqlProductoRepository`; el resto de modulos conservan repositorios en memoria vacios hasta que existan implementaciones JDBC propias.

La base SQL de referencia esta en:

- `sql/schema.sql`
- `sql/seeds.sql`

## Comandos

Compilar:

```bash
mvn compile
```

Ejecutar tests:

```bash
mvn test
```

Ejecutar la aplicacion desde el IDE:

```text
com.squarestruct.manager.Main
```

## Documentacion

### Arquitectura

- [Overview arquitectura](docs/architecture/overview.md)
- [Capa de repositorios](docs/architecture/repository-layer.md)
- [Persistencia en memoria](docs/architecture/inmemory-persistence.md)
- [Persistencia MySQL](docs/architecture/mysql-persistence.md)
- [Factory de repositorios](docs/architecture/repository-factory.md)
- [Menu de consola](docs/architecture/console-menu.md)
- [Gestion de presupuestos](docs/architecture/budget-management.md)

### Java, Maven, DTOs y CI

- [Conceptos clave de Maven](docs/java/maven-conceptos-clave.md)
- [Instalacion Java + Maven en Windows](docs/java/instalacion-java-maven-windows.md)
- [Instalacion Java + Maven en macOS](docs/java/instalacion-java-maven-macos.md)
- [DTO y Mapper](docs/java/dto-mapper-arquitectura-java.md)
- [Tests unitarios de servicios](docs/java/tests-unitarios-servicios-issue-8.md)
- [GitHub Actions CI](docs/java/github-actions-ci.md)

## Objetivo

Crear una aplicacion Java corporativa desacoplada del frontend principal de SquareStruct, enfocada en:

- gestion interna por consola
- separacion clara entre capas
- persistencia configurable
- patrones de repositorio y factory
- testing unitario
- documentacion tecnica mantenible
