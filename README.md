<p align="center">
  <img src="./assets/squarestruct-logo.jpg" alt="SquareStruct Logo" width="240" />
</p>

<p align="center">
  <a href="https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white&style=flat-square" alt="Java"/></a>
  <a href="https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white&style=flat-square" alt="Maven"/></a>
  <a href="https://img.shields.io/badge/MariaDB-11-003545?logo=mariadb&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/MariaDB-11-003545?logo=mariadb&logoColor=white&style=flat-square" alt="MariaDB"/></a>
  <a href="https://img.shields.io/badge/Docker-2496ed?logo=docker&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/Docker-2496ed?logo=docker&logoColor=white&style=flat-square" alt="Docker"/></a>
  <a href="https://img.shields.io/badge/IntelliJ_IDEA-black?logo=intellijidea&logoColor=white&style=flat-square"><img src="https://img.shields.io/badge/IntelliJ_IDEA-black?logo=intellijidea&logoColor=white&style=flat-square" alt="IntelliJ"/></a>
</p>

<h1 align="center">SquareStruct Java Manager</h1>

Aplicación corporativa en Java para la gestión interna de SquareStruct, diseñada como complemento del ecosistema principal. Está basada en arquitectura en capas, persistencia MariaDB/MySQL y una estructura modular preparada para mantenimiento, escalabilidad y futuras funcionalidades administrativas.


## Repositorio relacionado

Frontend y backend principal del ecosistema SquareStruct:

- [squarestruct-app](https://github.com/Key-Claw/squarestruct-app)


## Tecnologías

- Java 17
- Maven
- MariaDB/MySQL
- Docker
- IntelliJ IDEA


## Arquitectura

El proyecto sigue una arquitectura en capas basada en:

```plaintext
config
connection
dao
service
model
ui
```


## Base de datos

La aplicación reutiliza la estructura SQL principal del ecosistema SquareStruct mediante:

- schema.sql
- seeds.sql


## Documentación

### Arquitectura

- [Overview arquitectura](docs/architecture/overview.md)
- [Factory de repositorios](docs/architecture/repository-factory.md)

### Java, Maven, Dto y Mapper

- [Conceptos clave de Maven](docs/java/maven-conceptos-clave.md)
- [Instalación Java + Maven en Windows](docs/java/instalacion-java-maven-windows.md)
- [Instalación Java + Maven en macOS](docs/java/instalacion-java-maven-macos.md)
- [Dto y Mapper](docs/javadto-mapper-arquitectura-java.md)


## Objetivo

Crear una aplicación Java corporativa desacoplada del frontend principal de SquareStruct, enfocada en:

- gestión interna
- persistencia de datos
- arquitectura escalable
- conexión MySQL/MariaDB
- patrones de diseño
- testing y mantenimiento
