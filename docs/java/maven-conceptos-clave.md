# Maven - Conceptos Clave

## ¿Qué es Maven?

Maven es una herramienta de automatización y gestión de proyectos Java.

Permite:
- gestionar dependencias automáticamente
- compilar proyectos
- ejecutar pruebas
- generar artefactos (`.jar` / `.war`)
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
- configuración de construcción

---

### Elementos importantes de pom.xml

```xml
<groupId>com.squarestruct</groupId>
<artifactId>squarestruct-java-manager</artifactId>
<version>1.0-SNAPSHOT</version>
```

#### groupId
Identifica la organización o proyecto.

Suele escribirse con dominio invertido.

Ejemplo:
```plaintext
com.squarestruct
```

---

#### artifactId
Nombre del proyecto o aplicación.

Ejemplo:
```plaintext
squarestruct-java-manager
```

---

#### version
Versión actual del proyecto.

Ejemplo:
```plaintext
1.0-SNAPSHOT
```

`SNAPSHOT` indica que el proyecto sigue en desarrollo.

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

Ejecutar pruebas:

```bash
mvn test
```

Generar el artefacto:

```bash
mvn package
```

Instalar el artefacto localmente:

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

## Videos explicativos

- [Maven Conceptos Clave](https://www.youtube.com/watch?v=xf0Dx4qgBUs)
- [POM.xml Conceptos Clave](https://www.youtube.com/watch?v=8aDBQJPSYKY)
