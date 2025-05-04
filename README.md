# RetoDAM - PMDM

Para este proyecto se ha optado por una versión minimalista de los requisitos, aunque incluye las funcionalidades básicas:

- **Registro de usuarios**: los usuarios pueden registrar una nueva cuenta o pueden hacer login con una cuenta ya existente. En cualquiera de los dos casos, el usuario recibirá un token que se utilizará en adelante para realizar las peticiones.

- **Acceso a las vacantes disponibles**: en la segunda pantalla encontraremos un listado con todas las vacantes disponibles. Se incluye una barra de búsqueda para poder realizar un filtro básico. Al pulsar el icono "+", se podrá añadir un comentario (simulando lo que sería una postulación completa) y se enviará una request de método POST al backend para añadir una nueva solicitud a la vacante. Para un desarrollo completo, sería necesario introducir todos los campos del formulario, aunque este sirve de muestra para ver cómo se comporta.

- **SOlicitudes**: en esta pantalla el usuario puede ver todas las solicitudes asociadas a su cuenta. POdría mejorarse con la opción de editar dichas solicitudes o eliminarlas.


## Características


- **Propagación de errores**: Los errores se detectan de forma temprana en el repositorio y se se propagan correctametne hasta las vistas. En caso de que haya algún error con la API, aparecerá un mensaje de error en la interfaz gráfica (por ejemplo, si se escribe mal la URL de la API).

- **Las corrutinas se manejan con ViewModelScope dentro del ViewModel**, por lo tanto, el CorooutineScope y withContext(Dispatchers) desaparecen del repositorio ya que ya no serán necesarios en adelante.

- **Utiilzamos MUtableStateFlow**. FRente al LiveData del ejercicio anterior, optamos por utilizar en esta ocasión el MutableStateFlow, lo que  nos obliga a modificar los @Composables para utilizar el collectAsState y así estar atentos a cualquier cambio que se producza en los resultados del ViewModel.

- **La barra de búsqueda arroja resultados en tiempo real**. Ahora los resultados se filtran dentro de la APlicación. 

- **Permisos**: dentro del ANdroidManifest han quedado configurados los permisos para poder enviar y recibir tokens además de permitir la conexión a internet.



## Arquitectura del Proyecto

El proyecto está estructurado utilizando la arquitectura MVVM(Modelo-Vista-VistaModelo) , lo cual facilita la separación de preocupaciones y mejora la testabilidad del código. 

Además, dentro del proyecto podemos encontrar la siguiente estructura de directorios:

### **data** 

Incluye los modelos utilizados, tanto de respuesta de la API como los objetos que se esperan recibir. Para ello se utilizan data class básicos, sin establecer relaciones complejas ni relaciones, algo que idealmente podríamos hacer si quisiéramos hacer el almacenamiento interno en el dispositivo en una base de datos Sqlite a través de Room.

### **inyección de dependencias(di)**

Para mantener una estructura más limpia y clara dentro del proyecto aplicamos una inyección de dependencias con Hilt y dagger. Para ello se ha creado un módulo de inyección de dependencias. Idealmente, debería crearse un módulo para cada responsabilidad (uno para la inyeccion de repositorys, otro para servicios, etc). Sin embargo, por simplificar el código, todas las dependencias están en el mismo fichero tal que así:


```

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

```

### Retrofit

La configuración de retrofit está dentro del módulo de inyección de dependencias, y su uso se extrapola a las peticiones https dentro de los repositorios y el servicio.

```
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }


}

```

### REpository

Capa encargada de realizar las peticiones al servicio y manejar la lógica de los casos de error y éxito. Si, además, tuviésemos una lógica compleja, podríamos declarar una capa entre el REpository y los Viewmodels para manejar esa lógica adicional. NO es necesario utilizar dispatchers ni mantener operaciones asíncronas ya que estas se manejan desde el ViewMOdel, aún así, si quisiéramos gestionar el repository de manera asíncrona, podríamos hacerlo de esta manera:


```
class Repository @Inject constructor(
    private val apiService: ApiService,
    ) {


    fun fetchVacantesAsync(): Deferred<Result<List<Vacante>>> {
	    return CoroutineScope(Dispatchers.IO).async {
		try {
		    val response = apiService.getVacantes()
		    if (!response.isSuccessful) {
		        return@async Result.failure(Exception("Error en la respuesta del servidor: ${response.errorBody()?.string()}"))
		    }

		    val vacantes = response.body()
		    if (vacantes != null) {
		        Result.success(vacantes)
		    } else {
		        Result.failure(Exception("Respuesta exitosa pero cuerpo vacío."))
		    }
		} catch (e: Exception) {
		    Result.failure(Exception("Algo salió mal: ${e.message}, imposible conectar a ${AppStrings.BASE_URL}"))
		}
	    }
    }
}

```
Como comentábamos anteriormente, en nuestro caso hemos optado por otra solución que es una suspend fun y crear la corrutina dentro del viewmodel con viewMOdelScope.launch { // lógica del viewmodel }


### SErvicio

Esta es la capa que utilizará directamente a retrofit para lanzar peticiones https a la API. GRacias a la inyección de dependencias, neustra capa del servicio se queda completamente limpia:


```
interface ApiService {

    @GET("vacantes")
    suspend fun getVacantes(): Response<List<Vacante>>

    @GET("solicitudes/{id}")
    suspend fun getSolicitudes(@Path("id") userId: Int): Response<List<Solicitud>>

    @POST("solicitudes")
    suspend fun addSolicitud(@Body solicitud: Solicitud): Response<Solicitud>
}


```

### **viewmodels**: 
Actúa como capa intermediar entre los repositorios y la interfaz de usuaria, aplicando la lógica de negocio y funcionando como un controlador. En este caso, todas las operaciones asíncronas las gestionamos directamente en los métodos del viewmodel gracias al viewModelScope.launchedEffect{ .. }

Además, los estados los manejamos con un StateFlow, que es lo más apropiado para jetpackcompose (aunque igualmente podríamos usar LiveData, propio de los xml).


```
@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _vacantes = MutableStateFlow<List<Vacante>>(emptyList())
    val vacantes: MutableStateFlow<List<Vacante>> get() = _vacantes

    private val _error =MutableStateFlow<String?>(null)
    val error: MutableStateFlow<String?> get() = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading


    init {
        fetchVacantes()
    }

    fun fetchVacantes() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.fetchVacantes()
            result.onSuccess {
                _vacantes.value = it
                _error.value = null
            }
            result.onFailure {
                _error.value = it.message
            }
            _loading.value = false
        }
    }

}
```


### **navigation**: 
Creamos un sistema de rutas y navegación a través de un gráfico de navegación.

### **ui**: 
Todo lo relacionado con la interfaz de usuario (screens, componentes auxilares, tema y ViewModel).



## Requisitos

- Android Studio Bumblebee o superior
- JDK 17 o superior

## Instalación

1. **Clona este repositorio**:
```sh
	https://github.com/fjzamora93/RetoDamKotlinApp
```

2. **Abre el proyecto en Android Studio.**
3. **Sincroniza el proyecto con Gradle.**
4. **Ejecuta la aplicación en un emulador o dispositivo físico.**
5. **Asegura la conexión a internet (actualmente no funciona sin conexión)**
