# RetoDAM - PMDM

Para este proyecto se ha optado por una versión minimalista de los requisitos, aunque incluye las funcionalidades básicas:

- **Registro de usuarios**: los usuarios pueden registrar una nueva cuenta o pueden hacer login con una cuenta ya existente. En cualquiera de los dos casos, el usuario recibirá un token que se utilizará en adelante para realizar las peticiones.

- **Acceso a las vacantes disponibles**: en la segunda pantalla encontraremos un listado con todas las vacantes disponibles. Se incluye una barra de búsqueda para poder realizar un filtro básico. Al pulsar el icono "+", se podrá añadir un comentario (simulando lo que sería una postulación completa) y se enviará una request de método POST al backend para añadir una nueva solicitud a la vacante. Para un desarrollo completo, sería necesario introducir todos los campos del formulario, aunque este sirve de muestra para ver cómo se comporta.

-**SOlicitudes**: en esta pantalla el usuario puede ver todas las solicitudes asociadas a su cuenta. POdría mejorarse con la opción de editar dichas solicitudes o eliminarlas.


## Características


- **Propagación de errores**: Los errores se detectan de forma temprana en el repositorio y se se propagan correctametne hasta las vistas. En caso de que haya algún error con la API, aparecerá un mensaje de error en la interfaz gráfica (por ejemplo, si se escribe mal la URL de la API).

- **Las corrutinas se manejan con ViewModelScope dentro del ViewModel**, por lo tanto, el CorooutineScope y withContext(Dispatchers) desaparecen del repositorio ya que ya no serán necesarios en adelante.

- **Utiilzamos MUtableStateFlow**. FRente al LiveData del ejercicio anterior, optamos por utilizar en esta ocasión el MutableStateFlow, lo que  nos obliga a modificar los @Composables para utilizar el collectAsState y así estar atentos a cualquier cambio que se producza en los resultados del ViewModel.

- **La barra de búsqueda arroja resultados en tiempo real**. Ahora los resultados se filtran dentro de la APlicación. 



## Arquitectura del Proyecto

El proyecto está estructurado utilizando la arquitectura MVVM(Modelo-Vista-VistaModelo) , lo cual facilita la separación de preocupaciones y mejora la testabilidad del código. 

Además, dentro del proyecto podemos encontrar la siguiente estructura de directorios:

- **data**: Incluye los modelos utilizados, tanto de respuesta de la API como los objetos que se esperan recibir.

- **inyección de dependencias(di)**: Para mantener una estructura más limpia y clara dentro del proyecto aplicamos una inyección de dependencias con Hilt y dagger.

- **navigation**: Creamos un sistema de rutas y navegación a través de un gráfico de navegación.

- **ui**: Todo lo relacionado con la interfaz de usuario (screens, componentes auxilares, tema y ViewModel).

- **viewmodels**: Actúa como capa intermediar entre los repositorios y la interfaz de usuaria, aplicando la lógica de negocio y funcionando como un controlador.


## Requisitos

- Android Studio Bumblebee o superior
- JDK 11 o superior

## Instalación

1. **Clona este repositorio**:
```sh
	https://github.com/fjzamora93/RetoDamKotlinApp
```

2. **Abre el proyecto en Android Studio.**
3. **Sincroniza el proyecto con Gradle.**
4. **Ejecuta la aplicación en un emulador o dispositivo físico.**
5. **Asegura la conexión a internet (actualmente no funciona sin conexión)**
