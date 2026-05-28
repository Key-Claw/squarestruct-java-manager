# DTOs y mapeadores

Este documento explica el uso de DTOs y mapeadores dentro de la arquitectura Java del proyecto.

## Qué es un DTO

Un DTO (`Data Transfer Object`) es un objeto sencillo para transportar datos entre capas. En este proyecto se usa para mover información de forma controlada sin exponer siempre la entidad completa del dominio.

Un DTO contiene datos, constructores y métodos de lectura/escritura. No debe contener acceso a base de datos, cálculos complejos ni reglas de negocio.

## DTOs actuales

Los DTOs viven en:

```text
src/main/java/com/squarestruct/application/dto
```

Clases actuales:

- `ProductoDTO`
- `ProveedorDTO`
- `PedidoDTO`
- `PresupuestoDTO`
- `PresupuestoDetalleDTO`

`PresupuestoDTO` y `PresupuestoDetalleDTO` se usan para presentar resúmenes de presupuesto con líneas, cantidades, subtotales y total calculado.

## Qué es un mapeador

Un mapeador convierte objetos entre capas. En este proyecto convierte principalmente entidades de dominio a DTOs.

Ejemplo conceptual:

```text
Producto -> ProductoMapper -> ProductoDTO
```

Los mapeadores viven en:

```text
src/main/java/com/squarestruct/application/mapper
```

Clases actuales:

- `ProductoMapper`
- `ProveedorMapper`
- `PedidoMapper`
- `PresupuestoMapper`

## Relación con el dominio

El dominio mantiene entidades ricas para representar el negocio:

- `Producto`
- `Proveedor`
- `Pedido`
- `Factura`
- `Presupuesto`
- `PresupuestoDetalle`
- `PlantillaConstructiva`

Los DTOs no sustituyen a estas entidades. Sirven como representación de entrada o salida cuando una capa no necesita todo el objeto de dominio.

## Uso desde servicios

Los servicios de aplicación trabajan con DTOs en validaciones sencillas y con modelos de dominio cuando el caso de uso necesita reglas propias del dominio.

Ejemplos actuales:

- `ProductoService.validarProducto(ProductoDTO)` valida nombre y precio.
- `PedidoService.validarPedido(PedidoDTO)` valida identificador.
- `PresupuestoService.mostrarResumen(Presupuesto)` usa `PresupuestoMapper` para preparar la salida por consola.

## Reglas prácticas

Usar DTO cuando:

- la capa de entrada o salida solo necesita una parte del modelo;
- se quiere evitar exponer directamente una entidad completa;
- se prepara una respuesta para consola, API futura o interfaz externa;
- se necesita una estructura estable para transferir datos.

Usar entidad de dominio cuando:

- se ejecuta lógica de negocio;
- se necesita navegar relaciones del modelo;
- se persiste o consulta mediante repositorios;
- el caso de uso depende del comportamiento real del agregado.

## Encaje arquitectónico

La ubicación de DTOs y mapeadores en `application` evita que el dominio dependa de necesidades de presentación. Si en el futuro aparece una API REST o integración con SquareStruct web, podrán añadirse DTOs específicos sin modificar las entidades principales.
