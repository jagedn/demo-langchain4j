Readme.md para el proyecto de demostración de langchain4j:
======================================================

### Introducción

Este es un proyecto de demostración para mostrar el uso de langchain4j, una biblioteca de Java que permite interactuar con AI.

### Configuración del entorno de desarrollo

Para configurar el entorno de desarrollo, necesitarás tener instalado Java SDK versión 17

### Compilación y ejecución del proyecto

Para compilar el proyecto, ejecuta el siguiente comando en la carpeta raíz del proyecto:

```shell script
./gradlew build
```

### Ejecucion

Esta demo asume que tienes una instancia de Ollama ejecutandose en tu local y el modelo `mistral` descargado

Para ejecutar el proyecto, ejecuta el siguiente comando en la carpeta raíz del proyecto:
```shell script
./gradlew run
```

Abre un navegador a http://localhost:8080/swagger-ui