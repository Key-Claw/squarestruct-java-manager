# Gestión de presupuestos

Este documento describe cómo se calculan, validan y muestran los presupuestos en `squarestruct-java-manager`.

## Objetivo

El módulo de presupuestos permite construir un presupuesto a partir de productos y cantidades. El servicio calcula subtotales por línea, coste total y fecha de creación, y puede guardar el resultado si recibe un repositorio configurado.

## Modelo de dominio

Clases principales:

- `Presupuesto`
- `PresupuestoDetalle`
- `Producto`

`PresupuestoDetalle` representa una línea del presupuesto:

- producto;
- cantidad;
- subtotal.

`Presupuesto` conserva una lista de productos por compatibilidad con código anterior, pero la representación preferente para cálculo es la lista de detalles.

## Servicio de aplicación

La lógica vive en:

```text
com.squarestruct.application.service.PresupuestoService
```

Responsabilidades principales:

- validar nombre de proyecto;
- validar productos, cantidades y precios;
- calcular subtotales;
- calcular coste total;
- crear presupuestos;
- guardar presupuestos si existe repositorio;
- mostrar resúmenes por consola.

Los repositorios no calculan importes. Solo guardan y consultan presupuestos.

## Cálculo

Cada línea se calcula así:

```text
subtotal = producto.precio * cantidad
```

El total se calcula sumando los subtotales:

```text
costeTotal = suma(detalle.subtotal)
```

Flujo principal:

1. La capa cliente prepara una lista de `PresupuestoDetalle`.
2. `PresupuestoService.calcularPresupuesto` valida el nombre y las líneas.
3. El servicio recalcula cada línea con `calcularLinea`.
4. El servicio suma el total y asigna `LocalDate.now()`.
5. El método devuelve un `Presupuesto` válido.

Si se llama a `crearPresupuesto` y el servicio tiene un `PresupuestoRepository`, el presupuesto se guarda. Si no hay repositorio, se devuelve calculado sin persistir.

## Validaciones

El servicio rechaza:

- presupuesto nulo;
- nombre de proyecto nulo o en blanco;
- lista vacía de productos o detalles;
- línea nula;
- producto nulo;
- cantidad menor o igual que cero;
- precio negativo;
- subtotal negativo;
- coste total negativo;
- fecha de creación nula.

El modelo usa `double` para importes porque es la decisión actual del proyecto en `Producto`, `PedidoDetalle` y `Presupuesto`. Cambiar a `BigDecimal` requeriría una refactorización transversal.

## DTOs y salida

Para mostrar resúmenes se usan:

- `PresupuestoDTO`
- `PresupuestoDetalleDTO`
- `PresupuestoMapper`

El mapeador prepara una salida con nombre de proyecto, fecha, líneas, precio unitario, cantidad, subtotal y total. La consola usa esta representación para listar presupuestos guardados.

## Persistencia

El contrato está en:

```text
com.squarestruct.domain.repository.PresupuestoRepository
```

La implementación disponible es:

```text
com.squarestruct.infrastructure.persistence.memory.InMemoryPresupuestoRepository
```

La implementación MySQL de presupuestos está pendiente. En modo MySQL, este agregado queda cubierto temporalmente por la alternativa en memoria vacía definida en `MySqlRepositoryFactory`.

## Pruebas

`PresupuestoServiceTest` cubre:

- validaciones;
- cálculo de líneas;
- cálculo de total;
- salida del resumen;
- compatibilidad con presupuestos antiguos basados en lista de productos;
- guardado mediante repositorio en memoria.

Comando útil:

```bash
mvn -Dtest=PresupuestoServiceTest test
```
