# GitHub Actions CI

El repositorio incluye un flujo de integración continua para validar el proyecto Maven.

## Archivo

```text
.github/workflows/maven-ci.yml
```

## Cuándo se ejecuta

El flujo se lanza en:

- `push` a `dev` o `main`;
- `pull_request` hacia `dev` o `main`.

## Pasos

La canalización usa JDK 17 con distribución Temurin y ejecuta:

```bash
mvn validate
mvn compile
mvn test
```

Esto comprueba que el proyecto Maven es válido, compila correctamente y supera el conjunto de pruebas JUnit 5.
