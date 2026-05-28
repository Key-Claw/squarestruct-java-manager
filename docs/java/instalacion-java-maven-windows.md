# Instalación de Java y Maven en Windows

## Objetivo

Configurar Java 17 y Apache Maven en Windows para poder compilar, probar y construir el proyecto desde PowerShell o desde IntelliJ IDEA.

---

## 1. Comprobar si Maven está instalado

En PowerShell:

```powershell
mvn -v
```

Si aparece este error:

```plaintext
mvn no se reconoce como nombre de un cmdlet
```

significa que Maven no está instalado globalmente o no está añadido al `Path`.

---

## 2. Descargar Apache Maven

Descargar Maven desde la web oficial:

[Apache Maven Downloads](https://maven.apache.org/download.cgi)

Elegir la opción:

```plaintext
Binary zip archive
```

---

## 3. Extraer Maven

Crear la carpeta:

```plaintext
C:\tools\maven
```

Extraer el contenido del ZIP ahí.

La estructura debe quedar así:

```plaintext
C:\tools\maven
├── bin
├── boot
├── conf
├── lib
├── LICENSE
├── NOTICE
└── README.txt
```

---

## 4. Configurar MAVEN_HOME

Abrir:

```plaintext
Editar las variables de entorno del sistema
```

Entrar en:

```plaintext
Variables de entorno
```

Crear una nueva variable del sistema:

```plaintext
MAVEN_HOME = C:\tools\maven
```

---

## 5. Añadir Maven al Path

En variables del sistema, editar:

```plaintext
Path
```

Añadir:

```plaintext
C:\tools\maven\bin
```

---

## 6. Configurar JAVA_HOME

En IntelliJ IDEA se puede consultar la ruta del JDK desde:

```plaintext
File > Project Structure > SDKs
```

En este proyecto se está usando:

```plaintext
C:\Users\Socrates\.jdks\ms-17.0.18
```

Crear una nueva variable del sistema:

```plaintext
JAVA_HOME = C:\Users\Socrates\.jdks\ms-17.0.18
```

---

## 7. Añadir Java al Path

En variables del sistema, editar:

```plaintext
Path
```

Añadir:

```plaintext
%JAVA_HOME%\bin
```

---

## 8. Reiniciar PowerShell

Cerrar todas las terminales abiertas y volver a abrir PowerShell.

---

## 9. Comprobar Java

```powershell
java -version
```

Resultado esperado:

```plaintext
openjdk version "17.0.18"
```

Comprobar también el compilador:

```powershell
javac -version
```

Resultado esperado:

```plaintext
javac 17.0.18
```

---

## 10. Comprobar Maven

```powershell
mvn -v
```

Resultado esperado:

```plaintext
Apache Maven 3.9.16
Maven home: C:\tools\maven
Java version: 17.0.18
```

---

## 11. Comandos Maven útiles

Compilar el proyecto:

```powershell
mvn compile
```

Ejecutar pruebas:

```powershell
mvn test
```

Generar el artefacto:

```powershell
mvn package
```

Instalar el artefacto en el repositorio local:

```powershell
mvn install
```

Limpiar archivos generados:

```powershell
mvn clean
```

---

## Nota

Aunque IntelliJ IDEA puede ejecutar Maven internamente desde el panel Maven, instalar Maven globalmente permite usar comandos desde PowerShell, Git Bash, Docker, CI/CD y futuros despliegues.
