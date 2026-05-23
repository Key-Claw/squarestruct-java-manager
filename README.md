# squarestruct-java-manager
Aplicación corporativa en Java para la gestión interna de SquareStruct, desarrollada con arquitectura en capas, DTOs, Factory Pattern, persistencia MySQL/memoria y TDD.

## Tecnologías

- Java 17
- Maven
- MariaDB/MySQL
- Docker
- IntelliJ IDEA

## Arquitectura

El proyecto sigue una arquitectura en capas basada en:

- config
- connection
- dao
- service
- model
- ui

## Base de datos

La aplicación reutiliza la estructura SQL principal del ecosistema SquareStruct mediante:

- schema.sql
- seeds.sql
  CUANDO LO HAGAS