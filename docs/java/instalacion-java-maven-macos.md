# Instalación de Java y Maven en macOS

## Objetivo

Configurar Java 17 y Apache Maven en macOS para poder compilar, probar y construir proyectos Java desde terminal o IntelliJ IDEA.

---

## 1. Instalar Homebrew

Homebrew es el gestor de paquetes más utilizado en macOS.

Comprobar si está instalado:

```bash
brew --version
```

Si no está instalado:

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

---

## 2. Instalar Java 17

Instalar OpenJDK 17:

```bash
brew install openjdk@17
```

---

## 3. Configurar JAVA_HOME

Comprobar instalación:

```bash
java -version
```

Obtener la ruta del JDK:

```bash
/usr/libexec/java_home -V
```

Añadir JAVA_HOME al shell.

Si se usa zsh:

```bash
nano ~/.zshrc
```

Añadir:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

Guardar y aplicar cambios:

```bash
source ~/.zshrc
```

---

## 4. Comprobar Java

```bash
java -version
```

```bash
javac -version
```

Resultado esperado:

```plaintext
openjdk version "17"
javac 17
```

---

## 5. Instalar Maven

Instalar Maven con Homebrew:

```bash
brew install maven
```

---

## 6. Comprobar Maven

```bash
mvn -v
```

Resultado esperado:

```plaintext
Apache Maven 3.x.x
Java version: 17
```

---

## 7. Comandos Maven útiles

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

Instalar el artefacto local:

```bash
mvn install
```

Limpiar archivos generados:

```bash
mvn clean
```

---

## 8. IntelliJ IDEA

IntelliJ detecta automáticamente proyectos Maven mediante:

```plaintext
pom.xml
```

También permite ejecutar Maven desde:

```plaintext
View > Tool Windows > Maven
```

---

## Nota

macOS suele requerir menos configuración manual que Windows porque Homebrew instala y configura automáticamente gran parte del entorno Java.
