# Arquitectura inicial

Este documento resume la estructura general de capas del proyecto y enlaza la documentacion tecnica de cada pieza nueva.

SquareStruct Java Manager sigue una arquitectura en capas orientada a separar responsabilidades dentro de la aplicación.

## Capas principales

- config
- connection
- dao
- repository
- service
- model
- ui

## Repositorios

La abstraccion de persistencia se define como interfaces en `com.squarestruct.domain.repository`.
Estos contratos permiten que la logica de aplicacion dependa del dominio y no de MySQL o JDBC directamente.
El detalle del flujo y las decisiones queda documentado en [Capa de repositorios](repository-layer.md).

## Persistencia en memoria

La primera implementacion concreta de repositorios vive en `com.squarestruct.infrastructure.persistence.memory`.
Usa colecciones Java y queda documentada en [Persistencia en memoria](inmemory-persistence.md).

## Objetivo

Centralizar funcionalidades administrativas y empresariales relacionadas con el ecosistema SquareStruct mediante una aplicación Java desacoplada del frontend principal.
