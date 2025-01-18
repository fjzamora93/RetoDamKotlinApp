# MovieRequestApi - AE-2. Android avanzado

MovieRequestApi es una aplicación que forma parte de la asignatura de PMDM y que toma películas de la API de muestra http://www.omdbapi.com y permite guardar las películas seleccionadas en una base de datos SQLite local.

## Consideraciones sobre la API

La api no devuelve resultados que sean demasiado grandes (y tampoco acepta peticiones para devolver todo el contenido de la base de datos). En su lugar, es obligatorio especificar parámetros que devuelvan un máximo de 600 películas aproximadamente. Superado ese límite, devuelve una lista vacía.


## Características

- Conexión a API.
- Base de datos local (utilizando Room).
- Uso de jetpack-compose.
- Inyección de dependencias con Hilt y Dagger.
- Sistema de navegación.

## Arquitectura del Proyecto

El proyecto está estructurado utilizando la arquitectura MVVM(Modelo-Vista-VistaModelo) , lo cual facilita la separación de preocupaciones y mejora la testabilidad del código. 

Además, dentro del proyecto podemos encontrar la siguiente estructura de directorios:

- **data**: Incluye los servicios, modelos, modelos DAO y repositorios. Al hacer esta separación, nos aseguramos el principio de responsabilidad única de cada clase.
- **inyección de dependencias**: Para mantener una estructura más limpia y clara dentro del proyecto aplicamos una inyección de dependencias con Hilt y dagger.
- **navigation**: Creamos un sistema de rutas y navegación a través de un gráfico de navegación.
- **ui**: Todo lo relacionado con la interfaz de usuario (screens, componentes auxilares, tema y ViewModel).
- **viewmodels**: Actúa como capa intermediar entre los repositorios y la interfaz de usuaria, aplicando la lógica de negocio y funcionando como un controlador.


## Requisitos

- Android Studio Bumblebee o superior
- JDK 11 o superior

## Instalación

1. **Clona este repositorio**:
```sh
	https://github.com/fjzamora93/MovieRequestApi
```

2. **Abre el proyecto en Android Studio.**
3. **Sincroniza el proyecto con Gradle.**
4. **Ejecuta la aplicación en un emulador o dispositivo físico.**
5. **Asegura la conexión a internet (actualmente no funciona sin conexión)**
