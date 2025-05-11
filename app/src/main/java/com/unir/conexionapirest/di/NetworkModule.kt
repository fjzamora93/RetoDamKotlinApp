package com.unir.conexionapirest.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.roleapp.auth.security.AuthInterceptor
import com.unir.conexionapirest.data.service.ApiService
import com.unir.conexionapirest.data.repository.Repository
import com.unir.conexionapirest.ui.theme.AppStrings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**INYECCIÓN DE DEPENDENCIAS (DI).
 *
 * Esta clase nos va a dar una serie de métodos que nos van a permitir
 * gestionar las dependencias de la aplicación sin tener que estar instanciándolas
 * una y otra vez.
 *
 * */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://retodam.onrender.com/api/"

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }
    /** NYECCIÓN DE DEPENDENCIAS  DE RETROFIT + CONVERTIDOR JSON */

    // Incluimos el AUthInterceptor (que está dentro del package com.auth.di.AuthModule) para que pueda añadir el token a cada petición
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
