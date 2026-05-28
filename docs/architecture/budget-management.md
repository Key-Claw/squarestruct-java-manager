# Gestion de presupuestos

Este documento resume la implementacion de la issue #12, centrada en calcular presupuestos desde productos, cantidades y detalles de linea.

## Que habia antes

El proyecto ya tenia estas piezas relacionadas con presupuestos:

- `Presupuesto` en `com.squarestruct.domain.model`, con id, nombre de proyecto, lista de productos, coste total y fecha de creacion.
- `PresupuestoRepository` en `com.squarestruct.domain.repository`, con CRUD y busquedas por proyecto, producto y rango de fechas.
- `InMemoryPresupuestoRepository`, con un presupuesto semilla y busquedas en memoria.
- `PresupuestoService`, pero solo validaba datos basicos del presupuesto y no calculaba subtotales ni total.
- La consola ya mostraba el modulo de presupuestos, aunque sus acciones estaban marcadas como pendientes.

La principal carencia era que `Presupuesto` solo conocia productos sin cantidad. Por tanto no habia forma de representar lineas reales ni de centralizar el calculo de importes.

## Que se ha creado o modificado

Se ha anadido `PresupuestoDetalle` como modelo de dominio para representar cada linea del presupuesto:

- producto
- cantidad
- subtotal

`Presupuesto` se ha adaptado para incluir `List<PresupuestoDetalle>` y mantener la lista antigua de productos por compatibilidad con repositorios y tests existentes. Cuando un presupuesto antiguo solo tiene productos, `PresupuestoService` puede preparar un resumen con lineas de cantidad 1 sin mover el calculo al modelo.

Se han creado DTOs especificos:

- `PresupuestoDTO`
- `PresupuestoDetalleDTO`

Tambien se ha anadido `PresupuestoMapper` para preparar datos de salida sin exponer directamente el modelo de dominio en el resumen.

`PresupuestoService` concentra ahora la logica de negocio:

- `calcularPresupuesto`
- `crearPresupuesto`
- `calcularLinea`
- `calcularSubtotal`
- `mostrarResumen`
- `mostrarResumenesGuardados`
- validaciones de presupuesto, linea, producto, cantidad y precio

`InMemoryPresupuestoRepository` se ha ajustado para buscar presupuestos por producto usando los detalles cuando existan. La consola, desde el submenu de presupuestos, usa el servicio para listar resumenes guardados.

## Como se calcula el presupuesto

El flujo principal es:

1. La capa cliente prepara una lista de `PresupuestoDetalle` con productos y cantidades.
2. `PresupuestoService.calcularPresupuesto` valida el nombre del proyecto y los detalles recibidos.
3. Por cada detalle se llama a `calcularLinea`.
4. `calcularLinea` calcula el subtotal como:

```text
subtotal = producto.precio * cantidad
```

5. El total del presupuesto se calcula sumando todos los subtotales:

```text
costeTotal = suma(detalle.subtotal)
```

6. El servicio devuelve un `Presupuesto` con fecha de creacion, detalles calculados y coste total.

Si el servicio tiene un `PresupuestoRepository`, `crearPresupuesto` guarda el presupuesto calculado. Si no lo tiene, devuelve el presupuesto calculado sin persistirlo, lo que permite usar el servicio en tests unitarios puros.

## Validacion de cantidades y precios

La validacion se hace antes de calcular:

- El presupuesto no puede ser nulo.
- El nombre del proyecto no puede ser nulo ni estar en blanco.
- Debe existir al menos un producto o detalle.
- Cada linea debe tener un producto.
- La cantidad debe ser mayor que cero.
- El precio del producto no puede ser negativo.
- El subtotal y el coste total no pueden ser negativos.
- La fecha de creacion no puede ser nula.

La regla sobre precios sigue el modelo actual, que usa `double` para importes en `Producto`, `PedidoDetalle` y `Presupuesto`. No se ha introducido `BigDecimal` para evitar una refactorizacion transversal fuera del alcance de la issue #12.
## Decisiones de diseno

La nueva clase se llama `PresupuestoDetalle` para seguir el precedente de `PedidoDetalle`.

El calculo vive en `PresupuestoService` porque es logica de aplicacion y debe quedar fuera del repositorio. Los repositorios guardan y consultan presupuestos, pero no calculan importes.

Los DTOs se limitan al resumen del presupuesto y sus lineas. No sustituyen al modelo de dominio ni introducen una estructura paralela.

`Presupuesto` conserva `getProductos` y `setProductos` para no romper el contrato existente de `PresupuestoRepository` ni los datos semilla actuales. La nueva representacion preferente para calculo es `getDetalles`.

## Encaje en el flujo general

El flujo queda alineado con las capas actuales:

- `domain.model`: contiene `Presupuesto`, `PresupuestoDetalle` y `Producto`.
- `application.service`: centraliza calculo y validacion en `PresupuestoService`.
- `application.dto` y `application.mapper`: preparan el resumen para salida.
- `domain.repository`: mantiene el contrato de persistencia.
- `infrastructure.persistence.memory`: implementa busquedas y almacenamiento temporal.
- `manager.ui.menu`: muestra resumenes de presupuestos desde consola usando el servicio.

Con esto, presupuestos deja de ser una validacion aislada y pasa a funcionar como una parte real del dominio, preparada para persistencia en memoria y futura persistencia MySQL sin mover la logica de calculo.

## Verificacion

La verificacion se ha ejecutado con Maven 3.9.9 y JDK 17:

```bash
mvn -q -Dtest=PresupuestoServiceTest test
mvn -q test
```

Ambos comandos finalizaron correctamente.
