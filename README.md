# RequestApi - AE-3. Examen Android


## Características

Para esta actividad se ha utilizado de base el material visto en clase (reutilizando algunos componentes) y se han realizado los siguientes ajustes en el código para mejorar su legibilidad y que sea más robusto:

- **Propagación de errores**: Los errores se detectan de forma temprana en el repositorio y se se propagan correctametne hasta las vistas. En caso de que haya algún error con la API, aparecerá un mensaje de error en la interfaz gráfica (por ejemplo, si se escribe mal la URL de la API).

- **Las corrutinas se manejan con ViewModelScope dentro del ViewModel**, por lo tanto, el CorooutineScope y withContext(Dispatchers) desaparecen del repositorio ya que ya no serán necesarios en adelante.

- **Utiilzamos MUtableStateFlow**. FRente al LiveData del ejercicio anterior (el de movies), optamos por utilizar en esta ocasión el MutableStateFlow, lo que  nos obliga a modificar los @Composables para utilizar el collectAsState y así estar atentos a cualquier cambio que se producza en los resultados del ViewModel.

- **La barra de búsqueda arroja resultados en tiempo real**. Ahora los resultados se filtran dentro de la APlicación. No es una solución óptima, pero visualmente da sensación de fluidez a la aplicación, lo que mejora la experiencia de usuario. 



## Arquitectura del Proyecto

El proyecto está estructurado utilizando la arquitectura MVVM(Modelo-Vista-VistaModelo) , lo cual facilita la separación de preocupaciones y mejora la testabilidad del código. 

Además, dentro del proyecto podemos encontrar la siguiente estructura de directorios:

- **data**: Incluye los modelos utilizados, tanto de respuesta de la API como los objetos que se esperan recibir.

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
	git clone https://github.com/fjzamora93/KotlinRequestToApiApp
```

2. **Abre el proyecto en Android Studio.**
3. **Sincroniza el proyecto con Gradle.**
4. **Ejecuta la aplicación en un emulador o dispositivo físico.**
5. **Asegura la conexión a internet (actualmente no funciona sin conexión)**
