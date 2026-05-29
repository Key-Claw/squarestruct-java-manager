# Arquitectura inicial

Este documento resume la estructura general de capas del proyecto y enlaza la documentacion tecnica de cada pieza nueva.

SquareStruct Java Manager sigue una arquitectura en capas orientada a separar responsabilidades dentro de la aplicación.

## Paquetes principales

- `com.squarestruct.manager`: punto de entrada, carga de configuracion y menu de consola.
- `com.squarestruct.application`: servicios, DTOs y mappers de aplicacion.
- `com.squarestruct.domain`: modelos, enumerados y contratos de repositorio.
- `com.squarestruct.infrastructure`: implementaciones de persistencia y factories.

El proyecto conserva algunos paquetes historicos como `manager.config` y `manager.connection`, pero el flujo actual se organiza alrededor de `manager`, `application`, `domain` e `infrastructure`.

## Repositorios

La abstraccion de persistencia se define como interfaces en `com.squarestruct.domain.repository`.
Estos contratos permiten que la logica de aplicacion dependa del dominio y no de MySQL o JDBC directamente.
El detalle del flujo y las decisiones queda documentado en [Capa de repositorios](repository-layer.md).

## Persistencia en memoria

La primera implementacion concreta de repositorios vive en `com.squarestruct.infrastructure.persistence.memory`.
Usa colecciones Java y queda documentada en [Persistencia en memoria](inmemory-persistence.md).

## Factory de repositorios

La seleccion de persistencia se centraliza en `com.squarestruct.infrastructure.persistence.factory`.
El detalle de `RepositoryFactory`, `InMemoryRepositoryFactory`, `MySqlRepositoryFactory` y `RepositoryFactoryProvider` queda documentado en [Factory de repositorios](repository-factory.md).

## Gestion de presupuestos

El calculo de presupuestos se centraliza en `PresupuestoService` y queda documentado en [Gestion de presupuestos](budget-management.md).

## Menu de consola

El flujo interactivo de `MainMenu` conecta los modulos administrativos con servicios y repositorios, segun se resume en [Menu de consola](console-menu.md).

## Mejoras futuras

Docker queda previsto como mejora futura. El repositorio contiene `docker-compose.yml`, pero todavia no existe una configuracion operativa ni un `Dockerfile`.

## Objetivo

Centralizar funcionalidades administrativas y empresariales relacionadas con el ecosistema SquareStruct mediante una aplicación Java desacoplada del frontend principal.
