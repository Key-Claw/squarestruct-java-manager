# Maven - Conceptos Clave

## ¿Qué es Maven?

Maven es una herramienta de automatización y gestión de proyectos Java.

Permite:
- gestionar dependencias automáticamente
- compilar proyectos
- ejecutar tests
- generar builds (.jar / .war)
- mantener una estructura estándar

---

## Archivo principal

```xml
pom.xml
```

Es el archivo principal de configuración de Maven.

Aquí se define:
- nombre y versión del proyecto
- dependencias
- versión de Java
- plugins
- configuración del build

---

## Estructura básica Maven

```plaintext
src/
 ├── main/
 │    ├── java/
 │    └── resources/
 │
 └── test/
      └── java/
```

---

## Comandos importantes

Compilar proyecto:

```bash
mvn compile
```

Ejecutar tests:

```bash
mvn test
```

Generar build:

```bash
mvn package
```

Instalar build localmente:

```bash
mvn install
```

Limpiar archivos generados:

```bash
mvn clean
```

Comprobar instalación:

```bash
mvn -v
```

---

## Flujo habitual Maven

```bash
mvn clean install
```

---

## Video explicativo

[Maven Conceptos Clave](https://www.youtube.com/watch?v=xf0Dx4qgBUs)