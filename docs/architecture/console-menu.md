# Menu de consola

Este documento resume el estado actual del menu interactivo de `Main`.

## Objetivo

La consola actua como interfaz administrativa simple sobre las capas ya existentes. Desde `Main`, la aplicacion crea la `RepositoryFactory`, inyecta repositorios en los servicios y entrega esos servicios a `MainMenu`.

El menu principal expone:

- productos
- proveedores
- pedidos
- facturas
- presupuestos
- plantillas constructivas

Cada modulo ofrece operaciones de listar, crear, actualizar y eliminar conectadas con los repositorios configurados. Si una operacion necesita datos relacionados y no existen, la consola informa la causa concreta en lugar de mostrar un mensaje temporal.

## Encaje por modulo

- Productos: usa `ProductoService` para validacion basica y `ProductoRepository` para CRUD.
- Proveedores: usa `ProveedorService` y `ProveedorRepository`.
- Pedidos: crea y actualiza `Pedido` con usuario, estado, fecha y lineas calculadas desde productos existentes.
- Facturas: se asocian a pedidos existentes y toman el total del pedido.
- Presupuestos: usa `PresupuestoService` para calcular lineas, subtotales y total antes de guardar.
- Plantillas constructivas: usa `PlantillaService` para validar nombre y bloques antes de guardar.

## Persistencia

Con `persistence.type=memory`, todos los modulos trabajan contra repositorios en memoria con datos iniciales.

Con `persistence.type=mysql`, el proyecto actual solo tiene repositorio MySQL concreto para productos. El resto de repositorios siguen usando el fallback en memoria vacio definido en `MySqlRepositoryFactory`, por lo que esas secciones pueden no mostrar datos hasta que existan implementaciones MySQL especificas.

## Criterio de mensajes

El menu ya no usa mensajes genericos de desarrollo provisional. Los casos no ejecutables se expresan como estado real de la aplicacion, por ejemplo:

- repositorio no configurado
- registro no encontrado
- no hay productos para crear lineas
- no hay pedidos para asociar facturas
