# GitHub Actions CI - Maven Build

## Descripcion

El repositorio incluye un workflow de integracion continua para validar el proyecto Maven en cada `push` o `pull_request` hacia `dev` o `main`.

## Archivo del workflow

```text
.github/workflows/maven-ci.yml
```

## Flujo

El job se ejecuta en `ubuntu-latest` y realiza estos pasos:

1. Descarga el repositorio con `actions/checkout`.
2. Instala JDK 17 con `actions/setup-java`.
3. Activa cache de dependencias Maven.
4. Ejecuta `mvn validate`.
5. Ejecuta `mvn compile`.
6. Ejecuta `mvn test`.

## Objetivo

El workflow comprueba que el proyecto:

- conserva una configuracion Maven valida
- compila con Java 17
- mantiene la suite de tests en verde
- puede revisarse antes de integrar cambios en ramas principales
