# Arquitectura general

Este documento resume la arquitectura actual de `squarestruct-java-manager` y sirve como mapa de lectura para el resto de documentación técnica.

## Objetivo del proyecto

SquareStruct Java Manager es una aplicación Java de consola orientada a gestión interna del ecosistema SquareStruct. El proyecto organiza reglas de negocio, validaciones, cálculo de presupuestos y persistencia de datos para entidades como productos, proveedores, pedidos, facturas, presupuestos y plantillas constructivas.

La aplicación no es la interfaz web ni el servidor principal de SquareStruct. Es una herramienta Java separada, pensada para evolucionar como módulo administrativo y como base de arquitectura limpia para futuras integraciones.

## Capas principales

La arquitectura está organizada por responsabilidades:

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

## Responsabilidad de cada capa

`domain` contiene el núcleo del modelo: entidades, enumerados y contratos de repositorio. No debe depender de MySQL, consola, Maven, JDBC ni detalles externos.

`application` contiene servicios, DTOs y mapeadores. Aquí viven validaciones y casos de uso sencillos como cálculo de presupuestos o validación de productos y pedidos.

`infrastructure` contiene detalles técnicos reemplazables. Actualmente incluye persistencia en memoria, persistencia MySQL parcial y fábricas para elegir implementación.

`manager` contiene el punto de entrada de la aplicación, carga de configuración y menú de consola. `Main` actúa como raíz de composición: crea repositorios, servicios y el menú.

## Flujo de dependencias

La dirección esperada es:

```text
manager.ui.menu
    -> application.service
        -> domain.repository
            -> infrastructure.persistence
```

Los servicios dependen de interfaces de repositorio, no de clases concretas como `InMemoryProductoRepository` o `MySqlProductoRepository`. Esto permite cambiar persistencia desde configuración sin reescribir reglas de negocio.

## Arranque de la aplicación

El flujo de inicio es:

1. `Main.main` llama a `createMainMenu`.
2. `DatabaseConfig` carga `src/main/resources/application.properties`.
3. `RepositoryFactoryProvider` lee `persistence.type`.
4. Se crea una `RepositoryFactory` en memoria o MySQL.
5. `Main` instancia los servicios con repositorios por interfaz.
6. `MainMenu` muestra la consola y delega acciones en servicios.

## Persistencia disponible

La persistencia se selecciona con:

```properties
persistence.type=memory
```

o:

```properties
persistence.type=mysql
```

El modo en memoria está implementado para todos los agregados principales y se usa como opción por defecto. El modo MySQL está implementado de forma real para productos mediante `MySqlProductoRepository`; el resto de agregados delega temporalmente en memoria vacía desde `MySqlRepositoryFactory`.

Detalles:

- [Persistencia en memoria](inmemory-persistence.md)
- [Persistencia MySQL](mysql-persistence.md)
- [Factory de repositorios](repository-factory.md)

## DTOs y mapeadores

Los DTOs reducen el acoplamiento entre el modelo de dominio y las capas de entrada/salida. Los mapeadores convierten entidades del dominio a DTOs preparados para validación, resumen o presentación por consola.

El uso actual está documentado en [DTOs y mapeadores](../java/dto-mapper-arquitectura-java.md).

## Patrón Repository

Los repositorios son contratos del dominio. `CrudRepository<T, ID>` define operaciones comunes y cada repositorio específico añade búsquedas del agregado.

La capa completa está documentada en [Capa de repositorios](repository-layer.md).

## Ejecución por consola

La consola se inicia desde:

```text
com.squarestruct.manager.Main
```

El menú muestra módulos para productos, proveedores, pedidos, facturas, presupuestos y plantillas constructivas. La estructura del menú ya usa servicios inyectados; algunas acciones CRUD están marcadas como pendientes y presupuestos ya lista resúmenes guardados.

## Pruebas

El conjunto de pruebas actual usa JUnit 5 y Maven. Cubre servicios, repositorios en memoria, fábricas de persistencia y arranque del menú.

```bash
mvn test
```

La estrategia está documentada en [Pruebas unitarias](../java/tests-unitarios-servicios-issue-8.md).

## Relación con SquareStruct web

El repositorio relacionado es:

- [squarestruct-app](https://github.com/Key-Claw/squarestruct-app)

`squarestruct-java-manager` comparte vocabulario y base de datos conceptual con el proyecto web, especialmente a través de `sql/schema.sql` y `sql/seeds.sql`. Actualmente no hay integración HTTP, API REST ni sincronización automática entre ambos repositorios.

La intención es que esta aplicación Java pueda actuar como herramienta interna, módulo de administración o banco de pruebas de reglas de negocio que luego puedan coordinarse con el ecosistema web.

## Evolución futura e IFC

La integración IFC se contempla como una evolución futura para conectar el dominio constructivo de SquareStruct con información BIM. Esa integración podría permitir leer modelos, extraer cantidades, relacionar elementos con productos y generar presupuestos o plantillas constructivas.

Arquitectónicamente conviene mantener IFC como una capacidad externa al dominio puro:

- analizadores o adaptadores IFC en infraestructura;
- casos de uso en aplicación;
- modelos de dominio propios para productos, plantillas y presupuestos;
- repositorios estables por interfaz.

Así se evita que el dominio quede acoplado a una librería o formato concreto.
