# DTOs y Mappers — Conceptos básicos de arquitectura Java

## ¿Qué hemos hecho?

En esta parte del proyecto se ha creado la primera separación real de capas dentro de la arquitectura Java.

Antes únicamente existía la capa:

```text
domain
```

Ahora también existe:

```text
application
├── dto
└── mapper
```

Esto permite que el proyecto empiece a organizarse de una forma más profesional y escalable.

---

# ¿Qué es un DTO?

DTO significa:

```text
Data Transfer Object
```

Explicado de forma simple:

```text
Un DTO es una caja o almacén de datos simples.
```

Sirve para:
- mover información entre capas,
- transportar datos,
- evitar exponer directamente las entidades reales del dominio.

---

## Idea básica

```text
DTO = almacén de datos
```

Un DTO normalmente contiene:
- atributos,
- constructores,
- getters,
- setters.

Y normalmente NO contiene:
- lógica de negocio,
- cálculos,
- conexiones,
- funcionalidades complejas.

---

# ¿Qué es un Mapper?

El mapper es el encargado de convertir objetos.

---

## Idea básica

```text
Mapper = traductor o camino
```

Convierte:

```text
Entidad real -> DTO
```

Ejemplo:

```text
Producto
↓
ProductoMapper
↓
ProductoDTO
```

El mapper:
- recibe una entidad real,
- extrae sus datos,
- crea un DTO limpio.

---

# ¿Qué son las entidades?

Las entidades representan los objetos principales del dominio de la aplicación.

Ejemplos:

```text
Producto
Proveedor
Pedido
```

Estas clases representan la información principal del sistema.

---

# ¿Qué hace un constructor?

El constructor es el método utilizado para crear objetos.

---

## Idea básica

```text
Constructor = entrada o camino de creación
```

Ejemplo:

```java
new ProductoDTO(1L, "Bloque Modular", 29.99)
```

Eso crea un objeto ya inicializado con datos.

---

# ¿Qué hace un getter?

Un getter sirve para:
- leer,
- consultar,
- mostrar datos.

Ejemplo:

```java
producto.getNombre()
```

Significa:

```text
“dame el nombre”
```

---

# ¿Qué hace un setter?

Un setter sirve para:
- modificar,
- cambiar,
- actualizar datos.

Ejemplo:

```java
producto.setNombre("Bloque")
```

Significa:

```text
“cambia el nombre”
```

---

# Resumen mental rápido

```text
constructor -> crea
get -> muestra
set -> modifica
DTO -> almacena datos
Mapper -> traduce objetos
```

---

# ¿Por qué es importante esta arquitectura?

Separar capas permite:
- mantener el código más limpio,
- mejorar la organización,
- facilitar el mantenimiento,
- hacer el proyecto más escalable,
- aproximarse a una arquitectura Java profesional.

---

# Organización actual

```text
src/main/java/com.squarestruct
├── application
│   ├── dto
│   └── mapper
├── domain
│   ├── enums
│   └── model
```

---

# Conceptos aprendidos

Durante esta parte del proyecto se han trabajado:
- encapsulación básica,
- getters y setters,
- constructores,
- separación por capas,
- DTOs,
- mappers,
- organización arquitectónica en Java,
- generación automática de métodos mediante IntelliJ IDEA,
- estructura básica de una aplicación Java profesional.